package com.edutech.progressive.controller;
 
import com.edutech.progressive.entity.Product;
import com.edutech.progressive.service.ProductService;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import java.sql.SQLException;
import java.util.List;
@RestController
@RequestMapping
public class ProductController {
    // @Autowired
    // ProductService productService;
 
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() throws SQLException {
        // return ResponseEntity.ok(productService.getAllProducts());
        return null;
    }
 
    public ResponseEntity<Product> getProductById(int productId) {
        return null;
    }
 
    public ResponseEntity<Integer> addProduct(Product product) {
        return null;
    }
 
    public ResponseEntity<Void> updateProduct(int productId, Product product) {
        return null;
    }
 
    public ResponseEntity<Void> deleteProduct(int productId) {
        return null;
    }
 
    public ResponseEntity<List<Product>> getAllProductByWarehouse(int warehouseId) {
        return null;
    }
}