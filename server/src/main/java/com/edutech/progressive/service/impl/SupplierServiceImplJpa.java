package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Supplier;
import com.edutech.progressive.exception.SupplierAlreadyExistsException;
import com.edutech.progressive.exception.SupplierDoesNotExistException;
import com.edutech.progressive.repository.ProductRepository;
import com.edutech.progressive.repository.SupplierRepository;
import com.edutech.progressive.repository.WarehouseRepository;
import com.edutech.progressive.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

    @Autowired
    public SupplierServiceImplJpa(SupplierRepository supplierRepository,
                                  WarehouseRepository warehouseRepository,
                                  ProductRepository productRepository) {
        this.supplierRepository = supplierRepository;
        this.warehouseRepository = warehouseRepository;
        this.productRepository = productRepository;
    }

    public SupplierServiceImplJpa(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
        this.warehouseRepository = null;
        this.productRepository = null;
    }

    @Override
    public List<Supplier> getAllSuppliers() throws SQLException {
        try {
            return new ArrayList<>(supplierRepository.findAll());
        } catch (DataAccessException ex) {
            throw new SQLException("Failed to fetch suppliers", ex);
        }
    }

    @Override
    public int addSupplier(Supplier supplier) throws SQLException {
        try {
            // Day 9: uniqueness checks
            if (supplier.getUsername() != null) {
                Supplier byUser = supplierRepository.findByUsername(supplier.getUsername());
                if (byUser != null) {
                    throw new SupplierAlreadyExistsException("Username already exists");
                }
            }
            if (supplier.getEmail() != null) {
                Supplier byEmail = supplierRepository.findByEmail(supplier.getEmail());
                if (byEmail != null) {
                    throw new SupplierAlreadyExistsException("Email already exists");
                }
            }
            return supplierRepository.save(supplier).getSupplierId();
        } catch (SupplierAlreadyExistsException dup) {
            throw dup;
        } catch (DataAccessException ex) {
            throw new SQLException("Failed to add supplier", ex);
        }
    }

    @Override
    public List<Supplier> getAllSuppliersSortedByName() throws SQLException {
        try {
            List<Supplier> list = new ArrayList<>(supplierRepository.findAll());
            Collections.sort(list);
            return list;
        } catch (DataAccessException ex) {
            throw new SQLException("Failed to get suppliers sorted by name", ex);
        }
    }

    @Override
    public void updateSupplier(Supplier supplier) throws SQLException {
        try {
            // Day 9: username must be unique across others
            if (supplier.getUsername() != null) {
                Supplier byUser = supplierRepository.findByUsername(supplier.getUsername());
                if (byUser != null && byUser.getSupplierId() != supplier.getSupplierId()) {
                    throw new SupplierAlreadyExistsException("Username already exists");
                }
            }
            supplierRepository.save(supplier);
        } catch (SupplierAlreadyExistsException dup) {
            throw dup;
        } catch (DataAccessException ex) {
            throw new SQLException("Failed to update supplier id: " + supplier.getSupplierId(), ex);
        }
    }

    @Override
    @Transactional
    public void deleteSupplier(int supplierId) throws SQLException {
        try {
            if (productRepository != null) {
                productRepository.deleteBySupplierId(supplierId);
            }
            if (warehouseRepository != null) {
                warehouseRepository.deleteBySupplierId(supplierId);
            }
            supplierRepository.deleteBySupplierId(supplierId);
        } catch (DataAccessException ex) {
            throw new SQLException("Failed to delete supplier id: " + supplierId, ex);
        }
    }

    @Override
    public Supplier getSupplierById(int supplierId) throws SQLException {
        try {
            Supplier s = supplierRepository.findBySupplierId(supplierId);
            if (s == null) {
                throw new SupplierDoesNotExistException("Supplier not found: " + supplierId);
            }
            return s;
        } catch (SupplierDoesNotExistException dne) {
            throw dne;
        } catch (DataAccessException ex) {
            throw new SQLException("Failed to fetch supplier id: " + supplierId, ex);
        }
    }
}