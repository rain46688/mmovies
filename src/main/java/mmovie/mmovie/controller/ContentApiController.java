package mmovie.mmovie.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mmovie.mmovie.common.FileContent;
import mmovie.mmovie.domain.Content;
import mmovie.mmovie.dto.IdResponseDto;
import mmovie.mmovie.dto.Result;
import mmovie.mmovie.dto.ContentDto;
import mmovie.mmovie.service.ContentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
    public IdResponseDto createContents(@RequestParam Map<String, Object> paramMap, MultipartHttpServletRequest multipartRequest) throws Exception {
        log.info(" =========== createContents ===========");
        MultipartFile file = multipartRequest.getFile("src");

        if(!file.isEmpty()){
            paramMap.put("src", new FileContent().convertFileToByte(file));
        }

        Long id = contentService.createContents(paramMap);

        return new IdResponseDto(id);
    }

    /**
     * 컨텐츠 하나 불러오는 api
     * */
    @GetMapping("/api/v1/contents/{id}")
    public ContentDto getContents(@PathVariable("id") Long id){
        log.info(" =========== getContents ===========");
        log.info("id : "+id);
        ContentDto content = contentService.getContents(id);

        return content;
    }

    /**
     * 비디오들 불러오는 api
     * */
    @GetMapping("/api/v1/getVideos")
    public Result videosV1(){
        log.info(" =========== videosV1 ===========");
        List<Content> findMembers = contentService.findVideos();
        List<ContentDto> collect = findMembers.stream().map(v -> new ContentDto(v.getName(),v.getSrc())).collect(Collectors.toList());

        return new Result(collect.size(), collect);
    }

}