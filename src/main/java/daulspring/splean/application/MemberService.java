package daulspring.splean.application;

import daulspring.splean.application.ports.provided.MemberRegister;
import daulspring.splean.application.ports.required.EmailSender;
import daulspring.splean.application.ports.required.MemberRepository;
import daulspring.splean.domain.Member;
import daulspring.splean.domain.MemberRegisterRequest;
import daulspring.splean.domain.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberRegister {

  private final MemberRepository memberRepository;
  private final EmailSender emailSender;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Member register(MemberRegisterRequest registerRequest) {
    // 1. check

    // 2. domain model
    Member member = Member.register(registerRequest,passwordEncoder);

    // 3. repository
    memberRepository.save(member);

    // 4. post process
    emailSender.send(member.getEmail(),"등록을 완료해주세요","아래 링크를 눌러서 등록을 완료해 주세요.");

    return member;
  }
}
