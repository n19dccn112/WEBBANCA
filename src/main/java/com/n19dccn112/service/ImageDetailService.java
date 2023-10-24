package com.n19dccn112.service;

import com.n19dccn112.model.dto.ImageDetailDTO;
import com.n19dccn112.model.dto.ImageDetailDTO;
import com.n19dccn112.model.entity.ImageDetail;
import com.n19dccn112.repository.ImageDetailRepository;
import com.n19dccn112.repository.ImageRepository;
import com.n19dccn112.repository.ProductRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Service
public class ImageDetailService implements IBaseService<ImageDetailDTO, Long>, IModelMapper<ImageDetailDTO, ImageDetail> {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final ImageDetailRepository imageDetailRepository;
    private final ModelMapper modelMapper;

    public ImageDetailService(ImageRepository imageRepository, ProductRepository productRepository, ImageDetailRepository imageDetailRepository, ModelMapper modelMapper) {
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
        this.imageDetailRepository = imageDetailRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ImageDetailDTO> findAll() {
        return createFromEntities(imageDetailRepository.findAll());
    }

    public List<ImageDetailDTO> findAllProductId(Long productId) {
        return createFromEntities(imageDetailRepository.findAllByProduct_ProductId(productId));
    }

    public List<ImageDetailDTO> findAllImageId(Long imageId) {
        return createFromEntities(imageDetailRepository.findAllByImage_ImageId(imageId));
    }

    public List<ImageDetailDTO> findAlImageIdAndProductId(Long imageId, Long productId) {
        return createFromEntities(imageDetailRepository.findAllByImage_ImageIdAndProduct_ProductId(imageId, productId));
    }

    @Override
    public ImageDetailDTO findById(Long imageDetailId) {
        return createFromE(imageDetailRepository.findById(imageDetailId).get());
    }

    @Override
    public ImageDetailDTO update(Long imageDetailId, ImageDetailDTO imageDetailDTO) {
        ImageDetail imageDetail = imageDetailRepository.findById(imageDetailId).get();
        imageDetailRepository.save(updateEntity(imageDetail, imageDetailDTO));
        return imageDetailDTO;
    }

    @Override
    public ImageDetailDTO save(ImageDetailDTO imageDetailDTO) {
        imageDetailRepository.save(createFromD(imageDetailDTO));
        return imageDetailDTO;
    }

    @Override
    public ImageDetailDTO delete(Long imageDetailId) {
        ImageDetail imageDetail = imageDetailRepository.findById(imageDetailId).get();
        try {
            imageDetailRepository.delete(imageDetail);
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(ImageDetail.class, imageDetailId);
        }
        return createFromE(imageDetail);
    }

    @Override
    public ImageDetail createFromD(ImageDetailDTO imageDetailDTO) {
        ImageDetail imageDetail = modelMapper.map(imageDetailDTO, ImageDetail.class);
        imageDetail.setImage(imageRepository.findById(imageDetail.getImageDetailId()).get());
        imageDetail.setProduct(productRepository.findById(imageDetailDTO.getProductId()).get());
        return imageDetail;
    }

    @Override
    public ImageDetailDTO createFromE(ImageDetail imageDetail) {
        ImageDetailDTO imageDetailDTO = modelMapper.map(imageDetail, ImageDetailDTO.class);
        imageDetailDTO.setImageId(imageDetail.getImage().getImageId());
        imageDetailDTO.setProductId(imageDetail.getProduct().getProductId());
        return imageDetailDTO;
    }

    @Override
    public ImageDetail updateEntity(ImageDetail imageDetail, ImageDetailDTO imageDetailDTO) {
        if (imageDetail != null && imageDetailDTO != null){
            imageDetail.setImage(imageRepository.findById(imageDetail.getImageDetailId()).get());
            imageDetail.setProduct(productRepository.findById(imageDetailDTO.getProductId()).get());
        }
        return imageDetail;
    }
}
