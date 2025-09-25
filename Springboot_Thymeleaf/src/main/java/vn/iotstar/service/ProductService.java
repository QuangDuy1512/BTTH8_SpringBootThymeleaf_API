package vn.iotstar.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.iotstar.entity.Products;

public interface ProductService {
    List<Products> findAll();
    Page<Products> findAll(Pageable pageable);
    Optional<Products> findById(Long id);
    <S extends Products> S save(S entity);
    void delete(Products entity);
    void deleteById(Long id);
    List<Products> findByProductNameContaining(String name);
    Page<Products> findByProductNameContaining(String name, Pageable pageable);
    Optional<Products> findByProductName(String name);
}
