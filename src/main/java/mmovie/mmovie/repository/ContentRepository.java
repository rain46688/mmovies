package mmovie.mmovie.repository;

import mmovie.mmovie.domain.Content;
import mmovie.mmovie.dto.ContentThumbDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
    List<Content> findByName(String name);

    List<Content> findBySrc(byte[] src);

    @Query("select " +
            "new mmovie.mmovie.dto.ContentThumbDto(a.id, a.name, b.name, b.id, a.thumbnailSrc) " +
            "from " +
            "Content a inner join Category b on a.category.id = b.id")
    List<ContentThumbDto> findContentAll();
}
