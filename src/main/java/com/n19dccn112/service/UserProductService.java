package com.n19dccn112.service;

import com.n19dccn112.model.dto.UserProductDTO;
import com.n19dccn112.model.entity.UserProduct;
import com.n19dccn112.model.key.RateId;
import com.n19dccn112.repository.ProductRepository;
import com.n19dccn112.repository.RateRepository;
import com.n19dccn112.repository.UserRepository;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import com.n19dccn112.service.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;
@Service
public class RateService implements IModelMapper<UserProductDTO, UserProduct> {
    private final RateRepository rateRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public RateService(RateRepository ratesRepository, UserRepository userRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.rateRepository = ratesRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public RateId rateId(Long userId, Long productId){
        RateId rateId = new RateId();
        rateId.setUser(userRepository.getById(userId));
        rateId.setProduct(productRepository.getById(productId));
        return rateId;
    }

    public List<UserProductDTO> findAll() {
        return createFromEntities(rateRepository.findAll());
    }

    public List<UserProductDTO> findAll(Long productId) {
        return createFromEntities(rateRepository.findAllByRateIdProduct_ProductId(productId));
    }

    public UserProductDTO findById(Long userId, Long productId) {
        RateId rateId = rateId(userId, productId);
        UserProduct rate = rateRepository.findById(rateId).orElseThrow(() -> new NotFoundException(rateId));
        return createFromE(rate);
    }

    public UserProductDTO update(Long userId, Long productId, UserProductDTO rateDTO) {
        RateId rateId = rateId(userId, productId);
        Optional <UserProduct> rate = rateRepository.findById(rateId);
        rate.orElseThrow(() -> new NotFoundException(rateId));
        rateRepository.save(updateEntity(rate.get(), rateDTO));
        return createFromE(rate.get());
    }

    public UserProductDTO save(UserProductDTO rateDTO) {
        UserProduct rate = createFromD(rateDTO);
        rateRepository.save(rate);
        return createFromE(rate);
    }

    public UserProductDTO delete(Long userId, Long productId) {
        RateId rateId = rateId(userId, productId);
        Optional <UserProduct> rate = rateRepository.findById(rateId);
        rate.orElseThrow(() -> new NotFoundException(rateId));
        UserProductDTO rateDTO = createFromE(rate.get());
        try {
            rateRepository.delete(rate.get());
        }
        catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(UserProductDTO.class, rateId);
        }
        return rateDTO;
    }

    public UserProduct createFromD(UserProductDTO rateDTO) {
        UserProduct rate = modelMapper.map(rateDTO, UserProduct.class);
        RateId rateId = rateId(rateDTO.getUserId(), rateDTO.getProductId());
        rate.setRateId(rateId);
        return rate;
    }

    public UserProductDTO createFromE(UserProduct rate) {
        UserProductDTO rateDTO = modelMapper.map(rate, UserProductDTO.class);
        rateDTO.setProductId(rate.getRateId().getProduct().getProductId());
        Long userId = rate.getRateId().getUser().getUserId();
        rateDTO.setUserId(userId);
        rateDTO.setUsername(userRepository.findById(userId).get().getUsername());
        return rateDTO;
    }

    public UserProduct updateEntity(UserProduct rate, UserProductDTO rateDTO) {
        if (rate != null && rateDTO != null){
            RateId rateId = rateId(rateDTO.getUserId(), rateDTO.getProductId());
            rate.setRateId(rateId);
            rate.setPoint(rateDTO.getPoint());
            rate.setComment(rateDTO.getComment());
        }
        return rate;
    }
}
