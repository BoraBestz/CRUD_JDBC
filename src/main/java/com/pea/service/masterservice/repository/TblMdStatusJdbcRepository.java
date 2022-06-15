package com.pea.service.masterservice.repository;

import com.pea.service.masterservice.dto.TblMdStatusPageDto;
import com.pea.service.masterservice.entity.TblMdStatusEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class TblMdStatusJdbcRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public TblMdStatusJdbcRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<TblMdStatusPageDto> findAll(TblMdStatusPageDto pageDto){
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        StringBuilder query = new StringBuilder();
        query.append(" SELECT * FROM peaapp.TBL_MD_STATUS status ");
        query.append(" WHERE ");
        query.append(" status.STATUS = 'A' ");
        if (pageDto.getStatusId() != null){
            query.append("status.STATUS_ID = :id ");
            parameters.addValue("id",pageDto.getStatusId());
        }
        return namedParameterJdbcTemplate.query(query.toString(),parameters,new BeanPropertyRowMapper<>(TblMdStatusPageDto.class));
    }
}
