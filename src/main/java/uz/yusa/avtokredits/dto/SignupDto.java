package uz.yusa.avtokredits.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.ToString;
import uz.yusa.avtokredits.domain.Role;

import java.util.List;
public record SignupDto(
        String email,
        String phoneNumber,
        String username,
        String password
        ) {}
