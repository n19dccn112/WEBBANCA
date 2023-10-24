package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.CategoryDTO;
import com.n19dccn112.model.dto.DistrictDTO;
import com.n19dccn112.model.entity.District;
import com.n19dccn112.model.entity.Role;
import com.n19dccn112.service.CategoryService;
import com.n19dccn112.service.DistrictService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;


@CrossOrigin("*")
@RestController
@RequestMapping("api/districts")
@Tag(name = "District")
public class DistrictController implements IBaseController<DistrictDTO, Integer, DistrictService> {
    @Resource
    @Getter
    private DistrictService service;

    @GetMapping("")
    public List<DistrictDTO> getAll(@RequestParam(required = false) Integer provincesId) {
        if (provincesId != null){
            return getService().findAllByProvinces(provincesId);
        }
        return getService().findAll();
    }
}
