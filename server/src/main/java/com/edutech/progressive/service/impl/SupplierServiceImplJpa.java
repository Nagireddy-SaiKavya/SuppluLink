package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Supplier;
import com.edutech.progressive.repository.ProductRepository;
import com.edutech.progressive.repository.SupplierRepository;
import com.edutech.progressive.repository.WarehouseRepository;
import com.edutech.progressive.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("supplierServiceImplJpa")
public class SupplierServiceImplJpa implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final WarehouseRepository warehouseRepository; // may be null in 1-arg ctor (unit tests)
    private final ProductRepository productRepository;     // may be null in 1-arg ctor (unit tests)

    // Preferred constructor for Spring
    @Autowired
    public SupplierServiceImplJpa(SupplierRepository supplierRepository,
                                  WarehouseRepository warehouseRepository,
                                  ProductRepository productRepository) {
        this.supplierRepository = supplierRepository;
        this.warehouseRepository = warehouseRepository;
        this.productRepository = productRepository;
    }

    // Overload for tests that directly new() with only SupplierRepository
    public SupplierServiceImplJpa(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
        this.warehouseRepository = null;
        this.productRepository = null;
    }

    @Override
    public List<Supplier> getAllSuppliers() throws SQLException {
        return new ArrayList<>(supplierRepository.findAll());
    }

    @Override
    public int addSupplier(Supplier supplier) throws SQLException {
        return supplierRepository.save(supplier).getSupplierId();
    }

    @Override
    public List<Supplier> getAllSuppliersSortedByName() throws SQLException {
        List<Supplier> list = new ArrayList<>(supplierRepository.findAll());
        Collections.sort(list);
        return list;
    }

    @Override
    public void updateSupplier(Supplier supplier) throws SQLException {
        supplierRepository.save(supplier);
    }

    @Override
    @Transactional
    public void deleteSupplier(int supplierId) throws SQLException {
        // Day-8 cascade deletion
        if (productRepository != null) {
            productRepository.deleteBySupplierId(supplierId);
        }
        if (warehouseRepository != null) {
            warehouseRepository.deleteBySupplierId(supplierId);
        }
        supplierRepository.deleteBySupplierId(supplierId);
    }

    @Override
    public Supplier getSupplierById(int supplierId) throws SQLException {
        return supplierRepository.findBySupplierId(supplierId);
    }
}
 