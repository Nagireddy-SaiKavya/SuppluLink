package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Supplier;
import com.edutech.progressive.entity.Warehouse;
import com.edutech.progressive.service.WarehouseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class WarehouseServiceImplArraylist implements WarehouseService {

    private static List<Warehouse> warehouseList = new ArrayList<>();

    @Override
    public List<Warehouse> getAllWarehouses() {
        return warehouseList;
    }

    @Override
    public int addWarehouse(Warehouse warehouse) {
        warehouseList.add(warehouse);
        return warehouseList.size();
    }

    @Override
    public List<Warehouse> getWarehousesSortedByCapacity() {
        List<Warehouse> sortedWarehouses = warehouseList;
        sortedWarehouses.sort(Comparator.comparing(Warehouse::getCapacity)); // Sort by supplier name
        return sortedWarehouses;
    }

    @Override
    public void emptyArrayList() {
        warehouseList = new ArrayList<>();
    }
}