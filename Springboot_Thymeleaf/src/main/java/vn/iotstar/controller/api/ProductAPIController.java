package vn.iotstar.controller.api;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.iotstar.entity.Products;
import vn.iotstar.model.Response;
import vn.iotstar.service.ProductService;
import vn.iotstar.service.IStorageService;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "*")
public class ProductAPIController {

    @Autowired private ProductService productService;
    @Autowired private IStorageService storageService;

    @GetMapping
    public ResponseEntity<?> getAllProduct() {
        return new ResponseEntity<Response>(new Response(true, "Thành công", productService.findAll()), HttpStatus.OK);
    }

    @PostMapping("/getProduct")
    public ResponseEntity<?> getProduct(@RequestParam("id") Long id) {
        Optional<Products> opt = productService.findById(id);
        if (opt.isPresent()) return new ResponseEntity<Response>(new Response(true, "Thành công", opt.get()), HttpStatus.OK);
        else return new ResponseEntity<Response>(new Response(false, "Không tìm thấy", null), HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(
            @RequestParam("productName") String productName,
            @RequestParam("quantity") int quantity,
            @RequestParam("unitPrice") double unitPrice,
            @RequestParam("discount") double discount,
            @RequestParam("status") short status,
            @RequestParam("description") String description,
            @RequestParam(value = "images", required = false) MultipartFile images) {

        Products p = new Products();
        p.setProductName(productName);
        p.setQuantity(quantity);
        p.setUnitPrice(unitPrice);
        p.setDiscount(discount);
        p.setStatus(status);
        p.setDescription(description);
        p.setCreateDate(new Date());

        if (images != null && !images.isEmpty()) {
            String uuid = UUID.randomUUID().toString();
            p.setImages(storageService.getSorageFilename(images, uuid));
            storageService.store(images, p.getImages());
        }

        productService.save(p);
        return new ResponseEntity<Response>(new Response(true, "Thêm thành công", p), HttpStatus.OK);
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<?> updateProduct(
            @RequestParam("productId") Long productId,
            @RequestParam("productName") String productName,
            @RequestParam("quantity") int quantity,
            @RequestParam("unitPrice") double unitPrice,
            @RequestParam("discount") double discount,
            @RequestParam("status") short status,
            @RequestParam("description") String description,
            @RequestParam(value = "images", required = false) MultipartFile images) {

        Optional<Products> opt = productService.findById(productId);
        if (opt.isEmpty()) return new ResponseEntity<Response>(new Response(false, "Không tìm thấy", null), HttpStatus.BAD_REQUEST);

        Products p = opt.get();
        p.setProductName(productName);
        p.setQuantity(quantity);
        p.setUnitPrice(unitPrice);
        p.setDiscount(discount);
        p.setStatus(status);
        p.setDescription(description);

        if (images != null && !images.isEmpty()) {
            String uuid = UUID.randomUUID().toString();
            p.setImages(storageService.getSorageFilename(images, uuid));
            storageService.store(images, p.getImages());
        }

        productService.save(p);
        return new ResponseEntity<Response>(new Response(true, "Cập nhật thành công", p), HttpStatus.OK);
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity<?> deleteProduct(@RequestParam("productId") Long productId) {
        Optional<Products> opt = productService.findById(productId);
        if (opt.isEmpty()) return new ResponseEntity<Response>(new Response(false, "Không tìm thấy", null), HttpStatus.BAD_REQUEST);
        productService.delete(opt.get());
        return new ResponseEntity<Response>(new Response(true, "Xóa thành công", opt.get()), HttpStatus.OK);
    }
}
