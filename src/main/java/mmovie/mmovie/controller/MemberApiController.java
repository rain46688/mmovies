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
    @PostMapping("/api/v1/setMember")
    public MemberResponseDto create(@RequestBody @Valid MemberDto memberDto){
        log.info(" =========== create ===========");

        Long id = memberService.create(memberDto);
        return new MemberResponseDto(id);
    }

    /**
     * 회원 하나 불러오는 api
     * */
    @GetMapping("/api/v1/getMember/{id}")
    public MemberDto memberV1(@PathVariable("id") Long id){
        log.info(" =========== memberV1 ===========");
        log.info("id : "+id);
        MemberDto member = memberService.findMember(id);
        return member;
    }

    /**
    * 회원들 불러오는 api
    * */
    @GetMapping("/api/v1/getMembers")
    public Result membersV1(){
        log.info(" =========== membersV1 ===========");
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream().map(m -> new MemberDto(m.getEmail(), m.getPassword())).collect(Collectors.toList());

        return new Result(collect.size(), collect);
    }


}
