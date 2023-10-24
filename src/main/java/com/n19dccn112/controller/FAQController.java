package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.FAQDTO;
import com.n19dccn112.service.FAQService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/fqa")
@Tag(name = "FQA")
public class FAQController implements IBaseController<FAQDTO, Long, FAQService> {
    @Resource
    @Getter
    private FAQService service;
    @GetMapping("")
    public List<FAQDTO> getAll(@RequestParam(required = false) Long userProductId) {
        if (userProductId != null){
            return getService().findAllUserProductId(userProductId);
        }
        return getService().findAll();
    }
}
