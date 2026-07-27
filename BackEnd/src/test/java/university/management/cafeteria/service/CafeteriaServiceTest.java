package university.management.cafeteria.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import university.management.cafe.dto.CreateCafeteriaItem;
import university.management.cafe.entity.CafeteriaItem;
import university.management.cafe.repository.CafeteriaItemRepository;
import university.management.cafe.service.CafeteriaService;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CafeteriaServiceTest {

    @Mock
    private CafeteriaItemRepository cafeteriaItemRepository;

    @InjectMocks
    private CafeteriaService cafeteriaService;

    private CreateCafeteriaItem request;

    @BeforeEach
    void setUp() {
        request = new CreateCafeteriaItem("Iced Tea", "Drink", 1.5, "Cold iced tea", "AVAILABLE");
    }

    @Test
    void createItem_savesAndReturnsDto() {
        CafeteriaItem saved = new CafeteriaItem();
        saved.setName(request.name());
        saved.setCategory(request.category());
        saved.setPrice(BigDecimal.valueOf(request.price()));
        saved.setDescription(request.description());
        saved.setStatus(request.status());

        when(cafeteriaItemRepository.save(any(CafeteriaItem.class))).thenReturn(saved);

        var result = cafeteriaService.createItem(request);

        assertThat(result.name()).isEqualTo("Iced Tea");
        assertThat(result.price()).isEqualTo(1.5);
    }
}