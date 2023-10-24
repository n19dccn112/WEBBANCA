package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.CategoryDetailDTO;
import com.n19dccn112.model.dto.CategoryTypeDTO;
import com.n19dccn112.service.CategoryDetailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/categoryDetail")
@Tag(name = "CategoryDetail")
public class CategoryDetailController implements IBaseController<CategoryDetailDTO, Long, CategoryDetailService> {
    @Resource
    @Getter
    private CategoryDetailService service;

    @GetMapping("")
    public List<CategoryDetailDTO> getAll(@RequestParam(required = false) Long productId,
                                          @RequestParam(required = false) Long categoryId) {
        if (productId == null && categoryId != null){
            return getService().findAllCategoryId(categoryId);
        }
        if (productId != null && categoryId == null){
            return getService().findAllProductId(productId);
        }
        if (productId != null && categoryId != null){
            return getService().findAlCategoryIdAndProductId(categoryId, productId);
        }
        return getService().findAll();
    }
}

