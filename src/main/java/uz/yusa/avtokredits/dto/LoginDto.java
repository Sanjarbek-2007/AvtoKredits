package uz.yusa.avtokredits.dto;

import lombok.Builder;

@Builder
public record LoginDto (
        String email,
        String password
){

}
