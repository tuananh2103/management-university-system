package university.management.cafe.dto;

public record CafeteriaItemDto(
        Long id,
        String name,
        String category,
        double price,
        String description,
        String status
) {
}