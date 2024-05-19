package uz.yusa.avtokredits.controller;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.yusa.avtokredits.domain.Application;
import uz.yusa.avtokredits.domain.post.Car;
import uz.yusa.avtokredits.domain.post.CreditTarrif;
import uz.yusa.avtokredits.domain.post.Post;
import uz.yusa.avtokredits.dto.UploadPostDto;
import uz.yusa.avtokredits.exeption.NoFileExeption;
import uz.yusa.avtokredits.exeption.PostUploadFailedException;
import uz.yusa.avtokredits.service.PostService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    @GetMapping
    public ResponseEntity<List<Post>> getCars() {
        return ResponseEntity.ok(postService.getAllActivePosts());
    }
/**/

//    @PreAuthorize("hasRole('ADMIN')")
@PostMapping("/add")
public ResponseEntity<Post> addPost(@ModelAttribute UploadPostDto dto
//                                     @RequestParam("postTitle") String postTitle,
//                                       @RequestParam("carContent") String carContent,
//                                       @RequestParam("carImages") List<MultipartFile> carImages,
//                                       @RequestParam("carBrand") String carBrand,
//                                       @RequestParam("carModel") String carModel,
//                                       @RequestParam("carYear") String carYear,
//                                       @RequestParam("carColor") String carColor,
//                                       @RequestParam("carEngine") String carEngine,
//                                       @RequestParam("carGear") String carGear,
//                                       @RequestParam("carFuelType") String carFuelType,
//                                       @RequestParam("creditTarifs") String creditTarifs,
//                                       @RequestParam("creditMonthCount") int creditMonthCount,
//                                       @RequestParam("amount") double amount,
//                                       @RequestParam("procents") double procents

) throws NoFileExeption {
//
//    Post post1 = Post.builder().photos(new ArrayList<>()).car(new Car("SDsd", "sdsd", "sdsd", "sdsds", "sdsdds", "AT", "AALS:",
//            new CreditTarrif("dsdsdssd", 123, 123, 123, 123))).build();
            Post post1 = Post.builder().title(dto.postTitle()).content(dto.carContent()).photos(new ArrayList<>()).car(new Car(dto.carBrand(),
                    dto.carModel(), dto.carYear(), dto.carColor(), dto.carEngine(), dto.carGear(), dto.carFuelType(),
            new CreditTarrif(dto.creditTarifs(), dto.amount(), dto.creditMonthCount(), dto.procents(), dto.amount()/100*dto.procents()))).build();


//        post.setTitle(postTitle);
//        post.setContent(carContent);
//        post.getCar().setBrand(carBrand);
//        post.getCar().setModel(carModel);
//        post.getCar().setYear(carYear);
//        post.getCar().setColor(carColor);
//        post.getCar().setEngine(carEngine);
//        post.getCar().setGear(carGear);
//        post.getCar().setFuelType(carFuelType);
//        post.getCar().getTarrif().setName(creditTarifs);
//        post.getCar().getTarrif().setCountMonths(creditMonthCount);
//        post.getCar().getTarrif().setPrice(amount);
//        post.getCar().getTarrif().setProcents(procents);
        try {
            return ResponseEntity.ok(postService.savePost(post1, dto.carImages()));
//            return ResponseEntity.ok(postService.savePost(post, null));
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
