package com.pea.service.masterservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Data
@Table(name = "tbl_md_status", schema = "peaapp", catalog = "pea")
public class TblMdStatusEntity {

    @Id
    @GeneratedValue(generator = "tbl_rd_status_seq")
    @SequenceGenerator(
            name = "tbl_rd_status_seq",
            sequenceName = "tbl_rd_status_seq",
            allocationSize = 1,
            schema = "peaapp")
    private int statusId;
    @Basic
    @Column(name = "document_type_id")
    private Integer documentTypeId;
    @Basic
    @Column(name = "status_code")
    private String statusCode;
    @Basic
    @Column(name = "status_desc")
    private String statusDesc;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "create_by")
    private Integer createBy;
    @Basic
    @Column(name = "create_dt")
    private Timestamp createDt;
    @Basic
    @Column(name = "update_by")
    private Integer updateBy;
    @Basic
    @Column(name = "update_dt")
    private Timestamp updateDt;
}
