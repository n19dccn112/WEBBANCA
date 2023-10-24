package com.n19dccn112.repository;
import com.n19dccn112.model.entity.ImageDetail;
import com.n19dccn112.model.entity.ImageDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageDetailRepository extends JpaRepository<ImageDetail, Long> {
    List<ImageDetail> findAllByProduct_ProductId(Long productId);
    List<ImageDetail> findAllByImage_ImageId(Long imageId);
    List<ImageDetail> findAllByImage_ImageIdAndProduct_ProductId(Long imageId, Long productId);
    void deleteAllByProduct_ProductId(Long productId);
}
