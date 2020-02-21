package jp.co.sample.emp_management.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

//import javax.servlet.ServletContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.form.InsertEmployeeForm;
import jp.co.sample.emp_management.form.UpdateEmployeeForm;
import jp.co.sample.emp_management.service.EmployeeService;

/**
 * 従業員情報を操作するコントローラー.
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

//	@Autowired
//	private ServletContext application;

	@Autowired
	private EmployeeService employeeService;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public UpdateEmployeeForm setUpForm() {
		return new UpdateEmployeeForm();
	}

	@ModelAttribute
	public InsertEmployeeForm setUpInsertEmployeeForm() {
		return new InsertEmployeeForm();
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員一覧を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員一覧画面を出力します.
	 * 
	 * @param model モデル
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		List<Employee> employeeList = employeeService.showList();
		model.addAttribute("employeeList", employeeList);
		return "employee/list";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細画面を出力します.
	 * 
	 * @param id    リクエストパラメータで送られてくる従業員ID
	 * @param model モデル
	 * @return 従業員詳細画面
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id, Model model) {
		Employee employee = employeeService.showDetail(Integer.parseInt(id));
		model.addAttribute("employee", employee);
		return "employee/detail";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を更新する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細(ここでは扶養人数のみ)を更新します.
	 * 
	 * @param form 従業員情報用フォーム
	 * @return 従業員一覧画面へリダクレクト
	 */
	@RequestMapping("/update")
	public String update(@Validated UpdateEmployeeForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return showDetail(form.getId(), model);
		}
		Employee employee = new Employee();
		employee.setId(form.getIntId());
		employee.setDependentsCount(form.getIntDependentsCount());
		employeeService.update(employee);
		return "redirect:/employee/showList";
	}

	/**
	 * 名前から曖昧検索した従業員一覧画面を出力します. 空文字・検索履歴なしの場合は全件を出力します
	 * 
	 * @param name  リクエストパラメータで送られてくる従業員名（の一部）
	 * @param model モデル
	 * @return 従業員一覧画面
	 * 
	 */
	@RequestMapping("/search-by-name")
	public String searchByName(Model model, String employeeName) {
		List<Employee> employeeList = employeeService.searchByName(employeeName);
		if (employeeList.size() == 0) {
			model.addAttribute("searchErrorMessage", "1件もありませんでした");
			return showList(model);
		}
		model.addAttribute("employeeList", employeeList);
		return "employee/list";
	}

	/**
	 * 従業員登録画面を出力します.
	 * 
	 * @return 従業員登録画面
	 */
	@RequestMapping("/to-register")
	public String toRegister(Model model) {
		return "employee/register";
	}

	/**
	 * 従業員情報を登録します.
	 * 
	 * @param model リクエストスコープ
	 * @param form  従業員情報登録用フォーム
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/register")
	public String register(Model model, InsertEmployeeForm form) {
		Employee employee = new Employee();
		BeanUtils.copyProperties(form, employee);
		employee.setHireDate(Date.valueOf(form.getHireDate()));
		File file = new File ("/Users/shumpei/Documents/workspace-spring-tool-suite-4-4.5.0.RELEASE/"
				+ "ex-emp-management-bugfix/src/main/resources/static/img/" + form.getImage().getOriginalFilename());
//		String path = (application.getRealPath("/") + "img/" + form.getImage().getOriginalFilename());
//		Path path = Paths.get(System.getProperty("java.io.tmpdir"), form.getImage().getOriginalFilename());
//		System.out.println("path=" + System.getProperty("java.io.tmpdir"));
//		System.out.println(path);
		try {
//			form.getImage().transferTo(path.toFile());
			form.getImage().transferTo(file);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		;

		employee.setImage(form.getImage().getOriginalFilename());
		employeeService.register(employee);
		return showList(model);
	}

//	@RequestMapping("/test")
//	public String test(Model model) {
//		String path = application.getServletcontext;
//		System.out.println("path" + path);
//		return "employee/register";
//	}

}
