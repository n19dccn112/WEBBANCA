package n19dccn112.repository;

import com.n19dccn112.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "select * from product where product_id = (SELECT MAX(product_id) FROM product)", nativeQuery = true)
    Optional<Product> findProductByName(String name);

    @Query(value = "SELECT * from product p where  p.product_id in (SELECT fd.product_id from feature_detail fd where fd.feature_id in ?1) and p.category_id = ?2", nativeQuery = true)
    List<Product> findAllByFilter(List<Long> featureIds, Long cateId);

    List<Product> findAllByCategory_CategoryId(Long categoryId);

    @Query(value = "SELECT * from product p where  p.product_id in (SELECT fd.product_id from feature_detail fd where fd.feature_id in ?1)", nativeQuery = true)
    List<Product> findAllByFeaturesID(List<Long> featureIds);

    @Query(value = "SELECT * from product p where  p.product_id in ?1", nativeQuery = true)
    List<Product> findAllByProductID(List<Long> productId);
}
