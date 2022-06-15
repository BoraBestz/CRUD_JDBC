package com.pea.service.masterservice.repository;

import com.pea.service.masterservice.dto.TblMdUsersDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class TblMdUsersJdbcRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public TblMdUsersJdbcRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<TblMdUsersDto> findAll(TblMdUsersDto pageDto){
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        StringBuilder query = new StringBuilder();
        query.append(" SELECT * FROM peaapp.TBL_MD_USERS users ");
        query.append(" WHERE ");
        query.append(" users.STATUS = 'A' ");
        if (pageDto.getUserId() != null){
            query.append("users.USER_ID = :id ");
            parameters.addValue("id",pageDto.getUserId());
        }
        if (pageDto.getUserUsername() != null){
            query.append(" And users.USER_USERNAME like concat('%', :username, '%') ");
            parameters.addValue("username", pageDto.getUserUsername());
        }
        if (pageDto.getUserPassword() != null){
            query.append(" And users.USER_PASSWORD like concat('%', :password, '%') ");
            parameters.addValue("password", pageDto.getUserPassword());
        }
        if (pageDto.getUserFirstName() != null){
            query.append(" And users.USER_FIRST_NAME like concat('%', :firstname, '%') ");
            parameters.addValue("firstname", pageDto.getUserFirstName());
        }
        if (pageDto.getUserLastName() != null){
            query.append(" And users.USER_LAST_NAME like concat('%', :lastname, '%') ");
            parameters.addValue("lastname", pageDto.getUserLastName());
        }
        if (pageDto.getUserTitle() != null){
            query.append(" And users.USER_TITLE  = :userTitle ");
            parameters.addValue("userTitle", pageDto.getUserTitle());
        }
        if (pageDto.getUserEmail() != null){
            query.append(" And users.USER_EMAIL like concat('%', :email, '%') ");
            parameters.addValue("email", pageDto.getUserEmail());
        }
        if (pageDto.getUserTel() != null){
            query.append(" And users.USER_TEL like concat('%', :userTel, '%') ");
            parameters.addValue("userTel", pageDto.getUserTel());
        }
        if (pageDto.getUserAddress() != null){
            query.append(" And users.USER_ADDRESS like concat('%', :address, '%') ");
            parameters.addValue("address", pageDto.getUserAddress());
        }
        if (pageDto.getUserDepartment() != null){
            query.append(" And users.USER_DEPARTMENT like concat('%', :department, '%') ");
            parameters.addValue("department", pageDto.getUserDepartment());
        }
        if (pageDto.getUserType() != null){
            query.append(" And users.CAN_EDIT like concat('%', :userType, '%') ");
            parameters.addValue("userType", pageDto.getUserType());
        }
        if (pageDto.getStatus() != null){
            query.append(" And users.STATUS = :status ");
            parameters.addValue("status", pageDto.getStatus());
        }
        if (pageDto.getCreateBy() != null){
            query.append(" And users.CREATE_BY = :create ");
            parameters.addValue("create", pageDto.getCreateBy());
        }
        if (pageDto.getUpdateBy() != null){
            query.append(" And users.UPDATE_BY = :update ");
            parameters.addValue("update", pageDto.getUpdateBy());
        }
        if (pageDto.getUserIdcard() != null){
            query.append(" And users.USER_IDCARD = :idCard ");
            parameters.addValue("idCard", pageDto.getUserIdcard());
        }
        if (pageDto.getUserDn() != null){
            query.append(" And users.USER_DN = :userDN ");
            parameters.addValue("userDn", pageDto.getUserDn());
        }
        if (pageDto.getLoginFail() != null){
            query.append(" And users.LOGIN_FAIL = :loginFail ");
            parameters.addValue("loginFail", pageDto.getLoginFail());
        }
        if (pageDto.getTitleNameId() != null){
            query.append(" And users.TITLE_NAME_ID = :titleNameId ");
            parameters.addValue("titleNameId", pageDto.getTitleNameId());
        }
        if (pageDto.getDepartmentId() != null){
            query.append(" And users.DEPARTMENT_ID = :departmentId ");
            parameters.addValue("departmentId", pageDto.getDepartmentId());
        }
        if (pageDto.getPositionId() != null){
            query.append(" And users.POSITION_ID = :positionId ");
            parameters.addValue("positionId", pageDto.getPositionId());
        }

        return namedParameterJdbcTemplate.query(query.toString(),parameters,new BeanPropertyRowMapper<>(TblMdUsersDto.class));
    }
}

