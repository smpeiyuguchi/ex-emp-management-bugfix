package jp.co.sample.emp_management;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 設定用のクラス
@EnableWebSecurity // SpringSecurityのWeb用の機能を利用する
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * アプリケーション全体のセキュリティフィルターの設定をします. staticディレクトリ下のファイルをセキュリティフィルターから無効にする
	 *
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/img/**", "/js/**", "/fonts/**");
	}

	/**
	 * アプリケーションの認証設定やログイン・ログアウトの設定をします.
	 *
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests() // 認証に関する設定
				.antMatchers("/", "/toInsert", "/insert").permitAll() // 指定したパスは全てのユーザーに許可
				.anyRequest().authenticated(); // 指定していないパスは認可が必要

		http.formLogin() // ログイン時の認可に関する設定
				.loginPage("/") // ログインページ用のURL
				.loginProcessingUrl("/login") // ログインボタンを押した時の送信先のパス
				.failureUrl("/?error=true") // ログイン失敗時のパス(エラー用のパラメータの受け渡し)
				.defaultSuccessUrl("/employee/showList", false) // ログイン成功時のパス（falseにすることで操作途中の再ログイン時に前回操作時のページを表示)
				.usernameParameter("mailAddress") // ログイン時のユーザ名のパラメータ（フォーム名と一致）
				.passwordParameter("password"); // ログイン時のパスワードのパラメータ

		http.logout() // ログアウトに関する設定
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout**")) // ログアウトする時のパス
				.logoutSuccessUrl("/") // ログアウトに成功した時の遷移先のパス
				.deleteCookies("JSESSIONID") // ログアウト後にCookieのセッションIDを削除?
				.invalidateHttpSession(true); // ログアウト後にセッションを無効にする
	}

	/**
	 * パスワードハッシュ化用のクラスをDIコンテナに注入します.
	 * 
	 * @return bcryptアルゴリズムでパスワード暗号化するためのオブジェクト
	 */
	@Bean
	public PasswordEncoder passwordEncorder() {
		return new BCryptPasswordEncoder();
	}

}
