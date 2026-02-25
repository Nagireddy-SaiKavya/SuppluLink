package com.edutech.progressive.service;
 
import com.edutech.progressive.entity.Product;
 
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 
 
public interface ProductService {
 
    List<Product> getAllProducts() throws SQLException;
 
    Product getProductById(int productId) throws SQLException;
 
    int addProduct(Product product) throws SQLException;
 
    void updateProduct(Product product) throws SQLException;
 
    void deleteProduct(int productId) throws SQLException;
 
    // Do not implement these methods in ProductServiceImplJdbc.java class
    public default List<Product> getAllProductByWarehouse(int warehouseId) throws SQLException {
        List<Product> products = getAllProducts();
        List<Product> filtered = new ArrayList<>();
        for (Product p : products) {
            if (p.getWarehouseId() == warehouseId) {
                filtered.add(p);
            }
        }
        return filtered;
    }
}
