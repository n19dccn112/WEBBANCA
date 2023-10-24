package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.CategoryTypeDTO;
import com.n19dccn112.service.CategoryTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/categoryTypes")
@Tag(name = "CategoryType")
public class CategoryTypeController implements IBaseController<CategoryTypeDTO, Long, CategoryTypeService> {
    @Resource
    @Getter
    private CategoryTypeService service;

    @GetMapping("")
    public List<CategoryTypeDTO> getAll() {
        return getService().findAll();
    }
}
