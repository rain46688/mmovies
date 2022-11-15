package mmovie.mmovie.repository;

import mmovie.mmovie.domain.Category;
import mmovie.mmovie.dto.ContentThumbDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByName(String name);

    @Query("select " +
            "new mmovie.mmovie.dto.ContentThumbDto(a.id, a.name, b.name, b.id, a.thumbnailSrc) " +
            "from " +
            "Content a inner join Category b on a.category.id = :id")
    List<ContentThumbDto> getContentReferenceById(@Param("id") Long id);
}
