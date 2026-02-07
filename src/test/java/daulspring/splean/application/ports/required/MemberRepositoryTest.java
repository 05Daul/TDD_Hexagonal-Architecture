package daulspring.splean.application.ports.required;

import static org.junit.jupiter.api.Assertions.*;

import daulspring.splean.domain.Member;
import daulspring.splean.domain.MemberRegisterRequest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {
  @Autowired
  MemberRepository memberRepository;

  @Autowired
  EntityManager entityManager;

  @Test
  void CreateMember() {
    Member member = new Member(new MemberRegisterRequest());

  }


}