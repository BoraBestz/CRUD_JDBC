package com.pea.service.masterservice.controller;

import com.pea.service.masterservice.dto.TblMdStatusPageDto;
import com.pea.service.masterservice.entity.TblMdStatusEntity;
import com.pea.service.masterservice.service.MdStatusService;
import com.pea.service.masterservice.utils.ResponseUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
@Slf4j
public class TestClass {

    private final MdStatusService mdStatusService;

    @Autowired
    public TestClass(MdStatusService mdStatusService) {
        this.mdStatusService = mdStatusService;
    }

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get", response = Object.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Error")})
    public ResponseEntity<ResponseUtils> getLgCampaignDiscount(@RequestParam(required = false, defaultValue = "1", value = "page") Integer page,
                                                                          @RequestParam(required = false, defaultValue = "10", value = "perPage") Integer perPage,
                                                                         @RequestParam(required = false,name = "statusId") Integer statusId) {

        TblMdStatusPageDto param = TblMdStatusPageDto.builder()
                .page(page)
                .perPage(perPage)
                .statusId(statusId)
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseUtils.result(mdStatusService.findAllPage(param)));
    }
}
