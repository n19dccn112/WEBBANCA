package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.EventStatusDTO;
import com.n19dccn112.model.dto.FeatureDetailDTO;
import com.n19dccn112.model.entity.FeatureDetail;
import com.n19dccn112.service.EventStatusService;
import com.n19dccn112.service.FeatureDetailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/featureDetails")
@Tag(name = "FeatureDetail")
public class FeatureDetailController implements IBaseController<FeatureDetailDTO, Long, FeatureDetailService> {
    @Resource
    @Getter
    private FeatureDetailService service;

    @GetMapping("")
    public List<FeatureDetailDTO> getAll(@RequestParam(required = false) Long unitDetailId,
                                       @RequestParam(required = false) Long featureId) {
        if (unitDetailId == null && featureId != null){
            return getService().findAllFeatureId(featureId);
        }
        if (unitDetailId != null && featureId == null){
            return getService().findAllUnitDetailId(unitDetailId);
        }
        if (unitDetailId != null && featureId != null){
            return getService().findAlFeatureIdAndUnitDetailId(featureId, unitDetailId);
        }
        return getService().findAll();
    }
}
