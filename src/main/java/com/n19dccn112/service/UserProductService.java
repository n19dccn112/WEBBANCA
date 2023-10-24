package com.n19dccn112.service;

import com.n19dccn112.model.dto.UserProductDTO;
import com.n19dccn112.model.entity.UserProduct;
import com.n19dccn112.repository.ProductRepository;
import com.n19dccn112.repository.UserProductRepository;
import com.n19dccn112.repository.UserRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
@Service
public class UserProductService implements IBaseService<UserProductDTO, Long>, IModelMapper<UserProductDTO, UserProduct> {
    private final UserProductRepository userProductRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private ModelMapper modelMapper;

    public UserProductService(UserProductRepository userProductRepository, UserRepository userRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.userProductRepository = userProductRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserProductDTO> findAll() {
        return createFromEntities(userProductRepository.findAll());
    }


    public List<UserProductDTO> findAllByProductId(Long productId) {
        return createFromEntities(userProductRepository.findAllByProduct_ProductId(productId));
    }
    public List<UserProductDTO> findAllByUserId(Long userId) {
        return createFromEntities(userProductRepository.findAllByUser_UserId(userId));
    }
    public List<UserProductDTO> findAllByUserIdAndProductId(Long userId, Long productId) {
        return createFromEntities(userProductRepository.findAllByUser_UserIdAndProduct_ProductId(userId, productId));
    }

    public List<UserProductDTO> findAllByIsLove(Long userId){
        return createFromEntities(userProductRepository.findAllByIsLove(userId));
    }
    public List<UserProductDTO> findAllByIsSeen(Long userId){
        return createFromEntities(userProductRepository.findAllByIsSeen(userId));
    }

    @Override
    public UserProductDTO findById(Long userProductId) {
        return createFromE(userProductRepository.findById(userProductId).get());
    }

    @Override
    public UserProductDTO update(Long userProductId, UserProductDTO userProductDTO) {
        UserProduct userProduct = userProductRepository.findById(userProductId).get();
        userProductRepository.save(updateEntity(userProduct, userProductDTO));
        return createFromE(userProductRepository.findById(userProductId).get());
    }

    @Override
    public UserProductDTO save(UserProductDTO userProductDTO) {
        userProductRepository.save(createFromD(userProductDTO));
        Long userProductId = userProductRepository.findUserByComment(userProductDTO.getComment());
        userProductDTO.setUserProductId(userProductId);
        return userProductDTO;
    }

    @Override
    public UserProductDTO delete(Long userProductId) {
        UserProduct userProduct = userProductRepository.findById(userProductId).get();
        try {
            userProductRepository.delete(userProduct);
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(UserProduct.class, userProductId);
        }
        return createFromE(userProduct);
    }

    @Override
    public UserProduct createFromD(UserProductDTO userProductDTO) {
        UserProduct userProduct = modelMapper.map(userProductDTO, UserProduct.class);
        userProduct.setProduct(productRepository.findById(userProductDTO.getProductId()).get());
        userProduct.setUser(userRepository.findById(userProductDTO.getUserId()).get());
        return userProduct;
    }

    @Override
    public UserProductDTO createFromE(UserProduct userProduct) {
        UserProductDTO userProductDTO = modelMapper.map(userProduct, UserProductDTO.class);
        userProductDTO.setProductId(userProduct.getProduct().getProductId());
        userProductDTO.setUserId(userProduct.getUser().getUserId());
        return userProductDTO;
    }

    @Override
    public UserProduct updateEntity(UserProduct userProduct, UserProductDTO userProductDTO) {
        if (userProduct != null && userProductDTO != null){
            if (userProductDTO.getComment() != null && userProductDTO.getPoint() != null){
                userProduct.setComment(userProductDTO.getComment());
                userProduct.setPoint(userProductDTO.getPoint());
            }
            if (userProductDTO.getIsLove() != null) {
                userProduct.setIsLove(userProductDTO.getIsLove());
            }
            if (userProductDTO.getIsSeen() != null) {
                userProduct.setIsSeen(userProductDTO.getIsSeen());
            }
            if (userProductDTO.getIdUserProductReply() != null) {
                userProduct.setIdUserProductReply(userProductDTO.getIdUserProductReply());
            }
        }
        return userProduct;
    }
}
