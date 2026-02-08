package daulspring.splean.application.ports.required;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import daulspring.splean.domain.Member;
import daulspring.splean.domain.MemberFixture;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

@DataJpaTest
class MemberRepositoryTest {

  @Autowired
  MemberRepository memberRepository;

  @Autowired
  EntityManager entityManager;

  @Test
  void CreateMember() {
    Member member = Member.register(MemberFixture.creatMemberRegisterRequest(),
        MemberFixture.createPasswordEncoder());

    memberRepository.save(member);
    entityManager.flush();
  }

  @Test
  void duplicateMemberFail() {
    Member member = Member.register(MemberFixture.creatMemberRegisterRequest(), MemberFixture.createPasswordEncoder());
    memberRepository.save(member);

    Member member2 = Member.register(MemberFixture.creatMemberRegisterRequest(), MemberFixture.createPasswordEncoder());
    assertThatThrownBy(()->memberRepository.save(member2))
        .isInstanceOf(DataIntegrityViolationException.class);

  }
}