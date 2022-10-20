package mmovie.mmovie.controller;

import mmovie.mmovie.domain.Member;
import mmovie.mmovie.dto.MemberDto;
import mmovie.mmovie.dto.MemberResponseDto;
import mmovie.mmovie.dto.Result;
import mmovie.mmovie.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
    * 회원을 추가하는 api
    * */
    @PostMapping("/api/v1/members")
    public MemberResponseDto createMembers(@RequestBody @Valid MemberDto memberDto){
        log.info(" =========== createMembers ===========");

        Long id = memberService.createMembers(memberDto);
        return new MemberResponseDto(id);
    }

    /**
     * 회원 하나 불러오는 api
     * */
    @GetMapping("/api/v1/members/{id}")
    public MemberDto getMembers(@PathVariable("id") Long id){
        log.info(" =========== getMembers One ===========");
        log.info("id : "+id);
        MemberDto member = memberService.getMembers(id);
        return member;
    }

    /**
    * 회원들 불러오는 api
    * */
    @GetMapping("/api/v1/members")
    public Result getMembers(){
        log.info(" =========== getMembers ===========");
        List<Member> getMembers = memberService.getMembers();
        List<MemberDto> collect = getMembers.stream().map(m -> new MemberDto(m.getEmail(), m.getPassword())).collect(Collectors.toList());

        return new Result(collect.size(), collect);
    }

    /**
     * 회원 삭제 api
     * */
    @DeleteMapping("/api/v1/members/{id}")
    public MemberResponseDto deleteMembers(@PathVariable("id") Long id){
        log.info(" =========== deleteMembers ===========");
        memberService.deleteMembers(id);

        return new MemberResponseDto(id);
    }


}
