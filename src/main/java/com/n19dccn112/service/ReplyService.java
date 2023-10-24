package com.n19dccn112.service;

import com.n19dccn112.model.dto.ReplyDTO;
import com.n19dccn112.model.entity.Reply;
import com.n19dccn112.repository.FAQRepository;
import com.n19dccn112.repository.ReplyRepository;
import com.n19dccn112.repository.StatusFishRepository;
import com.n19dccn112.repository.UserProductRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReplyService implements IBaseService<ReplyDTO, Long>, IModelMapper<ReplyDTO, Reply> {
    private final ReplyRepository replyRepository;
    private final FAQRepository faqRepository;
    private final ModelMapper modelMapper;

    public ReplyService(ReplyRepository replyRepository, FAQRepository faqRepository, ModelMapper modelMapper) {
        this.replyRepository = replyRepository;
        this.faqRepository = faqRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ReplyDTO> findAll() {
        return createFromEntities(replyRepository.findAll());
    }

    public List<ReplyDTO> findAllByFaqId(Long faqId) {
        return createFromEntities(replyRepository.findAllByFaq_FaqId(faqId));
    }

    @Override
    public ReplyDTO findById(Long replyId) {
        return createFromE(replyRepository.findById(replyId).get());
    }

    @Override
    public ReplyDTO update(Long replyId, ReplyDTO replyDTO) {
        Reply reply = replyRepository.findById(replyId).get();
        replyRepository.save(updateEntity(reply, replyDTO));
        return replyDTO;
    }

    @Override
    public ReplyDTO save(ReplyDTO replyDTO) {
        replyRepository.save(createFromD(replyDTO));
        return replyDTO;
    }

    @Override
    public ReplyDTO delete(Long replyId) {
        Reply reply = replyRepository.findById(replyId).get();
        return createFromE(reply);
    }

    @Override
    public Reply createFromD(ReplyDTO replyDTO) {
        Reply reply = modelMapper.map(replyDTO, Reply.class);
        reply.setFaq(faqRepository.findById(replyDTO.getFaqId()).get());
        return reply;
    }

    @Override
    public ReplyDTO createFromE(Reply reply) {
        ReplyDTO replyDTO = modelMapper.map(reply, ReplyDTO.class);
        replyDTO.setReplyId(replyDTO.getReplyId());
        return replyDTO;
    }

    @Override
    public Reply updateEntity(Reply reply, ReplyDTO replyDTO) {
        if (reply != null && replyDTO != null){
            reply.setReply(replyDTO.getReply());
            reply.setFaq(faqRepository.findById(replyDTO.getFaqId()).get());
        }
        return reply;
    }
}
