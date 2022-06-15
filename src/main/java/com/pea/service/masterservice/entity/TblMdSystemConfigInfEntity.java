package com.pea.service.masterservice.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tbl_md_system_config_inf", schema = "peaapp", catalog = "pea")
public class TblMdSystemConfigInfEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "peaapp.tbl_md_system_config_inf_seq")
    @SequenceGenerator(
            name = "peaapp.tbl_md_system_config_inf_seq",
            sequenceName = "peaapp.tbl_md_system_config_inf_seq",
            allocationSize = 1,
            schema = "peaapp")
    private Integer configInfId;



    @Basic
    @Column(name = "config_id")
    private Integer configId;


    @Basic
    @Column(name = "config_inf_code")
    private String configInfCode;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "reference_1")
    private String reference1;


    @Basic
    @Column(name = "reference_2")
    private String reference2;

    @Basic
    @Column(name = "reference_3")
    private String reference3;


    @Basic
    @Column(name = "status")
    private String status;


    @Basic
    @Column(name = "create_by")
    private Integer createBy;

    @Basic
    @Column(name = "create_dt")
    private Date createDt;


    @Basic
    @Column(name = "update_by")
    private Integer updateBy;

    @Basic
    @Column(name = "update_dt")
    private Date updateDt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TblMdSystemConfigInfEntity that = (TblMdSystemConfigInfEntity) o;
        return configInfId == that.configInfId && Objects.equals(configId, that.configId) && Objects.equals(configInfCode, that.configInfCode) && Objects.equals(description, that.description) && Objects.equals(reference1, that.reference1) && Objects.equals(reference2, that.reference2) && Objects.equals(reference3, that.reference3) && Objects.equals(status, that.status) && Objects.equals(createBy, that.createBy) && Objects.equals(createDt, that.createDt) && Objects.equals(updateBy, that.updateBy) && Objects.equals(updateDt, that.updateDt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configInfId, configId, configInfCode, description, reference1, reference2, reference3, status, createBy, createDt, updateBy, updateDt);
    }
}
