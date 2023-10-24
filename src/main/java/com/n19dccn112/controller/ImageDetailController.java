package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.EventStatusDTO;
import com.n19dccn112.model.dto.ImageDetailDTO;
import com.n19dccn112.service.EventStatusService;
import com.n19dccn112.service.ImageDetailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/imagesDetail")
@Tag(name = "ImageDetail")
public class ImageDetailController implements IBaseController<ImageDetailDTO, Long, ImageDetailService> {
    @Resource
    @Getter
    private ImageDetailService service;

    @GetMapping("")
    public List<ImageDetailDTO> getAll(@RequestParam(required = false) Long productId,
                                       @RequestParam(required = false) Long imageId) {
        if (productId == null && imageId != null){
            return getService().findAllImageId(imageId);
        }
        if (productId != null && imageId == null){
            return getService().findAllProductId(productId);
        }
        if (productId != null && imageId != null){
            return getService().findAlImageIdAndProductId(imageId, productId);
        }
        return getService().findAll();
    }
}