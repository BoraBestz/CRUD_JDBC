package com.pea.service.masterservice.controller;

import com.pea.service.masterservice.dto.TblMdUsersDto;
import com.pea.service.masterservice.entity.TblMdUsersEntity;
import com.pea.service.masterservice.exception.BusinessServiceException;
import com.pea.service.masterservice.service.MdUsersService;
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
public class TblMdUsersController {
    private final MdUsersService mdUsersService;
    private ModelMapper modelMapper;
    private MessageByLocaleService message;

    @Autowired
    public TblMdUsersController(MdUsersService mdUsersService,
                                       ModelMapper modelMapper, MessageByLocaleService message
    ) {
        this.mdUsersService = mdUsersService;
        this.modelMapper = modelMapper;
        this.message = message;
    }

    @GetMapping("/users/search")
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
            @RequestParam(required = false, defaultValue = "userUsername", value = "sort") String sort,
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
            log.info("Start TblMdUsersController.searchByCri()... ");
            TblMdUsersDto dto = modelMapper.map(paramDto, TblMdUsersDto.class);
            Page<TblMdUsersDto> byCri = mdUsersService.findByCri(dto);

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
    @GetMapping("/users")
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
        TblMdUsersDto dto = modelMapper.map(paramDto, TblMdUsersDto.class);
        TblMdUsersDto param = TblMdUsersDto.builder()
                .page(page)
                .perPage(perPage)
                .userId(dto.getUserId())
                .userUsername(dto.getUserUsername())
                .userPassword(dto.getUserPassword())
                .userLastName(dto.getUserLastName())
                .userTitle(dto.getUserTitle())
                .userEmail(dto.getUserEmail())
                .userTel(dto.getUserTel())
                .userAddress(dto.getUserAddress())
                .userDepartment(dto.getUserDepartment())
                .userType(dto.getUserType())
                .status(dto.getStatus())
                .createBy(dto.getCreateBy())
                .updateBy(dto.getUpdateBy())
                .userIdcard(dto.getUserIdcard())
                .userDn(dto.getUserDn())
                .loginFail(dto.getLoginFail())
                .titleNameId(dto.getTitleNameId())
                .departmentId(dto.getDepartmentId())
                .positionId(dto.getPositionId())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseUtils.result(mdUsersService.findAllPage(param)));
    }

    @GetMapping("/users/{id}")
    @ApiOperation(value = "Get Users By UserId", response = TblMdUsersDto.class)
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
            TblMdUsersController.log.info("Start TblMdUsersController.getById()... ");
            TblMdUsersDto dto = mdUsersService.getById(id);
            if (dto.getUserId() > 0) {
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
            log.error("Error TblMdUsersController.getById()... " + e.getMessage());

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
            TblMdUsersController.log.info("End TblMdUsersController.getById()... ");
        }
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "New User", response = void.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Something went wrong"),
                    @ApiResponse(code = 403, message = "Access denied"),
                    @ApiResponse(code = 500, message = "Error")
            })
    public ResponseEntity<ResponseModel> create(@RequestBody TblMdUsersDto dto) {
        /// FOR LOG///
        int status = 0;
        String remark = "";
        /// FOR LOG///
        try {
            log.info("Start TblMdUsersController.create()... ");

            int count = mdUsersService.checkDuplicateCode(dto);

            if (count > 0) {
                throw new BusinessServiceException(
                        HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
            }
            dto.setCreateDt(new Date());
            dto.setStatus("A");
            TblMdUsersEntity save = mdUsersService.save(dto);

            /// FOR LOG///
            status = HttpStatus.OK.value();
            remark = "ID=" + save.getUserId() + " USERNAME=" + save.getUserUsername();
            /// FOR LOG///

            return HelperUtils.responseSuccess(save.getUserId());
        } catch (BusinessServiceException e) {
            log.error("Error TblMdUsersController.create()... " + e.getMessage());

            /// FOR LOG///
            status = e.getHttpStatus().value();
            remark = e.getMessage() + " USERNAME=" + dto.getUserUsername();
            /// FOR LOG///

            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());

            /// FOR LOG///
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            remark = e.getMessage() + " USERNAME=" + dto.getUserUsername();
            /// FOR LOG///

            return HelperUtils.responseErrorInternalServerError(
                    String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage());
        } finally {
            TblMdUsersController.log.info("End TblMdUsersController.create()... ");
        }
    }
}
