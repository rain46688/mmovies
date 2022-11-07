package mmovie.mmovie.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mmovie.mmovie.common.JwtTokenProvider;
import mmovie.mmovie.domain.Member;
import mmovie.mmovie.dto.MemberDto;
import mmovie.mmovie.dto.TokenInfo;
import mmovie.mmovie.repository.MemberRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    /**
     * 회원을 추가하는 api
     * */
    @Transactional
    public Long createMembers(MemberDto memberDto) throws Exception{

        List<String> roles = new ArrayList<String>();
        roles.add(0, "ADMIN");

        Member member = Member.builder()
                .email(memberDto.getEmail())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .roles(roles)
                .build();

        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 유효성 검사 로직
     * */
    private void validateDuplicateMember(Member member) throws Exception{
        List<Member> getMembers = memberRepository.findByEmail(member.getEmail());
        log.info(" test : "+getMembers);
        if(getMembers.size() > 0){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 하나 불러오는 api
     * */
    public MemberDto getMembers(Long id) throws Exception{
        try {
            Member member = memberRepository.getReferenceById(id);
            return new MemberDto(member.getEmail(),member.getPassword());
        }catch (Exception e){
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
    }

    /**
     * 회원들 불러오는 api
     * */
    public List<Member> getMembers() throws Exception{
        return memberRepository.findAll();
    }

    /**
     * 회원 삭제 api
     * */
    @Transactional
    public void deleteMembers(Long id) throws Exception{
        try {
            memberRepository.deleteById(id);
        }catch (Exception e){
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }

    }

    @Transactional
    public TokenInfo login(String memberId, String password) throws Exception{
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        return tokenInfo;
    }
}
