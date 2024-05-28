package uz.yusa.avtokredits.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yusa.avtokredits.domain.Application;
import uz.yusa.avtokredits.domain.post.Post;
import uz.yusa.avtokredits.dto.ApplicationSaveDto;
import uz.yusa.avtokredits.dto.GetApplicationDto;
import uz.yusa.avtokredits.repository.ApplicationRepository;
import uz.yusa.avtokredits.repository.PostRepository;
import uz.yusa.avtokredits.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    public List<GetApplicationDto> getAllApplications() {
        List<Application> applications = applicationRepository.findAll();

        return applications.stream().map(this::convertToGetApplicationDto).collect(Collectors.toList());
    }

    public GetApplicationDto getApplicationById(Long id) {
        System.out.println(id + " _________________________ ID OF APPPPPPPPPPPPPPPPPP");
        Application application = applicationRepository.findById(id).orElse(null);
        System.out.println(application.getId() + " _________________________ ID OF APPPPPPPPPPPPPPPPPP IDDDDDDDDDDDDDDDDDDDDD");

        return convertToGetApplicationDto(application);
    }

    private GetApplicationDto convertToGetApplicationDto(Application application) {
        Post post = postRepository.findById(application.getPostId())
                .orElse(null);

        return GetApplicationDto.builder()
                .id(application.getId())
                .fullName(application.getFullName())
                .phone(application.getPhone())
                .title(application.getTitle())
                .description(application.getDescription())
                .isClosed(application.getIsClosed()).isAccepted(application.getIsAccepted())
                .post(post)
                .appliedDate(application.getAppliedDate()).build();

    }


    public Application saveApplication(ApplicationSaveDto application) {
        try {
            Application applicationEntity = new Application();
            // Установка полей из формы входных данных
            applicationEntity.setFullName(application.fullName());
            applicationEntity.setPhone(application.phone());
            applicationEntity.setPostId(application.postId());
            applicationEntity.setAppliedDate(new Date());
            applicationEntity.setTitle(application.title());
            applicationEntity.setDescription(application.description());
            applicationEntity.setIsAccepted(false);
            applicationEntity.setIsClosed(false);
            System.out.println(applicationEntity.getPostId()+"   POST ID HEREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
            return applicationRepository.save(applicationEntity); // Сохра
        } catch (Exception e) {
            throw new RuntimeException("Failed to save application", e);
        }
    }


    public void closeApplicationById(Long id) {
        applicationRepository.updateIsClosedById(true, id);
    }


    public void acceptApp(Long id) {
        applicationRepository.updateIsAcceptedById(true, id);



    }
}
