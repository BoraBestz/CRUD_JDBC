package com.pea.service.masterservice.service;

import com.google.common.annotations.VisibleForTesting;
import com.pea.service.masterservice.dto.PageDto;

import com.pea.service.masterservice.dto.TblMdSystemConfigDto;
import com.pea.service.masterservice.dto.TblMdSystemConfigInfDto;
import com.pea.service.masterservice.entity.TblMdSystemConfigInfEntity;
import com.pea.service.masterservice.exception.BusinessServiceException;
import com.pea.service.masterservice.repository.TblMdSystemConfigInfJdbcRepository;
import com.pea.service.masterservice.repository.TblMdSystemConfigInfRepository;

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

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Slf4j
public class MdSystemConfigInfService {
    private final ModelMapper modelMapper;
    private final EntityManager em;
    private final TblMdSystemConfigInfRepository tblMdSystemConfigInfRepository;
    private final TblMdSystemConfigInfJdbcRepository tblMdSystemConfigInfJdbcRepository;

    @Autowired
    public MdSystemConfigInfService(ModelMapper modelMapper,
                                 TblMdSystemConfigInfRepository tblMdSystemConfigInfRepository,
                                 TblMdSystemConfigInfJdbcRepository tblMdSystemConfigInfJdbcRepository,
                                 EntityManager em
    ) {
        this.modelMapper = modelMapper;
        this.em = em;
        this.tblMdSystemConfigInfRepository = tblMdSystemConfigInfRepository;
        this.tblMdSystemConfigInfJdbcRepository = tblMdSystemConfigInfJdbcRepository;

    }


    public Page<TblMdSystemConfigInfEntity> findAll(TblMdSystemConfigInfDto criDto)
            throws BusinessServiceException {
        try {
            MdSystemConfigInfService.log.info("Begin MdSystemConfigService.findAll()...");

            return tblMdSystemConfigInfRepository.findAll((Pageable) criDto);
        } catch (Exception ex) {
            MdSystemConfigInfService.log.error(
                    "ERROR SystemConfigService.findAll()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            MdSystemConfigInfService.log.info("End MdSystemConfigService.findAll()...");
        }
    }

    public TblMdSystemConfigInfDto getById(int id) throws BusinessServiceException {
        try {
            MdSystemConfigInfService.log.info("Begin MdSystemConfigInfService.getById()...");
            Optional<TblMdSystemConfigInfEntity> byId = tblMdSystemConfigInfRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), TblMdSystemConfigInfDto.class) : null;
        } catch (Exception ex) {
            MdSystemConfigInfService.log.error(
                    "ERROR MdSystemConfigInfService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            MdSystemConfigInfService.log.info("End MdSystemConfigInfService.getById()...");
        }
    }

//    public void add(TblMdSystemConfigInfDto dto) throws BusinessServiceException {
//        TblMdSystemConfigInfEntity entity;
//        try {
//            MdSystemConfigInfService.log.info("Begin MdSystemConfigService.add()...");
//            entity = modelMapper.map(dto, TblMdSystemConfigInfEntity.class);
//            entity.setcreateDt(new Timestamp(System.currentTimeMillis()));
//            tblMdSystemConfigInfRepository.save(entity);
//        } catch (Exception ex) {
//            MdSystemConfigInfService.log.error("ERROR MdSystemConfigService.add()...", ex.fillInStackTrace());
//            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
//        } finally {
//            MdSystemConfigInfService.log.info("End MdSystemConfigService.add()...");
//        }
//    }

    public Integer checkDuplicateCode(TblMdSystemConfigInfDto dto) throws BusinessServiceException {
        try {
            log.info("Begin MdSystemConfigInfService.checkDuplicateAbbr()...");
            List<TblMdSystemConfigInfEntity> rs =
                    tblMdSystemConfigInfRepository.findAllByConfigInfCodeAndStatus(dto.getConfigInfCode(),"A");
            if (rs != null && rs.size() > 0) {
                return 1;
            }
            return 0;
        } catch (Exception ex) {
            log.error("ERROR MdSystemConfigInfService.checkDuplicateAbbr()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End MdSystemConfigInfService.checkDuplicateAbbr()...");
        }
    }

    public TblMdSystemConfigInfEntity save(TblMdSystemConfigInfDto dto) throws BusinessServiceException {
        log.info("Begin MdSystemConfigInfService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TblMdSystemConfigInfEntity entity = modelMapper.map(dto, TblMdSystemConfigInfEntity.class);
        log.info("End MdSystemConfigInfService.save()...");
        return tblMdSystemConfigInfRepository.save(entity);
    }

    public Page<TblMdSystemConfigInfDto> findByCri(TblMdSystemConfigInfDto dto) throws BusinessServiceException {
        try {
            log.info("Begin MdSystemConfigInfService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            log.info("pageRequest Complete...");
            Page<TblMdSystemConfigInfEntity> all =
                    tblMdSystemConfigInfRepository.findAll(getSpecification(dto), pageRequest);
            log.info("all Complete...");
            Page<TblMdSystemConfigInfDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR MdSystemConfigInfService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End MdSystemConfigInfService.findByCri()...");
        }
    }

    @VisibleForTesting
    protected Specification<TblMdSystemConfigInfEntity> getSpecification(TblMdSystemConfigInfDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getConfigInfId() != null){
                predicates.add(cb.equal(root.get("configInfId"), filter.getConfigInfId()));
            }

            if (filter.getConfigId() != null){
                predicates.add(cb.equal(root.get("configId"), filter.getConfigId()));
            }

            if (filter.getConfigInfCode() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("configInfCode")), "%" + filter.getConfigInfCode().toLowerCase() + "%"));
            }

            if (filter.getDescription() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("description")), "%" + filter.getDescription().toLowerCase() + "%"));
            }

            if (filter.getReference1() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("reference1")), "%" + filter.getReference1().toLowerCase() + "%"));
            }

            if (filter.getReference2() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("reference2")), "%" + filter.getReference2().toLowerCase() + "%"));
            }

            if (filter.getReference3() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("reference3")), "%" + filter.getReference3().toLowerCase() + "%"));
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


            if (filter.getFullSearch() != null) {
                Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                predicates = new ArrayList<>();
                predicates.add(
                        cb.like(
                                cb.lower(root.get("configInfCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("status")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected Page<TblMdSystemConfigInfDto> convertPageEntityToPageDto(Page<TblMdSystemConfigInfEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<TblMdSystemConfigInfDto> map =
                source.map(
                        new Function<TblMdSystemConfigInfEntity, TblMdSystemConfigInfDto>() {
                            @Override
                            public TblMdSystemConfigInfDto apply(TblMdSystemConfigInfEntity entity) {
                                TblMdSystemConfigInfDto dto = modelMapper.map(entity, TblMdSystemConfigInfDto.class);
                                return dto;
                            }
                        });
        return map;
    }

    public Page<TblMdSystemConfigInfDto> findAllPage(TblMdSystemConfigInfDto param){
        List<TblMdSystemConfigInfDto> result = tblMdSystemConfigInfJdbcRepository.findAll(param);
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