package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.PondDTO;
import com.n19dccn112.model.dto.ProductDTO;
import com.n19dccn112.service.PondService;
import com.n19dccn112.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/ponds")
@Tag(name = "Pond")
public class PondController implements IBaseController<PondDTO, Long, PondService> {
    @Resource
    @Getter
    private PondService service;

    @GetMapping("")
    public List<PondDTO> getAll(@RequestParam(required = false) Long unitDetailId){
        if(unitDetailId != null){
            return getService().findAllByUnitDetailId(unitDetailId);
        }
        return getService().findAll();
    }
}

