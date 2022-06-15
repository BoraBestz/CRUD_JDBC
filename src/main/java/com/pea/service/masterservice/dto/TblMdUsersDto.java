package com.pea.service.masterservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TblMdUsersDto {
    @JsonIgnore
    private int page;
    @JsonIgnore private Integer perPage;
    @JsonIgnore private String direction;
    @JsonIgnore private String sort;
    private Integer userId;
    private String userUsername;
    private String userPassword;
    private String userFirstName;
    private String userLastName;
    private Integer userTitle;
    private String userEmail;
    private String userTel;
    private String userAddress;
    private String userDepartment;
    private String userType;
    private String status;
    private Integer createBy;
    private Date createDt;
    private Integer updateBy;
    private Date updateDt;
    private String userIdcard;
    private String userDn;
    private Integer loginFail;
    private Integer titleNameId;
    private Integer departmentId;
    private Integer positionId;


    @JsonIgnore private String fullSearch;
}
