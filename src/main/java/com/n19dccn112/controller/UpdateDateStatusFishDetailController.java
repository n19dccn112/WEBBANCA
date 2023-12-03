package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.StatusFishDetailDTO;
import com.n19dccn112.model.dto.UpdateDateStatusFishDetailDTO;
import com.n19dccn112.service.StatusFishDetailService;
import com.n19dccn112.service.UpdateDateStatusFishDetailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/updateDateStatusFishDetail")
@Tag(name = "UpdateDateStatusFishDetail")
public class UpdateDateStatusFishDetailController implements IBaseController<UpdateDateStatusFishDetailDTO, Long, UpdateDateStatusFishDetailService> {
    @Resource
    @Getter
    private UpdateDateStatusFishDetailService service;

    @GetMapping("")
    public List<UpdateDateStatusFishDetailDTO> getAll(@RequestParam(required = false) Long statusFishId,
                                      @RequestParam(required = false) Long unitDetailId) {
        return getService().findAll();
    }
}