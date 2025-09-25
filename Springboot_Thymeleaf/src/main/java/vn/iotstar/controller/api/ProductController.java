package vn.iotstar.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.iotstar.entity.Products;
import vn.iotstar.entity.Category;
import vn.iotstar.service.ProductService;
import vn.iotstar.service.CategoryService;
import vn.iotstar.service.IStorageService;

import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private IStorageService storageService;

    // Hiển thị danh sách
    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product/list"; // templates/product/list.html
    }

    // Form thêm
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("product", new Products());
        model.addAttribute("categories", categoryService.findAll());
        return "product/add"; // templates/product/add.html
    }

    // Xử lý thêm
    @PostMapping("/add")
    public String addProduct(@ModelAttribute("product") Products product,
                             @RequestParam("images") MultipartFile file,
                             @RequestParam("categoryId") Long categoryId) {

        Category cat = categoryService.findById(categoryId).orElse(null);
        product.setCategory(cat);
        product.setCreateDate(new Date());

        if (!file.isEmpty()) {
            String uuid = UUID.randomUUID().toString();
            product.setImages(storageService.getSorageFilename(file, uuid));
            storageService.store(file, product.getImages());
        }

        productService.save(product);
        return "redirect:/products";
    }

    // Form sửa
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        Products p = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm ID: " + id));
        model.addAttribute("product", p);
        model.addAttribute("categories", categoryService.findAll());
        return "product/update"; // templates/product/update.html
    }

    // Xử lý sửa
    @PostMapping("/edit")
    public String updateProduct(@ModelAttribute("product") Products product,
                                @RequestParam("images") MultipartFile file,
                                @RequestParam("categoryId") Long categoryId) {

        Category cat = categoryService.findById(categoryId).orElse(null);
        product.setCategory(cat);

        if (!file.isEmpty()) {
            String uuid = UUID.randomUUID().toString();
            product.setImages(storageService.getSorageFilename(file, uuid));
            storageService.store(file, product.getImages());
        }

        productService.save(product);
        return "redirect:/products";
    }

    // Xóa
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return "redirect:/products";
    }
}
