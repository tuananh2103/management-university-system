export type BookStatus = 'AVAILABLE' | 'BORROWED';

export interface Book {
  id: number;
  isbn: string;
  title: string;
  author: string;
  category: string;
  publishedYear: number;
  status: BookStatus;
}

export interface CreateBook {
  isbn: string;
  title: string;
  author: string;
  category: string;
  publishedYear: number;
  status: BookStatus;
}

export interface UpdateBook {
  isbn: string;
  title: string;
  author: string;
  category: string;
  publishedYear: number;
  status: BookStatus;
}

// Backend model for Book entity
// Fields: id, isbn, title, author, category, publishedYear, available
// Frontend need to fix when consuming API