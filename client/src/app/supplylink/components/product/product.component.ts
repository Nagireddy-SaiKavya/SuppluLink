import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Product } from '../../types/Product';
import { Supplier } from '../../types/Supplier';
import { Warehouse } from '../../types/Warehouse';


@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {
  productForm!: FormGroup;
  createdProduct: Product | null = null;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.productForm = this.fb.group({
      warehouseId: ['', [Validators.required, Validators.min(1)]],
      productName: ['', [Validators.required]],
      productDescription: [''],
      quantity: ['', [Validators.required, Validators.min(0)]],
      price: ['', [Validators.required, Validators.min(0.01)]]
    });
  }

  onSubmit(): void {
    if (this.productForm.invalid) return;

    const v = this.productForm.getRawValue();
    const placeholderSupplier = new Supplier(undefined, '', '', '', '', '', '', undefined);
    const warehouse = new Warehouse(Number(v.warehouseId), placeholderSupplier, '', '', 0);

    this.createdProduct = new Product(
      0,
      warehouse,
      v.productName,
      v.productDescription ?? '',
      Number(v.quantity),
      Number(v.price)
    );

    console.log('Created Product:', this.createdProduct);
  }


  get warehouseId() { return this.productForm.get('warehouseId') as FormControl; }
  get productName() { return this.productForm.get('productName') as FormControl; }
  get productDescription() { return this.productForm.get('productDescription') as FormControl; }
  get quantity() { return this.productForm.get('quantity') as FormControl; }
  get price() { return this.productForm.get('price') as FormControl; }
}