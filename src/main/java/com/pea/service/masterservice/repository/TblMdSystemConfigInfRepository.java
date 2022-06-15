package com.pea.service.masterservice.repository;


import com.pea.service.masterservice.entity.TblMdSystemConfigInfEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface TblMdSystemConfigInfRepository extends JpaRepository<TblMdSystemConfigInfEntity, Integer>,
        JpaSpecificationExecutor<TblMdSystemConfigInfEntity> {
    List<TblMdSystemConfigInfEntity> findAll(
            Specification<TblMdSystemConfigInfEntity> specification);
    List<TblMdSystemConfigInfEntity> findAllByConfigInfCodeAndStatus(String code,String status);
}
