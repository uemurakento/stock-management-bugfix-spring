package jp.co.rakus.stockmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import jp.co.rakus.stockmanagement.domain.Member;
import jp.co.rakus.stockmanagement.repository.MemberRepository;

/**
 * メンバー関連サービスクラス.
 * @author igamasayuki
 *
 */
@Service
public class MemberService {

	@Autowired
	MemberRepository memberRepository;
		
    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    static PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }
	
//	public List<Member> findAll(){
//		return memberRepository.findAll();
//	}
//	
//	public Member findOne(Integer id) {
//		return memberRepository.findOne(id);
//	}
	
	public Member findOneByMailAddressAndPassword(String mailAddress, String password){
		password = encryption(password);
		return memberRepository.findByMailAddressAndPassword(mailAddress, password);
	}
	
	public Member findOneByMailAddress(String mailAddress){
		return memberRepository.findByMailAddress(mailAddress);
	}	
	
	public Member save(Member member){
		return memberRepository.save(member);
	}
	
	/**
	 * 登録情報を受け取りパスワードを暗号化する.
	 * 
	 * @param member 登録情報
	 * @return パスワードを暗号化した登録情報
	 */
	public Member encryptionPassword(Member member) {
		member.setPassword(encryption(member.getPassword()));
		return member;
	}
	
    /**
     * パスワードを暗号化するメソッド.
     * 
     * @param password 暗号化するパスワード
     * @return 暗号化されたハッシュ値を16進数変換した文字列
     */
    public String encryption(String password) {
    	return passwordEncoder.encode(password);
    	
    	//        byte[] cipher_byte;
//        try{
//                MessageDigest md = MessageDigest.getInstance("SHA-256");
//                md.update(password.getBytes());
//                cipher_byte = md.digest();
//                StringBuilder sb = new StringBuilder(2 * cipher_byte.length);
//                for(byte b: cipher_byte) {
//                        sb.append(String.format("%02x", b&0xff) );
//                }
//                return sb.toString();
//        } catch (Exception e) {
//        	return null;
//        }
    }
    
    public boolean passwordMatcher(String password,String hash) {
    	if(passwordEncoder.matches(password,hash)) {
    		return true;
    	}else {
    		return false;
    	}
    }

	
//	public Member update(Member member){
//		return memberRepository.save(member);
//	}
//	
//	public void delete(Integer id){
//		memberRepository.delete(id);
//	}
}
