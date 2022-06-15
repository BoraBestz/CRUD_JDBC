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
public class TblMdUsersOrgchartDto {
    @JsonIgnore
    private int page;
    @JsonIgnore private Integer perPage;
    @JsonIgnore private String direction;
    @JsonIgnore private String sort;
    private Integer orgchartId;
    private Integer parentId;
    private String orgchartCode;
    private String orgchartName;
    private String status;
    private Integer createBy;
    private Date createDt;
    private Integer updateBy;
    private Date updateDt;

    @JsonIgnore private String fullSearch;

}
