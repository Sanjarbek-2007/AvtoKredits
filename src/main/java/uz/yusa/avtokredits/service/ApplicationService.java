package uz.yusa.avtokredits.service;

import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.yusa.avtokredits.domain.Application;
import uz.yusa.avtokredits.domain.post.Post;
import uz.yusa.avtokredits.domain.User;
import uz.yusa.avtokredits.dto.ApplicationSaveDto;
import uz.yusa.avtokredits.repository.ApplicationRepository;
import uz.yusa.avtokredits.repository.PostRepository;
import uz.yusa.avtokredits.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    public List<Application> getAllApplications() {


        return applicationRepository.findAll();

    }


    public Application saveApplication(ApplicationSaveDto application) {
        try {
            Application applicationEntity = new Application();
            // Установка полей из формы входных данных
            applicationEntity.setFullName(application.fullName());
            applicationEntity.setPhone(application.phone());
            applicationEntity.setPostId(application.postId());
            applicationEntity.setAppliedDate(new Date());
            applicationEntity.setIsAccepted(false);
            applicationEntity.setIsClosed(false);

            return  applicationRepository.save(applicationEntity); // Сохра
        } catch (Exception e) {
            throw new RuntimeException("Failed to save application", e);
        }
    }




    public Application getApplicationById(Long id) {

        return applicationRepository.findById(id).get();
    }

    public Application getApplication(Long id) {

        return applicationRepository.findById(id).get();
    }
    public void closeApplicationById(Long id) {
        applicationRepository.updateIsClosedById(true,id);
    }

//    public Application createApplication(Post post) {
//        String name = SecurityContextHolder.getContext().getAuthentication().getName();
//        User user = userRepository.findByEmail(name).get();
//        Application app = Application.builder()
//                .title("Sotib olmoqchi man")
//                .appliedDate(new Date())
//                .description("Avtomobilni")
//                .post(post)
//                .build();
//        return applicationRepository.save(app);
//
//    }




    public Application acceptApp(Long id) {
        applicationRepository.updateIsAcceptedById(true,id);


        return applicationRepository.findById(id).orElse(null);

    }
}
