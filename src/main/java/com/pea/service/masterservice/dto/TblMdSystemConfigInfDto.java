package com.pea.service.masterservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TblMdSystemConfigInfDto {
    @JsonIgnore
    private int page;
    @JsonIgnore private Integer perPage;
    @JsonIgnore private String direction;
    @JsonIgnore private String sort;
    private Integer configInfId;
    private Integer configId;
    private String configInfCode;
    private String description;
    private String reference1;
    private String reference2;
    private String reference3;
    private String status;
    private Integer createBy;
    private Date createDt;
    private Integer updateBy;
    private Date updateDt;

    @JsonIgnore private String fullSearch;

}
