package uz.yusa.avtokredits.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.yusa.avtokredits.domain.post.Photo;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class PhotoDto {
    private Long id;
    private String filePath;
    private String url;

    public PhotoDto(Photo photo) {
        this.id = photo.getId();
        this.filePath = photo.getPath();
        this.url = "/api/photos/" + filePath;
    }

    // getters and setters
}