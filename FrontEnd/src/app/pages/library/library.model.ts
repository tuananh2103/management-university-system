export interface Book {
  id: number;
  isbn: string;
  title: string;
  author: string;
  category: string;
  publishedYear: number;
  available: boolean;
}
// Backend model for Book entity
// Fields: id, isbn, title, author, category, publishedYear, available
// Frontend need to fix when consuming API