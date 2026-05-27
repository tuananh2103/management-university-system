package university.management.cafe.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import university.management.cafe.dto.CafeteriaItemDto;
import university.management.cafe.dto.CreateCafeteriaItem;
import university.management.cafe.dto.UpdateCafeteriaItem;
import university.management.cafe.service.CafeteriaService;

@RestController
@RequestMapping("/api/cafeteria/items")
@CrossOrigin(origins = "http://localhost:4200")
public class CafeteriaController {

    private final CafeteriaService cafeteriaService;

    public CafeteriaController(CafeteriaService cafeteriaService) {
        this.cafeteriaService = cafeteriaService;
    }

    @GetMapping
    public List<CafeteriaItemDto> getAllItems() {
        return cafeteriaService.getAllItems();
    }

    @GetMapping("/{id}")
    public CafeteriaItemDto getItemById(@PathVariable Long id) {
        return cafeteriaService.getItemById(id);
    }

    @PostMapping
    public ResponseEntity<CafeteriaItemDto> createItem(
            @RequestBody CreateCafeteriaItem request
    ) {
        CafeteriaItemDto createdItem = cafeteriaService.createItem(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    @PutMapping("/{id}")
    public CafeteriaItemDto updateItem(
            @PathVariable Long id,
            @RequestBody UpdateCafeteriaItem request
    ) {
        return cafeteriaService.updateItem(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        cafeteriaService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}