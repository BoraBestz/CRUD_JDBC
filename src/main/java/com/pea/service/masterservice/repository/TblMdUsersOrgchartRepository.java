package com.pea.service.masterservice.repository;


import com.pea.service.masterservice.entity.TblMdUsersOrgchartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TblMdUsersOrgchartRepository extends JpaRepository<TblMdUsersOrgchartEntity, Integer>,
        JpaSpecificationExecutor<TblMdUsersOrgchartEntity> {
    List<TblMdUsersOrgchartEntity> findAllByOrgchartCodeAndStatus(String code, String status);

}
