package uz.yusa.avtokredits.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public record UploadPostDto (
        String postTitle,
        String carContent,
        List<MultipartFile> carImages,
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
        double procents,
        double firstlyDeposit
){
}
