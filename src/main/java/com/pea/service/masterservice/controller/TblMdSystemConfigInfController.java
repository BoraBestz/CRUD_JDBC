package com.pea.service.masterservice.controller;


import com.pea.service.masterservice.dto.TblMdSystemConfigInfDto;
import com.pea.service.masterservice.entity.TblMdSystemConfigInfEntity;
import com.pea.service.masterservice.exception.BusinessServiceException;
import com.pea.service.masterservice.service.MdSystemConfigInfService;

import com.pea.service.masterservice.utils.HelperUtils;
import com.pea.service.masterservice.utils.MessageByLocaleService;
import com.pea.service.masterservice.utils.ResponseModel;
import com.pea.service.masterservice.utils.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(path = "/api")
@Api(tags = "SystemConfigInf")
@Slf4j
public class TblMdSystemConfigInfController {
    private final MdSystemConfigInfService mdSystemConfigInfService;
    private ModelMapper modelMapper;
    private MessageByLocaleService message;

    @Autowired
    public TblMdSystemConfigInfController(MdSystemConfigInfService mdSystemConfigInfService,
                                          ModelMapper modelMapper, MessageByLocaleService message
    ) {
        this.mdSystemConfigInfService = mdSystemConfigInfService;
        this.modelMapper = modelMapper;
        this.message = message;
    }
    @GetMapping("/configinf/search")
    @ApiOperation(value = "Search Configinf", response = Object.class, responseContainer = "List")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Something went wrong"),
                    @ApiResponse(code = 403, message = "Access denied"),
                    @ApiResponse(code = 500, message = "Error")
            })
    public ResponseEntity<ResponseModel> searchByCri(
            @RequestParam(required = false, defaultValue = "1", value = "page") Integer page,
            @RequestParam(required = false, defaultValue = "10", value = "perPage") Integer perPage,
            @RequestParam(required = false, defaultValue = "ASC", value = "order")
                    Sort.Direction direction,
            @RequestParam(required = false, defaultValue = "configInfCode", value = "sort") String sort,
            @RequestParam Map<String, Object> paramDto) {
        paramDto.put("page", page);
        paramDto.put("perPage", perPage);
        paramDto.put("direction", direction);
        paramDto.put("sort", sort);

        /// FOR LOG///
        int status = 0;
        String remark = "";
        /// FOR LOG///

        try {
            log.info("Start TblMdSystemConfigInfController.searchByCri()... ");
            TblMdSystemConfigInfDto dto = modelMapper.map(paramDto, TblMdSystemConfigInfDto.class);
            Page<TblMdSystemConfigInfDto> byCri = mdSystemConfigInfService.findByCri(dto);

            /// FOR LOG///
            status = HttpStatus.OK.value();
            remark = paramDto.toString();
            /// FOR LOG///

            return HelperUtils.responseSuccess(byCri);
        } catch (BusinessServiceException e) {
            log.error("Error TblMdSystemConfigInfController.searchByCri()... " + e.getMessage());

            /// FOR LOG///
            status = e.getHttpStatus().value();
            remark = e.getMessage();
            /// FOR LOG///

            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
        } finally {
            // ***START ACTIVITY LOG***//
            /*loggingServiceClient.activityLog(
                    ActivityType.SEARCH,
                    null,
                    null,
                    remark,
                    getApiOperationValue(new Object() {}),
                    getClientIp(),
                    getTracer(),
                    getMethodName(new Object() {}),
                    getToken(),
                    "XXX",
                    status);*/
            // ***END ACTIVITY LOG***//
            log.info("End TblMdSystemConfigInfController.searchByCri()... ");
        }
    }
    @GetMapping("/configinf")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get", response = Object.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Error")})
    public ResponseEntity<ResponseUtils> getAll(
            @RequestParam(required = false, defaultValue = "1", value = "page") Integer page,
            @RequestParam(required = false, defaultValue = "10", value = "perPage") Integer perPage,
            @RequestParam(required = false, name = "configInfId") Integer configInfId,
            @RequestParam Map<String, Object> paramDto){
        TblMdSystemConfigInfDto dto = modelMapper.map(paramDto, TblMdSystemConfigInfDto.class);
        TblMdSystemConfigInfDto param = TblMdSystemConfigInfDto.builder()
                .page(page)
                .perPage(perPage)
                .configInfId(configInfId)
                .configId(dto.getConfigId())
                .configInfCode(dto.getConfigInfCode())
                .description(dto.getDescription())
                .reference1(dto.getReference1())
                .reference2(dto.getReference2())
                .reference3(dto.getReference3())
                .status(dto.getStatus())
                .createBy(dto.getCreateBy())
                .updateBy(dto.getUpdateBy())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseUtils.result(mdSystemConfigInfService.findAllPage(param)));
    }

    @GetMapping("/configinf/{id}")
    @ApiOperation(value = "Get systemConfig By systemConfigId", response = TblMdSystemConfigInfDto.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Something went wrong"),
                    @ApiResponse(code = 403, message = "Access denied"),
                    @ApiResponse(code = 500, message = "Error")
            })
    public ResponseEntity<ResponseModel> getById(@PathVariable("id") int id) {
        /// FOR LOG///
        int status = 0;
        String remark = "";
        /// FOR LOG///
        try {
            TblMdSystemConfigInfController.log.info("Start TblMdSystemConfigInfController.getById()... ");
            TblMdSystemConfigInfDto dto = mdSystemConfigInfService.getById(id);
            if (dto.getConfigInfId() > 0) {
                /// FOR LOG///
                status = HttpStatus.OK.value();
                remark = "ID=" + id;
                /// FOR LOG///

                return HelperUtils.responseSuccess(dto);
            } else {
                throw new BusinessServiceException(
                        HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), ("ERM0001"));
            }
        } catch (BusinessServiceException e) {
            log.error("Error TblMdSystemConfigInfController.getById()... " + e.getMessage());

            /// FOR LOG///
            status = e.getHttpStatus().value();
            remark = e.getMessage() + " ID=" + id;
            /// FOR LOG///

            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());

            /// FOR LOG///
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            remark = e.getMessage() + " ID=" + id;
            /// FOR LOG///

            return HelperUtils.responseErrorInternalServerError(
                    String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage());
        } finally {
            TblMdSystemConfigInfController.log.info("End TblMdSystemConfigInfController.getById()... ");
        }
    }

    @PostMapping("/configinf")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get", response = Object.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Error")})
    public ResponseUtils create(@RequestBody TblMdSystemConfigInfDto dto) throws BusinessServiceException {
        int check = mdSystemConfigInfService.checkDuplicateCode(dto);
        if (check > 0) {
            throw new BusinessServiceException(
                    HttpStatus.CONFLICT, "test");
        }
        dto.setCreateDt(new Date());
        dto.setStatus("A");
        TblMdSystemConfigInfEntity save = mdSystemConfigInfService.save(dto);

        return ResponseUtils.result(save);
    }

}
