package daulspring.splean.domain;


import java.util.Objects;
import org.springframework.util.Assert;

public class Member {

  private String email;
  private String nickname;
  private String passwordHash;
  private MemberStatus memberStatus;

  public Member(String email, String nickname, String passwordHash) {
    this.email = Objects.requireNonNull(email);
    this.nickname = Objects.requireNonNull(nickname);
    this.passwordHash = passwordHash;
    this.memberStatus = MemberStatus.PENDING;
  }

  public String getEmail() {
    return email;
  }

  public MemberStatus getMemberStatus() {
    return memberStatus;
  }

  public String getNickname() {
    return nickname;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void activate() {
    // if (memberStatus != MemberStatus.PENDING) throw  new IllegalStateException("Pending 상태가 아닙니다.");
    Assert.state(memberStatus == MemberStatus.PENDING,"Pending 상태가 아닙니다.");

    this.memberStatus = MemberStatus.ACTIVE;
  }

  public void deactivate() {
    Assert.state(memberStatus == MemberStatus.ACTIVE,"Activate 상태가 아닙니다.");

    this.memberStatus = MemberStatus.DEACTIVATED;
  }
}
