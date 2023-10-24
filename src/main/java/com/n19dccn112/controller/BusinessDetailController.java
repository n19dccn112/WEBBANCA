package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.BusinessDTO;
import com.n19dccn112.model.dto.BusinessDetailDTO;
import com.n19dccn112.service.BusinessDetailService;
import com.n19dccn112.service.BusinessService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@CrossOrigin("*")
@RestController
@RequestMapping("api/businessDetail")
@Tag(name = "BusinessDetail")
public class BusinessDetailController implements IBaseController<BusinessDetailDTO, Long, BusinessDetailService> {
    @Resource
    @Getter
    private BusinessDetailService service;

    @GetMapping("")
    public List<BusinessDetailDTO> getAll(@RequestParam(required = false) Long productId,
                                          @RequestParam(required = false) Long businessId) {
        if (productId == null && businessId != null){
            return getService().findAllBusinessId(businessId);
        }
        if (productId != null && businessId == null){
            return getService().findAllProductId(productId);
        }
        if (productId != null && businessId != null){
            return getService().findAllBusinessIdAndProductId(businessId, productId);
        }
        return getService().findAll();
    }
}
