package uz.yusa.avtokredits.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.yusa.avtokredits.domain.post.Photo;
import uz.yusa.avtokredits.domain.post.Post;
import uz.yusa.avtokredits.repository.PhotoRepository;
import uz.yusa.avtokredits.repository.PostRepository;
import uz.yusa.avtokredits.service.PhotoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class PhotoController {

    private final PhotoRepository photoRepository; // Assuming you have a repository for Photo entity
    private final PostRepository postRepository;
    @GetMapping("/{id}")
        public ResponseEntity<Resource> getImage(@PathVariable Long id) {
        // Find the photo by name

        Post post = postRepository.findById(id).get();
        Photo photo = post.getPhotos().get(0);
        if (photo == null || photo.getPath() == null) {
            return ResponseEntity.notFound().build();
        }

        // Load image data from file system
        String filePath = photo.getPath(); // No need for concatenation
        Path imagePath = Paths.get(filePath);
        ByteArrayResource resource;
        try {
            resource = new ByteArrayResource(Files.readAllBytes(imagePath));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Set appropriate content type based on file extension
        MediaType mediaType = getImageMediaType(imagePath);

        // Prepare response with image data and content type
        return ResponseEntity.ok()
                .contentType(mediaType)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + photo.getPhotoName() + "\"")
                .body(resource);
    }

    @GetMapping("/{id}/photos")
    public ResponseEntity<List<Resource>> getAllPhotos(@PathVariable Long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (!postOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Post post = postOptional.get();
        List<Resource> photoResources = new ArrayList<>();

        for (Photo photo : post.getPhotos()) {
            Path imagePath = Paths.get(photo.getPath());
            System.out.println(photo.getPath());
            ByteArrayResource resource;
            try {
                resource = new ByteArrayResource(Files.readAllBytes(imagePath));
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            MediaType mediaType = getImageMediaType(imagePath);
            photoResources.add(resource);
        }

        return ResponseEntity.ok().body(photoResources);
    }

    // Helper method to determine the content type based on file extension
    private MediaType getImageMediaType(Path imagePath) {
        String fileName = imagePath.getFileName().toString();
        String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

        switch (fileExtension) {
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "png":
                return MediaType.IMAGE_PNG;
            case "gif":
                return MediaType.IMAGE_GIF;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
