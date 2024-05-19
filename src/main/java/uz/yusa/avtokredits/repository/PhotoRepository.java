package uz.yusa.avtokredits.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yusa.avtokredits.domain.post.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO post_photos (post_id, photos_id) values( :postId, :photoId )")
    public void addPhotoByPhotoIdAndPostId(@Param("postId") Long postId, Long photoId);
}