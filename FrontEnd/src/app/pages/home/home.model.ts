export interface DashboardSummary {
  totalStudents: number;
  activeStudents: number;
  inactiveStudents: number;

  totalBooks: number;
  availableBooks: number;
  borrowedBooks: number;

  totalCafeteriaItems: number;
  availableCafeteriaItems: number;
  soldOutCafeteriaItems: number;
}