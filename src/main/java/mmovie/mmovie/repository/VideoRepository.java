package mmovie.mmovie.repository;

import mmovie.mmovie.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {
}
