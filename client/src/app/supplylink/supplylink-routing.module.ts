import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { SupplierComponent } from './components/supplier/supplier.component';
import { WarehouseComponent } from './components/warehouse/warehouse.component';
import { ProductComponent } from './components/product/product.component';
import { Component } from '@angular/core';


@Component({ template: `<h3>SupplyLink Dashboard</h3>` })
export class DashboardComponent {}

@Component({ template: `<h3>Edit Supplier</h3>` })
export class SupplierEditComponent {}

@Component({ template: `<h3>Edit Warehouse</h3>` })
export class WarehouseEditComponent {}

const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },

  { path: 'dashboard', component: DashboardComponent },
  { path: 'supplier', component: SupplierComponent },
  { path: 'warehouse', component: WarehouseComponent },
  { path: 'product', component: ProductComponent },

  { path: 'supplier/edit/:id', component: SupplierEditComponent },
  { path: 'warehouse/edit/:id', component: WarehouseEditComponent },
];

@NgModule({
  declarations: [DashboardComponent, SupplierEditComponent, WarehouseEditComponent],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SupplyLinkRoutingModule {}