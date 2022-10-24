package mmovie.mmovie.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mmovie.mmovie.common.Convert;
import mmovie.mmovie.domain.Category;
import mmovie.mmovie.domain.Content;
import mmovie.mmovie.dto.CategoryDto;
import mmovie.mmovie.dto.CategoryResult;
import mmovie.mmovie.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * 카테고리 추가하는 api
     */
    @Transactional
    public Long createCategories(CategoryDto categoryDto) throws Exception {
        log.info(" === categoryDto.getName() : " + categoryDto.getName());
        Category category = new Category().builder()
                .name(new Convert().enCoder(categoryDto.getName()))
                .build();
        validateDuplicateCategory(category);
        categoryRepository.save(category);
        return category.getId();
    }

    /**
     * 유효성 검사 로직
     */
    private void validateDuplicateCategory(Category category) throws Exception {
        List<Category> getCategories = categoryRepository.findByName(category.getName());
        if (getCategories.size() > 0) {
            log.info("들어옴");
            throw new IllegalStateException("이미 존재하는 목록입니다.");
        }
    }

    public CategoryResult getCategories(Long id) throws Exception{
        try {
            Category category = categoryRepository.getReferenceById(id);
            List<Content> getContents = category.getContents().stream().map(c -> new Content(
                    c.getId(),
                    new Convert().deCoder(c.getName()),
                    new Convert().deCoder(c.getType()),
                    c.getCategory(),
                    c.getSrc()
            )).collect(Collectors.toList());

            return new CategoryResult(category.getId(),new Convert().deCoder(category.getName()),getContents.size(),getContents);
        }catch (Exception e){
            throw new IllegalStateException("존재하지 않는 목록입니다.");
        }
    }

    public List<Category> getCategories() throws Exception{
        return categoryRepository.findAll();
    }
    @Transactional
    public void deleteCategories(Long id) throws Exception {
        try {
            categoryRepository.deleteById(id);
        }catch (Exception e){
            throw new IllegalStateException("존재하지 않는 목록입니다.");
        }
    }
}
