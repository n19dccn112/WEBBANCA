package com.n19dccn112.service;

import com.n19dccn112.model.dto.CategoryDetailDTO;
import com.n19dccn112.model.entity.CategoryDetail;
import com.n19dccn112.repository.CategoryDetailRepository;
import com.n19dccn112.repository.CategoryRepository;
import com.n19dccn112.repository.ProductRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
@Service
public class CategoryDetailService implements IBaseService<CategoryDetailDTO, Long>, IModelMapper<CategoryDetailDTO, CategoryDetail> {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CategoryDetailRepository categoryDetailRepository;
    private final ModelMapper modelMapper;

    public CategoryDetailService(CategoryRepository categoryRepository, ProductRepository productRepository, CategoryDetailRepository categoryDetailRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.categoryDetailRepository = categoryDetailRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CategoryDetailDTO> findAll() {
        return createFromEntities(categoryDetailRepository.findAll());
    }

    public List<CategoryDetailDTO> findAllProductId(Long productId) {
        return createFromEntities(categoryDetailRepository.findAllByProduct_ProductId(productId));
    }

    public List<CategoryDetailDTO> findAllCategoryId(Long categoryId) {
        return createFromEntities(categoryDetailRepository.findAllByCategory_CategoryId(categoryId));
    }

    public List<CategoryDetailDTO> findAlCategoryIdAndProductId(Long categoryId, Long productId) {
        return createFromEntities(categoryDetailRepository.findAllByCategory_CategoryIdAndProduct_ProductId(categoryId, productId));
    }

    @Override
    public CategoryDetailDTO findById(Long categoryDetailId) {
        return createFromE(categoryDetailRepository.findById(categoryDetailId).get());
    }

    @Override
    public CategoryDetailDTO update(Long categoryDetailId, CategoryDetailDTO categoryDetailDTO) {
        CategoryDetail categoryDetail = categoryDetailRepository.findById(categoryDetailId).get();
        categoryDetailRepository.save(updateEntity(categoryDetail, categoryDetailDTO));
        return createFromE(categoryDetail);
    }

    @Override
    public CategoryDetailDTO save(CategoryDetailDTO categoryDetailDTO) {
        categoryDetailRepository.save(createFromD(categoryDetailDTO));
        return categoryDetailDTO;
    }

    @Override
    public CategoryDetailDTO delete(Long categoryDetailId) {
        CategoryDetail categoryDetail = categoryDetailRepository.findById(categoryDetailId).get();
        try {
            categoryDetailRepository.delete(categoryDetail);
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(CategoryDetail.class, categoryDetailId);
        }
        return createFromE(categoryDetail);
    }

    @Override
    public CategoryDetail createFromD(CategoryDetailDTO categoryDetailDTO) {
        CategoryDetail categoryDetail = modelMapper.map(categoryDetailDTO, CategoryDetail.class);
        categoryDetail.setCategory(categoryRepository.findById(categoryDetailDTO.getCategoryId()).get());
        categoryDetail.setProduct(productRepository.findById(categoryDetailDTO.getProductId()).get());
        return categoryDetail;
    }

    @Override
    public CategoryDetailDTO createFromE(CategoryDetail categoryDetail) {
        CategoryDetailDTO categoryDetailDTO = modelMapper.map(categoryDetail, CategoryDetailDTO.class);
        categoryDetailDTO.setCategoryId(categoryDetail.getCategory().getCategoryId());
        categoryDetailDTO.setProductId(categoryDetail.getProduct().getProductId());
        return categoryDetailDTO;
    }

    @Override
    public CategoryDetail updateEntity(CategoryDetail categoryDetail, CategoryDetailDTO categoryDetailDTO) {
        if (categoryDetail != null && categoryDetailDTO != null){
            categoryDetail.setCategory(categoryRepository.findById(categoryDetailDTO.getCategoryId()).get());
            categoryDetail.setProduct(productRepository.findById(categoryDetailDTO.getProductId()).get());
        }
        return categoryDetail;
    }
}

