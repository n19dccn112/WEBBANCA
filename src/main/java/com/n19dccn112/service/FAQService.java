package com.n19dccn112.service;

import com.n19dccn112.model.dto.FAQDTO;
import com.n19dccn112.model.entity.FAQ;
import com.n19dccn112.repository.FAQRepository;
import com.n19dccn112.repository.UserProductRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
@Service
public class FAQService implements IBaseService<FAQDTO, Long>, IModelMapper<FAQDTO, FAQ> {
    private final FAQRepository faqRepository;
    private final UserProductRepository userProductRepository;
    private final ModelMapper modelMapper;

    public FAQService(FAQRepository faqRepository, UserProductRepository userProductRepository, ModelMapper modelMapper) {
        this.faqRepository = faqRepository;
        this.userProductRepository = userProductRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<FAQDTO> findAll() {
        return createFromEntities(faqRepository.findAll());
    }

    public List<FAQDTO> findAllUserProductId(Long userProductId) {
        return createFromEntities(faqRepository.findAllByUserProduct_UserProductId(userProductId));
    }

    @Override
    public FAQDTO findById(Long faqId) {
        return createFromE(faqRepository.findById(faqId).get());
    }

    @Override
    public FAQDTO update(Long faqId, FAQDTO faqdto) {
        FAQ faq = faqRepository.findById(faqId).get();
        faqRepository.save(updateEntity(faq, faqdto));
        return faqdto;
    }

    @Override
    public FAQDTO save(FAQDTO faqdto) {
        faqRepository.save(createFromD(faqdto));
        return faqdto;
    }

    @Override
    public FAQDTO delete(Long faqId) {
        FAQ faq = faqRepository.findById(faqId).get();
        try {
            faqRepository.delete(faq);
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(FAQ.class, faqId);
        }
        return createFromE(faq);
    }

    @Override
    public FAQ createFromD(FAQDTO faqdto) {
        FAQ faq =  modelMapper.map(faqdto, FAQ.class);
        faq.setUserProduct(userProductRepository.findById(faqdto.getUserProductId()).get());
        return faq;
    }

    @Override
    public FAQDTO createFromE(FAQ faq) {
        FAQDTO faqdto = modelMapper.map(faq, FAQDTO.class);
        faqdto.setUserProductId(faq.getUserProduct().getUserProductId());
        return faqdto;
    }

    @Override
    public FAQ updateEntity(FAQ faq, FAQDTO faqdto) {
        if (faq != null && faqdto != null){
            faq.setQuestion(faqdto.getQuestion());
            faq.setUserProduct(userProductRepository.findById(faqdto.getUserProductId()).get());
        }
        return faq;
    }
}
