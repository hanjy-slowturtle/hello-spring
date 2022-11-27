package hello.hellospring.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import hello.hellospring.domain.Member;

public class JdbcMemberRepository implements MemberRepository {
	
	private final DataSource dataSource;
	
	public JdbcMemberRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public Member save(Member member) throws SQLException {
		String sql = "insert into member(name) values(?)";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, member.getName());
			
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			
			if (rs.next()) {
				member.setId(rs.getLong(1));
			} else {
				throw new SQLException("id 조회 실패");
			}
			return member;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				throw e;
			}
		}
	}

	@Override
	public Optional<Member> findById(Long id) throws SQLException {
		String sql = "select id, name from member where id = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, id);
			
			rs = pstmt.executeQuery();
			
			Member member = new Member();
			if (rs.next()) {
				member.setId(rs.getLong(1));
				member.setName(rs.getString(2));
			}
			return Optional.of(member);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				throw e;
			}
		}
	}

	@Override
	public Optional<Member> findByName(String name) throws SQLException {
		String sql = "select id, name from member where name = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, name);
			
			rs = pstmt.executeQuery();
			
			Member member = new Member();
			if (rs.next()) {
				member.setId(rs.getLong(1));
				member.setName(rs.getString(2));
				return Optional.of(member);
			} else {
				return Optional.empty();
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				throw e;
			}
		}
	}

	@Override
	public List<Member> findAll() throws SQLException {
		String sql = "select id, name from member";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			List<Member> list = new ArrayList<>();
			if (rs.next()) {
				Member member = new Member();
				member.setId(rs.getLong(1));
				member.setName(rs.getString(2));
				list.add(member);
			}
			return list;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				throw e;
			}
		}
	}
	
}
