package com.n19dccn112.service;

import com.n19dccn112.model.dto.ProductDTO;
import com.n19dccn112.model.dto.UnitDetailDTO;
import com.n19dccn112.model.entity.*;
import com.n19dccn112.repository.*;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.*;

@Service
public class ProductService implements IBaseService<ProductDTO, Long>, IModelMapper<ProductDTO, Product> {
    private final ProductRepository productRepository;
    private final ImageDetailRepository imageDetailRepository;
    private final ImageRepository imageRepository;
    private final CategoryDetailRepository categoryDetailRepository;
    private final StatusFishDetailRepository statusFishDetailRepository;
    private final FeatureDetailRepository featureDetailRepository;
    private final CategoryRepository categoryRepository;
    private final UnitDetailRepository unitDetailRepository;
    private final UnitRepository unitRepository;
    private final PondRepository pondRepository;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, ImageDetailRepository imageDetailRepository, ImageRepository imageRepository, CategoryDetailRepository categoryDetailRepository, StatusFishDetailRepository statusFishDetailRepository, FeatureDetailRepository featureDetailRepository, CategoryRepository categoryRepository, UnitDetailRepository unitDetailRepository, UnitRepository unitRepository, PondRepository pondRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.imageDetailRepository = imageDetailRepository;
        this.imageRepository = imageRepository;
        this.categoryDetailRepository = categoryDetailRepository;
        this.statusFishDetailRepository = statusFishDetailRepository;
        this.featureDetailRepository = featureDetailRepository;
        this.categoryRepository = categoryRepository;
        this.unitDetailRepository = unitDetailRepository;
        this.unitRepository = unitRepository;
        this.pondRepository = pondRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductDTO> findAll() {
        return createFromEntities(productRepository.findAll());
    }

    public List<ProductDTO> findAllByProductIds(List<Long> productIds) {
        return createFromEntities(productRepository.findAllByProductIds(productIds));
    }

    public ProductDTO findByProductIdNewSave(String name){
        return createFromE(productRepository.findById(productRepository.productIdNewSave(name)).get());
    }

    public List<ProductDTO> findAllByFeatureIds(List<Long> featureIds) {
        return createFromEntities(productRepository.findAllByFeatureIds(featureIds));
    }

    public List<ProductDTO> findAllByCategoryId(Long categoryId) {
        return createFromEntities(productRepository.findAllByCategoryId(categoryId));
    }

    public List<ProductDTO> findAllByCategoryTypeId(Long categoryTypeId) {
        return createFromEntities(productRepository.findAllByCategoryTypeId(categoryTypeId));
    }

    public List<ProductDTO> findAllByEventId(Long eventId) {
        return createFromEntities(productRepository.findAllByEventId(eventId));
    }

    public List<ProductDTO> findAllByCategoryIdAndFeatureIds(Long categoryId, List<Long> featureIds) {
        return createFromEntities(productRepository.findAllByCategoryIdAndFeatureIds(categoryId, featureIds));
    }

    public List<ProductDTO> findAllByCategoryTypeIdAndFeatureIds(Long categoryTypeId, List<Long> featureIds) {
        return createFromEntities(productRepository.findAllByCategoryTypeIdAndFeatureIds(categoryTypeId, featureIds));
    }

    public List<ProductDTO> findAllByEventIdAndCategoryId(Long eventId, Long categoryId) {
        return createFromEntities(productRepository.findAllByEventIdAndCategoryId(eventId, categoryId));
    }

    public List<ProductDTO> findAllByEventIdAndCategoryTypeId(Long eventId, Long categoryTypeId) {
        return createFromEntities(productRepository.findAllByEventIdAndCategoryTypeId(eventId, categoryTypeId));
    }

    public List<ProductDTO> findAllByEventIdAndFeatureIds(Long eventId, List<Long> featureIds) {
        return createFromEntities(productRepository.findAllByEventIdAndFeatureIds(eventId, featureIds));
    }

    public List<ProductDTO> findAllByFeatureIdsAndProductIds(List<Long> featureIds, List<Long> productIds) {
        return createFromEntities(productRepository.findAllByFeatureIdsAndProductIds(featureIds, productIds));
    }

    public List<ProductDTO> findAllByCategoryIdAndProductIds(Long categoryId, List<Long> productIds) {
        return createFromEntities(productRepository.findAllByCategoryIdAndProductIds(categoryId, productIds));
    }

    public List<ProductDTO> findAllByCategoryTypeIdAndProductIds(Long categoryTypeId, List<Long> productIds) {
        return createFromEntities(productRepository.findAllByCategoryTypeIdAndProductIds(categoryTypeId, productIds));
    }

    public List<ProductDTO> findAllByEventIdAndProductIds(Long eventId, List<Long> productIds) {
        return createFromEntities(productRepository.findAllByEventIdAndProductIds(eventId, productIds));
    }

    public List<ProductDTO> findAllByCategoryIdAndFeatureIdsAndProductIds(Long categoryId, List<Long> featureIds, List<Long> productIds) {
        return createFromEntities(productRepository.findAllByCategoryIdAndFeatureIdsAndProductIds(categoryId, featureIds, productIds));
    }

    public List<ProductDTO> findAllByCategoryTypeIdAndFeatureIdsAndProductIds(Long categoryTypeId, List<Long> featureIds, List<Long> productIds) {
        return createFromEntities(productRepository.findAllByCategoryTypeIdAndFeatureIdsAndProductIds(categoryTypeId, featureIds, productIds));
    }

    public List<ProductDTO> findAllByEventIdAndCategoryIdAndFeatureIds(Long eventId, Long categoryId, List<Long> featureIds) {
        return createFromEntities(productRepository.findAllByEventIdAndCategoryIdAndFeatureIds(eventId, categoryId, featureIds));
    }

    public List<ProductDTO> findAllByEventIdAndCategoryTypeIdAndFeatureIds(Long eventId, Long categoryTypeId, List<Long> featureIds) {
        return createFromEntities(productRepository.findAllByEventIdAndCategoryTypeIdAndFeatureIds(eventId, categoryTypeId, featureIds));
    }

    public List<ProductDTO> findAllByEventIdAndCategoryIdAndProductIds(Long eventId, Long categoryId, List<Long> productIds) {
        return createFromEntities(productRepository.findAllByEventIdAndCategoryIdAndProductIds(eventId, categoryId, productIds));
    }

    public List<ProductDTO> findAllByEventIdAndCategoryTypeIdAndProductIds(Long eventId, Long categoryTypeId, List<Long> productIds) {
        return createFromEntities(productRepository.findAllByEventIdAndCategoryTypeIdAndProductIds(eventId, categoryTypeId, productIds));
    }

    public List<ProductDTO> findAllByEventIdAndFeatureIdsAndProductIds(Long eventId, List<Long> featureIds, List<Long> productIds) {
        return createFromEntities(productRepository.findAllByEventIdAndFeatureIdsAndProductIds(eventId, featureIds, productIds));
    }

    public List<ProductDTO> findAllByEventIdAndCategoryIdAndFeatureIdsProductIds(Long eventId, Long categoryId, List<Long> featureIds, List<Long> productIds) {
        return createFromEntities(productRepository.findAllByEventIdAndCategoryIdAndFeatureIdsProductIds(eventId, categoryId, featureIds, productIds));
    }

    public List<ProductDTO> findAllByEventIdAndCategoryTypeIdAndFeatureIdsProductIds(Long eventId, Long categoryTypeId, List<Long> featureIds, List<Long> productIds) {
        return createFromEntities(productRepository.findAllByEventIdAndCategoryTypeIdAndFeatureIdsProductIds(eventId, categoryTypeId, featureIds, productIds));
    }

    public List<ProductDTO> findAllByUnitIdDetails(List<Long> unitsIdDetails){
        List<Product> products = new ArrayList<>();
        for (Long unitDetailId : unitsIdDetails){
            products.addAll(productRepository.findAllByUnitIdDetail(unitDetailId));
        }
        return createFromEntities(products);
    }

    @Override
    public ProductDTO findById(Long productId) {
        return createFromE(productRepository.findById(productId).get());
    }

    @Transactional
    @Override
    public ProductDTO update(Long productId, ProductDTO productDTO) {
        Product product = productRepository.findById(productId).get();
        productRepository.save(updateEntity(product, productDTO));
        for (UnitDetail unitDetail: product.getUnitDetails()) {
            featureDetailRepository.deleteAllByUnitDetail_UnitDetailId(unitDetail.getUnitDetailId());
            statusFishDetailRepository.deleteAllByUnitDetail_UnitDetailId(unitDetail.getUnitDetailId());
        }
//        unitDetailRepository.deleteAllByProduct_ProductId(productId);
        imageDetailRepository.deleteAllByProduct_ProductId(productId);
        categoryDetailRepository.deleteAllByProduct_ProductId(productId);
        return productDTO;
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        if (productDTO.getIsAnimal().equals("true")) {
            productDTO.setImportDate(new Date());
        }
        productDTO.setUpdateDateProduct(new Date());
        productRepository.save(createFromD(productDTO));
        return productDTO;
    }

    @Override
    public ProductDTO delete(Long productId) {
        Product product = productRepository.findById(productId).get();
        try {
            productRepository.delete(product);
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(Product.class, productId);
        }
        return createFromE(product);
    }

    @Override
    public Product createFromD(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        return product;
    }

    @Override
    public ProductDTO createFromE(Product product) {
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        List<String> categoryNames = new ArrayList<>();
        for (CategoryDetail categoryDetail: categoryDetailRepository.findAllByProduct_ProductId(productDTO.getProductId())){
            categoryNames.add(categoryDetail.getCategory().getCategoryName());
        }
        productDTO.setCategoryNames(categoryNames);

        List<String> url = new ArrayList<>();
        for (ImageDetail imageDetail: imageDetailRepository.findAllByProduct_ProductId(productDTO.getProductId())){
            url.add(imageDetail.getImage().getUrl());
        }
        productDTO.setImages(url);

        int amountProduct = 0;
        int minPrice = -1;
        int maxPrice = 0;
        for (UnitDetail unitDetail: unitDetailRepository.findAllByProduct_ProductId(productDTO.getProductId())){
            if (minPrice == -1 || minPrice > unitDetail.getProductPrice()){
                minPrice = unitDetail.getProductPrice();
            }
            if (maxPrice < unitDetail.getProductPrice()){
                maxPrice = unitDetail.getProductPrice();
            }
            try {
                StatusFishDetail statusFishDetail = statusFishDetailRepository.findAllByUnitDetail_UnitDetailIdAndAndStatusFish_StatusFishId(unitDetail.getUnitDetailId(), 1L).get(0);
                amountProduct += statusFishDetail.getAmount();
            }catch (Exception e){
                amountProduct = 0;
            }
        }
        productDTO.setAmountProduct(amountProduct);
        productDTO.setMinPrice(minPrice == -1 ? 0 : minPrice);
        productDTO.setMaxPrice(maxPrice);

        return productDTO;
    }

    @Override
    public Product updateEntity(Product product, ProductDTO productDTO) {
        if (product != null && productDTO != null){
            if (productDTO.getProductName() != null) {
                product.setProductName(productDTO.getProductName());
            }
            if (productDTO.getUpdateDateProduct() != null) {
                product.setUpdateDateProduct(productDTO.getUpdateDateProduct());
            }
            if (productDTO.getProductDescription() != null) {
                product.setProductDescription(productDTO.getProductDescription());
            }
            product.setUpdateDateProduct(new Date());
            if (productDTO.getIsAnimal() != null) {
                product.setIsAnimal(productDTO.getIsAnimal());
            }
        }
        return product;
    }
}
