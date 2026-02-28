package com.edutech.progressive.dao;
 
import com.edutech.progressive.entity.Product;
import com.edutech.progressive.entity.Warehouse;
import com.edutech.progressive.service.impl.WarehouseServiceImplJpa; // only to construct Warehouse if needed
import com.edutech.progressive.config.DatabaseConnectionManager;
 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
 
public class ProductDAOImpl implements ProductDAO {
 
    @Override
    public int addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO product (warehouse_id, product_name, product_description, quantity, price) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
 
            ps.setInt(1, product.getWarehouse().getWarehouseId());
            ps.setString(2, product.getProductName());
            ps.setString(3, product.getProductDescription());
            ps.setInt(4, product.getQuantity());
            ps.setLong(5, product.getPrice());
 
            ps.executeUpdate();
 
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    product.setProductId(id);
                    return id;
                }
            }
        }
        return -1;
    }
 
    @Override
    public Product getProductById(int productId) throws SQLException {
        String sql = "SELECT product_id, warehouse_id, product_name, product_description, quantity, price " +
                     "FROM product WHERE product_id = ?";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setInt(1, productId);
 
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Product p = new Product();
                    p.setProductId(rs.getInt("product_id"));
 
                    Warehouse w = new Warehouse();
                    w.setWarehouseId(rs.getInt("warehouse_id"));
                    p.setWarehouse(w);
 
                    p.setProductName(rs.getString("product_name"));
                    p.setProductDescription(rs.getString("product_description"));
                    p.setQuantity(rs.getInt("quantity"));
                    p.setPrice(rs.getLong("price"));
 
                    return p;
                }
            }
        }
        return null;
    }
 
    @Override
    public void updateProduct(Product product) throws SQLException {
        String sql = "UPDATE product SET warehouse_id = ?, product_name = ?, product_description = ?, " +
                     "quantity = ?, price = ? WHERE product_id = ?";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setInt(1, product.getWarehouse().getWarehouseId());
            ps.setString(2, product.getProductName());
            ps.setString(3, product.getProductDescription());
            ps.setInt(4, product.getQuantity());
            ps.setLong(5, product.getPrice());
            ps.setInt(6, product.getProductId());
 
            ps.executeUpdate();
        }
    }
 
    @Override
    public void deleteProduct(int productId) throws SQLException {
        String sql = "DELETE FROM product WHERE product_id = ?";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setInt(1, productId);
            ps.executeUpdate();
        }
    }
 
    @Override
    public List<Product> getAllProducts() throws SQLException {
        String sql = "SELECT product_id, warehouse_id, product_name, product_description, quantity, price FROM product";
        List<Product> list = new ArrayList<>();
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
 
            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
 
                Warehouse w = new Warehouse();
                w.setWarehouseId(rs.getInt("warehouse_id"));
                p.setWarehouse(w);
 
                p.setProductName(rs.getString("product_name"));
                p.setProductDescription(rs.getString("product_description"));
                p.setQuantity(rs.getInt("quantity"));
                p.setPrice(rs.getLong("price"));
 
                list.add(p);
            }
        }
        return list;
    }
}
