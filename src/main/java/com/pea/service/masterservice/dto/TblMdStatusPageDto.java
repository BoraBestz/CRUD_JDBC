package com.pea.service.masterservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TblMdStatusPageDto {
    @JsonIgnore
    private int page;
    @JsonIgnore private Integer perPage;
    private Integer statusId;
    private String canEdit;
    private String status;
    private Integer createBy;
    private Timestamp createDt;
    private Integer updateBy;
    private Timestamp updateDt;

}
