package uz.yusa.avtokredits.dto;

public record GetPostDto(
        String carContent,
        String carBrand,
        String carModel,
        String carYear,
        String carColor,
        String carEngine,
        String carGear,
        String carFuelType,
        String creditTarifs,
        int creditMonthCount,
        double amount,
        double procents
) {

}
