package uz.yusa.avtokredits.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record GetPostDto(
        Long    id,
        List<Long> photoIds,
        String carContent,
        String carBrand,
        String carModel,
        String carYear,
        String carColor,
        String carEngine,
        String carGear,
        String carFuelType,
        String creditTarifs,
        int    creditMonthCount,
        double amount,
        double procents
) {

}
