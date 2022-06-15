package com.pea.service.masterservice.service;

import com.google.common.annotations.VisibleForTesting;
import com.pea.service.masterservice.dto.PageDto;
import com.pea.service.masterservice.dto.TblMdUsersOrgchartDto;
import com.pea.service.masterservice.entity.TblMdUsersOrgchartEntity;
import com.pea.service.masterservice.exception.BusinessServiceException;
import com.pea.service.masterservice.repository.TblMdUsersOrgchartJdbcRepository;
import com.pea.service.masterservice.repository.TblMdUsersOrgchartRepository;
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
public class MdUsersOrgchartService {
    private final ModelMapper modelMapper;
    private final TblMdUsersOrgchartRepository tblMdUsersOrgchartRepository;
    private final TblMdUsersOrgchartJdbcRepository tblMdUsersOrgchartJdbcRepository;

    @Autowired
    public MdUsersOrgchartService(ModelMapper modelMapper,
                                  TblMdUsersOrgchartRepository tblMdUsersOrgchartRepository,
                                  TblMdUsersOrgchartJdbcRepository tblMdUsersOrgchartJdbcRepository) {
        this.modelMapper = modelMapper;
        this.tblMdUsersOrgchartRepository = tblMdUsersOrgchartRepository;
        this.tblMdUsersOrgchartJdbcRepository = tblMdUsersOrgchartJdbcRepository;
    }

    public TblMdUsersOrgchartDto getById(int id) throws BusinessServiceException {
        try {
            MdUsersOrgchartService.log.info("Begin MdUsersOrgchartService.getById()...");
            Optional<TblMdUsersOrgchartEntity> byId = tblMdUsersOrgchartRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), TblMdUsersOrgchartDto.class) : null;
        } catch (Exception ex) {
            MdUsersOrgchartService.log.error(
                    "ERROR MdUsersOrgchartService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            MdUsersOrgchartService.log.info("End MdUsersOrgchartService.getById()...");
        }
    }

    public Integer checkDuplicateCode(TblMdUsersOrgchartDto dto) throws BusinessServiceException {
        try {
            log.info("Begin MdUsersOrgchartService.checkDuplicateAbbr()...");
            List<TblMdUsersOrgchartEntity> rs =
                    tblMdUsersOrgchartRepository.findAllByOrgchartCodeAndStatus(dto.getOrgchartCode(), "A");
            if (rs != null && rs.size() > 0) {
                return 1;
            }
            return 0;
        } catch (Exception ex) {
            log.error("ERROR MdUsersOrgchartService.checkDuplicateAbbr()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End MdUsersOrgchartService.checkDuplicateAbbr()...");
        }
    }

    public TblMdUsersOrgchartEntity save(TblMdUsersOrgchartDto dto) throws BusinessServiceException {
        log.info("Begin MdUsersOrgchartService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TblMdUsersOrgchartEntity entity = modelMapper.map(dto, TblMdUsersOrgchartEntity.class);
        log.info("End MdUsersOrgchartService.save()...");
        return tblMdUsersOrgchartRepository.save(entity);
    }

    public Page<TblMdUsersOrgchartDto> findByCri(TblMdUsersOrgchartDto dto) throws BusinessServiceException {
        try {
            log.info("Begin MdUsersOrgchartService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            log.info("pageRequest Complete...");
            Page<TblMdUsersOrgchartEntity> all =
                    tblMdUsersOrgchartRepository.findAll(getSpecification(dto), pageRequest);
            log.info("all Complete...");
            Page<TblMdUsersOrgchartDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR MdUsersOrgchartService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End MdUsersOrgchartService.findByCri()...");
        }
    }

    @VisibleForTesting
    protected Specification<TblMdUsersOrgchartEntity> getSpecification(TblMdUsersOrgchartDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getOrgchartId() != null) {
                predicates.add(cb.equal(root.get("orgchartId"), filter.getOrgchartId()));
            }

            if (filter.getParentId() != null) {
                predicates.add(cb.equal(root.get("parentId"), filter.getParentId()));
            }

            if (filter.getOrgchartCode() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("orgchartCode")), "%" + filter.getOrgchartCode().toLowerCase() + "%"));
            }

            if (filter.getOrgchartName() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("orgchartName")), "%" + filter.getOrgchartName().toLowerCase() + "%"));
            }

            if (filter.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
            }
            if (filter.getCreateBy() != null) {
                predicates.add(cb.equal(root.get("createBy"), filter.getCreateBy()));
            }
            if (filter.getUpdateBy() != null) {
                predicates.add(cb.equal(root.get("updateBy"), filter.getUpdateBy()));
            }

            if (filter.getFullSearch() != null) {
                Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                predicates = new ArrayList<>();
                predicates.add(
                        cb.like(
                                cb.lower(root.get("orgchartCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("orgchartName")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected Page<TblMdUsersOrgchartDto> convertPageEntityToPageDto(Page<TblMdUsersOrgchartEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<TblMdUsersOrgchartDto> map =
                source.map(
                        new Function<TblMdUsersOrgchartEntity, TblMdUsersOrgchartDto>() {
                            @Override
                            public TblMdUsersOrgchartDto apply(TblMdUsersOrgchartEntity entity) {
                                TblMdUsersOrgchartDto dto = modelMapper.map(entity, TblMdUsersOrgchartDto.class);
                                return dto;
                            }
                        });
        return map;
    }

    public Page<TblMdUsersOrgchartDto> findAllPage(TblMdUsersOrgchartDto param) {
        List<TblMdUsersOrgchartDto> result = tblMdUsersOrgchartJdbcRepository.findAll(param);
        PageDto newPage = createPage(param.getPage(), param.getPerPage(), result.size());
        return new PageImpl<>(result.subList(newPage.getStart(), newPage.getEnd()), newPage.getPaging(), result.size());
    }

    public PageDto createPage(Integer page, Integer perPage, Integer size) {
        PageDto newPage = new PageDto();
        Pageable paging = PageRequest.of(page - 1, perPage);
        int start = Math.min((int) paging.getOffset(), size);
        int end = Math.min((start + paging.getPageSize()), size);
        newPage.setPaging(paging);
        newPage.setStart(start);
        newPage.setEnd(end);
        return newPage;
    }
}

