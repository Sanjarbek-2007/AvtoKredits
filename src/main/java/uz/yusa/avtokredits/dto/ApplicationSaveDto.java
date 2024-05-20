package uz.yusa.avtokredits.dto;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.util.Date;
import lombok.Builder;
import uz.yusa.avtokredits.domain.post.Post;

@Builder
public record ApplicationSaveDto(
         String fullName,
         String phone,
         Long car,
         String loanAmount,
         String title,
         String description,
         Boolean isClosed,
         Boolean isAccepted ,
         Post post
){}
