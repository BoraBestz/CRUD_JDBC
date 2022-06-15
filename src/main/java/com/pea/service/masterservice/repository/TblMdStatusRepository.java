package com.pea.service.masterservice.repository;

import com.pea.service.masterservice.entity.TblMdStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TblMdStatusRepository extends JpaRepository<TblMdStatusEntity, Integer>,
        JpaSpecificationExecutor<TblMdStatusEntity> {
}
