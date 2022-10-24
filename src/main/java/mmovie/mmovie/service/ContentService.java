package mmovie.mmovie.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mmovie.mmovie.common.Convert;
import mmovie.mmovie.domain.Category;
import mmovie.mmovie.domain.Content;
import mmovie.mmovie.domain.Member;
import mmovie.mmovie.dto.ContentDto;
import mmovie.mmovie.repository.ContentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;

    @Transactional
    public Long createContents(Map<String, Object> paramMap) throws Exception {

        Content content = Content.builder()
                .name(new Convert().enCoder(((String)paramMap.get("name"))))
                .type(new Convert().enCoder(((String)paramMap.get("type"))))
                .category(new Category(Long.valueOf((String)paramMap.get("ctId"))))
                .src((byte[])paramMap.get("src"))
                .build();

        validateDuplicateContent(content);
        contentRepository.save(content);

        return content.getId();
    }

    /**
     * 유효성 검사 로직
     * */
    private void validateDuplicateContent(Content content) throws Exception{
        List<Content> getContentsName = contentRepository.findByName(content.getName());
        List<Content> getContentsSrc = contentRepository.findBySrc(content.getSrc());
        if(getContentsName.size() > 0 || getContentsSrc.size() > 0){
            throw new IllegalStateException("이미 존재하는 컨텐츠입니다.");
        }
    }

    public ContentDto getContents(Long id) throws Exception {
        try {
            Content content = contentRepository.getReferenceById(id);
            return new ContentDto(
                    content.getId(),
                    new Convert().deCoder(content.getName()),
                    new Convert().deCoder(content.getType()),
                    new Convert().deCoder(content.getCategory().getName()),
                    content.getSrc()
            );
        }catch (Exception e){
            throw new IllegalStateException("존재하지 않는 컨텐츠입니다.");
        }
    }

    public List<Content> getContents() {
        return contentRepository.findAll();
    }

    @Transactional
    public void deleteContents(Long id) throws Exception{
        try {
            contentRepository.deleteById(id);
        }catch (Exception e){
            throw new IllegalStateException("존재하지 않는 컨텐츠입니다.");
        }
    }


}
