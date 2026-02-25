package com.edutech.progressive.service.impl;
 
import java.sql.SQLException;
import java.util.List;
 
import org.springframework.stereotype.Service;
 
import com.edutech.progressive.entity.Supplier;
import com.edutech.progressive.service.SupplierService;
@Service("supplierServiceImplJpa")
public class SupplierServiceImplJpa implements SupplierService {
 
    @Override
    public List<Supplier> getAllSuppliers() throws SQLException {
        return null;
    }
 
    @Override
    public int addSupplier(Supplier supplier) throws SQLException {
        return -1;
    }
 
    @Override
    public List<Supplier> getAllSuppliersSortedByName() throws SQLException {
        return null;
    }
 
    public void updateSupplier(Supplier supplier) throws SQLException{
    }
 
    public void deleteSupplier(int supplierId) throws SQLException{
    }
 
    public Supplier getSupplierById(int supplierId) throws SQLException{
        return null;
    }
 
}