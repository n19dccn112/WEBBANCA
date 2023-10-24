package com.n19dccn112.service;

import com.n19dccn112.model.dto.PaymentMethodDTO;
import com.n19dccn112.model.entity.PaymentMethod;
import com.n19dccn112.repository.PaymentMethodRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
@Service
public class PaymentMethodService implements IBaseService<PaymentMethodDTO, Long>, IModelMapper<PaymentMethodDTO, PaymentMethod> {
    private final PaymentMethodRepository paymentMethodRepository;
    private final ModelMapper modelMapper;

    public PaymentMethodService(PaymentMethodRepository paymentMethodRepository, ModelMapper modelMapper) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PaymentMethodDTO> findAll() {
        return createFromEntities(paymentMethodRepository.findAll());
    }

    @Override
    public PaymentMethodDTO findById(Long paymentMethodId) {
        return createFromE(paymentMethodRepository.findById(paymentMethodId).get());
    }

    @Override
    public PaymentMethodDTO update(Long paymentMethodId, PaymentMethodDTO paymentMethodDTO) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId).get();
        paymentMethodRepository.save(updateEntity(paymentMethod, paymentMethodDTO));
        return paymentMethodDTO;
    }

    @Override
    public PaymentMethodDTO save(PaymentMethodDTO paymentMethodDTO) {
        paymentMethodRepository.save(createFromD(paymentMethodDTO));
        return paymentMethodDTO;
    }

    @Override
    public PaymentMethodDTO delete(Long paymentMethodId) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId).get();
        try {
            paymentMethodRepository.save(paymentMethod);
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(PaymentMethod.class, paymentMethodId);
        }
        return createFromE(paymentMethod);
    }

    @Override
    public PaymentMethod createFromD(PaymentMethodDTO paymentMethodDTO) {
        PaymentMethod paymentMethod = modelMapper.map(paymentMethodDTO, PaymentMethod.class);
        return paymentMethod;
    }

    @Override
    public PaymentMethodDTO createFromE(PaymentMethod paymentMethod) {
        PaymentMethodDTO paymentMethodDTO = modelMapper.map(paymentMethod, PaymentMethodDTO.class);
        return paymentMethodDTO;
    }

    @Override
    public PaymentMethod updateEntity(PaymentMethod paymentMethod, PaymentMethodDTO paymentMethodDTO) {
        if (paymentMethod != null && paymentMethodDTO != null){
            paymentMethod.setPaymentMethodName(paymentMethodDTO.getPaymentMethodName());
        }
        return paymentMethod;
    }
}
