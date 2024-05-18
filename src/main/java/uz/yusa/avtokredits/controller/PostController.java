package uz.yusa.avtokredits.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.yusa.avtokredits.domain.Application;
import uz.yusa.avtokredits.domain.post.Post;
import uz.yusa.avtokredits.dto.UploadPostDto;
import uz.yusa.avtokredits.exeption.NoFileExeption;
import uz.yusa.avtokredits.exeption.PostUploadFailedException;
import uz.yusa.avtokredits.service.PostService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class PostController {
    private final PostService postService;
    @GetMapping
    public ResponseEntity<List<Post>> getCars() {
        return ResponseEntity.ok(postService.getAllActivePosts());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Post> addPost(@RequestBody UploadPostDto request) throws NoFileExeption {
        Post post = new Post();
        post.setTitle(request.getPostTitle());
        post.setContent(request.getCarContent());
        post.getCar().setBrand(request.getCarBrand());
        post.getCar().setModel(request.getCarModel());
        post.getCar().setYear(request.getCarYear());
        post.getCar().setColor(request.getCarColor());
        post.getCar().setEngine(request.getCarEngine());
        post.getCar().setGear(request.getCarGear());
        post.getCar().setFuelType(request.getCarFuelType());
        post.getCar().getTarrif().setName(request.getCreditTarifs());
        post.getCar().getTarrif().setCountMonths(request.getCreditMonthCount());
        post.getCar().getTarrif().setPrice(request.getAmount());
        post.getCar().getTarrif().setProcents(request.getProcents());
        MultipartFile[] carImages = request.getCarImages();
        try {
            return ResponseEntity.ok(postService.savePost(post, carImages));
        } catch (NoFileExeption e) {
            throw new RuntimeException(e);
        } catch (PostUploadFailedException e) {
            throw new RuntimeException(e);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.deletePost(id));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }
    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
        return ResponseEntity.ok(postService.updatePost(post, id));
    }
    @PostMapping("/{id}/buy")
    public ResponseEntity<Application> buyCar(@PathVariable Long id) {
        return ResponseEntity.ok(postService.buyPost(id));
    }



}
