package uz.yusa.avtokredits.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.yusa.avtokredits.domain.Application;
import uz.yusa.avtokredits.domain.Payment;
import uz.yusa.avtokredits.domain.PaymentTimeTable;
import uz.yusa.avtokredits.domain.post.CreditTarrif;
import uz.yusa.avtokredits.domain.post.Photo;
import uz.yusa.avtokredits.domain.post.Post;
import uz.yusa.avtokredits.dto.AllPostsDto;
import uz.yusa.avtokredits.exeption.NoFileExeption;
import uz.yusa.avtokredits.exeption.PostUploadFailedException;
import uz.yusa.avtokredits.repository.PaymentRepository;
import uz.yusa.avtokredits.repository.PhotoRepository;
import uz.yusa.avtokredits.repository.PostRepository;
import uz.yusa.avtokredits.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ApplicationService applicationService;
    private final PaymentTimeTableRepository paymentTimeTableRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;


    public List<AllPostsDto> getAllPosts() {

        List<Post> all = postRepository.findAll();
        List<AllPostsDto> dtos = new ArrayList<>();
        for (Post post : all) {
            Photo photo = post.getPhotos().get(1);
            dtos.add(AllPostsDto.builder()

                            .id(post.getId())
                            .title(post.getTitle())
                            .carBrand(post.getCar().getBrand())
                            .carModel(post.getCar().getModel())
                            .photoName(photo.getPhotoName())
                            .path(photo.getPath())
                            .creditMonthCount(post.getCar().getTarrif().getCountMonths())
                            .amount(post.getCar().getTarrif().getPrice())

                            .procents(post.getCar().getTarrif().getProcents())

                    .build());
            System.out.println(post);
        }

        return dtos;
    }
    public List<AllPostsDto> getAllActivePosts() {
        List<Post> all = postRepository.findByIsActiveTrue();
        List<AllPostsDto> dtos = new ArrayList<>();
        for (Post post : all) {


            dtos.add(AllPostsDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .carBrand(post.getCar().getBrand())
                    .carModel(post.getCar().getModel())
//                    .photoName(firstPhoto.getPhotoName()) // Access photo fields if available
//                    .path(firstPhoto.getPath()) // Access photo fields if available
                    .creditMonthCount(post.getCar().getTarrif().getCountMonths())
                    .amount(post.getCar().getTarrif().getPrice())
                    .procents(post.getCar().getTarrif().getProcents())
                    .build());
        }
        return dtos;
    }
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }
    private final String storagePath = " src/main/resources/static/cars/";
    public Post savePost(Post post,  List<MultipartFile> files) throws NoFileExeption, PostUploadFailedException {


        if (files == null || files.size() == 0 || files.stream().allMatch(MultipartFile::isEmpty)) {
                throw new NoFileExeption("No file uploaded");
            }

        List<String> filePaths = new ArrayList<>();
        post.setIsActive(true);
        Post savedPost = postRepository.save(post);

        for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    try {

                        // Save the file to a specific location on server
                        byte[] bytes = file.getBytes();
                        String fileName = savedPost.getId() + "-" + file.getOriginalFilename();
                        String filePath = storagePath + fileName;
                        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                        stream.write(bytes);
                        stream.close();
                        filePaths.add(filePath);
                        Photo photo = photoRepository.save(new Photo(fileName, "jpg", filePath));
                        photoRepository.addPhotoByPhotoIdAndPostId(savedPost.getId(), photo.getId());
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new PostUploadFailedException("File upload failed for file: " + file.getOriginalFilename());
                    }
                }
            }
        CreditTarrif tarrif = post.getCar().getTarrif();

            return post;
    }

    private void createPaymentTimeTable(CreditTarrif tarrif) {
        double price = tarrif.getPrice();
        int countMonths = tarrif.getCountMonths();
        double procents = tarrif.getProcents();

        double monthlyInterestRate = procents / 100 / 12;

// Calculate the monthly payment
        double monthlyPayment = (price * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -countMonths));

// Round the monthly payment to two decimal places
        monthlyPayment = Math.round(monthlyPayment * 100.0) / 100.0;
        PaymentTimeTable paymentTimeTable = new PaymentTimeTable();
        List<Payment> payments = new ArrayList<>();
        for (int i = 0; i < countMonths; i++) {
            Date date = new Date();

            // Convert Date to Calendar
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            // Set day of the month
            calendar.set(Calendar.DAY_OF_MONTH, new Date().getDay());

            // Set month (Note: January is 0)
            calendar.set(Calendar.MONTH, new Date().getMonth());

            // Convert Calendar back to Date
            Date modifiedDate = calendar.getTime();

            Payment payment = new Payment();
            payment.setAmount(monthlyPayment);
            payment.setPaymentDate(new Date());
            paymentRepository.save(payment);
        }

        paymentTimeTable.setPayments(payments);
        paymentTimeTableRepository.save(paymentTimeTable);
        System.out.println("Monthly payment: " + monthlyPayment);

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
