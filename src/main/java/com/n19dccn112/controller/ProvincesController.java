package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.DistrictDTO;
import com.n19dccn112.model.dto.ProvincesDTO;
import com.n19dccn112.service.DistrictService;
import com.n19dccn112.service.ProvincesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/provinces")
@Tag(name = "Provinces")
public class ProvincesController implements IBaseController<ProvincesDTO, Integer, ProvincesService> {
    @Resource
    @Getter
    private ProvincesService service;

    @GetMapping("")
    public List<ProvincesDTO> getAll() {
        return getService().findAll();
    }
}
