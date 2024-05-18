package uz.yusa.avtokredits.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yusa.avtokredits.domain.post.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}