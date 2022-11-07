package mmovie.mmovie.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mmovie.mmovie.common.Convert;
import mmovie.mmovie.domain.Content;
import mmovie.mmovie.dto.IdResponseDto;
import mmovie.mmovie.dto.Result;
import mmovie.mmovie.dto.ContentDto;
import mmovie.mmovie.service.ContentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ContentApiController {

    private final ContentService contentService;

    /**
     * 컨텐츠 추가하는 api
     * */
    @PostMapping("/api/v1/contents")
    public IdResponseDto createContents(@RequestParam("file") MultipartFile file, @RequestParam Map<String, Object> paramMap) throws Exception {
        log.info(" =========== createContents ===========");

            Long id = contentService.createContents(file, (String) paramMap.get("ctId"));

            return new IdResponseDto(id);
    }

    /**
     * 컨텐츠 하나 불러오는 api
     * */
    @GetMapping("/api/v1/contents/{id}")
    public ContentDto getContents(@PathVariable("id") Long id) throws Exception{
        log.info(" =========== getContents ===========");
        ContentDto content = contentService.getContents(id);

        return content;
    }

    /**
     * 컨텐츠들 불러오는 api
     * */
    @GetMapping("/api/v1/contents")
    public Result getContents() throws Exception{
        log.info(" =========== getContents All ===========");
        List<Content> getContents = contentService.getContents();
        List<ContentDto> collect = getContents.stream().map(v -> new ContentDto(
                v.getId(),
                new Convert().deCoder(v.getName()),
                new Convert().deCoder(v.getType()),
                new Convert().deCoder(v.getCategory().getName()),
                v.getSrc()
        )).collect(Collectors.toList());

        return new Result(collect.size(), collect);
    }

    /**
     * 컨텐츠 삭제 api
     * */
    @DeleteMapping("/api/v1/contents/{id}")
    public IdResponseDto deleteContents(@PathVariable("id") Long id) throws Exception{
        log.info(" =========== deleteContents ===========");
        contentService.deleteContents(id);

        return new IdResponseDto(id);
    }


}
