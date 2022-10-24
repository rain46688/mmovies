package mmovie.mmovie.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mmovie.mmovie.common.Convert;
import mmovie.mmovie.domain.Category;
import mmovie.mmovie.dto.CategoryDto;
import mmovie.mmovie.dto.CategoryResult;
import mmovie.mmovie.dto.IdResponseDto;
import mmovie.mmovie.dto.Result;
import mmovie.mmovie.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;

    /**
     * 카테고리 추가하는 api
     * */
    @PostMapping("/api/v1/categories")
    public IdResponseDto createCategories(@RequestBody CategoryDto categoryDto) throws Exception{
        log.info(" =========== createCategories ===========");
        Long id = categoryService.createCategories(categoryDto);
        return new IdResponseDto(id);
    }

    /**
     * 카테고리 하나 불러오는 api
     */
    @GetMapping("/api/v1/categories/{id}")
    public CategoryResult getCategories(@PathVariable("id") Long id) throws Exception{
        log.info(" =========== getCategories ===========");
        CategoryResult categories = categoryService.getCategories(id);
        return categories;
    }

    /**
     * 카테고리들 불러오는 api
     * */
    @GetMapping("/api/v1/categories")
    public Result getCategories() throws Exception{
        log.info(" =========== getCategories All ===========");
        List<Category> getCategories = categoryService.getCategories();
        List<CategoryDto> collect = getCategories.stream().map(c -> new CategoryDto(c.getId(), new Convert().deCoder(c.getName()))).collect(Collectors.toList());

        return new Result(collect.size(), collect);
    }

    /**
     * 카테고리 삭제 api
     * */
    @DeleteMapping("/api/v1/categories/{id}")
    public IdResponseDto deleteCategories(@PathVariable("id") Long id) throws Exception{
        log.info(" =========== deleteCategories ===========");
        categoryService.deleteCategories(id);

        return new IdResponseDto(id);
    }


}
