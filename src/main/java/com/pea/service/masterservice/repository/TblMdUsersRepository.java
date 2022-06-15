package com.pea.service.masterservice.repository;

import com.pea.service.masterservice.entity.TblMdUsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TblMdUsersRepository extends JpaRepository<TblMdUsersEntity, Integer>,
        JpaSpecificationExecutor<TblMdUsersEntity> {
    List<TblMdUsersEntity> findAllByUserLastNameAndStatus(String lastName, String status);

}
