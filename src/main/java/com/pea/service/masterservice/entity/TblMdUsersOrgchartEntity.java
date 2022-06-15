package com.pea.service.masterservice.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tbl_md_users_orgchart", schema = "peaapp", catalog = "pea")
public class TblMdUsersOrgchartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "peaapp.tbl_md_user_orgchart_seq")
    @SequenceGenerator(
            name = "peaapp.tbl_md_tbl_md_user_orgchart_seq",
            sequenceName = "peaapp.tbl_md_tbl_md_user_orgchart_seq",
            allocationSize = 1,
            schema = "peaapp")
    @Column(name = "orgchart_id", nullable = false)
    private Integer orgchartId;


    @Basic
    @Column(name = "parent_id", nullable = true)
    private Integer parentId;

    @Basic
    @Column(name = "orgchart_code", nullable = true, length = 15)
    private String orgchartCode;

    @Basic
    @Column(name = "orgchart_name", nullable = true, length = 128)
    private String orgchartName;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TblMdUsersOrgchartEntity that = (TblMdUsersOrgchartEntity) o;
        return orgchartId == that.orgchartId && Objects.equals(parentId, that.parentId) && Objects.equals(orgchartCode, that.orgchartCode) && Objects.equals(orgchartName, that.orgchartName) && Objects.equals(status, that.status) && Objects.equals(createBy, that.createBy) && Objects.equals(createDt, that.createDt) && Objects.equals(updateBy, that.updateBy) && Objects.equals(updateDt, that.updateDt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orgchartId, parentId, orgchartCode, orgchartName, status, createBy, createDt, updateBy, updateDt);
    }
}
