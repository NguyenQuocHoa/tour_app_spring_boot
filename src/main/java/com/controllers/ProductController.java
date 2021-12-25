package com.controllers;

import com.models.Product;
import com.models.ResponseObject;
import com.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductController {
    // DI = Dependency Injection
    @Autowired
    private ProductRepository repository;

    @GetMapping("")
    @ResponseBody
    ResponseEntity<ResponseObject>  getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "query list product success",  repository.findAll())
        );
    }

    @GetMapping("/{id}")
    @ResponseBody
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Product> fundProduct = repository.findById(id);
        if (fundProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "query product success", fundProduct)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("false", "can't find  product with id " + id, "")
            );
        }
    }

    // insert new product with POST method
    @PostMapping("/insert")
    @ResponseBody
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct) {
        List<Product> foundProducts = repository.findByProductName(newProduct.getProductName().trim());
        if (foundProducts.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Product name already exist", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert Product success", repository.save(newProduct))
        );
    }

    // update a product => Method PUT
    @PutMapping("/{id}")
    @ResponseBody
    ResponseEntity<ResponseObject> updateProduct(@RequestBody Product newProduct, @PathVariable Long id) {
        Product foundProduct = repository.findById(id).map(product -> {
            product.setProductName(newProduct.getProductName());
            product.setYear(newProduct.getYear());
            product.setPrice(newProduct.getPrice());
            product.setUrl(newProduct.getUrl());
            return repository.save(product);
        }).orElseGet(() -> {
            newProduct.setId(id);
            return repository.save(newProduct);
        });
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObject("ok", "Upsert Product success", foundProduct)
        );
    }

    // Delete a product => Method DELETE
    @DeleteMapping("/{id}")
    @ResponseBody
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id) {
        boolean isExist = repository.existsById(id);
        if (isExist) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("ok", "Delete product success", id)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObject("failed", "Can't find product " + id, "")
        );
    }

}













