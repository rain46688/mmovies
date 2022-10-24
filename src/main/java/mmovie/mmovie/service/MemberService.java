package mmovie.mmovie.service;

import mmovie.mmovie.domain.Member;
import mmovie.mmovie.dto.MemberDto;
import mmovie.mmovie.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;

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

}
