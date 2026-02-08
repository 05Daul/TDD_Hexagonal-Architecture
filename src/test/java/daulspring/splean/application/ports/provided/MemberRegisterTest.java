package daulspring.splean.application.ports.provided;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import daulspring.splean.application.MemberService;
import daulspring.splean.application.ports.required.EmailSender;
import daulspring.splean.application.ports.required.MemberRepository;
import daulspring.splean.domain.Email;
import daulspring.splean.domain.Member;
import daulspring.splean.domain.MemberFixture;
import daulspring.splean.domain.MemberStatus;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

class MemberRegisterTest {

  @Test
  void registerTestStub() {
    MemberRegister memberRegister = new MemberService(
        new MemberRepoStub(), new EmailSenderStub(), MemberFixture.createPasswordEncoder()
    );
    Member member = memberRegister.register(MemberFixture.creatMemberRegisterRequest());
    assertThat(member.getId()).isNotNull();
    assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.PENDING);
  }



  @Test
  void registerTestMockito() {
    EmailSenderMock emailSenderMock = Mockito.mock(EmailSenderMock.class);
    MemberRegister memberRegister = new MemberService(
        new MemberRepoStub(), emailSenderMock, MemberFixture.createPasswordEncoder()
    );
    Member member = memberRegister.register(MemberFixture.creatMemberRegisterRequest());

    assertThat(member.getId()).isNotNull();
    assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.PENDING);


    Mockito.verify(emailSenderMock).send(eq(member.getEmail()),any(),any());
  }


  static class MemberRepoStub implements MemberRepository {

    @Override
    public Member save(Member member) {
      ReflectionTestUtils.setField(member, "id", 1L);
      return member;
    }
  }

  static class EmailSenderStub implements EmailSender {

    @Override
    public void send(Email email, String subject, String body) {
    }
  }

  static class EmailSenderMock implements EmailSender {

    List<Email> tos = new ArrayList<>();

    public List<Email> getTos() {
      return tos;
    }

    @Override
    public void send(Email email, String subject, String body) {
      tos.add(email);
    }
  }
}