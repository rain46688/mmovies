package mmovie.mmovie.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mmovie.mmovie.common.FileContent;
import mmovie.mmovie.domain.Member;
import mmovie.mmovie.domain.Video;
import mmovie.mmovie.dto.MemberDto;
import mmovie.mmovie.dto.Result;
import mmovie.mmovie.dto.VideoDto;
import mmovie.mmovie.dto.VideoResponseDto;
import mmovie.mmovie.service.VideoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class VideoApiController {

    private final VideoService videoService;

    /**
     * 비디오 추가하는 api
     * */
    @PostMapping("/api/v1/setVideo")
    public VideoResponseDto create(@RequestParam Map<String, Object> paramMap, MultipartHttpServletRequest multipartRequest) throws Exception{
        log.info(" =========== uploadV1 ===========");
        MultipartFile file = multipartRequest.getFile("blob");

        if(!file.isEmpty()){
            paramMap.put("video", new FileContent().convertFileToByte(file));
        }

        Long id = videoService.create(paramMap);

        return new VideoResponseDto(id);
    }

    /**
     * 비디오 하나 불러오는 api
     * */
    @GetMapping("/api/v1/getVideo/{id}")
    public VideoDto videoV1(@PathVariable("id") Long id){
        log.info(" =========== memberV1 ===========");
        log.info("id : "+id);
        VideoDto video = videoService.findVideo(id);

        return video;
    }

    /**
     * 비디오들 불러오는 api
     * */
    @GetMapping("/api/v1/getVideos")
    public Result videosV1(){
        log.info(" =========== videosV1 ===========");
        List<Video> findMembers = videoService.findVideos();
        List<VideoDto> collect = findMembers.stream().map(v -> new VideoDto(v.getName(),v.getVideo())).collect(Collectors.toList());

        return new Result(collect.size(), collect);
    }

}
