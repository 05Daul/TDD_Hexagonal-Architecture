package daulspring.splean.domain;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class MemberTest {

  @Test
  void createMember() {
    var member = new Member("elre519@naver.com", "daul", "secret");
    // 실제 값이 주어진 조건(Matcher)과 일치하는가?
    assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.PENDING);
  }

  @Test
  void constructNullCheck() {
    assertThatThrownBy(() -> new Member(null, "daul", "secret"))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void activate() {
    var member = new Member("elre519@naver.com", "daul", "secret");
    member.activate();
    assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.ACTIVE);
  }

  @Test
  void activateFail() {
    var member = new Member("elre519@naver.com", "daul", "secret");
    member.activate();

    assertThatThrownBy(() -> {
      member.activate();
    }).isInstanceOf(IllegalStateException.class);
  }

  @Test
  void deactivateFail() {
    var member = new Member("elre519@naver.com", "daul", "secret");
    assertThatThrownBy(() -> member.deactivate()).isInstanceOf(IllegalStateException.class);

    member.activate();
    member.deactivate();

    assertThatThrownBy(() -> member.deactivate()).isInstanceOf(IllegalStateException.class);
  }

  @Test
  void deactivate() {
    var member = new Member("elre519@naver.com", "daul", "secret");

    member.activate();

    member.deactivate();
    assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.DEACTIVATED);
  }
}