package uz.yusa.avtokredits.domain.post;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class CreditTarrif {

    private String name;
    private double price;
    @Nullable
    private int countMonths;
    @Nullable
    private double  procents;
    private double total;

}
