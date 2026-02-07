package daulspring.splean.application.ports.required;

import daulspring.splean.domain.Member;
import org.springframework.data.repository.Repository;


public interface MemberRepository extends Repository<Member, Long> {

  Member save(Member member);

}
