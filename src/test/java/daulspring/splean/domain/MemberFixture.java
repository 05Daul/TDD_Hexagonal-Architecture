package daulspring.splean.domain;

public class MemberFixture {
  public static MemberRegisterRequest creatMemberRegisterRequest() {
    return new MemberRegisterRequest("elre@naver.com", "daul", "secret");
  }

  public static MemberRegisterRequest creatMemberRegisterRequest(String invaildEmail) {
    return new MemberRegisterRequest(invaildEmail, "daul", "secret");
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

}
