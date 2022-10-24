package mmovie.mmovie.repository;

import mmovie.mmovie.domain.Content;
import mmovie.mmovie.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
    List<Content> findByName(String name);

    List<Content> findBySrc(byte[] src);
}
