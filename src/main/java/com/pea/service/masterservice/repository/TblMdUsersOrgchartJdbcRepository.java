package com.pea.service.masterservice.repository;

import com.pea.service.masterservice.dto.TblMdSystemConfigDto;
import com.pea.service.masterservice.dto.TblMdUsersOrgchartDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class TblMdUsersOrgchartJdbcRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public TblMdUsersOrgchartJdbcRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<TblMdUsersOrgchartDto> findAll(TblMdUsersOrgchartDto pageDto){
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        StringBuilder query = new StringBuilder();
        query.append(" SELECT * FROM peaapp.TBL_MD_USERS_ORGCHART orgchart ");
        query.append(" WHERE ");
        query.append(" orgchart.STATUS = 'A' ");
        if (pageDto.getOrgchartId() != null){
            query.append("orgchart.ORGCHART_ID = :id ");
            parameters.addValue("id",pageDto.getOrgchartId());
        }
        if (pageDto.getParentId() != null){
            query.append(" And orgchart.PARENT_ID  = :parentId ");
            parameters.addValue("parentId", pageDto.getParentId());
        }
        if (pageDto.getOrgchartCode() != null){
            query.append(" And orgchart.ORGCHART_CODE like concat('%', :code, '%') ");
            parameters.addValue("code", pageDto.getOrgchartCode());
        }
        if (pageDto.getOrgchartName() != null){
            query.append(" And orgchart.ORGCHART_NAME like concat('%', :orgchartName, '%') ");
            parameters.addValue("orgchartName", pageDto.getOrgchartName());
        }
        if (pageDto.getStatus() != null){
            query.append(" And orgchart.STATUS = :status ");
            parameters.addValue("status", pageDto.getStatus());
        }
        if (pageDto.getCreateBy() != null){
            query.append(" And orgchart.CREATE_BY = :create ");
            parameters.addValue("create", pageDto.getCreateBy());
        }
        if (pageDto.getUpdateBy() != null){
            query.append(" And orgchart.UPDATE_BY = :update ");
            parameters.addValue("update", pageDto.getUpdateBy());
        }
        return namedParameterJdbcTemplate.query(query.toString(),parameters,new BeanPropertyRowMapper<>(TblMdUsersOrgchartDto.class));
    }
}

