package uz.yusa.avtokredits.dto;

import uz.yusa.avtokredits.domain.post.Car;
import uz.yusa.avtokredits.domain.User;

public record AddingPostDto(
        Car car,
        String title,
        String content,
        User author
) {
}
