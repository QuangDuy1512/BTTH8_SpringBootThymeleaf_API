package vn.iotstar.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.iotstar.entity.Products;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Constructor injection (nếu muốn)
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public <S extends Products> S save(S entity) {
        // Nếu entity chưa có ID -> tạo mới
        if (entity.getProductId() == null) {
            return productRepository.save(entity);
        } else {
            // Nếu có ID -> update
            Optional<Products> opt = findById(entity.getProductId());
            if (opt.isPresent()) {
                // Giữ lại ảnh cũ nếu người dùng không update
                if (entity.getImages() == null || entity.getImages().isEmpty()) {
                    entity.setImages(opt.get().getImages());
                }
            }
            return productRepository.save(entity);
        }
    }

    @Override
    public List<Products> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Page<Products> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<Products> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void delete(Products entity) {
        productRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Products> findByProductNameContaining(String name) {
        return productRepository.findByProductNameContaining(name);
    }

    @Override
    public Page<Products> findByProductNameContaining(String name, Pageable pageable) {
        return productRepository.findByProductNameContaining(name, pageable);
    }

    @Override
    public Optional<Products> findByProductName(String name) {
        return productRepository.findByProductName(name);
    }
}
