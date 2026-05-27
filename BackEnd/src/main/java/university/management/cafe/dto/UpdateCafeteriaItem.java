package university.management.cafe.dto;

public record UpdateCafeteriaItem(
        String name,
        String category,
        double price,
        String description,
        String status
) {
}