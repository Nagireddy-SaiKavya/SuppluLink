package com.edutech.progressive.controller;
 
import com.edutech.progressive.entity.Supplier;


import com.edutech.progressive.service.SupplierService;
 
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.beans.factory.annotation.Qualifier;


import org.springframework.http.HttpStatus;


import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;
 
import java.sql.SQLException;


import java.util.List;
 
@RestController


@RequestMapping("/supplier")


public class SupplierController {
 
 
    @Autowired


    @Qualifier("supplierServiceImplJpa")


    private SupplierService supplierServiceJpa;
 
    @Autowired


    @Qualifier("supplierServiceImplArraylist")


    private SupplierService supplierArray;
 
 
    @GetMapping


    public ResponseEntity<List<Supplier>> getAllSuppliers() {


        try {


            return ResponseEntity.ok(supplierServiceJpa.getAllSuppliers());


        } catch (SQLException e) {


            return ResponseEntity.status(500).build();


        }


    }
 
    @GetMapping("/{supplierId}")


    public ResponseEntity<Supplier> getSupplierById(@PathVariable int supplierId) {


        try {


            return ResponseEntity.ok(supplierServiceJpa.getSupplierById(supplierId));


        } catch (SQLException e) {


            return ResponseEntity.status(500).build();


        }


    }
 
    @PostMapping


    public ResponseEntity<Integer> addSupplier(@RequestBody Supplier supplier) {


        try {


            return ResponseEntity.status(201).body(supplierServiceJpa.addSupplier(supplier));


        } catch (SQLException e) {


            return ResponseEntity.status(500).build();


        }


    }
 
    @PutMapping("/{supplierId}")


    public ResponseEntity<Void> updateSupplier(@PathVariable int supplierId, @RequestBody Supplier supplier) {


        try {


            supplier.setSupplierId(supplierId);


            supplierServiceJpa.updateSupplier(supplier);


            return ResponseEntity.ok().build();


        } catch (SQLException e) {


            return ResponseEntity.status(500).build();


        }


    }
 
    @DeleteMapping("/{supplierId}")


    public ResponseEntity<Void> deleteSupplier(@PathVariable int supplierId) {


        try {


            supplierServiceJpa.deleteSupplier(supplierId);


            return ResponseEntity.noContent().build();


        } catch (SQLException e) {


            return ResponseEntity.status(500).build();


        }


    }
 
    @GetMapping("/fromArrayList")


    public ResponseEntity<List<Supplier>> getAllSuppliersFromArrayList() {


        try {


            return ResponseEntity.ok(supplierArray.getAllSuppliers());


        } catch (SQLException e) {


            e.printStackTrace();


        }


        return null;


    }
 
    @GetMapping("/fromArrayList/all")


    public ResponseEntity<List<Supplier>> getAllSuppliersSortedByNameFromArrayList() {


        try {


            return ResponseEntity.ok(supplierArray.getAllSuppliersSortedByName());


        } catch (SQLException e) {


            e.printStackTrace();


        }


        return null;


    }
 
    @PostMapping("/toArrayList")


    public ResponseEntity<Integer> addSupplierToArrayList(@RequestBody Supplier supplier) {


        int size = -1;


        try {


            size = supplierArray.addSupplier(supplier);


        } catch (SQLException e) {


            e.printStackTrace();


        }


        return ResponseEntity.status(HttpStatus.CREATED).body(size);


    }


}
 