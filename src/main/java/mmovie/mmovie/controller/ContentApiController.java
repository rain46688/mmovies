package mmovie.mmovie.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mmovie.mmovie.common.Convert;
import mmovie.mmovie.common.FileUtil;
import mmovie.mmovie.dto.ContentDto;
import mmovie.mmovie.dto.ContentThumbDto;
import mmovie.mmovie.dto.IdResponseDto;
import mmovie.mmovie.dto.Result;
import mmovie.mmovie.service.ContentService;
import org.jcodec.api.FrameGrab;
import org.jcodec.common.DemuxerTrack;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;
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

        File source = new FileUtil().multipartFileToFile(file);

        log.info(" === file : "+file);
        log.info(" === paramMap : "+paramMap);
        log.info(" === source : "+source);

        log.info("1 : "+NIOUtils.readableChannel(source));

        FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(source));

        log.info(" === grab : "+grab);

        DemuxerTrack vt = grab.getVideoTrack();

        log.info(" === vt : "+vt);

        int count = vt.getMeta().getTotalFrames();
        log.info("=== count : "+count);

        int frameNumber = count / 2;
        Picture picture = FrameGrab.getFrameFromFile(source, frameNumber);

        log.info(" === test1");

        BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);

        log.info(" === test2");

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", output);
        String imageAsBase64 = Base64.getEncoder().encodeToString(output.toByteArray());
        output.close();

        new FileUtil().deleteFile(".\\contents");

        log.info(" === test3");

        Long id = contentService.createContents(file, (String) paramMap.get("ctId"), imageAsBase64);

        log.info(" === test4");

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
    public Result getContentsCms() throws Exception {
        List<ContentThumbDto> getContents = contentService.getContents();

        List<ContentThumbDto> collect = getContents.stream().map(v -> new ContentThumbDto(
            v.getId(),
                new Convert().deCoder(v.getName()),
                new Convert().deCoder(v.getCateName()),
                v.getCateId(),
                v.getThumbnailSrc()
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
