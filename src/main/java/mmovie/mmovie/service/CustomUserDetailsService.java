package mmovie.mmovie.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mmovie.mmovie.domain.Member;
import mmovie.mmovie.repository.MemberRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("=== loadUserByUsername");
        List<Member> members = memberRepository.findByEmail(email);

        log.info(" 비번 : "+members.get(0).getPassword());

        Member member = new Member().builder()
                .email(members.get(0).getEmail())
                .password(members.get(0).getPassword())
                .roles(members.get(0).getRoles())
                .build();

        return createUserDetails(member);
    }

    private UserDetails createUserDetails(Member member) {
        log.info("=== createUserDetails");
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRoles().toArray(new String[0]))
                .build();
    }
}