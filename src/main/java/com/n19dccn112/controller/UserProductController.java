package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.UserProductDTO;
import com.n19dccn112.service.UserProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/userProducts")
@Tag(name = "UserProduct")
public class UserProductController implements IBaseController<UserProductDTO, Long, UserProductService> {
    @Resource
    @Getter
    private UserProductService service;

    @GetMapping("")
    public List<UserProductDTO> getAll(@RequestParam(required = false) Long productId,
                                       @RequestParam(required = false) Long userId,
                                       @RequestParam(required = false) String isSeen,
                                       @RequestParam(required = false) String isLove) {
        if (isLove != null && userId != null){
            return getService().findAllByIsLove(userId);
        }
        if (isSeen != null && userId != null) {
            return getService().findAllByIsSeen(userId);
        }
        if (productId == null && userId != null){
            return getService().findAllByUserId(userId);
        }
        if (productId != null && userId == null){
            return getService().findAllByProductId(productId);
        }
        if (productId != null){
            return getService().findAllByUserIdAndProductId(userId, productId);
        }
        return getService().findAll();
    }
}