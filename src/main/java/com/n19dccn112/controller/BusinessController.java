package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.BusinessDTO;
import com.n19dccn112.service.BusinessService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/business")
@Tag(name = "Business")
public class BusinessController implements IBaseController<BusinessDTO, Long, BusinessService> {
    @Resource
    @Getter
    private BusinessService service;

    @GetMapping("")
    public List<BusinessDTO> getAll() {
        return getService().findAll();
    }
}
