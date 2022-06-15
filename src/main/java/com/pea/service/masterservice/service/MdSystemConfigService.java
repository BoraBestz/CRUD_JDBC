package com.pea.service.masterservice.service;

import com.google.common.annotations.VisibleForTesting;
import com.pea.service.masterservice.dto.PageDto;
import com.pea.service.masterservice.dto.TblMdSystemConfigDto;
import com.pea.service.masterservice.entity.TblMdSystemConfigEntity;
import com.pea.service.masterservice.exception.BusinessServiceException;
import com.pea.service.masterservice.repository.TblMdSystemConfigJdbcRepository;
import com.pea.service.masterservice.repository.TblMdSystemConfigRepository;
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
public class MdSystemConfigService {
    private final ModelMapper modelMapper;
    private final EntityManager em;
    private final TblMdSystemConfigRepository tblMdSystemConfigRepository;
    private final TblMdSystemConfigJdbcRepository tblMdSystemConfigJdbcRepository;

    @Autowired
    public MdSystemConfigService(ModelMapper modelMapper,
                                 TblMdSystemConfigRepository tblMdSystemConfigRepository,
                                 TblMdSystemConfigJdbcRepository tblMdSystemConfigJdbcRepository,
                                 EntityManager em
    ) {
        this.modelMapper = modelMapper;
        this.em = em;
        this.tblMdSystemConfigRepository = tblMdSystemConfigRepository;
        this.tblMdSystemConfigJdbcRepository = tblMdSystemConfigJdbcRepository;

    }


    public Page<TblMdSystemConfigEntity> findAll(TblMdSystemConfigDto criDto)
            throws BusinessServiceException {
        try {
            MdSystemConfigService.log.info("Begin MdSystemConfigService.findAll()...");

            return tblMdSystemConfigRepository.findAll((Pageable) criDto);
        } catch (Exception ex) {
            MdSystemConfigService.log.error(
                    "ERROR SystemConfigService.findAll()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            MdSystemConfigService.log.info("End MdSystemConfigService.findAll()...");
        }
    }

    public TblMdSystemConfigDto getById(int id) throws BusinessServiceException {
        try {
            MdSystemConfigService.log.info("Begin MdSystemConfigService.getById()...");
            Optional<TblMdSystemConfigEntity> byId = tblMdSystemConfigRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), TblMdSystemConfigDto.class) : null;
        } catch (Exception ex) {
            MdSystemConfigService.log.error(
                    "ERROR SystemConfigService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            MdSystemConfigService.log.info("End MdSystemConfigService.getById()...");
        }
    }


    public Integer checkDuplicateCode(TblMdSystemConfigDto dto) throws BusinessServiceException {
        try {
            log.info("Begin MdSystemConfigService.checkDuplicateAbbr()...");
            List<TblMdSystemConfigEntity> rs =
                    tblMdSystemConfigRepository.findAllByConfigCodeAndStatus(dto.getConfigCode(),"A");
            if (rs != null && rs.size() > 0) {
                return 1;
            }
            return 0;
        } catch (Exception ex) {
            log.error("ERROR MdSystemConfigService.checkDuplicateAbbr()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End MdSystemConfigService.checkDuplicateAbbr()...");
        }
    }

    public TblMdSystemConfigEntity save(TblMdSystemConfigDto dto) throws BusinessServiceException {
        log.info("Begin TblMdProductService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TblMdSystemConfigEntity entity = modelMapper.map(dto, TblMdSystemConfigEntity.class);
        log.info("End TblMdProductService.save()...");
        return tblMdSystemConfigRepository.save(entity);
    }

    public Page<TblMdSystemConfigDto> findByCri(TblMdSystemConfigDto dto) throws BusinessServiceException {
        try {
            log.info("Begin MdSystemConfigService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            log.info("pageRequest Complete...");
            Page<TblMdSystemConfigEntity> all =
                    tblMdSystemConfigRepository.findAll(getSpecification(dto), pageRequest);
            log.info("all Complete...");
            Page<TblMdSystemConfigDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR MdSystemConfigService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End MdSystemConfigService.findByCri()...");
        }
    }

    @VisibleForTesting
    protected Specification<TblMdSystemConfigEntity> getSpecification(TblMdSystemConfigDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getConfigId() != null){
                predicates.add(cb.equal(root.get("configId"), filter.getConfigId()));
            }

            if (filter.getConfigCode() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("configCode")), "%" + filter.getConfigCode().toLowerCase() + "%"));
            }

            if (filter.getDescription() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("description")), "%" + filter.getDescription().toLowerCase() + "%"));
            }

            if (filter.getCanEdit() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("canEdit")), "%" + filter.getCanEdit().toLowerCase() + "%"));
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
                                cb.lower(root.get("configCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("canEdit")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected Page<TblMdSystemConfigDto> convertPageEntityToPageDto(Page<TblMdSystemConfigEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<TblMdSystemConfigDto> map =
                source.map(
                        new Function<TblMdSystemConfigEntity, TblMdSystemConfigDto>() {
                            @Override
                            public TblMdSystemConfigDto apply(TblMdSystemConfigEntity entity) {
                                TblMdSystemConfigDto dto = modelMapper.map(entity, TblMdSystemConfigDto.class);
                                return dto;
                            }
                        });
        return map;
    }


    public Page<TblMdSystemConfigDto> findAllPage(TblMdSystemConfigDto param){
        List<TblMdSystemConfigDto> result = tblMdSystemConfigJdbcRepository.findAll(param);
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




