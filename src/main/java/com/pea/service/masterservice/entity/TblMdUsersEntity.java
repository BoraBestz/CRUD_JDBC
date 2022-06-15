package com.pea.service.masterservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@Table(name = "tbl_md_users", schema = "peaapp", catalog = "pea")
public class TblMdUsersEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "peaapp.tbl_md_user_seq")
    @SequenceGenerator(
            name = "peaapp.tbl_md_tbl_md_user_seq",
            sequenceName = "peaapp.tbl_md_tbl_md_user_seq",
            allocationSize = 1,
            schema = "peaapp")
    @Column(name = "user_id", nullable = false)
    private Integer userId;


    @Basic
    @Column(name = "user_username", nullable = true, length = 32)
    private String userUsername;


    @Basic
    @Column(name = "user_password", nullable = true, length = 128)
    private String userPassword;


    @Basic
    @Column(name = "user_first_name", nullable = true, length = 64)
    private String userFirstName;


    @Basic
    @Column(name = "user_last_name", nullable = true, length = 64)
    private String userLastName;


    @Basic
    @Column(name = "user_title", nullable = true)
    private Integer userTitle;

    @Basic
    @Column(name = "user_email", nullable = true, length = 64)
    private String userEmail;


    @Basic
    @Column(name = "user_tel", nullable = true, length = 32)
    private String userTel;

    @Basic
    @Column(name = "user_address", nullable = true, length = 512)
    private String userAddress;


    @Basic
    @Column(name = "user_department", nullable = true, length = 128)
    private String userDepartment;

    @Basic
    @Column(name = "user_type", nullable = true, length = -1)
    private String userType;


    @Basic
    @Column(name = "status", nullable = true, length = -1)
    private String status;


    @Basic
    @Column(name = "create_by", nullable = true)
    private Integer createBy;


    @Basic
    @Column(name = "create_dt", nullable = true)
    private Date createDt;


    @Basic
    @Column(name = "update_by", nullable = true)
    private Integer updateBy;


    @Basic
    @Column(name = "update_dt", nullable = true)
    private Date updateDt;

    @Basic
    @Column(name = "user_idcard", nullable = true, length = 13)
    private String userIdcard;


    @Basic
    @Column(name = "user_dn", nullable = true, length = 256)
    private String userDn;


    @Basic
    @Column(name = "login_fail", nullable = true)
    private Integer loginFail;


    @Basic
    @Column(name = "title_name_id", nullable = true)
    private Integer titleNameId;


    @Basic
    @Column(name = "department_id", nullable = true)
    private Integer departmentId;

    @Basic
    @Column(name = "position_id", nullable = true)
    private Integer positionId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TblMdUsersEntity that = (TblMdUsersEntity) o;
        return userId == that.userId && Objects.equals(userUsername, that.userUsername) && Objects.equals(userPassword, that.userPassword) && Objects.equals(userFirstName, that.userFirstName) && Objects.equals(userLastName, that.userLastName) && Objects.equals(userTitle, that.userTitle) && Objects.equals(userEmail, that.userEmail) && Objects.equals(userTel, that.userTel) && Objects.equals(userAddress, that.userAddress) && Objects.equals(userDepartment, that.userDepartment) && Objects.equals(userType, that.userType) && Objects.equals(status, that.status) && Objects.equals(createBy, that.createBy) && Objects.equals(createDt, that.createDt) && Objects.equals(updateBy, that.updateBy) && Objects.equals(updateDt, that.updateDt) && Objects.equals(userIdcard, that.userIdcard) && Objects.equals(userDn, that.userDn) && Objects.equals(loginFail, that.loginFail) && Objects.equals(titleNameId, that.titleNameId) && Objects.equals(departmentId, that.departmentId) && Objects.equals(positionId, that.positionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userUsername, userPassword, userFirstName, userLastName, userTitle, userEmail, userTel, userAddress, userDepartment, userType, status, createBy, createDt, updateBy, updateDt, userIdcard, userDn, loginFail, titleNameId, departmentId, positionId);
    }
}
