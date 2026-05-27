export type CafeteriaItemStatus = 'AVAILABLE' | 'SOLD_OUT';

export interface CafeteriaItem {
  id: number;
  name: string;
  category: string;
  price: number;
  description: string;
  status: CafeteriaItemStatus;
}

export interface CreateCafeteriaItem {
  name: string;
  category: string;
  price: number;
  description: string;
  status: CafeteriaItemStatus;
}

export interface UpdateCafeteriaItem {
  name: string;
  category: string;
  price: number;
  description: string;
  status: CafeteriaItemStatus;
}