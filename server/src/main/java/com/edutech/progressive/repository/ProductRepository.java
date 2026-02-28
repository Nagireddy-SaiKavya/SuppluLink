package com.edutech.progressive.repository;

import com.edutech.progressive.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Find a single product by its domain id
    Product findByProductId(int productId);

    // All products for a given warehouse (association path)
    List<Product> findAllByWarehouse_WarehouseId(int warehouseId);

    // Count products in a particular warehouse (used later for capacity checks)
    int countByWarehouse_WarehouseId(Integer warehouseId);

    // Delete all products for a warehouse
    @Modifying
    @Transactional
    @Query("DELETE FROM Product p WHERE p.warehouse.warehouseId = :warehouseId")
    void deleteByWarehouseId(@Param("warehouseId") int warehouseId);

    // Delete all products for a supplier (via subquery on that supplier's warehouses)
    @Modifying
    @Transactional
    @Query("DELETE FROM Product p WHERE p.warehouse.warehouseId IN (" +
           "SELECT w.warehouseId FROM Warehouse w WHERE w.supplier.supplierId = :supplierId)")
    void deleteBySupplierId(@Param("supplierId") int supplierId);
}