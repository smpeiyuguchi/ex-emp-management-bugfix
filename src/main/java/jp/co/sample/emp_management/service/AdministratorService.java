package jp.co.sample.emp_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.emp_management.domain.Administrator;
import jp.co.sample.emp_management.repository.AdministratorRepository;

/**
 * 管理者情報を操作するサービス.
 * 
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository administratorRepository;

	// PasswordEncorderを利用するためにDIコンテナから注入
	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * 管理者情報を登録します.
	 * 
	 * @param administrator 管理者情報
	 */
	public void insert(Administrator administrator) {
		// パスワードをハッシュ化してドメインへ再保存
		administrator.setPassword(passwordEncoder.encode(administrator.getPassword()));
		administratorRepository.insert(administrator);
	}

	/**
	 * ログインをします.
	 * (Spring Security)が実装するためコメントアウト
	 * 
	 * @param mailAddress メールアドレス
	 * @param password    パスワード
	 * @return 管理者情報 存在しない場合はnullが返ります
	 */
//	public Administrator login(String mailAddress, String passward) {
//		// メールアドレスで管理者情報を検索する.
//		Administrator administrator = administratorRepository.findByMailAddress(mailAddress);
//		// パスワード一致チェック
//		if (passwordEncoder.matches(passward, administrator.getPassword())) {
//			return administrator;
//		}
//		return null;
//	}

	/**
	 * メールアドレスから管理者情報を取得します.
	 * 
	 * @param mailAddress
	 * @return 管理者情報 存在しない場合はnullが返ります
	 */
	public Administrator findByMailAddress(String mailAddress) {
		return administratorRepository.findByMailAddress(mailAddress);
	}

}
