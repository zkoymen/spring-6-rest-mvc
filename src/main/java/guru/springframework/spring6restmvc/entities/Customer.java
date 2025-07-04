package guru.springframework.spring6restmvc.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Customer {


    // Generated by hibernate
    // GenericGenerator is deprecated
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    // Hints for Hibernate to generate SQL table
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID id;


    private String customerName;
    @Version
    private Integer version;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
