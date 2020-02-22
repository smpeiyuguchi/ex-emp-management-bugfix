package jp.co.sample.emp_management.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * 管理者のログイン情報を格納するエンティティ. Springのユーザークラスを継承
 * 
 * @author shumpei
 *
 */
public class LoginAdministrator extends User {

	private static final long serialVersionUID = 1L;
	private final Administrator administrator;

	public LoginAdministrator(Administrator administrator, Collection<GrantedAuthority> authorityList) {
		super(administrator.getMailAddress(), administrator.getPassword(), authorityList); // UserクラスのコンストラクタによりUserを作成
		this.administrator = administrator; //管理者情報へ格納
	}

	public Administrator getAdministrator() {
		return administrator;
	}

}
