package daulspring.splean.domain;


import jakarta.persistence.OneToMany;
import java.util.Objects;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

@Getter
@ToString
public class Member {

  private Email email;
  private String nickname;
  private String passwordHash;
  private MemberStatus memberStatus;

  public Member() {
  }

 /* private Member(String email, String nickname, String passwordHash) {
    this.email = Objects.requireNonNull(email);
    this.nickname = Objects.requireNonNull(nickname);
    this.passwordHash = passwordHash;
    this.memberStatus = MemberStatus.PENDING;
  }*/

  public static Member create(MemberCreateRequest createRequest, PasswordEncoder passwordEncoder) {
    Member member = new Member();

    member.email = new Email(createRequest.email());
    member.nickname = Objects.requireNonNull(createRequest.nickname());
    member.passwordHash = Objects.requireNonNull(passwordEncoder.encode(createRequest.password()));
    member.memberStatus = MemberStatus.PENDING;
    return member;
  }

  public void activate() {
    // if (memberStatus != MemberStatus.PENDING) throw  new IllegalStateException("Pending 상태가 아닙니다.");
    Assert.state(memberStatus == MemberStatus.PENDING, "Pending 상태가 아닙니다.");

    this.memberStatus = MemberStatus.ACTIVE;
  }

  public void deactivate() {
    Assert.state(memberStatus == MemberStatus.ACTIVE, "Activate 상태가 아닙니다.");

    this.memberStatus = MemberStatus.DEACTIVATED;
  }

  public void changePassword(String password, PasswordEncoder passwordEncoder) {
    this.passwordHash = passwordEncoder.encode(Objects.requireNonNull(password));
  }

  public void changeNickname(String nickname) {
    this.nickname = Objects.requireNonNull(nickname);
  }

  public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
    return passwordEncoder.matches(password, this.passwordHash);
  }

  public boolean isActive() {
    return this.memberStatus == MemberStatus.ACTIVE;
  }
}
