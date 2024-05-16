package uz.yusa.avtokredits.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.yusa.avtokredits.domain.post.Car;
import uz.yusa.avtokredits.domain.post.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Transactional
    @Modifying
    @Query("update Post p set p.car = ?1, p.title = ?2, p.content = ?3 where p.id = ?4")
    void updateCarAndTitleAndContentById(Car car, String title, String content,   Long id);

    @Transactional
    @Modifying
    @Query("update Post p set p.isActive = ?1 where p.id = ?2")
    int updateIsActiveById(Boolean isActive, Long id);

    List<Post> findByIsActiveTrue();

}
