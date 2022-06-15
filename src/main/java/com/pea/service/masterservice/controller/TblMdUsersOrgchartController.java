package com.pea.service.masterservice.controller;

import com.pea.service.masterservice.dto.TblMdUsersOrgchartDto;
import com.pea.service.masterservice.entity.TblMdUsersOrgchartEntity;
import com.pea.service.masterservice.exception.BusinessServiceException;
import com.pea.service.masterservice.service.MdUsersOrgchartService;
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
public class TblMdUsersOrgchartController {
    private final MdUsersOrgchartService mdUsersOrgchartService;
    private ModelMapper modelMapper;
    private MessageByLocaleService message;

    @Autowired
    public TblMdUsersOrgchartController(MdUsersOrgchartService mdUsersOrgchartService,
                                        ModelMapper modelMapper, MessageByLocaleService message
    ) {
        this.mdUsersOrgchartService = mdUsersOrgchartService;
        this.modelMapper = modelMapper;
        this.message = message;
    }

    @GetMapping("/users/orgchart/search")
    @ApiOperation(value = "Search User", response = Object.class, responseContainer = "List")
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
            @RequestParam(required = false, defaultValue = "orgchartName", value = "sort") String sort,
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
            log.info("Start TblMdUsersOrgchartController.searchByCri()... ");
            TblMdUsersOrgchartDto dto = modelMapper.map(paramDto, TblMdUsersOrgchartDto.class);
            Page<TblMdUsersOrgchartDto> byCri = mdUsersOrgchartService.findByCri(dto);

            /// FOR LOG///
            status = HttpStatus.OK.value();
            remark = paramDto.toString();
            /// FOR LOG///

            return HelperUtils.responseSuccess(byCri);
        } catch (BusinessServiceException e) {
            log.error("Error TblMdUsersController.searchByCri()... " + e.getMessage());

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
            log.info("End TblMdUsersController.searchByCri()... ");
        }
    }

    @GetMapping("/users/orgchart")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get", response = Object.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Error")})
    public ResponseEntity<ResponseUtils> getAll(
            @RequestParam(required = false, defaultValue = "1", value = "page") Integer page,
            @RequestParam(required = false, defaultValue = "10", value = "perPage") Integer perPage,
            @RequestParam(required = false, name = "orgchatId") Integer orgchartId,
            @RequestParam Map<String, Object> paramDto) {
        TblMdUsersOrgchartDto dto = modelMapper.map(paramDto, TblMdUsersOrgchartDto.class);
        TblMdUsersOrgchartDto param = TblMdUsersOrgchartDto.builder()
                .page(page)
                .perPage(perPage)
                .orgchartId(dto.getOrgchartId())
                .parentId(dto.getParentId())
                .orgchartCode(dto.getOrgchartCode())
                .orgchartName(dto.getOrgchartName())
                .status(dto.getStatus())
                .createBy(dto.getCreateBy())
                .updateBy(dto.getUpdateBy())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseUtils.result(mdUsersOrgchartService.findAllPage(param)));
    }

    @GetMapping("/users/orgchart/{id}")
    @ApiOperation(value = "Get Users By UserId", response = TblMdUsersOrgchartDto.class)
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
            TblMdUsersOrgchartController.log.info("Start TblMdUsersOrgchartController.getById()... ");
            TblMdUsersOrgchartDto dto = mdUsersOrgchartService.getById(id);
            if (dto.getOrgchartId() > 0) {
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
            log.error("Error TblMdUsersOrgchartController.getById()... " + e.getMessage());

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
            TblMdUsersOrgchartController.log.info("End TblMdUsersOrgchartController.getById()... ");
        }
    }

    @PostMapping("/users/orgchart")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "New User", response = void.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Something went wrong"),
                    @ApiResponse(code = 403, message = "Access denied"),
                    @ApiResponse(code = 500, message = "Error")
            })
    public ResponseEntity<ResponseModel> create(@RequestBody TblMdUsersOrgchartDto dto) {
        /// FOR LOG///
        int status = 0;
        String remark = "";
        /// FOR LOG///
        try {
            log.info("Start TblMdUsersOrgchartController.create()... ");

            int count = mdUsersOrgchartService.checkDuplicateCode(dto);

            if (count > 0) {
                throw new BusinessServiceException(
                        HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
            }
            dto.setCreateDt(new Date());
            dto.setStatus("A");
            TblMdUsersOrgchartEntity save = mdUsersOrgchartService.save(dto);
            

            return HelperUtils.responseSuccess(save.getClass());
        } catch (BusinessServiceException e) {
            log.error("Error TblMdUsersOrgchartController.create()... " + e.getMessage());

            /// FOR LOG///
            status = e.getHttpStatus().value();
            remark = e.getMessage() + " name=" + dto.getOrgchartName();
            /// FOR LOG///

            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());

            /// FOR LOG///
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            remark = e.getMessage() + " name=" + dto.getOrgchartName();
            /// FOR LOG///

            return HelperUtils.responseErrorInternalServerError(
                    String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage());
        } finally {
            TblMdUsersOrgchartController.log.info("End TblMdUsersController.create()... ");
        }
    }
}
