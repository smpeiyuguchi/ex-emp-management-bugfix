package jp.co.sample.emp_management.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.service.EmployeeService;

/**
 * 従業員検索時にオートコンプリートを表示します.
 * 
 * @author shumpei
 *
 */
@RestController
@RequestMapping("/search_employee_api")
public class SearchEmployeeApiController {

	@Autowired
	private EmployeeService employeeService;

	/**
	 * 従業員一覧を配列に格納して返します.
	 * 
	 * @param 従業員名（の一部）
	 * @return 従業員一覧
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Map<String, List<String>> search(String name) {
		Map<String, List<String>> map = new HashMap<>();
		List<String> employeeNameList = new ArrayList<String>();
		List<Employee> employeeList = employeeService.searchByName(name);
		for (Employee employee : employeeList) {
			employeeNameList.add(employee.getName());
		}
		map.put("employeeNameList", employeeNameList);
		return map;
	}

}
