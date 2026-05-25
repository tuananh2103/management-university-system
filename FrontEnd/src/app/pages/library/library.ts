import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Book } from './library.model';

@Component({
  selector: 'app-library',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './library.html',
  styleUrl: './library.scss',
})
export class LibraryComponent {
  // Sample data for books
  books: Book[] = [
    {
      id: 1,
      isbn: '978-0132350884',
      title: 'Clean Code',
      author: 'Robert C. Martin',
      category: 'Software Engineering',
      publishedYear: 2008,
      available: true,
    },
    {
      id: 2,
      isbn: '978-0134494166',
      title: 'Clean Architecture',
      author: 'Robert C. Martin',
      category: 'Architecture',
      publishedYear: 2017,
      available: false,
    },
    {
      id: 3,
      isbn: '978-1491950357',
      title: 'Designing Data-Intensive Applications',
      author: 'Martin Kleppmann',
      category: 'Data Systems',
      publishedYear: 2017,
      available: true,
    },
    {
      id: 4,
      isbn: '978-1617294945',
      title: 'Spring in Action',
      author: 'Craig Walls',
      category: 'Java / Spring',
      publishedYear: 2018,
      available: true,
    },
  ];
}
