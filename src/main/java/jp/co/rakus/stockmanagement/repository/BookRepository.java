package jp.co.rakus.stockmanagement.repository;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.rakus.stockmanagement.domain.Book;

/**
 * booksテーブル操作用のリポジトリクラス.
 * @author igamasayuki
 */
@Repository
public class BookRepository {
	public static final String TABLE_NAME = "books";
	/**
	 * ResultSetオブジェクトからBookオブジェクトに変換するためのクラス実装&インスタンス化
	 */	
	private static final RowMapper<Book> BOOK_ROW_MAPPER = (rs, i) -> {
		Integer id = rs.getInt("id");
		String name = rs.getString("name");
		String author = rs.getString("author");
		String publisher = rs.getString("publisher");
		Integer price = rs.getInt("price");
		String isbncode = rs.getString("isbncode");
		Date saledate = rs.getDate("saledate");
		String explanation = rs.getString("explanation");
		String image = rs.getString("image");
		Integer stock = rs.getInt("stock");
		return new Book(id, name, author, publisher, price, isbncode, saledate, explanation, image, stock);
	};
	private static final RowMapper<Integer> BOOK_MAX_ID_ROW_MAPPER = (rs,i) -> {
		Integer id = rs.getInt("max_id");
		return id;
	};

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public List<Book> findAll() {
		List<Book> books = jdbcTemplate.query(
		"SELECT id,name,author,publisher,price,isbncode,saledate,explanation,image,stock FROM "+TABLE_NAME+" ORDER BY saledate DESC", 
				BOOK_ROW_MAPPER);
		return books;
	}
	
	public Book findOne(Integer id) {
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("id",id);
		Book book = jdbcTemplate.queryForObject(
				"SELECT id,name,author,publisher,price,isbncode,saledate,explanation,image,stock FROM "+TABLE_NAME+" WHERE id=:id", 
				param, 
				BOOK_ROW_MAPPER);
		return book;
	}
	
	public Book update(Book book) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(book);
		if (book.getId() == null) {
			throw new NullPointerException();
		} 
		jdbcTemplate.update(
				"UPDATE "+TABLE_NAME+" SET stock=:stock WHERE id=:id",
				param);
		return book;
	}
	
	public void insert(Book book) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(book);
		jdbcTemplate.update(
				"INSERT INTO "+TABLE_NAME+" (id,name,author,publisher,price,isbncode,saledate,explanation,image) VALUES (:id,:name,:author,:publisher,:price,:isbncode,:saledate,:explanation,:image);",
				param);
	}
	
	public Integer findMaxId() {
		SqlParameterSource param = new MapSqlParameterSource();
		String sql = "SELECT MAX(id) max_id FROM "+TABLE_NAME+";";
		Integer max = jdbcTemplate.queryForObject(sql, param, BOOK_MAX_ID_ROW_MAPPER);
		return max;
	}
	
}
