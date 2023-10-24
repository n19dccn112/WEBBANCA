package com.n19dccn112.repository;

import com.n19dccn112.model.entity.Image;
import com.n19dccn112.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query(value = "SELECT i.* " +
            "FROM IMAGE i " +
            "WHERE i.IMAGE_ID IN (SELECT id.IMAGE_ID FROM IMAGE_DETAIL id WHERE id.PRODUCT_ID = ?1)", nativeQuery = true)
    List<Image> findAllByProductId(Long productId);

    @Query(value = "SELECT IMAGE_ID FROM IMAGE WHERE IMAGE_ID = " +
            "(SELECT MAX(IMAGE_ID) FROM IMAGE where URL LIKE ?1);", nativeQuery = true)
    Long imageIdNewSave(String url);
}
