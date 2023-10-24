package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.FeatureDTO;
import com.n19dccn112.model.dto.UnitDTO;
import com.n19dccn112.model.dto.UnitDetailDTO;
import com.n19dccn112.model.entity.UnitDetail;
import com.n19dccn112.service.FeatureService;
import com.n19dccn112.service.UnitService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/unit")
@Tag(name = "Unit")
public class UnitController implements IBaseController<UnitDTO, Long, UnitService> {
    @Resource
    @Getter
    private UnitService service;
    @GetMapping("")
    public List<UnitDTO> getAll(@RequestParam(required = false) Long productId,
                                @RequestParam(required = false) List<Long> unitIds) {
        if (productId != null){
            return getService().findAllByProductId(productId);
        }
        if (unitIds != null){
            return getService().findAllByUnits(unitIds);
        }
        return getService().findAll();
    }
}
