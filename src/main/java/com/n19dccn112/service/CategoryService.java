package com.n19dccn112.service;

import com.n19dccn112.model.dto.CategoryDTO;
import com.n19dccn112.model.entity.Category;
import com.n19dccn112.model.entity.CategoryDetail;
import com.n19dccn112.model.entity.ImageDetail;
import com.n19dccn112.repository.CategoryDetailRepository;
import com.n19dccn112.repository.CategoryRepository;
import com.n19dccn112.repository.CategoryTypeRepository;
import com.n19dccn112.repository.ProductRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import com.n19dccn112.service.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements IBaseService<CategoryDTO, Long>, IModelMapper<CategoryDTO, Category> {
    private final CategoryRepository categoryRepository;
    private final CategoryTypeRepository categoryTypeRepository;
    private final CategoryDetailRepository categoryDetailRepository;

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryTypeRepository categoryTypeRepository, CategoryDetailRepository categoryDetailRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryTypeRepository = categoryTypeRepository;
        this.categoryDetailRepository = categoryDetailRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CategoryDTO> findAll() {
        return createFromEntities(categoryRepository.findAll());
    }

    public List<CategoryDTO> findAllCategoryTypeId(Long categoryTypeId) {
        return createFromEntities(categoryRepository.findAllByCategoryType_CategoryTypeId(categoryTypeId));
    }

    @Override
    public CategoryDTO findById(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        category.orElseThrow(() -> new NotFoundException(CategoryDTO.class, categoryId));
        return createFromE(category.get());
    }

    @Override
    public CategoryDTO update(Long categoryId, CategoryDTO categoriesDTO) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        category.orElseThrow(() ->  new NotFoundException(CategoryDTO.class, categoryId));
        categoryRepository.save(updateEntity(category.get(), categoriesDTO));
        return createFromE(category.get());
    }

    @Override
    public CategoryDTO save(CategoryDTO categoriesDTO) {
        categoryRepository.save(createFromD(categoriesDTO));
        return categoriesDTO;
    }

    @Override
    public CategoryDTO delete(Long categoryId) {
        Optional<Category> categories = categoryRepository.findById(categoryId);
        CategoryDTO categoryDTO = createFromE(categories.get());
        categories.orElseThrow(() -> new NotFoundException(Category.class, categoryId));
        try {
            categoryRepository.delete(categories.get());
        }
        catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(Category.class, categoryId);
        }
        return categoryDTO;
    }

    @Override
    public Category createFromD(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        category.setCategoryType(categoryTypeRepository.findById(categoryDTO.getCategoryTypeId()).get());
        return category;
    }

    @Override
    public CategoryDTO createFromE(Category category) {
        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
        categoryDTO.setCategoryTypeId(category.getCategoryType().getCategoryTypeId());
        return categoryDTO;
    }

    @Override
    public Category updateEntity(Category category, CategoryDTO categoryDTO) {
        if (category != null && categoryDTO != null){
            category.setCategoryName(categoryDTO.getCategoryName());
            category.setCategoryDescription(categoryDTO.getCategoryDescription());
            category.setCategoryType(categoryTypeRepository.findById(categoryDTO.getCategoryTypeId()).get());
        }
        return category;
    }
}
