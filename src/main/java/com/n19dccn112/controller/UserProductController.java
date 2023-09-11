package com.n19dccn112.controller;

import com.n19dccn112.model.dto.UserProductDTO;
import com.n19dccn112.service.UserProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/rates")
@Tag(name = "Rate")
public class RateController {
    @Resource
    @Getter
    private UserProductService service;

    @GetMapping("")
    public List<UserProductDTO> getAll(@RequestParam(required = false) Long productId) {
        if (productId == null)
            return getService().findAll();
        else
            return getService().findAll(productId);
    }

    @GetMapping("/{userId}-{productId}")
    public UserProductDTO get1(@PathVariable Long userId, @PathVariable Long productId) {
        return service.findById(userId, productId);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{userId}-{productId}")
    public UserProductDTO update(@PathVariable Long userId, @PathVariable Long productId, @Valid @RequestBody UserProductDTO dto) {
        dto.setProductId(productId);
        dto.setUserId(userId);
        return service.update(userId, productId, dto);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{userId}-{productId}")
    public UserProductDTO delete(@PathVariable Long userId, @PathVariable Long productId) {
        return service.delete(userId, productId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public UserProductDTO insert(@Valid @RequestBody UserProductDTO d) {
        return getService().save(d);
    }
}
