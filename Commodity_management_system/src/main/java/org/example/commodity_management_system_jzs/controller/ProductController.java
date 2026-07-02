package org.example.commodity_management_system_jzs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.Valid;
import org.example.commodity_management_system_jzs.common.Result;
import org.example.commodity_management_system_jzs.entity.Product;
import org.example.commodity_management_system_jzs.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 商品控制器（核心业务）
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    public String listPage(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "5") Integer pageSize,
                           @RequestParam(required = false) String name,
                           @RequestParam(required = false) String category,
                           @RequestParam(required = false) String sortField,
                           @RequestParam(required = false) String sortOrder,
                           Model model) {
        Page<Product> page = new Page<>(pageNum, pageSize);
        Page<Product> result = productService.pageQuery(page, name, category, sortField, sortOrder);
        model.addAttribute("page", result);
        model.addAttribute("name", name);
        model.addAttribute("category", category);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortOrder", sortOrder);
        return "product-list";
    }

    @PostMapping("/add")
    @ResponseBody
    public Result<?> add(@Valid Product product,
                         @RequestParam(required = false) MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = saveFile(imageFile);
            product.setImage(imagePath);
        }
        productService.save(product);
        return Result.ok("新增成功");
    }

    @PostMapping("/edit")
    @ResponseBody
    public Result<?> edit(@Valid Product product,
                          @RequestParam(required = false) MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = saveFile(imageFile);
            product.setImage(imagePath);
        }
        productService.updateById(product);
        return Result.ok("修改成功");
    }

    @GetMapping("/delete")
    @ResponseBody
    public Result<?> delete(@RequestParam Integer id) {
        productService.removeById(id);
        return Result.ok("删除成功");
    }

    @GetMapping("/get")
    @ResponseBody
    public Result<Product> getById(@RequestParam Integer id) {
        Product product = productService.getById(id);
        return product != null ? Result.ok(product) : Result.fail("商品不存在");
    }

    // ==================== 文件保存工具方法 ====================

    private static final String UPLOAD_DIR = "D:/spring-boot/期末设计/uploads/";

    private String saveFile(MultipartFile file) {
        return saveStaticFile(file);
    }

    public static String saveStaticFile(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;
        try {
            String originalName = file.getOriginalFilename();
            String suffix = originalName != null && originalName.contains(".")
                    ? originalName.substring(originalName.lastIndexOf(".")) : ".jpg";
            String newName = UUID.randomUUID().toString() + suffix;
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) dir.mkdirs();
            File dest = new File(dir, newName);
            file.transferTo(dest);
            return "/uploads/" + newName;
        } catch (IOException e) {
            throw new IllegalArgumentException("文件上传失败：" + e.getMessage());
        }
    }
}
