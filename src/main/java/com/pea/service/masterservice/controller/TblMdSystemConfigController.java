package com.pea.service.masterservice.controller;

import com.pea.service.masterservice.dto.TblMdSystemConfigDto;
import com.pea.service.masterservice.entity.TblMdSystemConfigEntity;
import com.pea.service.masterservice.exception.BusinessServiceException;
import com.pea.service.masterservice.service.MdSystemConfigService;
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
@Api(tags = "SystemConfig")
@Slf4j
public class TblMdSystemConfigController {
    private final MdSystemConfigService mdSystemConfigService;
    private ModelMapper modelMapper;
    private MessageByLocaleService message;

    @Autowired
    public TblMdSystemConfigController(MdSystemConfigService mdSystemConfigService,
                                       ModelMapper modelMapper, MessageByLocaleService message
                                  ) {
        this.mdSystemConfigService = mdSystemConfigService;
        this.modelMapper = modelMapper;
        this.message = message;
    }

    @GetMapping("/config/search")
    @ApiOperation(value = "Search Config", response = Object.class, responseContainer = "List")
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
            @RequestParam(required = false, defaultValue = "configCode", value = "sort") String sort,
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
            log.info("Start TblMdSystemConfigController.searchByCri()... ");
            TblMdSystemConfigDto dto = modelMapper.map(paramDto, TblMdSystemConfigDto.class);
            Page<TblMdSystemConfigDto> byCri = mdSystemConfigService.findByCri(dto);

            /// FOR LOG///
            status = HttpStatus.OK.value();
            remark = paramDto.toString();
            /// FOR LOG///

            return HelperUtils.responseSuccess(byCri);
        } catch (BusinessServiceException e) {
            log.error("Error TblMdSystemConfigController.searchByCri()... " + e.getMessage());

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
            log.info("End TblMdSystemConfigController.searchByCri()... ");
        }
    }
    @GetMapping("/config")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get", response = Object.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Error")})
    public ResponseEntity<ResponseUtils> getAll(
            @RequestParam(required = false, defaultValue = "1", value = "page") Integer page,
            @RequestParam(required = false, defaultValue = "10", value = "perPage") Integer perPage,
            @RequestParam(required = false, name = "configId") Integer configInfId,
            @RequestParam Map<String, Object> paramDto){
        TblMdSystemConfigDto dto = modelMapper.map(paramDto, TblMdSystemConfigDto.class);
        TblMdSystemConfigDto param = TblMdSystemConfigDto.builder()
                .page(page)
                .perPage(perPage)
                .configId(dto.getConfigId())
                .configCode(dto.getConfigCode())
                .description(dto.getDescription())
                .canEdit(dto.getCanEdit())
                .status(dto.getStatus())
                .createBy(dto.getCreateBy())
                .updateBy(dto.getUpdateBy())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseUtils.result(mdSystemConfigService.findAllPage(param)));
    }

    @GetMapping("/config/{id}")
    @ApiOperation(value = "Get systemConfig By systemConfigId", response = TblMdSystemConfigDto.class)
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
            TblMdSystemConfigController.log.info("Start TblMdSystemConfigController.getById()... ");
            TblMdSystemConfigDto dto = mdSystemConfigService.getById(id);
            if (dto.getConfigId() > 0) {
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
            log.error("Error TblMdSystemConfigController.getById()... " + e.getMessage());

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
            TblMdSystemConfigController.log.info("End TblMdSystemConfigController.getById()... ");
        }
    }

    @PostMapping("/config")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "New systemConfig", response = void.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Something went wrong"),
                    @ApiResponse(code = 403, message = "Access denied"),
                    @ApiResponse(code = 500, message = "Error")
            })
    public ResponseEntity<ResponseModel> create(@RequestBody TblMdSystemConfigDto dto) {
        /// FOR LOG///
        int status = 0;
        String remark = "";
        /// FOR LOG///
        try {
            log.info("Start TblMdSystemConfigController.create()... ");

            int count = mdSystemConfigService.checkDuplicateCode(dto);

            if (count > 0) {
                throw new BusinessServiceException(
                        HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
            }
            dto.setCreateDt(new Date());
            dto.setStatus("A");
            TblMdSystemConfigEntity save = mdSystemConfigService.save(dto);

            /// FOR LOG///
            status = HttpStatus.OK.value();
            remark = "ID=" + save.getConfigId() + " CODE=" + save.getConfigCode();
            /// FOR LOG///

            return HelperUtils.responseSuccess(save.getConfigId());
        } catch (BusinessServiceException e) {
            log.error("Error TblMdSystemConfigController.create()... " + e.getMessage());

            /// FOR LOG///
            status = e.getHttpStatus().value();
            remark = e.getMessage() + " CODE=" + dto.getConfigCode();
            /// FOR LOG///

            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());

            /// FOR LOG///
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            remark = e.getMessage() + " CODE=" + dto.getConfigCode();
            /// FOR LOG///

            return HelperUtils.responseErrorInternalServerError(
                    String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage());
        } finally {
            TblMdSystemConfigController.log.info("End TblMdSystemConfigController.create()... ");
        }
    }
}
