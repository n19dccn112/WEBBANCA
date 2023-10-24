package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.EventStatusDTO;
import com.n19dccn112.model.dto.ProductDTO;
import com.n19dccn112.model.dto.UnitDetailDTO;
import com.n19dccn112.service.EventStatusService;
import com.n19dccn112.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@CrossOrigin("*")
@RestController
@RequestMapping("api/products")
@Tag(name = "Product")
public class ProductController implements IBaseController<ProductDTO, Long, ProductService> {
    @Resource
    @Getter
    private ProductService service;

    @GetMapping("")
    public List<ProductDTO> getAll(@RequestParam(required = false) Long eventId,
                                   @RequestParam(required = false) Long categoryId,
                                   @RequestParam(required = false) Long categoryTypeId,
                                   @RequestParam(required = false) List<Long> featureIds,
                                   @RequestParam(required = false) List<Long> productIds,
                                   @RequestParam(required = false) List<Long> unitDetailIds) {
        if (unitDetailIds != null){
            return getService().findAllByUnitIdDetails(unitDetailIds);
        }
        if (eventId != null && categoryId == null && categoryTypeId == null && featureIds == null && productIds == null) {
            return getService().findAllByEventId(eventId);
        }
        if (eventId == null && categoryTypeId != null && featureIds == null && productIds == null) {
            return getService().findAllByCategoryTypeId(categoryTypeId);
        }
        if (eventId == null && categoryId != null && featureIds == null && productIds == null) {
            return getService().findAllByCategoryId(categoryId);
        }
        if (eventId == null && categoryId == null && categoryTypeId == null && featureIds != null && productIds == null) {
            return getService().findAllByFeatureIds(featureIds);
        }
        if (eventId == null && categoryId == null && categoryTypeId == null && featureIds == null && productIds != null) {
            return getService().findAllByProductIds(productIds);
        }



        if (eventId != null && categoryId != null && featureIds == null && productIds == null) {
            return getService().findAllByEventIdAndCategoryId(eventId, categoryId);
        }
        if (eventId != null && categoryTypeId != null && featureIds == null && productIds == null) {
            return getService().findAllByEventIdAndCategoryTypeId(eventId, categoryTypeId);
        }
        if (eventId != null && categoryId == null && categoryTypeId == null && featureIds != null && productIds == null) {
            return getService().findAllByEventIdAndFeatureIds(eventId, featureIds);
        }
        if (eventId != null && categoryId == null && categoryTypeId == null && featureIds == null && productIds != null) {
            return getService().findAllByEventIdAndProductIds(eventId, productIds);
        }
        if (eventId == null && categoryTypeId != null && featureIds != null && productIds == null) {
            return getService().findAllByCategoryTypeIdAndFeatureIds(categoryTypeId, featureIds);
        }
        if (eventId == null && categoryTypeId != null && featureIds == null && productIds != null) {
            return getService().findAllByCategoryTypeIdAndProductIds(categoryTypeId, productIds);
        }
        if (eventId == null && categoryId != null && featureIds != null && productIds == null) {
            return getService().findAllByCategoryIdAndFeatureIds(categoryId, featureIds);
        }
        if (eventId == null && categoryId != null && featureIds == null && productIds != null) {
            return getService().findAllByCategoryIdAndProductIds(categoryId, productIds);
        }
        if (eventId == null && categoryId == null && categoryTypeId == null && featureIds != null && productIds != null) {
            return getService().findAllByFeatureIdsAndProductIds(featureIds, productIds);
        }



        if (eventId != null && categoryTypeId != null && featureIds != null && productIds == null) {
            return getService().findAllByEventIdAndCategoryTypeIdAndFeatureIds(eventId, categoryTypeId, featureIds);
        }
        if (eventId != null && categoryId != null && featureIds != null && productIds == null) {
            return getService().findAllByEventIdAndCategoryIdAndFeatureIds(eventId, categoryId, featureIds);
        }
        if (eventId != null && categoryId == null && categoryTypeId == null && featureIds != null && productIds != null) {
            return getService().findAllByEventIdAndFeatureIdsAndProductIds(eventId, featureIds, productIds);
        }
        if (eventId != null && categoryTypeId != null && featureIds == null && productIds != null) {
            return getService().findAllByEventIdAndCategoryTypeIdAndProductIds(eventId, categoryTypeId, productIds);
        }
        if (eventId == null && categoryTypeId != null && featureIds != null && productIds != null) {
            return getService().findAllByCategoryTypeIdAndFeatureIdsAndProductIds(categoryTypeId, featureIds, productIds);
        }
        if (eventId != null && categoryId != null && featureIds == null && productIds != null) {
            return getService().findAllByEventIdAndCategoryIdAndProductIds(eventId, categoryId, productIds);
        }
        if (eventId == null && categoryId != null && featureIds != null && productIds != null) {
            return getService().findAllByCategoryIdAndFeatureIdsAndProductIds(categoryId, featureIds, productIds);
        }



        if (eventId != null && categoryTypeId != null && featureIds != null && productIds != null) {
            return getService().findAllByEventIdAndCategoryTypeIdAndFeatureIdsProductIds(eventId, categoryTypeId, featureIds, productIds);
        }
        if (eventId != null && categoryId != null && featureIds != null && productIds != null) {
            return getService().findAllByEventIdAndCategoryIdAndFeatureIdsProductIds(eventId, categoryId, featureIds, productIds);
        }


        return getService().findAll();
    }

    @GetMapping("/name")
    public ProductDTO get1Name(@RequestParam(required = true) String name) {
        return getService().findByProductIdNewSave(name);
    }
}

