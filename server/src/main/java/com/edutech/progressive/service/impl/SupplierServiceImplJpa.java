package com.edutech.progressive.service.impl;
 
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.Comparator;

import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Service;
 
import com.edutech.progressive.entity.Supplier;

import com.edutech.progressive.repository.SupplierRepository;

import com.edutech.progressive.service.SupplierService;
 
@Service("supplierServiceImplJpa")

public class SupplierServiceImplJpa implements SupplierService {
 
    @Autowired

    SupplierRepository supplierRepository;
 
    // Added for unit tests that instantiate the service with a repository

    public SupplierServiceImplJpa(SupplierRepository supplierRepository) {

        this.supplierRepository = supplierRepository;

    }
 
    @Override

    public List<Supplier> getAllSuppliers() throws SQLException {

        try {

            return supplierRepository.findAll();

        } catch (DataAccessException ex) {

            throw new SQLException("Failed to fetch suppliers", ex);

        }

    }
 
    @Override

    public int addSupplier(Supplier supplier) throws SQLException {

        try {

            Supplier saved = supplierRepository.save(supplier);

            return saved.getSupplierId();

        } catch (DataAccessException ex) {

            throw new SQLException("Failed to add supplier", ex);

        }

    }
 
    @Override

    public List<Supplier> getAllSuppliersSortedByName() throws SQLException {

        try {

            List<Supplier> list = new ArrayList<>(supplierRepository.findAll());

            list.sort(Comparator.comparing(

                s -> s.getSupplierName() == null ? "" : s.getSupplierName(),

                String.CASE_INSENSITIVE_ORDER

            ));

            return list;

        } catch (DataAccessException ex) {

            throw new SQLException("Failed to get suppliers sorted by name", ex);

        }

    }
 
    @Override

    public void updateSupplier(Supplier supplier) throws SQLException {

        try {

            supplierRepository.save(supplier);

        } catch (DataAccessException ex) {

            throw new SQLException("Failed to update supplier id: " + supplier.getSupplierId(), ex);

        }

    }
 
    @Override

    public void deleteSupplier(int supplierId) throws SQLException {

        try {

            supplierRepository.deleteById(supplierId);

        } catch (DataAccessException ex) {

            throw new SQLException("Failed to delete supplier id: " + supplierId, ex);

        }

    }
 
    @Override

    public Supplier getSupplierById(int supplierId) throws SQLException {

        try {

            return supplierRepository.findById(supplierId).orElse(null);

        } catch (DataAccessException ex) {

            throw new SQLException("Failed to fetch supplier id: " + supplierId, ex);

        }

    }

}
 