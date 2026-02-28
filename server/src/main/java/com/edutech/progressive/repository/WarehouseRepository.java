package com.edutech.progressive.repository;
 
import com.edutech.progressive.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
 
import java.util.List;
 
@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
 
    Warehouse findByWarehouseId(int warehouseId);
 
    List<Warehouse> findAllBySupplier_SupplierId(int supplierId);
 
    @Modifying
    @Transactional
    @Query("DELETE FROM Warehouse w WHERE w.supplier.supplierId = :supplierId")
    void deleteBySupplierId(@Param("supplierId") int supplierId);
}
