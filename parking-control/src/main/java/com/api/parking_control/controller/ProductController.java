package com.api.parking_control.controller;

import com.api.parking_control.dtos.ProductRecordDto;
import com.api.parking_control.models.ProductModel;
import com.api.parking_control.repositories.ProductRepositry;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProductController {

    @Autowired
    ProductRepositry productRepositry;

    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) { // @valid makes validations happening
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel); //converts from dto to entity model
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepositry.save(productModel));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productRepositry.findAll());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable(value = "id") UUID uuid) { //what is path variable
        Optional<ProductModel> myProduct = productRepositry.findById(uuid); //search to understand about the optional
        if (myProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(myProduct.get());
    }


    @PutMapping("/products/{id}")
    public ResponseEntity<Object> UpdateProduct(@PathVariable(value = "id") UUID id,
                                                @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<ProductModel> productModel = productRepositry.findById(id);
        if (productModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        var currentProductModel = productModel.get();
        BeanUtils.copyProperties(productRecordDto, currentProductModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepositry.save(currentProductModel));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id,
                                                @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<ProductModel> productModel = productRepositry.findById(id);
        if (productModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        productRepositry.delete(productModel.get());
        return ResponseEntity.status(HttpStatus.OK).body("Deleted");
    }


}


