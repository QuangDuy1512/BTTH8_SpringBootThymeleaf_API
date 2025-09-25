package vn.iotstar.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.iotstar.entity.Category;
import vn.iotstar.service.CategoryService;
import vn.iotstar.service.IStorageService;

import java.util.UUID;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private IStorageService storageService;

    // Trang danh sách
    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category/list"; // tạo file list.html
    }

    // Trang thêm mới
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("category", new Category());
        return "category/add"; // add.html
    }

    // Xử lý thêm mới
    @PostMapping("/add")
    public String addCategory(@ModelAttribute("category") Category category,
                              @RequestParam("icon") MultipartFile iconFile) {
        if (!iconFile.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuString = uuid.toString();
            category.setIcon(storageService.getSorageFilename(iconFile, uuString));
            storageService.store(iconFile, category.getIcon());
        }
        categoryService.save(category);
        return "redirect:/categories";
    }
}
