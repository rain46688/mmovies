package mmovie.mmovie.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mmovie.mmovie.domain.Category;
import mmovie.mmovie.domain.Content;
import mmovie.mmovie.dto.ContentDto;
import mmovie.mmovie.repository.ContentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;

    @Transactional
    public Long createContents(Map<String, Object> paramMap) {

        Category category = new Category();
        Content video = Content.builder()
                .name((String)paramMap.get("name"))
                .type((String)paramMap.get("type"))
                .category(new Category(Long.valueOf((String)paramMap.get("ct_id"))))
                .src((byte[])paramMap.get("src"))
                .build();

        contentRepository.save(video);

        return video.getId();
    }

    public ContentDto getContents(Long id) {
        Content content = contentRepository.getReferenceById(id);
        return new ContentDto(content.getName(),content.getSrc());
    }

    public List<Content> findVideos() {
        return contentRepository.findAll();
    }
}
