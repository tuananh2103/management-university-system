package university.management.cafe.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import university.management.cafe.dto.CafeteriaItemDto;
import university.management.cafe.dto.CreateCafeteriaItem;
import university.management.cafe.dto.UpdateCafeteriaItem;
import university.management.cafe.entity.CafeteriaItem;
import university.management.cafe.repository.CafeteriaItemRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class CafeteriaService {

    private final CafeteriaItemRepository cafeteriaItemRepository;

    public CafeteriaService(CafeteriaItemRepository cafeteriaItemRepository) {
        this.cafeteriaItemRepository = cafeteriaItemRepository;
    }

    @Transactional(readOnly = true)
    public List<CafeteriaItemDto> getAllItems() {
        return cafeteriaItemRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public CafeteriaItemDto getItemById(Long id) {
        return cafeteriaItemRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cafeteria item not found with id: " + id));
    }

    public CafeteriaItemDto createItem(CreateCafeteriaItem request) {
        validateCreate(request);
        CafeteriaItem item = new CafeteriaItem();
        item.setName(request.name());
        item.setCategory(request.category());
        item.setPrice(BigDecimal.valueOf(request.price()));
        item.setDescription(request.description());
        item.setStatus(request.status() != null ? request.status() : "AVAILABLE");
        return toDto(cafeteriaItemRepository.save(item));
    }

    public CafeteriaItemDto updateItem(Long id, UpdateCafeteriaItem request) {
        validateUpdate(request);
        CafeteriaItem item = cafeteriaItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cafeteria item not found with id: " + id));
        item.setName(request.name());
        item.setCategory(request.category());
        item.setPrice(BigDecimal.valueOf(request.price()));
        item.setDescription(request.description());
        item.setStatus(request.status());
        return toDto(cafeteriaItemRepository.save(item));
    }

    public void deleteItem(Long id) {
        if (!cafeteriaItemRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cafeteria item not found with id: " + id);
        }
        cafeteriaItemRepository.deleteById(id);
    }

    public long countAll() { return cafeteriaItemRepository.count(); }
    public long countByStatus(String status) { return cafeteriaItemRepository.countByStatus(status); }

    private void validateCreate(CreateCafeteriaItem request) {
        if (request.name() == null || request.name().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item name is required");
        if (request.category() == null || request.category().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category is required");
        if (request.price() < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Price cannot be negative");
    }

    private void validateUpdate(UpdateCafeteriaItem request) {
        if (request.name() == null || request.name().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item name is required");
        if (request.category() == null || request.category().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category is required");
        if (request.price() < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Price cannot be negative");
    }

    private CafeteriaItemDto toDto(CafeteriaItem i) {
        return new CafeteriaItemDto(i.getId(), i.getName(), i.getCategory(),
                i.getPrice().doubleValue(), i.getDescription(), i.getStatus());
    }
}
