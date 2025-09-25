package vn.iotstar.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.iotstar.entity.Products;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
    List<Products> findByProductNameContaining(String name);
    Page<Products> findByProductNameContaining(String name, Pageable pageable);
    Optional<Products> findByProductName(String name);
    Optional<Products> findByCreateDate(Date createAt);
}
