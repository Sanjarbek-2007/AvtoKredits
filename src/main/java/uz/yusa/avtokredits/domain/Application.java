package uz.yusa.avtokredits.domain;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import uz.yusa.avtokredits.domain.post.Post;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "phone")
    private String phone;

    private String title;
    private String description;

    private Boolean isClosed = Boolean.FALSE;
    private Boolean isAccepted = Boolean.FALSE;

    private Long postId;

    private Date appliedDate;
}
