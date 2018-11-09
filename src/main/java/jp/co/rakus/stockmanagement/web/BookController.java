package jp.co.rakus.stockmanagement.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.rakus.stockmanagement.domain.Book;
import jp.co.rakus.stockmanagement.service.BookService;

/**
 * 書籍関連処理を行うコントローラー.
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/book")
@Transactional
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	/**
	 * フォームを初期化します.
	 * @return フォーム
	 */
	@ModelAttribute
	public BookForm setUpForm() {
		return new BookForm();
	}
	
	/**
	 * 書籍情報登録フォームの初期化.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public BookRegistrationForm setUpRegistrationForm() {
		return new BookRegistrationForm();
	}
	
	/**
	 * 書籍リスト情報を取得し書籍リスト画面を表示します.
	 * @param model モデル
	 * @return 書籍リスト表示画面
	 */
	@RequestMapping(value = "list")
	public String list(Model model) {
		List<Book> bookList = bookService.findAll();
		model.addAttribute("bookList", bookList);
		return "book/list";
	}
	
	/**
	 * 書籍詳細情報を取得し書籍詳細画面を表示します.
	 * @param id 書籍ID
	 * @param model　モデル
	 * @return　書籍詳細画面
	 */
	@RequestMapping(value = "show/{bookId}")
	public String show(@PathVariable("bookId") Integer id, Model model) {
		Book book = bookService.findOne(id);
		model.addAttribute("book", book);
		return "book/show";
	}
	
	/**
	 * 書籍更新を行います.
	 * @param form フォーム
	 * @param result リザルト情報
	 * @param model　モデル
	 * @return　書籍リスト画面
	 */
	@RequestMapping(value = "update")
	public String update(@Validated BookForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return show(form.getId(), model);
		}
		Book book = bookService.findOne(form.getId());
		book.setStock(form.getStock());
		bookService.update(book);
		return list(model);
	}
	
	@RequestMapping(value = "registrationView")
	public String registrationView() {
		return "book/bookForm";
	}
	
	@RequestMapping(value = "registration")
	public String registration(@Validated BookRegistrationForm form,BindingResult result,Model model) throws ParseException {
		if(form.getImage().isEmpty()) {
			result.rejectValue("image", null, "画像を選択してください");
		}
		if (form.getImage().getContentType().indexOf("image") == -1) {
			System.out.println(form.getImage().getContentType().indexOf("image"));
			result.rejectValue("image", null, "画像形式のファイルを選択してください");
		}
		if(result.hasErrors()) {
			return registrationView();
		}
		//画像保存処理
		//TODO:同じ名前の画像ファイルが入力されたときに上書きされてしまうので直す必要がある
		bookService.uploadImage(form.getImage());
		
		//書籍登録処理
		Book book = new Book();
		BeanUtils.copyProperties(form, book);
		book.setPrice(Integer.valueOf(form.getPrice()));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date saleDate = sdf.parse(form.getSaledate());
		book.setSaledate(saleDate);
		book.setImage(form.getImage().getOriginalFilename());
		book.setStock(0);
		
		bookService.insert(book);
		
		return list(model);
	}

}
