import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

import {
  CafeteriaItem,
  CafeteriaItemStatus,
  CreateCafeteriaItem,
  UpdateCafeteriaItem,
} from './cafeteria.model';

import { CafeteriaApiService } from './cafeteria.api.service';

@Component({
  selector: 'app-cafeteria',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cafeteria.html',
  styleUrl: './cafeteria.scss',
})
export class CafeteriaComponent implements OnInit {
  items: CafeteriaItem[] = [];

  search = '';

  loading = false;
  saving = false;

  error = '';
  message = '';

  editingId: number | null = null;

  form: CreateCafeteriaItem = {
    name: '',
    category: 'Food',
    price: 0,
    description: '',
    status: 'AVAILABLE',
  };

  categories = ['Food', 'Drink', 'Snack', 'Dessert'];
  statuses: CafeteriaItemStatus[] = ['AVAILABLE', 'SOLD_OUT'];

  constructor(private cafeteriaApi: CafeteriaApiService) {}

  ngOnInit(): void {
    this.loadItems();
  }

  loadItems(clearMessages = true): void {
    this.loading = true;

    if (clearMessages) {
      this.error = '';
      this.message = '';
    }

    this.cafeteriaApi.getItems().subscribe({
      next: (data) => {
        this.items = [...data];
        this.loading = false;
      },
      error: () => {
        this.error = 'Failed to load cafeteria items from backend.';
        this.items = [];
        this.loading = false;
      },
    });
  }

  submitForm(): void {
    this.error = '';
    this.message = '';

    if (!this.form.name.trim()) {
      this.error = 'Item name is required.';
      return;
    }

    if (!this.form.category.trim()) {
      this.error = 'Category is required.';
      return;
    }

    if (this.form.price < 0) {
      this.error = 'Price cannot be negative.';
      return;
    }

    this.saving = true;

    if (this.editingId === null) {
      this.createItem();
    } else {
      this.updateItem(this.editingId);
    }
  }

  createItem(): void {
    this.cafeteriaApi.createItem(this.form).subscribe({
      next: () => {
        this.message = 'Item created successfully.';
        this.saving = false;
        this.resetForm();
        this.loadItems(false);
      },
      error: (err: HttpErrorResponse) => {
        this.error =
          err.error?.message ||
          err.error?.detail ||
          'Failed to create item.';
        this.saving = false;
      },
    });
  }

  updateItem(id: number): void {
    const payload: UpdateCafeteriaItem = { ...this.form };

    this.cafeteriaApi.updateItem(id, payload).subscribe({
      next: () => {
        this.message = 'Item updated successfully.';
        this.saving = false;
        this.resetForm();
        this.loadItems(false);
      },
      error: (err: HttpErrorResponse) => {
        this.error =
          err.error?.message ||
          err.error?.detail ||
          'Failed to update item.';
        this.saving = false;
      },
    });
  }

  startEdit(item: CafeteriaItem): void {
    this.error = '';
    this.message = '';
    this.editingId = item.id;

    this.form = {
      name: item.name,
      category: item.category,
      price: item.price,
      description: item.description,
      status: item.status,
    };
  }

  cancelEdit(): void {
    this.resetForm();
  }

  deleteItem(id: number): void {
    this.error = '';
    this.message = '';

    this.cafeteriaApi.deleteItem(id).subscribe({
      next: () => {
        this.message = 'Item deleted successfully.';

        if (this.editingId === id) {
          this.resetForm();
        }

        this.loadItems(false);
      },
      error: () => {
        this.error = 'Failed to delete item.';
      },
    });
  }

  resetForm(): void {
    this.editingId = null;

    this.form = {
      name: '',
      category: 'Food',
      price: 0,
      description: '',
      status: 'AVAILABLE',
    };
  }

  get filteredItems(): CafeteriaItem[] {
    const q = this.search.trim().toLowerCase();

    if (!q) {
      return this.items;
    }

    return this.items.filter((item) => {
      return (
        item.name.toLowerCase().includes(q) ||
        item.category.toLowerCase().includes(q) ||
        item.description.toLowerCase().includes(q) ||
        item.status.toLowerCase().includes(q)
      );
    });
  }
}