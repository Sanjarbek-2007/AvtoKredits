package uz.yusa.avtokredits.dto;

public record CreationApplicationDto(
        Long customerId,
        String title,
        String description,
        Long postId
) {}
