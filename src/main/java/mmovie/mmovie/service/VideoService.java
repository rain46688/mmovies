package mmovie.mmovie.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mmovie.mmovie.domain.Member;
import mmovie.mmovie.domain.Video;
import mmovie.mmovie.dto.MemberDto;
import mmovie.mmovie.dto.VideoDto;
import mmovie.mmovie.repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    @Transactional
    public Long create(Map<String, Object> paramMap) {

        Video video = Video.builder()
                .name((String)paramMap.get("name"))
                .video((byte[])paramMap.get("video"))
                .build();

        videoRepository.save(video);

        return video.getId();
    }

    public VideoDto findVideo(Long id) {
        Video video = videoRepository.getReferenceById(id);
        return new VideoDto(video.getName(),video.getVideo());
    }

    public List<Video> findVideos() {
        return videoRepository.findAll();
    }
}
