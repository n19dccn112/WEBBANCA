package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.DistrictDTO;
import com.n19dccn112.model.dto.WardDTO;
import com.n19dccn112.service.DistrictService;
import com.n19dccn112.service.WardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@CrossOrigin("*")
@RestController
@RequestMapping("api/wards")
@Tag(name = "Ward")
public class WardController implements IBaseController<WardDTO, Integer, WardService> {
    @Resource
    @Getter
    private WardService service;

    @GetMapping("")
    public List<WardDTO> getAll(@RequestParam(required = true) Integer districtsId) {
        if (districtsId != null){
            return getService().findAllByDistrictId(districtsId);
        }
        return getService().findAll();
    }
}
