package com.pea.service.masterservice.repository;

import com.pea.service.masterservice.dto.TblMdSystemConfigInfDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class TblMdSystemConfigInfJdbcRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public TblMdSystemConfigInfJdbcRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<TblMdSystemConfigInfDto> findAll(TblMdSystemConfigInfDto pageDto){
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        StringBuilder query = new StringBuilder();
        query.append(" SELECT * FROM peaapp.TBL_MD_SYSTEM_CONFIG_INF config_inf ");
        query.append(" WHERE ");
        query.append(" config_inf.STATUS = 'A' ");
        if (pageDto.getConfigInfId() != null){
            query.append("config_inf.CONFIG_INF_ID = :id ");
            parameters.addValue("id",pageDto.getConfigId());
        }
        if (pageDto.getConfigInfCode() != null){
            query.append(" And config_inf.CONFIG_INF_CODE like concat('%', :code, '%') ");
            parameters.addValue("code", pageDto.getConfigInfCode());
        }
        if (pageDto.getDescription() != null){
            query.append(" And config_inf.DESCRIPTION like concat('%', :type, '%') ");
            parameters.addValue("description", pageDto.getDescription());
        }
        if (pageDto.getConfigId() != null){
            query.append(" And config_inf.CONFIG_ID = :cpu ");
            parameters.addValue("configId", pageDto.getConfigId());
        }
        if (pageDto.getReference1() != null){
            query.append(" And config_inf.REFERENCE_1 = :memory ");
            parameters.addValue("reference1", pageDto.getReference1());
        }
        if (pageDto.getReference2() != null){
            query.append(" And config_inf.REFERENCE_2 = :diskOs ");
            parameters.addValue("reference2", pageDto.getReference2());
        }
        if (pageDto.getReference3() != null){
            query.append(" And config_inf.REFERENCE_3 = :diskData ");
            parameters.addValue("reference3", pageDto.getReference3());
        }
        if (pageDto.getStatus() != null){
            query.append(" And config_inf.STATUS = :status ");
            parameters.addValue("status", pageDto.getStatus());
        }
        if (pageDto.getCreateBy() != null){
            query.append(" And config_inf.CREATE_BY = :create ");
            parameters.addValue("create", pageDto.getCreateBy());
        }
        if (pageDto.getUpdateBy() != null){
            query.append(" And config_inf.UPDATE_BY = :update ");
            parameters.addValue("update", pageDto.getUpdateBy());
        }
        return namedParameterJdbcTemplate.query(query.toString(),parameters,new BeanPropertyRowMapper<>(TblMdSystemConfigInfDto.class));
    }
}
