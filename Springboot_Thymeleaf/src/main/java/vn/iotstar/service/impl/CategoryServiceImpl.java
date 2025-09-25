package vn.iotstar.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import vn.iotstar.entity.Category;
import vn.iotstar.repository.CategoryRepository;
import vn.iotstar.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public <S extends Category> S save(S entity) {
        if (entity.getId() == null) {
            return categoryRepository.save(entity);
        } else {
            Optional<Category> opt = findById(entity.getId());
            if (opt.isPresent()) {
                if (StringUtils.isEmpty(entity.getIcon())) {
                    entity.setIcon(opt.get().getIcon());
                } else {
                    entity.setIcon(entity.getIcon());
                }
            }
            return categoryRepository.save(entity);
        }
    }

    // implement các method còn lại bằng cách gọi repository tương tự file gốc
    @Override public Optional<Category> findByCategoryName(String name) { return categoryRepository.findByCategoryName(name); }
    @Override public List<Category> findAll() { return categoryRepository.findAll(); }
    @Override public Page<Category> findAll(Pageable pageable) { return categoryRepository.findAll(pageable); }
    @Override public Optional<Category> findById(Long id) { return categoryRepository.findById(id); }
    @Override public void delete(Category entity) { categoryRepository.delete(entity); }
    @Override public void deleteById(Long id) { categoryRepository.deleteById(id); }
    @Override public List<Category> findByCategoryNameContaining(String name) { return categoryRepository.findByCategoryNameContaining(name); }
    @Override public Page<Category> findByCategoryNameContaining(String name, Pageable pageable) { return categoryRepository.findByCategoryNameContaining(name, pageable); }
    // ... (nếu cần, triển khai các method khác)
}
