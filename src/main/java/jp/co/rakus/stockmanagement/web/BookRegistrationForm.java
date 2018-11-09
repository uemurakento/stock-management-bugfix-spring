package jp.co.rakus.stockmanagement.web;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

/**
 * 書籍登録のフォーム.
 * 
 * @author kento.uemura
 *
 */
public class BookRegistrationForm {
	/** 書籍名 */
	@NotBlank(message="書籍名を入力してください")
	private String name;
	/** 著者 */
	@NotBlank(message="著者を入力してください")
	private String author;
	/** 出版社 */
	@NotBlank(message="出版社を入力してください")
	private String publisher;
	/** 価格 */
	@NotBlank(message="価格を入力してください")
	private String price;
	/** ISBNコード */
	@NotBlank(message="ISBNコードを入力してください")
	private String isbncode;
	/** 発売日 */
	@NotBlank(message="発売日を入力してください")
	private String saledate;
	/** 説明 */
	@NotBlank(message="説明を入力してください")
	private String explanation;
	/** 画像 */
	private MultipartFile image;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getIsbncode() {
		return isbncode;
	}
	public void setIsbncode(String isbncode) {
		this.isbncode = isbncode;
	}
	public String getSaledate() {
		return saledate;
	}
	public void setSaledate(String saledate) {
		this.saledate = saledate;
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}
}
