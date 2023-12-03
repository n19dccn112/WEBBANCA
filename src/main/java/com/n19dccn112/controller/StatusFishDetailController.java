package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.StatusFishDTO;
import com.n19dccn112.model.dto.StatusFishDetailDTO;
import com.n19dccn112.service.StatusFishDetailService;
import com.n19dccn112.service.StatusFishService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/statusFishDetail")
@Tag(name = "StatusFishDetail")
public class StatusFishDetailController implements IBaseController<StatusFishDetailDTO, Long, StatusFishDetailService> {
    @Resource
    @Getter
    private StatusFishDetailService service;

    @GetMapping("")
    public List<StatusFishDetailDTO> getAll(@RequestParam(required = false) Long statusFishId,
                                      @RequestParam(required = false) Long unitDetailId) {
        if (statusFishId == null && unitDetailId != null){
            return getService().findAllUnitDetailId(unitDetailId);
        }
        if (statusFishId != null && unitDetailId == null){
            return getService().findAllByStatusFishId(statusFishId);
        }
        if (statusFishId != null){
            return getService().findAllUnitDetailIdAndStatusFishId(unitDetailId, statusFishId);
        }
        return getService().findAll();
    }
}