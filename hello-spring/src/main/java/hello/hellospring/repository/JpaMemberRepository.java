package hello.hellospring.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import hello.hellospring.domain.Member;

public class JpaMemberRepository implements MemberRepository {
	
	private final EntityManager em;
	
	public JpaMemberRepository(EntityManager em) {
		this.em = em;
	}

	@Override
	public Member save(Member member) throws SQLException {
		em.persist(member);
		return member;
	}

	@Override
	public Optional<Member> findById(Long id) throws SQLException {
		Member member = em.find(Member.class, id);
		return Optional.of(member);
	}

	@Override
	public Optional<Member> findByName(String name) throws SQLException {
		List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class).setParameter("name", name).getResultList();
		return result.stream().findAny();
	}

	@Override
	public List<Member> findAll() throws SQLException {
		return em.createQuery("select m from Member m", Member.class).getResultList();
	}

}
