package com.edutech.progressive.service.impl;

 
import java.util.ArrayList;
import java.util.List;

import com.edutech.progressive.dao.WarehouseDAO;
import com.edutech.progressive.entity.Warehouse;
 
public class WarehouseDAOImpl implements WarehouseDAO {
 
    private List<Warehouse> warehouseList = new ArrayList<>();
 
    @Override
    public int addWarehouse(Warehouse warehouse) {
        warehouseList.add(warehouse);
        return warehouse.getWarehouseId();
    }
 
    @Override
    public Warehouse getWarehouseById(int warehouseId) {
        for (Warehouse w : warehouseList) {
            if (w.getWarehouseId() == warehouseId) {
                return w;
            }
        }
        return null;
    }
 
    @Override
    public void updateWarehouse(Warehouse warehouse) {
        for (int i = 0; i < warehouseList.size(); i++) {
            if (warehouseList.get(i).getWarehouseId() == warehouse.getWarehouseId()) {
                warehouseList.set(i, warehouse);
                break;
            }
        }
    }
 
    @Override
    public void deleteWarehouse(int warehouseId) {
        for (int i = 0; i < warehouseList.size(); i++) {
            if (warehouseList.get(i).getWarehouseId() == warehouseId) {
                warehouseList.remove(i);
                break;
            }
        }
    }
 
    @Override
    public List<Warehouse> getAllWarehouse() {
        return warehouseList;
    }

 
}
