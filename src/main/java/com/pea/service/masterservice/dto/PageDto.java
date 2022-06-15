package com.pea.service.masterservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto {
    private Pageable paging;
    private int start;
    private int end;
}
