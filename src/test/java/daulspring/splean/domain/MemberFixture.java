package daulspring.splean.domain;

public class MemberFixture {
  private static MemberRegisterRequest creatMemberRegisterRequest() {
    return new MemberRegisterRequest("elre@naver.com", "daul", "secret");
  }

  private static PasswordEncoder createPasswordEncoder() {
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

}
