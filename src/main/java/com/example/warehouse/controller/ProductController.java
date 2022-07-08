package com.example.warehouse.controller;

import com.example.warehouse.dto.ProductDTO;
import com.example.warehouse.entity.*;
import com.example.warehouse.repository.AttachmentContentRepository;
import com.example.warehouse.repository.CategoryRepository;
import com.example.warehouse.repository.MeasurementRepository;
import com.example.warehouse.repository.ProductRepository;
import com.example.warehouse.service.ProductService;
import lombok.SneakyThrows;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    MeasurementRepository measurementRepository;
    @Autowired
    AttachmentContentRepository attachmentContentRepository;
    @GetMapping
    public String getProductPage(Model model) {
        model.addAttribute("list", productRepository.findAllByActiveTrue());
        return "product/product";
    }

    @SneakyThrows
    @GetMapping("/photo/{id}")
    public HttpEntity<?> getPhoto(HttpServletResponse response, @PathVariable Integer id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Product());
        }
        Attachment attachment = optionalProduct.get().getAttachment();
        response.setContentType(attachment.getContentType());
        Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachmentId(attachment.getId());
        if (optionalAttachmentContent.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Xatolik");
        }
        byte[] bytes = optionalAttachmentContent.get().getBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        int copy = IOUtils.copy(inputStream, response.getOutputStream());
        return ResponseEntity.ok().body(copy);
    }

    @GetMapping("/add")
    public String getSaveProduct(Model model) {
        model.addAttribute("categoryList", categoryRepository.findAll());
        model.addAttribute("measurementList", measurementRepository.findAllByActiveTrue());
        return "product/product-add";
    }

    @PostMapping("/add")
    public String saveProduct(@ModelAttribute ProductDTO dto, MultipartFile file) {
        productService.add(dto, file);
        return "redirect:/product";
    }

    @GetMapping("/delete/{id}") //1 45 24 90
    public String delete(@PathVariable Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(product -> product.setActive(false));
        return "redirect:/product";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) return "product/product";
        Product product = optionalProduct.get();
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(product.getMeasurement().getId());
        Optional<Category> optionalCategory = categoryRepository.findById(product.getCategory().getId());
        if (optionalMeasurement.isEmpty() || optionalCategory.isEmpty()) return "product/product";
        model.addAttribute("category", optionalCategory.get());
        model.addAttribute("measurement", optionalMeasurement.get());
        model.addAttribute("product", product);
        model.addAttribute("warehouseList", categoryRepository.findAll());
        model.addAttribute("currencyList", measurementRepository.findAllByActiveTrue());
        return "product/product-edit";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@ModelAttribute ProductDTO dto, @PathVariable Integer id, MultipartFile multipartFile) {
        productService.edit(id, dto, multipartFile);
        return "redirect:/product";
    }


}
