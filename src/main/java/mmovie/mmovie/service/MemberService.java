package mmovie.mmovie.service;

import mmovie.mmovie.domain.Member;
import mmovie.mmovie.dto.MemberDto;
import mmovie.mmovie.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
    * 회원 등록
    * */
    @Transactional
    public Long create(MemberDto memberDto) {

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
    private void validateDuplicateMember(Member member){
        List<Member> findMembers = memberRepository.findByEmail(member.getEmail());
        log.info(" =========== findMembers : "+findMembers.size());
        if(findMembers.size() > 0){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
    * 한명의 유저 조회
    * */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public MemberDto findMember(Long id) {
        Member member = memberRepository.getReferenceById(id);
        return new MemberDto(member.getEmail(),member.getPassword());
    }
}
