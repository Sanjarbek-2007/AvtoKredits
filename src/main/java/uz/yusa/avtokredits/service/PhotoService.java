package uz.yusa.avtokredits.service;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import uz.yusa.avtokredits.domain.post.Post;
import uz.yusa.avtokredits.dto.PhotoDto;
import uz.yusa.avtokredits.repository.PostRepository;

@Service
public class PhotoService {
//
//    @Autowired
//    private PostRepository postRepository;
//
//    private final Path fileStorageLocation;
//
//    public PhotoService() {
//        this.fileStorageLocation = Paths.get("path/to/photos")
//                .toAbsolutePath().normalize();
//    }
//
//    public List<PhotoDto> getAllPhotos() {
//        List<Post> posts = postRepository.findAll();
//        return posts.stream()
//                .flatMap(post -> post.getPhotos().stream())
//                .map(PhotoDto::new)
//                .collect(Collectors.toList());
//    }
//
//    public Resource loadFileAsResource(String fileName) {
//        try {
//            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
//            Resource resource = new UrlResource(filePath.toUri());
//            if (resource.exists()) {
//                return resource;
//            } else {
//                throw new RuntimeException("File not found " + fileName);
//            }
//        } catch (Exception ex) {
//            throw new RuntimeException("File not found " + fileName, ex);
//        }
//    }
}
