package uz.yusa.avtokredits.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.yusa.avtokredits.domain.post.Car;
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

    @OneToOne
    private User customer;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "phone")
    private String phone;

//    @Column(name = "car_id")
    @ManyToOne
    private Post car;

    @Column(name = "loanAmount")
    private String loanAmount;

    private String title;
    private String description;

    private Boolean isClosed = Boolean.FALSE;
    private Boolean isAccepted = Boolean.FALSE;
    @OneToOne
    private Post post;

    private Date appliedDate;
}
