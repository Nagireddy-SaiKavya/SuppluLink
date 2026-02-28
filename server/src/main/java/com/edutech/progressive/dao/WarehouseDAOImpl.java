package com.edutech.progressive.dao;

import com.edutech.progressive.entity.Supplier;
import com.edutech.progressive.entity.Warehouse;
import com.edutech.progressive.config.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WarehouseDAOImpl implements WarehouseDAO {

    @Override
    public int addWarehouse(Warehouse warehouse) throws SQLException {
        String sql = "INSERT INTO warehouse (supplier_id, warehouse_name, location, capacity) " +
                     "VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, warehouse.getSupplier().getSupplierId());
            ps.setString(2, warehouse.getWarehouseName());
            ps.setString(3, warehouse.getLocation());
            ps.setInt(4, warehouse.getCapacity());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    warehouse.setWarehouseId(id);
                    return id;
                }
            }
        }
        return -1;
    }

    @Override
    public Warehouse getWarehouseById(int warehouseId) throws SQLException {
        String sql = "SELECT warehouse_id, supplier_id, warehouse_name, location, capacity " +
                     "FROM warehouse WHERE warehouse_id = ?";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, warehouseId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Warehouse w = new Warehouse();
                    w.setWarehouseId(rs.getInt("warehouse_id"));

                    Supplier s = new Supplier();
                    s.setSupplierId(rs.getInt("supplier_id"));
                    w.setSupplier(s);

                    w.setWarehouseName(rs.getString("warehouse_name"));
                    w.setLocation(rs.getString("location"));
                    w.setCapacity(rs.getInt("capacity"));

                    return w;
                }
            }
        }
        return null;
    }

    @Override
    public void updateWarehouse(Warehouse warehouse) throws SQLException {
        String sql = "UPDATE warehouse SET supplier_id = ?, warehouse_name = ?, location = ?, capacity = ? " +
                     "WHERE warehouse_id = ?";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, warehouse.getSupplier().getSupplierId());
            ps.setString(2, warehouse.getWarehouseName());
            ps.setString(3, warehouse.getLocation());
            ps.setInt(4, warehouse.getCapacity());
            ps.setInt(5, warehouse.getWarehouseId());

            ps.executeUpdate();
        }
    }

    @Override
    public void deleteWarehouse(int warehouseId) throws SQLException {
        String sql = "DELETE FROM warehouse WHERE warehouse_id = ?";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, warehouseId);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Warehouse> getAllWarehouse() throws SQLException {
        String sql = "SELECT warehouse_id, supplier_id, warehouse_name, location, capacity FROM warehouse";
        List<Warehouse> list = new ArrayList<>();

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Warehouse w = new Warehouse();
                w.setWarehouseId(rs.getInt("warehouse_id"));

                Supplier s = new Supplier();
                s.setSupplierId(rs.getInt("supplier_id"));
                w.setSupplier(s);

                w.setWarehouseName(rs.getString("warehouse_name"));
                w.setLocation(rs.getString("location"));
                w.setCapacity(rs.getInt("capacity"));

                list.add(w);
            }
        }
        return list;
    }
}