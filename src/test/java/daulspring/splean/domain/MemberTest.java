package daulspring.splean.domain;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberTest {

  Member member;
  PasswordEncoder passwordEncoder;

  @BeforeEach
  void setUp() {
    this.passwordEncoder = createPasswordEncoder();
    member = Member.register(creatMemberRegisterRequest(),
        passwordEncoder);

  }

  public static MemberRegisterRequest creatMemberRegisterRequest() {
    return new MemberRegisterRequest("elre@naver.com", "daul", "secret");
  }

  public static PasswordEncoder createPasswordEncoder() {
    return new PasswordEncoder() {
      @Override
      public String encode(String rawPassword) {
        return rawPassword.toUpperCase();
      }

      @Override
      public boolean matches(String rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
      }
    };
  }


  @Test
  void registerMember() {
    // 실제 값이 주어진 조건(Matcher)과 일치하는가?
    assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.PENDING);
  }


  @Test
  void activate() {
    member.activate();
    assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.ACTIVE);
  }

  @Test
  void activateFail() {
    member.activate();

    assertThatThrownBy(() -> {
      member.activate();
    }).isInstanceOf(IllegalStateException.class);
  }

  @Test
  void deactivateFail() {
    assertThatThrownBy(() -> member.deactivate()).isInstanceOf(IllegalStateException.class);

    member.activate();
    member.deactivate();

    assertThatThrownBy(() -> member.deactivate()).isInstanceOf(IllegalStateException.class);
  }

  @Test
  void deactivate() {
    member.activate();

    member.deactivate();
    assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.DEACTIVATED);
  }

  @Test
  void verifyPassword() {
    assertThat(member.verifyPassword("secret", passwordEncoder)).isTrue();
  }

  @Test
  void changePassword() {
    member.changePassword("verysecret", passwordEncoder);
    assertThat(member.verifyPassword("verysecret", passwordEncoder)).isTrue();
  }

  @Test
  void shouldBeActive(){
    assertThat(member.isActive()).isFalse();
    member.activate();
    assertThat(member.isActive()).isTrue();
    member.deactivate();
    assertThat(member.isActive()).isFalse();

  }

  @Test
  void invaildEmail() {
    assertThatThrownBy(()->
        Member.register(creatMemberRegisterRequest("invaild email"),passwordEncoder)
    ).isInstanceOf(IllegalArgumentException.class);
    Member.register(creatMemberRegisterRequest(),passwordEncoder);
  }

  private MemberRegisterRequest creatMemberRegisterRequest(String invaildEmail) {
    return null;
  }

}