package com.n19dccn112.service;

import com.n19dccn112.model.dto.CategoryTypeDTO;
import com.n19dccn112.model.entity.CategoryType;
import com.n19dccn112.repository.CategoryRepository;
import com.n19dccn112.repository.CategoryTypeRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Service
public class CategoryTypeService implements IBaseService<CategoryTypeDTO, Long>, IModelMapper<CategoryTypeDTO, CategoryType> {
    private final CategoryTypeRepository categoryTypeRepository;
    private final ModelMapper modelMapper;

    public CategoryTypeService(CategoryTypeRepository categoryTypeRepository, ModelMapper modelMapper) {
        this.categoryTypeRepository = categoryTypeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CategoryTypeDTO> findAll() {
        return createFromEntities(categoryTypeRepository.findAll());
    }

    @Override
    public CategoryTypeDTO findById(Long categoryTypeId) {
        return createFromE(categoryTypeRepository.findById(categoryTypeId).get());
    }

    @Override
    public CategoryTypeDTO update(Long categoryTypeId, CategoryTypeDTO categoryTypeDTO) {
        CategoryType categoryType = categoryTypeRepository.findById(categoryTypeId).get();
        categoryTypeRepository.save(updateEntity(categoryType, categoryTypeDTO));
        return categoryTypeDTO;
    }

    @Override
    public CategoryTypeDTO save(CategoryTypeDTO categoryTypeDTO) {
        categoryTypeRepository.save(createFromD(categoryTypeDTO));
        return categoryTypeDTO;
    }

    @Override
    public CategoryTypeDTO delete(Long categoryTypeId) {
        CategoryType categoryType = categoryTypeRepository.findById(categoryTypeId).get();
        try {
            categoryTypeRepository.delete(categoryType);
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(CategoryTypeDTO.class, categoryTypeId);
        }
        return createFromE(categoryType);
    }

    @Override
    public CategoryType createFromD(CategoryTypeDTO categoryTypeDTO) {
        CategoryType categoryType = modelMapper.map(categoryTypeDTO, CategoryType.class);
        return categoryType;
    }

    @Override
    public CategoryTypeDTO createFromE(CategoryType categoryType) {
        CategoryTypeDTO categoryTypeDTO = modelMapper.map(categoryType, CategoryTypeDTO.class);
        return categoryTypeDTO;
    }

    @Override
    public CategoryType updateEntity(CategoryType categoryType, CategoryTypeDTO categoryTypeDTO) {
        if (categoryType != null && categoryTypeDTO != null){
            categoryType.setCategoryTypeName(categoryTypeDTO.getCategoryTypeName());
            categoryType.setCategoryTypeDescription(categoryTypeDTO.getCategoryTypeDescription());
        }
        return categoryType;
    }
}
