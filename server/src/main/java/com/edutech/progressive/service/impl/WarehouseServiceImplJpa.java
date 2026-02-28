package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Warehouse;
import com.edutech.progressive.repository.ProductRepository;
import com.edutech.progressive.repository.WarehouseRepository;
import com.edutech.progressive.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Primary
@Service("warehouseServiceImplJpa")
public class WarehouseServiceImplJpa implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository; // may be null in 1-arg ctor (unit tests)

    /**
     * ✔ Preferred constructor for Spring wiring.
     * Marked @Autowired so Spring chooses this one when creating the bean.
     */
    @Autowired
    public WarehouseServiceImplJpa(WarehouseRepository warehouseRepository,
                                   ProductRepository productRepository) {
        this.warehouseRepository = warehouseRepository;
        this.productRepository = productRepository;
    }

    /**
     * ✔ Overloaded constructor required by Day-8 tests which do:
     *    new WarehouseServiceImplJpa(warehouseRepository)
     * Do NOT annotate this. Spring will ignore it for bean creation.
     */
    public WarehouseServiceImplJpa(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
        this.productRepository = null; // intentionally null for tests that don't wire ProductRepository
    }

    @Override
    public List<Warehouse> getAllWarehouses() throws SQLException {
        return new ArrayList<>(warehouseRepository.findAll());
    }

    @Override
    public int addWarehouse(Warehouse warehouse) throws SQLException {
        Warehouse saved = warehouseRepository.save(warehouse);
        return saved.getWarehouseId();
    }

    @Override
    public List<Warehouse> getWarehousesSortedByCapacity() throws SQLException {
        List<Warehouse> list = new ArrayList<>(warehouseRepository.findAll());
        Collections.sort(list); // relies on Comparable<Warehouse> (capacity DESC)
        return list;
    }

    @Override
    public void updateWarehouse(Warehouse warehouse) throws SQLException {
        warehouseRepository.save(warehouse);
    }

    @Override
    @Transactional
    public void deleteWarehouse(int warehouseId) throws SQLException {
        // Day-8 cascade: delete products of this warehouse, then delete the warehouse itself.
        if (productRepository != null) {
            productRepository.deleteByWarehouseId(warehouseId);
        }
        warehouseRepository.deleteById(warehouseId);
    }

    @Override
    public Warehouse getWarehouseById(int warehouseId) throws SQLException {
        return warehouseRepository.findByWarehouseId(warehouseId);
    }

    @Override
    public List<Warehouse> getWarehouseBySupplier(int supplierId) throws SQLException {
        return new ArrayList<>(warehouseRepository.findAllBySupplier_SupplierId(supplierId));
    }
}