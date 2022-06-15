package com.pea.service.masterservice.service;


import com.google.common.annotations.VisibleForTesting;
import com.pea.service.masterservice.dto.PageDto;
import com.pea.service.masterservice.dto.TblMdUsersDto;
import com.pea.service.masterservice.entity.TblMdUsersEntity;
import com.pea.service.masterservice.exception.BusinessServiceException;
import com.pea.service.masterservice.repository.TblMdUsersJdbcRepository;
import com.pea.service.masterservice.repository.TblMdUsersRepository;
import com.pea.service.masterservice.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Slf4j
public class MdUsersService {
    private final ModelMapper modelMapper;
    private final TblMdUsersRepository tblMdUsersRepository;
    private final TblMdUsersJdbcRepository tblMdUsersJdbcRepository;

    @Autowired
    public MdUsersService(ModelMapper modelMapper,
                          TblMdUsersRepository tblMdUsersRepository,
                          TblMdUsersJdbcRepository tblMdUsersJdbcRepository){
        this.modelMapper = modelMapper;
        this.tblMdUsersRepository = tblMdUsersRepository;
        this.tblMdUsersJdbcRepository = tblMdUsersJdbcRepository;
    }

    public TblMdUsersDto getById(int id) throws BusinessServiceException {
        try {
            MdUsersService.log.info("Begin MdUsersService.getById()...");
            Optional<TblMdUsersEntity> byId = tblMdUsersRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), TblMdUsersDto.class) : null;
        } catch (Exception ex) {
            MdUsersService.log.error(
                    "ERROR MdUsersService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            MdUsersService.log.info("End MdUsersService.getById()...");
        }
    }

    public Integer checkDuplicateCode(TblMdUsersDto dto) throws BusinessServiceException {
        try {
            log.info("Begin MdUsersService.checkDuplicateAbbr()...");
            List<TblMdUsersEntity> rs =
                    tblMdUsersRepository.findAllByUserLastNameAndStatus(dto.getUserLastName(),"A");
            if (rs != null && rs.size() > 0) {
                return 1;
            }
            return 0;
        } catch (Exception ex) {
            log.error("ERROR MdUsersService.checkDuplicateAbbr()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End MdUsersService.checkDuplicateAbbr()...");
        }
    }

    public TblMdUsersEntity save(TblMdUsersDto dto) throws BusinessServiceException {
        log.info("Begin MdUsersService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TblMdUsersEntity entity = modelMapper.map(dto, TblMdUsersEntity.class);
        log.info("End MdUsersService.save()...");
        return tblMdUsersRepository.save(entity);
    }

    public Page<TblMdUsersDto> findByCri(TblMdUsersDto dto) throws BusinessServiceException {
        try {
            log.info("Begin MdUsersService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            log.info("pageRequest Complete...");
            Page<TblMdUsersEntity> all =
                    tblMdUsersRepository.findAll(getSpecification(dto), pageRequest);
            log.info("all Complete...");
            Page<TblMdUsersDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR MdUsersService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End MdUsersService.findByCri()...");
        }
    }

    @VisibleForTesting
    protected Specification<TblMdUsersEntity> getSpecification(TblMdUsersDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getUserId() != null){
                predicates.add(cb.equal(root.get("userId"), filter.getUserId()));
            }

            if (filter.getUserUsername() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("username")), "%" + filter.getUserUsername().toLowerCase() + "%"));
            }

            if (filter.getUserPassword() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("password")), "%" + filter.getUserPassword().toLowerCase() + "%"));
            }

            if (filter.getUserFirstName() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("firstname")), "%" + filter.getUserFirstName().toLowerCase() + "%"));
            }
            if (filter.getUserLastName() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("lastname")), "%" + filter.getUserLastName().toLowerCase() + "%"));
            }
            if (filter.getUserTitle() != null){
                predicates.add(cb.equal(root.get("userTitle"), filter.getStatus()));
            }
            if (filter.getUserEmail() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("email")), "%" + filter.getUserEmail().toLowerCase() + "%"));
            }
            if (filter.getUserTel() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("userTel")), "%" + filter.getUserTel().toLowerCase() + "%"));
            }
            if (filter.getUserAddress() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("address")), "%" + filter.getUserAddress().toLowerCase() + "%"));
            }
            if (filter.getUserDepartment() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("userDepartment")), "%" + filter.getUserDepartment().toLowerCase() + "%"));
            }
            if (filter.getUserType() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("userType")), "%" + filter.getUserType().toLowerCase() + "%"));
            }
            if (filter.getStatus() != null){
                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
            }
            if (filter.getCreateBy() != null){
                predicates.add(cb.equal(root.get("createBy"), filter.getCreateBy()));
            }
            if (filter.getUpdateBy() != null){
                predicates.add(cb.equal(root.get("updateBy"), filter.getUpdateBy()));
            }
            if (filter.getUserIdcard() != null){
                predicates.add(cb.equal(root.get("userIdCard"), filter.getUserIdcard()));
            }
            if (filter.getUserDn() != null){
                predicates.add(cb.equal(root.get("userDn"), filter.getUserDn()));
            }
            if (filter.getLoginFail() != null){
                predicates.add(cb.equal(root.get("loginFail"), filter.getLoginFail()));
            }
            if (filter.getTitleNameId() != null){
                predicates.add(cb.equal(root.get("titleNameId"), filter.getTitleNameId()));
            }
            if (filter.getDepartmentId() != null){
                predicates.add(cb.equal(root.get("departmentId"), filter.getDepartmentId()));
            }
            if (filter.getPositionId() != null){
                predicates.add(cb.equal(root.get("positionId"), filter.getPositionId()));
            }
            if (filter.getFullSearch() != null) {
                Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                predicates = new ArrayList<>();
                predicates.add(
                        cb.like(
                                cb.lower(root.get("username")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("userDepartment")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected Page<TblMdUsersDto> convertPageEntityToPageDto(Page<TblMdUsersEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<TblMdUsersDto> map =
                source.map(
                        new Function<TblMdUsersEntity, TblMdUsersDto>() {
                            @Override
                            public TblMdUsersDto apply(TblMdUsersEntity entity) {
                                TblMdUsersDto dto = modelMapper.map(entity, TblMdUsersDto.class);
                                return dto;
                            }
                        });
        return map;
    }

    public Page<TblMdUsersDto> findAllPage(TblMdUsersDto param){
        List<TblMdUsersDto> result = tblMdUsersJdbcRepository.findAll(param);
        PageDto newPage = createPage(param.getPage(),param.getPerPage(),result.size());
        return new PageImpl<>(result.subList(newPage.getStart(), newPage.getEnd()), newPage.getPaging(), result.size());
    }

    public PageDto createPage(Integer page,Integer perPage,Integer size){
        PageDto newPage = new PageDto();
        Pageable paging = PageRequest.of(page-1, perPage);
        int start = Math.min((int)paging.getOffset(), size);
        int end = Math.min((start + paging.getPageSize()), size);
        newPage.setPaging(paging);
        newPage.setStart(start);
        newPage.setEnd(end);
        return newPage;
    }


}
