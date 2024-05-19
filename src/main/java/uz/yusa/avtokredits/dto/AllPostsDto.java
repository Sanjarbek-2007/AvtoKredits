package uz.yusa.avtokredits.dto;

import lombok.Builder;

@Builder
public record AllPostsDto(
        Long id,
        String title,
        String carBrand,
        String carModel,
        String photoName,
        String path,
        int creditMonthCount,
        double amount,
        double procents
) {
}
