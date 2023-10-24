package com.n19dccn112.service;

import com.n19dccn112.model.dto.ImageDTO;
import com.n19dccn112.model.entity.Image;
import com.n19dccn112.model.entity.ImageDetail;
import com.n19dccn112.repository.ImageDetailRepository;
import com.n19dccn112.repository.ImageRepository;
import com.n19dccn112.repository.ProductRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Service
public class ImageService implements IBaseService<ImageDTO, Long>, IModelMapper<ImageDTO, Image> {
    private final ImageRepository imageRepository;
    private final ImageDetailRepository imageDetailRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ImageService(ImageRepository imageRepository, ImageDetailRepository imageDetailRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.imageRepository = imageRepository;
        this.imageDetailRepository = imageDetailRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ImageDTO> findAll() {
        return createFromEntities(imageRepository.findAll());
    }
    public List<ImageDTO> findAllByProductId(Long productId){
        return createFromEntities(imageRepository.findAllByProductId(productId));
    }

    @Override
    public ImageDTO findById(Long imageId) {
        return createFromE(imageRepository.findById(imageId).get());
    }

    @Override
    public ImageDTO update(Long imageId, ImageDTO imageDTO) {
        Image image = imageRepository.findById(imageId).get();
        imageRepository.save(updateEntity(image, imageDTO));
        return imageDTO;
    }
    @Transactional
    @Override
    public ImageDTO save(ImageDTO imageDTO) {
        imageRepository.save(createFromD(imageDTO));
        if(imageDTO.getProductId() != null){
            ImageDetail imageDetail = new ImageDetail();
            imageDetail.setProduct(productRepository.findById(imageDTO.getProductId()).get());
            imageDetail.setImage(imageRepository.findById(imageRepository.imageIdNewSave(imageDTO.getUrl())).get());
            imageDetailRepository.save(imageDetail);
        }
        return imageDTO;
    }

    @Override
    public ImageDTO delete(Long imageId) {
        Image image = imageRepository.findById(imageId).get();
        try {
            imageRepository.delete(image);
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(Image.class, imageId);
        }
        return createFromE(image);
    }

    @Override
    public Image createFromD(ImageDTO imageDTO) {
        Image image = modelMapper.map(imageDTO, Image.class);
        return image;
    }

    @Override
    public ImageDTO createFromE(Image image) {
        ImageDTO imageDTO = modelMapper.map(image, ImageDTO.class);
        return imageDTO;
    }

    @Override
    public Image updateEntity(Image image, ImageDTO imageDTO) {
        if (image != null && imageDTO != null){
            image.setUrl(imageDTO.getUrl());
        }
        return image;
    }
}
