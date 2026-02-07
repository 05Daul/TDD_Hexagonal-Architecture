package daulspring.splean.application.ports.provided;


import daulspring.splean.domain.Member;
import daulspring.splean.domain.MemberRegisterRequest;

/*
 * 회원의 등록과 관련된 기능을 제공
 * */
public interface MemberRegister {

  Member register(MemberRegisterRequest registerRequest);


}
