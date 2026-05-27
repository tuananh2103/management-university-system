package university.management.cafe.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import university.management.cafe.dto.CafeteriaItemDto;
import university.management.cafe.dto.CreateCafeteriaItem;
import university.management.cafe.dto.UpdateCafeteriaItem;

@Service
public class CafeteriaService {

    private final List<CafeteriaItemDto> items = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(4);

    public CafeteriaService() {
        items.add(new CafeteriaItemDto(
                1L,
                "Chicken Sandwich",
                "Food",
                4.50,
                "Fresh sandwich with chicken, salad and sauce.",
                "AVAILABLE"
        ));

        items.add(new CafeteriaItemDto(
                2L,
                "Orange Juice",
                "Drink",
                2.20,
                "Fresh orange juice.",
                "AVAILABLE"
        ));

        items.add(new CafeteriaItemDto(
                3L,
                "Chocolate Muffin",
                "Snack",
                2.80,
                "Soft muffin with chocolate chips.",
                "SOLD_OUT"
        ));
    }

    public List<CafeteriaItemDto> getAllItems() {
        return items;
    }

    public CafeteriaItemDto getItemById(Long id) {
        return items.stream()
                .filter(item -> item.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cafeteria item not found with id: " + id
                ));
    }

    public CafeteriaItemDto createItem(CreateCafeteriaItem request) {
        validateCreateRequest(request);

        Long id = idGenerator.getAndIncrement();

        CafeteriaItemDto item = new CafeteriaItemDto(
                id,
                request.name(),
                request.category(),
                request.price(),
                request.description(),
                request.status()
        );

        items.add(item);

        return item;
    }

    public CafeteriaItemDto updateItem(Long id, UpdateCafeteriaItem request) {
        validateUpdateRequest(request);

        for (int i = 0; i < items.size(); i++) {
            CafeteriaItemDto current = items.get(i);

            if (current.id().equals(id)) {
                CafeteriaItemDto updated = new CafeteriaItemDto(
                        id,
                        request.name(),
                        request.category(),
                        request.price(),
                        request.description(),
                        request.status()
                );

                items.set(i, updated);

                return updated;
            }
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cafeteria item not found with id: " + id
        );
    }

    public void deleteItem(Long id) {
        boolean removed = items.removeIf(item -> item.id().equals(id));

        if (!removed) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Cafeteria item not found with id: " + id
            );
        }
    }

    private void validateCreateRequest(CreateCafeteriaItem request) {
        if (request.name() == null || request.name().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item name is required");
        }

        if (request.category() == null || request.category().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category is required");
        }

        if (request.price() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Price cannot be negative");
        }
    }

    private void validateUpdateRequest(UpdateCafeteriaItem request) {
        if (request.name() == null || request.name().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item name is required");
        }

        if (request.category() == null || request.category().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category is required");
        }

        if (request.price() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Price cannot be negative");
        }
    }
}