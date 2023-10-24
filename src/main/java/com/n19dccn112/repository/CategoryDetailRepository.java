package com.n19dccn112.repository;

import com.n19dccn112.model.entity.BusinessDetail;
import com.n19dccn112.model.entity.CategoryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryDetailRepository  extends JpaRepository<CategoryDetail, Long> {
    List<CategoryDetail> findAllByProduct_ProductId(Long productId);
    List<CategoryDetail> findAllByCategory_CategoryId(Long categoryId);
    List<CategoryDetail> findAllByCategory_CategoryIdAndProduct_ProductId(Long categoryId, Long productId);
    void deleteAllByProduct_ProductId(Long productId);
}
