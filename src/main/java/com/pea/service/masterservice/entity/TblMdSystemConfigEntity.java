package com.pea.service.masterservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@Table(name = "tbl_md_system_config", schema = "peaapp", catalog = "pea")
public class TblMdSystemConfigEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "peaapp.tbl_md_system_config_seq")
    @SequenceGenerator(
            name = "peaapp.tbl_md_system_config_seq",
            sequenceName = "peaapp.tbl_md_system_config_seq",
            allocationSize = 1,
            schema = "peaapp")
    private Integer configId;
    @Basic
    @Column(name = "config_code", nullable = true, length = 255)
    private String configCode;
    @Basic
    @Column(name = "description", nullable = true, length = 255)
    private String description;
    @Basic
    @Column(name = "can_edit", nullable = true, length = -1)
    private String canEdit;
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
        TblMdSystemConfigEntity that = (TblMdSystemConfigEntity) o;
        return configId == that.configId && Objects.equals(configCode, that.configCode) && Objects.equals(description, that.description) && Objects.equals(canEdit, that.canEdit) && Objects.equals(status, that.status) && Objects.equals(createBy, that.createBy) && Objects.equals(createDt, that.createDt) && Objects.equals(updateBy, that.updateBy) && Objects.equals(updateDt, that.updateDt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configId, configCode, description, canEdit, status, createBy, createDt, updateBy, updateDt);
    }
}
