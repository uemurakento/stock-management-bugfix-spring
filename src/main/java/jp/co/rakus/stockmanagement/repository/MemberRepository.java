package jp.co.rakus.stockmanagement.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.rakus.stockmanagement.domain.Member;

/**
 * membersテーブル操作用のリポジトリクラス.
 * 
 * @author igamasayuki
 */
@Repository
public class MemberRepository {

	/**
	 * ResultSetオブジェクトからMemberオブジェクトに変換するためのクラス実装&インスタンス化
	 */
	private static final RowMapper<Member> MEMBER_ROW_MAPPER = (rs, i) -> {
		Integer id = rs.getInt("id");
		String name = rs.getString("name");
		String mailAddress = rs.getString("mail_address");
		String password = rs.getString("password");
		return new Member(id, name, mailAddress, password);
	};

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * メールアドレスとパスワードからメンバーを取得.
	 * 
	 * @param mailAddress
	 *            メールアドレス
	 * @param password
	 *            パスワード
	 * @return メンバー情報.メンバーが存在しない場合はnull.
	 */
	public Member findByMailAddressAndPassword(String mailAddress, String password) {
		List<Member> members = new ArrayList<>();
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress).addValue("password",password);
		members = jdbcTemplate.query(
				"SELECT id,name,mail_address,password FROM members WHERE mail_address=:mailAddress and password=:password;",
				param, MEMBER_ROW_MAPPER);
		if(members.isEmpty()) {
			System.out.println("null");
			return null;
		}
		return members.get(0);
	}

	/**
	 * メールアドレスが登録済みか確認するためのfindメソッド.
	 * 
	 * @param mailAddress
	 *            メールアドレス
	 * @return メンバー情報.メンバーが存在しない場合はnull.
	 */
	public Member findByMailAddress(String mailAddress) {
		List<Member> members = new ArrayList<>();
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress);
		members = jdbcTemplate.query(
				"SELECT id,name,mail_address,password FROM members WHERE mail_address=:mailAddress;", param,
				MEMBER_ROW_MAPPER);
		if(members.isEmpty()) {
			return null;
		}
		return members.get(0);
	}

	/**
	 * メンバー情報を保存 または 更新する.
	 * 
	 * @param member
	 *            保存または更新するメンバー情報
	 * @return 保存または更新されたメンバー情報
	 */
	public Member save(Member member) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(member);
		if (member.getId() == null) {
			jdbcTemplate.update("INSERT INTO members(name,mail_address,password) values(:name,:mailAddress,:password)",
					param);
		} else {
			jdbcTemplate.update(
					"UPDATE members SET name=:name,mail_address=:mailAddress,password=:password WHERE id=:id", param);
		}
		return member;
	}

}
