package uz.yusa.avtokredits.domain.post;

import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car {
    private String brand;
    private String model;
    private String year;
    private String color;
    private String engine;
    private String gear;
    private String fuelType;
    @Embedded
    private CreditTarrif tarrif;

}
