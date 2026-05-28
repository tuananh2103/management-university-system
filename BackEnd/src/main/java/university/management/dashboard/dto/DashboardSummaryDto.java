package university.management.dashboard.dto;

public record DashboardSummaryDto(
        int totalStudents,
        int activeStudents,
        int inactiveStudents,

        int totalBooks,
        int availableBooks,
        int borrowedBooks,

        int totalCafeteriaItems,
        int availableCafeteriaItems,
        int soldOutCafeteriaItems
) {
}