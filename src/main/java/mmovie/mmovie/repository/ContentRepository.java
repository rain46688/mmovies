package mmovie.mmovie.repository;

import mmovie.mmovie.domain.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
