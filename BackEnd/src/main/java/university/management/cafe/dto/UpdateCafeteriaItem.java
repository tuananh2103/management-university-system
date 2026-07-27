package university.management.cafe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
public record UpdateCafeteriaItem(
       @NotBlank(message = "Item name is required") String name,
        @NotBlank(message = "Category is required") String category,
        @PositiveOrZero(message = "Price cannot be negative") double price,
        String description,
        String status
) {
}