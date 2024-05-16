package uz.yusa.avtokredits.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.yusa.avtokredits.domain.Application;
import uz.yusa.avtokredits.domain.post.Post;
import uz.yusa.avtokredits.exeption.NoFileExeption;
import uz.yusa.avtokredits.exeption.PostUploadFailedException;
import uz.yusa.avtokredits.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ApplicationService applicationService;
    private final String storagePath = "src/main/resources/static/cars/";


    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    public List<Post> getAllActivePosts() {
        return postRepository.findByIsActiveTrue();
    }
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post savePost(Post post , MultipartFile[] files) throws NoFileExeption, PostUploadFailedException {

            if (files == null || files.length == 0 || Arrays.stream(files).allMatch(MultipartFile::isEmpty)) {
                throw new NoFileExeption("No file uploaded");
            }

            List<String> filePaths = new ArrayList<>();

            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    try {
                        // Save the file to a specific location on server
                        byte[] bytes = file.getBytes();
                        String fileName = post.getId() + "-" + file.getOriginalFilename();
                        String filePath = storagePath + fileName;
                        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                        stream.write(bytes);
                        stream.close();
                        filePaths.add(filePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new PostUploadFailedException("File upload failed for file: " + file.getOriginalFilename());
                    }
                }
            }

            // Join file paths to a single string, assuming you store them as a comma-separated list
            post.setPhotoPath(String.join(",", filePaths));
            postRepository.save(post);

            return post;
    }

    public String deletePost(Long id) {
        return postRepository.updateIsActiveById(false, id)+"";
    }

    public Post updatePost(Post post, Long id) {
         postRepository.updateCarAndTitleAndContentById(
                post.getCar(),
                post.getTitle(),
                post.getContent(),
                 id
        );
         return  post;
    }

    public Application buyPost(Long id) {
        Post postById = getPostById(id);
        return applicationService.createApplication(postById);
    }
}
