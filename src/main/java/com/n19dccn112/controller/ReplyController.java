package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.EventStatusDTO;
import com.n19dccn112.model.dto.ReplyDTO;
import com.n19dccn112.service.EventStatusService;
import com.n19dccn112.service.ReplyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@CrossOrigin("*")
@RestController
@RequestMapping("api/reply")
@Tag(name = "Reply")
public class ReplyController implements IBaseController<ReplyDTO, Long, ReplyService> {
    @Resource
    @Getter
    private ReplyService service;

    @GetMapping("")
    public List<ReplyDTO> getAll(@RequestParam(required = false) Long faqId) {
        if (faqId != null){
            return getService().findAllByFaqId(faqId);
        }
        return getService().findAll();
    }
}

