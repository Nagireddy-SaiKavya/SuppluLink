package com.edutech.progressive.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.edutech.progressive.entity.Supplier;
import com.edutech.progressive.service.SupplierService;
 
public class SupplierServiceImplArraylist implements SupplierService {
 
    private List<Supplier> supplierList = new ArrayList<>();
 
    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierList;
    }
 
    @Override
    public int addSupplier(Supplier supplier) {
        supplierList.add(supplier);
        return supplier.getSupplierId();
    }
 
    @Override
    public List<Supplier> getAllSuppliersSortedByName() {
        supplierList.sort(Comparator.comparing(Supplier::getSupplierName));
        return supplierList;
    }
 
    @Override
    public void emptyArrayList() {
        supplierList.clear();
    }
}