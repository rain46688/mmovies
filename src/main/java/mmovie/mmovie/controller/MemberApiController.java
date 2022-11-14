package mmovie.mmovie.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mmovie.mmovie.domain.Member;
import mmovie.mmovie.dto.IdResponseDto;
import mmovie.mmovie.dto.MemberDto;
import mmovie.mmovie.dto.Result;
import mmovie.mmovie.dto.TokenInfo;
import mmovie.mmovie.service.MemberService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
    public IdResponseDto createMembers(@RequestBody @Valid MemberDto memberDto) throws Exception{
        log.info(" =========== createMembers ===========");

        Long id = memberService.createMembers(memberDto);
        return new IdResponseDto(id);
    }

    /**
     * 회원 하나 불러오는 api
     * */
    @GetMapping("/api/v1/members/{id}")
    public MemberDto getMembers(@PathVariable("id") Long id) throws Exception{
        log.info(" =========== getMembers ===========");
        MemberDto member = memberService.getMembers(id);
        return member;
    }

    /**
    * 회원들 불러오는 api
    * */
    @GetMapping("/api/v1/members")
    public Result getMembers() throws Exception{
        log.info(" =========== getMembers All ===========");
        List<Member> getMembers = memberService.getMembers();
        List<MemberDto> collect = getMembers.stream().map(m -> new MemberDto(m.getEmail(), m.getPassword())).collect(Collectors.toList());

        return new Result(collect.size(), collect);
    }

    /**
     * 회원 삭제 api
     * */
    @DeleteMapping("/api/v1/members/{id}")
    public IdResponseDto deleteMembers(@PathVariable("id") Long id) throws Exception{
        log.info(" =========== deleteMembers ===========");
        memberService.deleteMembers(id);

        return new IdResponseDto(id);
    }

    /**
     * 로그인 로직
     * */
    @PostMapping("/login")
    public TokenInfo login(@RequestBody MemberDto memberDto) throws Exception{
        log.info(" =========== login ===========");
        String memberId = memberDto.getEmail();
        String password = memberDto.getPassword();

        log.info("memberId : "+memberId);
        log.info("password : "+password);

        TokenInfo tokenInfo = memberService.login(memberId, password);

        return tokenInfo;
    }

}
