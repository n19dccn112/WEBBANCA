package com.n19dccn112.repository;

import com.n19dccn112.model.entity.FeatureDetail;
import com.n19dccn112.model.entity.UserProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProductRepository extends JpaRepository<UserProduct, Long> {
    List<UserProduct> findAllByProduct_ProductId(Long productId);
    List<UserProduct> findAllByUser_UserId(Long userId);
    List<UserProduct> findAllByUser_UserIdAndProduct_ProductId(Long userId, Long productId);
    @Query(value = "Select * from USER_PRODUCT where IS_LOVE = 'true' and USER_ID = ?1", nativeQuery = true)
    List<UserProduct> findAllByIsLove(Long userId);
    @Query(value = "Select * from USER_PRODUCT where IS_SEEN = 'true' and USER_ID = ?1", nativeQuery = true)
    List<UserProduct> findAllByIsSeen(Long userId);
    @Query(value = "Select USER_PRODUCT_ID from USER_PRODUCT where USER_PRODUCT_ID = (SELECT MAX(USER_PRODUCT_ID) FROM USER_PRODUCT WHERE COMMNET = ?1)", nativeQuery = true)
    Long findUserByComment(String comment);
}
