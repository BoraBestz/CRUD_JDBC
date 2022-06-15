package com.pea.service.masterservice.repository;

import com.pea.service.masterservice.entity.TblMdSystemConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TblMdSystemConfigRepository extends JpaRepository<TblMdSystemConfigEntity, Integer>,
        JpaSpecificationExecutor<TblMdSystemConfigEntity> {
    List<TblMdSystemConfigEntity> findAllByConfigCodeAndStatus(String code,String status);

}
