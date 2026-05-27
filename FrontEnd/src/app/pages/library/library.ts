import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

import {
  Book,
  BookStatus,
  CreateBook,
  UpdateBook,
} from './library.model';

import { LibraryApiService } from './library.api.service';

@Component({
  selector: 'app-library',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './library.html',
  styleUrl: './library.scss',
})
export class LibraryComponent implements OnInit {
  books: Book[] = [];

  search = '';

  loading = false;
  saving = false;

  error = '';
  message = '';

  editingId: number | null = null;

  form: CreateBook = {
    isbn: '',
    title: '',
    author: '',
    category: 'Programming',
    publishedYear: 2024,
    status: 'AVAILABLE',
  };

  statuses: BookStatus[] = ['AVAILABLE', 'BORROWED'];
  categories = ['Programming', 'Backend', 'Frontend', 'Architecture', 'Database', 'Networking'];

  constructor(private libraryApi: LibraryApiService) {}

  ngOnInit(): void {
    this.loadBooks();
  }

  loadBooks(clearMessages = true): void {
    this.loading = true;

    if (clearMessages) {
      this.error = '';
      this.message = '';
    }

    this.libraryApi.getBooks().subscribe({
      next: (data) => {
        this.books = [...data];
        this.loading = false;
      },
      error: () => {
        this.error = 'Failed to load books from backend.';
        this.books = [];
        this.loading = false;
      },
    });
  }

  submitForm(): void {
    this.error = '';
    this.message = '';

    if (!this.form.isbn.trim()) {
      this.error = 'ISBN is required.';
      return;
    }

    if (!this.form.title.trim()) {
      this.error = 'Title is required.';
      return;
    }

    if (!this.form.author.trim()) {
      this.error = 'Author is required.';
      return;
    }

    this.saving = true;

    if (this.editingId === null) {
      this.createBook();
    } else {
      this.updateBook(this.editingId);
    }
  }

  createBook(): void {
    this.libraryApi.createBook(this.form).subscribe({
      next: () => {
        this.message = 'Book created successfully.';
        this.saving = false;
        this.resetForm();
        this.loadBooks(false);
      },
      error: (err: HttpErrorResponse) => {
        this.error =
          err.error?.message ||
          err.error?.detail ||
          'Failed to create book.';
        this.saving = false;
      },
    });
  }

  updateBook(id: number): void {
    const payload: UpdateBook = { ...this.form };

    this.libraryApi.updateBook(id, payload).subscribe({
      next: () => {
        this.message = 'Book updated successfully.';
        this.saving = false;
        this.resetForm();
        this.loadBooks(false);
      },
      error: (err: HttpErrorResponse) => {
        this.error =
          err.error?.message ||
          err.error?.detail ||
          'Failed to update book.';
        this.saving = false;
      },
    });
  }

  startEdit(book: Book): void {
    this.error = '';
    this.message = '';
    this.editingId = book.id;

    this.form = {
      isbn: book.isbn,
      title: book.title,
      author: book.author,
      category: book.category,
      publishedYear: book.publishedYear,
      status: book.status,
    };
  }

  cancelEdit(): void {
    this.resetForm();
  }

  deleteBook(id: number): void {
    this.error = '';
    this.message = '';

    this.libraryApi.deleteBook(id).subscribe({
      next: () => {
        this.message = 'Book deleted successfully.';

        if (this.editingId === id) {
          this.resetForm();
        }

        this.loadBooks(false);
      },
      error: () => {
        this.error = 'Failed to delete book.';
      },
    });
  }

  resetForm(): void {
    this.editingId = null;

    this.form = {
      isbn: '',
      title: '',
      author: '',
      category: 'Programming',
      publishedYear: 2024,
      status: 'AVAILABLE',
    };
  }

  get filteredBooks(): Book[] {
    const q = this.search.trim().toLowerCase();

    if (!q) {
      return this.books;
    }

    return this.books.filter((book) => {
      return (
        book.isbn.toLowerCase().includes(q) ||
        book.title.toLowerCase().includes(q) ||
        book.author.toLowerCase().includes(q) ||
        book.category.toLowerCase().includes(q) ||
        book.status.toLowerCase().includes(q)
      );
    });
  }
}