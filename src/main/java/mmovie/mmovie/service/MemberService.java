package mmovie.mmovie.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mmovie.mmovie.domain.Member;
import mmovie.mmovie.dto.MemberDto;
import mmovie.mmovie.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원을 추가하는 api
     * */
    @Transactional
    public Long createMembers(MemberDto memberDto) throws Exception{

        Member member = Member.builder()
                .email(memberDto.getEmail())
                .password(memberDto.getPassword())
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
    public UserDetails loadUserByUsername(final String email) {
        return memberRepository.findOneWithAuthoritiesByEmail(email)
                .map(user -> createUser(email, user))
                .orElseThrow(() -> new UsernameNotFoundException(email + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createUser(String username, Member member) {

        List<GrantedAuthority> grantedAuthorities = member.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(member.getEmail(),
                member.getPassword(),
                grantedAuthorities);
    }




}
