package daulspring.splean.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.springframework.util.Assert;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NaturalIdCache
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  @NaturalId
  private Email email;

  private String nickname;

  private String passwordHash;

  @Enumerated(EnumType.STRING)
  private MemberStatus memberStatus;

  public static Member register(MemberRegisterRequest createRequest,
      PasswordEncoder passwordEncoder) {
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
