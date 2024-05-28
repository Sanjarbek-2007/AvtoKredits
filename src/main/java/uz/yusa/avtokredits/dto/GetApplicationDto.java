package uz.yusa.avtokredits.dto;

import java.util.Date;
import lombok.Builder;
import uz.yusa.avtokredits.domain.post.Post;

@Builder
public record GetApplicationDto(
        Long id,
        String fullName,
        String phone,
        String title,
        String description,
        Boolean isClosed,
        Boolean isAccepted,
        Post post,
        Date appliedDate
){}
