import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CafeItem } from './cafeteria.model';

@Component({
  selector: 'app-courses',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cafeteria.html',
  styleUrl: './cafeteria.scss',
})
export class CafeteriaComponent {
  // Sample data for cafe items
  cafeItems: CafeItem[] = [
    {
      id: 1,
      name: 'Cappuccino',
      description: 'Espresso with steamed milk foam',
      category: 'COFFEE',
      price: 3.5,
      available: true,
    },
    {
      id: 2,
      name: 'Green Tea',
      description: 'Freshly brewed green tea',
      category: 'TEA',
      price: 2.1,
      available: true,
    },
    {
      id: 3,
      name: 'Orange Juice',
      description: 'Fresh squeezed orange juice',
      category: 'JUICE',
      price: 2.5,
      available: false,
    },
    {
      id: 4,
      name: 'Chocolate Muffin',
      description: 'Delicious chocolate muffin',
      category: 'SNACK',
      price: 1.5,
      available: true,
    },
  ];

    formatPrice(price: number): string {
    return price.toLocaleString('euro-EUR') + ' €';
  }
}