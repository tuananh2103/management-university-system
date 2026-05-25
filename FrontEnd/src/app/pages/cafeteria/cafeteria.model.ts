export interface CafeItem {
  id: number;
  name: string;
  description: string;
  category: 'COFFEE' | 'TEA' | 'JUICE' | 'SNACK';
  price: number;
  available: boolean;
}
