package com.pea.service.masterservice.repository;

import com.pea.service.masterservice.dto.TblMdSystemConfigDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class TblMdSystemConfigJdbcRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public TblMdSystemConfigJdbcRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<TblMdSystemConfigDto> findAll(TblMdSystemConfigDto pageDto){
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        StringBuilder query = new StringBuilder();
        query.append(" SELECT * FROM peaapp.TBL_MD_SYSTEM_CONFIG config ");
        query.append(" WHERE ");
        query.append(" config.STATUS = 'A' ");
        if (pageDto.getConfigId() != null){
            query.append("config.CONFIG_ID = :id ");
            parameters.addValue("id",pageDto.getConfigId());
        }
        if (pageDto.getConfigCode() != null){
            query.append(" And config.CONFIG_INF_CODE like concat('%', :code, '%') ");
            parameters.addValue("code", pageDto.getConfigCode());
        }
        if (pageDto.getDescription() != null){
            query.append(" And config.DESCRIPTION like concat('%', :type, '%') ");
            parameters.addValue("description", pageDto.getDescription());
        }
        if (pageDto.getCanEdit() != null){
            query.append(" And config.CAN_EDIT = :edit ");
            parameters.addValue("canEdit", pageDto.getCanEdit());
        }
        if (pageDto.getStatus() != null){
            query.append(" And config.STATUS = :status ");
            parameters.addValue("status", pageDto.getStatus());
        }
        if (pageDto.getCreateBy() != null){
            query.append(" And config.CREATE_BY = :create ");
            parameters.addValue("create", pageDto.getCreateBy());
        }
        if (pageDto.getUpdateBy() != null){
            query.append(" And config.UPDATE_BY = :update ");
            parameters.addValue("update", pageDto.getUpdateBy());
        }
        return namedParameterJdbcTemplate.query(query.toString(),parameters,new BeanPropertyRowMapper<>(TblMdSystemConfigDto.class));
    }
}

