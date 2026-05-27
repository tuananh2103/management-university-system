package university.management.cafe.dto;

public record CreateCafeteriaItem(
        String name,
        String category,
        double price,
        String description,
        String status
) {
}