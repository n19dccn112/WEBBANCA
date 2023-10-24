package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.UnitDTO;
import com.n19dccn112.model.dto.UnitDetailDTO;
import com.n19dccn112.model.dto.UserDetailDTO;
import com.n19dccn112.service.UnitDetailService;
import com.n19dccn112.service.UnitService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/unitDetail")
@Tag(name = "UnitDetail")
public class UnitDetailController implements IBaseController<UnitDetailDTO, Long, UnitDetailService> {
    @Resource
    @Getter
    private UnitDetailService service;
    @GetMapping("")
    public List<UnitDetailDTO> getAll(@RequestParam(required = false) Long productId,
                                      @RequestParam(required = false) Long unitId,
                                      @RequestParam(required = false) List<Long> unitIds,
                                      @RequestParam(required = false) List<Long> unitDetailIds) {
        if (unitDetailIds != null){
            return getService().findAllByUnitDetailIds(unitDetailIds);
        }
        if (productId == null && unitId != null){
            return getService().findAllByUnitId(unitId);
        }
        if (productId != null && unitId == null && unitIds == null){
            return getService().findAllByProductId(productId);
        }
        if (productId != null && unitId != null){
            return getService().findAllByUnitIdAndProductId(unitId, productId);
        }
        if (productId != null && unitIds != null){
            return getService().findAllByUnitIdsAndProductId(unitIds, productId);
        }
        return getService().findAll();
    }
    @GetMapping("/name")
    public UnitDetailDTO get1Name(@RequestParam(required = true) Long productId,
                                  @RequestParam(required = true) Long unitId) {
        return getService().findByUserDetailIdNewSave(unitId, productId);
    }
}