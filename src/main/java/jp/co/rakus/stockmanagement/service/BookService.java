package jp.co.rakus.stockmanagement.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jp.co.rakus.stockmanagement.domain.Book;
import jp.co.rakus.stockmanagement.repository.BookRepository;

/**
 * 書籍関連サービスクラス.
 * @author igamasayuki
 *
 */
@Service
public class BookService {

	@Autowired
	BookRepository bookRepository;
	
	public List<Book> findAll(){
		return bookRepository.findAll();
	}
	
	public Book findOne(Integer id) {
		return bookRepository.findOne(id);
	}
	
//	public Book save(Book book){
//		return bookRepository.save(book);
//	}
	
	public Book update(Book book){
		return bookRepository.update(book);
	}
	
	public void insert(Book book) {
		book.setId(idMaxPlus1());
		bookRepository.insert(book);
	}
	
	public Integer idMaxPlus1() {
		return bookRepository.findMaxId() + 1;
	}
	
	public void uploadImage(MultipartFile image) {
		try {
			//windowsではpathはwindowsの\\(円マークで指定しないといけない)
			File uploadFile = new File("C:\\env\\springworkspace\\stock-management-bugfix-spring\\src\\main\\webapp\\img\\"+image.getOriginalFilename());
			byte[] bytes = image.getBytes();
			BufferedOutputStream uploadFileStream = new BufferedOutputStream(new FileOutputStream(uploadFile));
			uploadFileStream.write(bytes);
			uploadFileStream.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
//	public void delete(Integer id){
//		bookRepository.delete(id);
//	}
}
