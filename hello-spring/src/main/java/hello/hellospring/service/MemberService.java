package hello.hellospring.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

public class MemberService {

	private final MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	/**
	 * 회원 가입
	 * @throws SQLException 
	 */
	public Long join(Member member) throws SQLException {
		// 같은 이름이 있는 중복 회원 x
		validateDuplicateMember(member);
		
		memberRepository.save(member);
		return member.getId();
	}
	
	private void validateDuplicateMember(Member member) throws SQLException {
		memberRepository.findByName(member.getName())
			.ifPresent(m -> {
				throw new IllegalStateException("이미 존재하는 회원입니다.");
			});
	}
	
	/**
	 * 전체 회원 조회
	 * @return
	 * @throws SQLException 
	 */
	public List<Member> findMembers() throws SQLException {
		return memberRepository.findAll();
	}
	
	/**
	 * 회원 조회
	 * @throws SQLException 
	 */
	public Optional<Member> findOne(Long memberId) throws SQLException {
		return memberRepository.findById(memberId);
	}
}
