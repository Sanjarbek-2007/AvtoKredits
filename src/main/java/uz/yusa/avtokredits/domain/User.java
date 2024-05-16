package uz.yusa.avtokredits.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import uz.yusa.avtokredits.config.auditing.Auditor;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "users")
public class User extends Auditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @Column(columnDefinition = "boolean default true")
    @Builder.Default
    private Boolean enabled=Boolean.TRUE;
    @Column(columnDefinition = "boolean default true")
    @Builder.Default
    private Boolean confirmed=Boolean.FALSE;
    @ManyToMany
    private List<Role> roles;

}
