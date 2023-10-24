package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.UserDTO;
import com.n19dccn112.model.dto.UserDetailDTO;
import com.n19dccn112.service.UserDetailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/userDetails")
@Tag(name = "UserDetail")
public class UserDetailController implements IBaseController<UserDetailDTO, Long, UserDetailService> {
    @Resource
    @Getter
    private UserDetailService service;

    @GetMapping("")
    public List<UserDetailDTO> getAll(@RequestParam(required = false) Long userId) {
        if (userId != null){
            return getService().findAllByUserId(userId);
        }
        return getService().findAll();
    }
    @GetMapping("/name/{name}")
    public UserDetailDTO get1Name(@PathVariable String name) {
        return getService().findByUserDetailIdNewSave(name);
    }
}
