package com.example.warehouse.service;

import com.example.warehouse.dto.ApiResponse;
import com.example.warehouse.dto.ProductDTO;
import com.example.warehouse.entity.*;
import com.example.warehouse.repository.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    AttachmentContentRepository attachmentContentRepository;
    @Autowired
    MeasurementRepository measurementRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @SneakyThrows
    public ApiResponse add(ProductDTO dto, MultipartFile multipartFile) {
        Optional<Category> optionalCategory = categoryRepository.findById(dto.getCategoryId());
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(dto.getMeasurementId());
        if (optionalCategory.isEmpty() || optionalMeasurement.isEmpty())
            return new ApiResponse("Akaajon bunaqa id yoq", false);
        Category category = optionalCategory.get();
        Measurement measurement = optionalMeasurement.get();

        Attachment attachment = new Attachment();
        attachment.setName(multipartFile.getOriginalFilename());
        attachment.setContentType(multipartFile.getContentType());
        attachment.setSize(multipartFile.getSize());
        Attachment save = attachmentRepository.save(attachment);

        AttachmentContent attachmentContent = new AttachmentContent();
        attachmentContent.setAttachment(save);
        attachmentContent.setBytes(multipartFile.getBytes());
        attachmentContentRepository.save(attachmentContent);

        Product product = new Product();
        product.setCategory(category);
        product.setMeasurement(measurement);
        product.setName(dto.getName());
        product.setAttachment(attachment);

        Product saved = productRepository.save(product);

        return new ApiResponse("Saved", true, saved);
    }


    @SneakyThrows
    public ApiResponse edit(Integer id, ProductDTO dto, MultipartFile multipartFile) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Optional<Category> optionalCategory = categoryRepository.findById(dto.getCategoryId());
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(dto.getMeasurementId());
        if (optionalProduct.isEmpty() || optionalCategory.isEmpty() || optionalMeasurement.isEmpty())
            return new ApiResponse("Akaajon bunaqa id yoq", false);
        Product product = optionalProduct.get();
        Category category = optionalCategory.get();
        Measurement measurement = optionalMeasurement.get();


        Attachment attachment = product.getAttachment();
        attachment.setName(multipartFile.getOriginalFilename());
        attachment.setContentType(multipartFile.getContentType());
        attachment.setSize(multipartFile.getSize());
        Attachment save = attachmentRepository.save(attachment);

        Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachmentId(attachment.getId());
        if (optionalAttachmentContent.isEmpty()) return new ApiResponse("Akaajon bunaqa id yoq", false);
        AttachmentContent attachmentContent = optionalAttachmentContent.get();
        attachmentContent.setAttachment(save);
        attachmentContent.setBytes(multipartFile.getBytes());
        attachmentContentRepository.save(attachmentContent);

        product.setCategory(category);
        product.setMeasurement(measurement);
        product.setName(dto.getName());
        product.setAttachment(attachment);

        Product edit = productRepository.save(product);

        return new ApiResponse("Saved", true, edit);
    }
}
