package uz.yusa.avtokredits.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.yusa.avtokredits.domain.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    List<User> findByRoles_Name(String name);


    List<User> findByEmailLike(String email);



    public Optional<User> findByEmail(String email);
    public Optional<User> findByUsername(String username);

    @Transactional
    @Modifying
    @Query("update User u set u.confirmed = ?1 where u.id = ?2")
    void updateConfirmedById(Boolean confirmed, Long id);



}