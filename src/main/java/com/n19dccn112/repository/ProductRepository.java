package com.n19dccn112.repository;

import com.n19dccn112.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT p.* " +
            "FROM PRODUCT p " +
            "WHERE p.PRODUCT_ID IN " +
            "(SELECT DISTINCT ud.PRODUCT_ID " +
            "FROM UNIT_DETAIL ud " +
            "WHERE ud.UNIT_DETAIL_ID = ?1)", nativeQuery = true)
    List<Product> findAllByUnitIdDetail(Long unitIdDetails);
    @Query(value = "SELECT p.* " +
            "FROM PRODUCT p " +
            "WHERE p.PRODUCT_ID IN ?1", nativeQuery = true)
    List<Product> findAllByProductIds(List<Long> productIds);

    @Query(value = "SELECT DISTINCT p.* " +
            "FROM PRODUCT p " +
            "INNER JOIN UNIT_DETAIL ud ON p.PRODUCT_ID = ud.PRODUCT_ID " +
            "INNER JOIN FEATURE_DETAIL fd ON ud.UNIT_DETAIL_ID = fd.UNIT_DETAIL_ID " +
            "WHERE fd.FEATURE_ID IN ?1", nativeQuery = true)
    List<Product> findAllByFeatureIds(List<Long> featureIds);

    @Query(value = "SELECT DISTINCT p.* " +
            "from PRODUCT p " +
            "INNER JOIN CATEGORY_DETAIL cd on cd.PRODUCT_ID = p.PRODUCT_ID " +
            "where cd.CATEGORY_ID=?1", nativeQuery = true)
    List<Product> findAllByCategoryId(Long categoryId);

    @Query(value = "SELECT DISTINCT p.* " +
            "from PRODUCT p " +
            "INNER JOIN CATEGORY_DETAIL cd on cd.PRODUCT_ID = p.PRODUCT_ID " +
            "where cd.CATEGORY_ID in (SELECT c.CATEGORY_ID FROM CATEGORY c WHERE c.CATEGORY_TYPE_ID = ?1)", nativeQuery = true)
    List<Product> findAllByCategoryTypeId(Long categoryTypeId);

    @Query(value = "SELECT DISTINCT p.* " +
            "from PRODUCT p " +
            "INNER JOIN EVENT_PRODUCT ep on ep.PRODUCT_ID = p.PRODUCT_ID " +
            "where ep.EVENT_ID=?1", nativeQuery = true)
    List<Product> findAllByEventId(Long eventId);

    @Query(value = "SELECT DISTINCT p.* " +
            "from PRODUCT p " +
            "INNER JOIN UNIT_DETAIL ud ON p.PRODUCT_ID = ud.PRODUCT_ID " +
            "INNER JOIN FEATURE_DETAIL fd ON ud.UNIT_DETAIL_ID = fd.UNIT_DETAIL_ID " +
            "INNER JOIN CATEGORY_DETAIL cd on cd.PRODUCT_ID = p.PRODUCT_ID " +
            "where cd.CATEGORY_ID=?1 and fd.FEATURE_ID IN ?2", nativeQuery = true)
    List<Product> findAllByCategoryIdAndFeatureIds(Long categoryId, List<Long> featureIds);

    @Query(value = "SELECT DISTINCT p.* " +
            "from PRODUCT p " +
            "INNER JOIN UNIT_DETAIL ud ON p.PRODUCT_ID = ud.PRODUCT_ID " +
            "INNER JOIN FEATURE_DETAIL fd ON ud.UNIT_DETAIL_ID = fd.UNIT_DETAIL_ID " +
            "INNER JOIN CATEGORY_DETAIL cd on cd.PRODUCT_ID = p.PRODUCT_ID " +
            "where cd.CATEGORY_ID  in (SELECT c.CATEGORY_ID FROM CATEGORY c WHERE c.CATEGORY_TYPE_ID = ?1)" +
            " and fd.FEATURE_ID IN ?2", nativeQuery = true)
    List<Product> findAllByCategoryTypeIdAndFeatureIds(Long categoryTypeId, List<Long> featureIds);

    @Query(value = "SELECT DISTINCT p.* " +
            "from PRODUCT p " +
            "INNER JOIN EVENT_PRODUCT ep on ep.PRODUCT_ID = p.PRODUCT_ID " +
            "INNER JOIN CATEGORY_DETAIL cd on cd.PRODUCT_ID = p.PRODUCT_ID " +
            "where ep.EVENT_ID=?1 and cd.CATEGORY_ID=?2", nativeQuery = true)
    List<Product> findAllByEventIdAndCategoryId(Long eventId, Long categoryId);

    @Query(value = "SELECT DISTINCT p.* " +
            "from PRODUCT p " +
            "INNER JOIN EVENT_PRODUCT ep on ep.PRODUCT_ID = p.PRODUCT_ID " +
            "INNER JOIN CATEGORY_DETAIL cd on cd.PRODUCT_ID = p.PRODUCT_ID " +
            "where ep.EVENT_ID=?1 and cd.CATEGORY_ID in " +
            "(SELECT c.CATEGORY_ID FROM CATEGORY c WHERE c.CATEGORY_TYPE_ID = ?2)", nativeQuery = true)
    List<Product> findAllByEventIdAndCategoryTypeId(Long eventId, Long categoryTypeId);

    @Query(value = "SELECT DISTINCT p.* " +
            "FROM PRODUCT p " +
            "INNER JOIN EVENT_PRODUCT ep on ep.PRODUCT_ID = p.PRODUCT_ID " +
            "INNER JOIN UNIT_DETAIL ud ON p.PRODUCT_ID = ud.PRODUCT_ID " +
            "INNER JOIN FEATURE_DETAIL fd ON ud.UNIT_DETAIL_ID = fd.UNIT_DETAIL_ID " +
            "WHERE  ep.EVENT_ID=?1 and fd.FEATURE_ID IN ?2", nativeQuery = true)
    List<Product> findAllByEventIdAndFeatureIds(Long eventId, List<Long> featureIds);
    @Query(value = "SELECT DISTINCT p.* " +
            "FROM PRODUCT p " +
            "INNER JOIN UNIT_DETAIL ud ON p.PRODUCT_ID = ud.PRODUCT_ID " +
            "INNER JOIN FEATURE_DETAIL fd ON ud.UNIT_DETAIL_ID = fd.UNIT_DETAIL_ID " +
            "WHERE fd.FEATURE_ID IN ?1 AND p.PRODUCT_ID IN ?2", nativeQuery = true)
    List<Product> findAllByFeatureIdsAndProductIds(List<Long> featureIds, List<Long> productIds);

    @Query(value = "SELECT DISTINCT p.* " +
            "from PRODUCT p " +
            "INNER JOIN CATEGORY_DETAIL cd on cd.PRODUCT_ID = p.PRODUCT_ID " +
            "where cd.CATEGORY_ID=?1 AND p.PRODUCT_ID IN ?2", nativeQuery = true)
    List<Product> findAllByCategoryIdAndProductIds(Long categoryId, List<Long> productIds);

    @Query(value = "SELECT DISTINCT p.* " +
            "from PRODUCT p " +
            "INNER JOIN CATEGORY_DETAIL cd on cd.PRODUCT_ID = p.PRODUCT_ID " +
            "where cd.CATEGORY_ID in (SELECT c.CATEGORY_ID FROM CATEGORY c WHERE c.CATEGORY_TYPE_ID = ?1)" +
            " AND p.PRODUCT_ID IN ?2", nativeQuery = true)
    List<Product> findAllByCategoryTypeIdAndProductIds(Long categoryTypeId, List<Long> productIds);

    @Query(value = "SELECT DISTINCT p.* " +
            "from PRODUCT p " +
            "INNER JOIN EVENT_PRODUCT ep on ep.PRODUCT_ID = p.PRODUCT_ID " +
            "where ep.EVENT_ID=?1 AND p.PRODUCT_ID IN ?2", nativeQuery = true)
    List<Product> findAllByEventIdAndProductIds(Long eventId, List<Long> productIds);

    @Query(value = "SELECT DISTINCT p.* " +
            "from PRODUCT p " +
            "INNER JOIN UNIT_DETAIL ud ON p.PRODUCT_ID = ud.PRODUCT_ID " +
            "INNER JOIN FEATURE_DETAIL fd ON ud.UNIT_DETAIL_ID = fd.UNIT_DETAIL_ID " +
            "INNER JOIN CATEGORY_DETAIL cd on cd.PRODUCT_ID = p.PRODUCT_ID " +
            "where cd.CATEGORY_ID=?1 and fd.FEATURE_ID IN ?2 AND p.PRODUCT_ID IN ?3", nativeQuery = true)
    List<Product> findAllByCategoryIdAndFeatureIdsAndProductIds(Long categoryId, List<Long> featureIds, List<Long> productIds);

    @Query(value = "SELECT DISTINCT p.* " +
            "from PRODUCT p " +
            "INNER JOIN UNIT_DETAIL ud ON p.PRODUCT_ID = ud.PRODUCT_ID " +
            "INNER JOIN FEATURE_DETAIL fd ON ud.UNIT_DETAIL_ID = fd.UNIT_DETAIL_ID " +
            "INNER JOIN CATEGORY_DETAIL cd on cd.PRODUCT_ID = p.PRODUCT_ID " +
            "where cd.CATEGORY_ID in (SELECT c.CATEGORY_ID FROM CATEGORY c WHERE c.CATEGORY_TYPE_ID = ?1)" +
            " and fd.FEATURE_ID IN ?2 AND p.PRODUCT_ID IN ?3", nativeQuery = true)
    List<Product> findAllByCategoryTypeIdAndFeatureIdsAndProductIds(Long categoryTypeId, List<Long> featureIds, List<Long> productIds);

    @Query(value = "SELECT DISTINCT p.* " +
            "from PRODUCT p " +
            "INNER JOIN UNIT_DETAIL ud ON p.PRODUCT_ID = ud.PRODUCT_ID " +
            "INNER JOIN FEATURE_DETAIL fd ON ud.UNIT_DETAIL_ID = fd.UNIT_DETAIL_ID " +
            "INNER JOIN EVENT_PRODUCT ep on ep.PRODUCT_ID = p.PRODUCT_ID " +
            "INNER JOIN CATEGORY_DETAIL cd on cd.PRODUCT_ID = p.PRODUCT_ID " +
            "where ep.EVENT_ID=?1 " +
            "and cd.CATEGORY_ID  in (SELECT c.CATEGORY_ID FROM CATEGORY c WHERE c.CATEGORY_TYPE_ID = ?2)" +
            " and fd.FEATURE_ID IN ?3 ", nativeQuery = true)
    List<Product> findAllByEventIdAndCategoryTypeIdAndFeatureIds(Long eventId, Long categoryTypeId, List<Long> featureIds);

    @Query(value = "SELECT DISTINCT p.* " +
            "from PRODUCT p " +
            "INNER JOIN UNIT_DETAIL ud ON p.PRODUCT_ID = ud.PRODUCT_ID " +
            "INNER JOIN FEATURE_DETAIL fd ON ud.UNIT_DETAIL_ID = fd.UNIT_DETAIL_ID " +
            "INNER JOIN EVENT_PRODUCT ep on ep.PRODUCT_ID = p.PRODUCT_ID " +
            "INNER JOIN CATEGORY_DETAIL cd on cd.PRODUCT_ID = p.PRODUCT_ID " +
            "where ep.EVENT_ID=?1 and cd.CATEGORY_ID=?2 and fd.FEATURE_ID IN ?3 ", nativeQuery = true)
    List<Product> findAllByEventIdAndCategoryIdAndFeatureIds(Long eventId, Long categoryId, List<Long> featureIds);

    @Query(value = "SELECT DISTINCT p.* " +
            "from PRODUCT p " +
            "INNER JOIN EVENT_PRODUCT ep on ep.PRODUCT_ID = p.PRODUCT_ID " +
            "INNER JOIN CATEGORY_DETAIL cd on cd.PRODUCT_ID = p.PRODUCT_ID " +
            "where ep.EVENT_ID=?1 and cd.CATEGORY_ID=?2 AND p.PRODUCT_ID IN ?3", nativeQuery = true)
    List<Product> findAllByEventIdAndCategoryIdAndProductIds(Long eventId, Long categoryId, List<Long> productIds);

    @Query(value = "SELECT DISTINCT p.* " +
            "from PRODUCT p " +
            "INNER JOIN EVENT_PRODUCT ep on ep.PRODUCT_ID = p.PRODUCT_ID " +
            "INNER JOIN CATEGORY_DETAIL cd on cd.PRODUCT_ID = p.PRODUCT_ID " +
            "where ep.EVENT_ID=?1 " +
            "and cd.CATEGORY_ID in (SELECT c.CATEGORY_ID FROM CATEGORY c WHERE c.CATEGORY_TYPE_ID = ?2) " +
            "AND p.PRODUCT_ID IN ?3", nativeQuery = true)
    List<Product> findAllByEventIdAndCategoryTypeIdAndProductIds(Long eventId, Long categoryTypeId, List<Long> productIds);

    @Query(value = "SELECT DISTINCT p.* " +
            "from PRODUCT p " +
            "INNER JOIN UNIT_DETAIL ud ON p.PRODUCT_ID = ud.PRODUCT_ID " +
            "INNER JOIN FEATURE_DETAIL fd ON ud.UNIT_DETAIL_ID = fd.UNIT_DETAIL_ID " +
            "INNER JOIN EVENT_PRODUCT ep on ep.PRODUCT_ID = p.PRODUCT_ID " +
            "INNER JOIN CATEGORY_DETAIL cd on cd.PRODUCT_ID = p.PRODUCT_ID " +
            "where ep.EVENT_ID=?1 and fd.FEATURE_ID IN ?2 AND p.PRODUCT_ID IN ?3", nativeQuery = true)
    List<Product> findAllByEventIdAndFeatureIdsAndProductIds(Long eventId, List<Long> featureIds, List<Long> productIds);

    @Query(value = "SELECT DISTINCT p.* " +
            "FROM PRODUCT p " +
            "INNER JOIN EVENT_PRODUCT ep on ep.PRODUCT_ID = p.PRODUCT_ID " +
            "INNER JOIN CATEGORY_DETAIL cd on cd.PRODUCT_ID = p.PRODUCT_ID " +
            "INNER JOIN UNIT_DETAIL ud ON p.PRODUCT_ID = ud.PRODUCT_ID " +
            "INNER JOIN FEATURE_DETAIL fd ON ud.UNIT_DETAIL_ID = fd.UNIT_DETAIL_ID " +
            "WHERE ep.EVENT_ID=?1 and cd.CATEGORY_ID=?2 and fd.FEATURE_ID IN ?3 AND p.PRODUCT_ID IN ?4", nativeQuery = true)
    List<Product> findAllByEventIdAndCategoryIdAndFeatureIdsProductIds(Long eventId, Long categoryId, List<Long> featureIds, List<Long> productIds);
    @Query(value = "SELECT DISTINCT p.* " +
            "FROM PRODUCT p " +
            "INNER JOIN EVENT_PRODUCT ep on ep.PRODUCT_ID = p.PRODUCT_ID " +
            "INNER JOIN CATEGORY_DETAIL cd on cd.PRODUCT_ID = p.PRODUCT_ID " +
            "INNER JOIN UNIT_DETAIL ud ON p.PRODUCT_ID = ud.PRODUCT_ID " +
            "INNER JOIN FEATURE_DETAIL fd ON ud.UNIT_DETAIL_ID = fd.UNIT_DETAIL_ID " +
            "WHERE ep.EVENT_ID=?1 " +
            "and cd.CATEGORY_ID in (SELECT c.CATEGORY_ID FROM CATEGORY c WHERE c.CATEGORY_TYPE_ID = ?2)" +
            "and fd.FEATURE_ID IN ?3 AND p.PRODUCT_ID IN ?4", nativeQuery = true)
    List<Product> findAllByEventIdAndCategoryTypeIdAndFeatureIdsProductIds(Long eventId, Long categoryTypeId, List<Long> featureIds, List<Long> productIds);

    @Query(value = "SELECT PRODUCT_ID FROM PRODUCT WHERE PRODUCT_ID = " +
            "(SELECT MAX(PRODUCT_ID) FROM PRODUCT where PRODUCT_NAME = ?1);", nativeQuery = true)
    Long productIdNewSave(String name);
}
