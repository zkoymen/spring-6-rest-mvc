package guru.springframework.spring6restmvc.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Builder
@Data
public class BeerDTO {

    // DTOs are used to transfer data between processes
    // carry data without necessarily enforcing strict validation rules

    private UUID id;
    private Integer version;

    @NotBlank           // blank space still is a value
    @NotNull
    private String beerName;

    // enum
    @NotNull
    private BeerStyle beerStyle;

    @NotBlank
    @NotNull
    private String upc;
    private Integer quantityOnHand;


    @NotNull
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
