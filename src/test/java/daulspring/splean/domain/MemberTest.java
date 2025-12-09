package daulspring.splean.domain;


import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberTest {

  Member member;
  PasswordEncoder passwordEncoder;

  @BeforeEach
  void setUp() {
    this.passwordEncoder = new PasswordEncoder() {
      @Override
      public String encode(String rawPassword) {
        return rawPassword.toUpperCase();
      }

      @Override
      public boolean matches(String rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
      }
    };
    member = Member.create(new MemberCreateRequest("elre@naver.com", "daul", "secret"),
        passwordEncoder);

  }


  @Test
  void createMember() {
    // 실제 값이 주어진 조건(Matcher)과 일치하는가?
    assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.PENDING);
  }

  @Test
  void constructNullCheck() {
    assertThatThrownBy(() -> member.create(new MemberCreateRequest("elre@naver.com", "daul", "secret"),
        passwordEncoder)).isInstanceOf(NullPointerException.class);
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
  void invaildEmail() {
    assertThatThrownBy(()->
        Member.create(new MemberCreateRequest("invaild","dau;","secret"),passwordEncoder)
    ).isInstanceOf(IllegalArgumentException.class);

    Member.create(new MemberCreateRequest("elre519@naver.com","dau;","secret"),passwordEncoder);

  }

}