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
import uz.yusa.avtokredits.repository.ApplicationRepository;
import uz.yusa.avtokredits.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    public final ApplicationRepository applicationRepository;
    public final UserRepository userRepository;
    public List<Application> getAllApplications() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return applicationRepository.findByCustomer_Email(email);

    }


    public Application saveApplication(Application application) {
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();

            // Установка полей из формы входных данных
            application.setFullName(application.getFullName());
            application.setPhone(application.getPhone());
            application.setCar(application.getCar());
            application.setCarPrice(application.getCarPrice());
            application.setLoanAmount(application.getLoanAmount());
            application.setAppliedDate(new Date());

            // Получение пользователя по email
            User user = userRepository.findByEmail(name)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + name));

            application.setCustomer(user); // Установка пользователя

            return applicationRepository.save(application); // Сохранение заявки
        } catch (Exception e) {
            throw new RuntimeException("Failed to save application", e);
        }
    }




    public Application getApplicationById(Long id) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return applicationRepository.findByIdAndCustomer_Email(id, email).get();
    }
    public Application getApplication(Long id) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return applicationRepository.findByIdAndCustomer_Email(id, email).get();
    }
    public void closeApplicationById(Long id) {
        applicationRepository.updateIsClosedById(true,id);
    }

    public Application createApplication(Post post) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(name).get();
        Application app = Application.builder()
                .title("Sotib olmoqchi man")
                .appliedDate(new Date())
                .description("Avtomobilni")
                .customer(user)
                .post(post)
                .build();
        return applicationRepository.save(app);

    }





    public Application getApplication() {
        return null;
    }
}
