package uz.yusa.avtokredits.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UploadPostDto {

        private String postTitle;
        private String carContent;
        private MultipartFile[] carImages;
        private String carBrand;
        private String carModel;
        private String carYear;
        private String carColor;
        private String carEngine;
        private String carGear;
        private String carFuelType;
        private String creditTarifs;
        private int creditMonthCount;
        private double amount;
        private double procents;

        // Getters and setters
        // (Omitted for brevity)

}
