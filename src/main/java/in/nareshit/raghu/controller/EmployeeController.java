package in.nareshit.raghu.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.apiclub.captcha.Captcha;
import in.nareshit.raghu.captcha.CaptchaUtils;
import in.nareshit.raghu.modal.Employee;
import in.nareshit.raghu.service.IEmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private IEmployeeService service;

	private void setupCaptcha(Employee e) {
		Captcha captcha = CaptchaUtils.createCaptcha(200, 50);
		e.setHidden(captcha.getAnswer());
		e.setCaptcha("");
		e.setImage(CaptchaUtils.encodeBase64(captcha));
	}

	@GetMapping("/register")
	public String showRegister(Model model) {
		Employee e  = new Employee();
		setupCaptcha(e);
		model.addAttribute("employee", e);

		return "EmployeeRegister";
	}

	@PostMapping("/save")
	public String saveEmployee(
			@ModelAttribute("employee") Employee employee,
			Model model) 
	{
		String page="";
		if(employee.getCaptcha().equals(employee.getHidden()))
		{
			service.createEmployee(employee);
			page ="redirect:all";
		} else {
			setupCaptcha(employee);
			return "EmployeeRegister";
		}
		return page;
	}

	@GetMapping("/all")
	public String getAllEmployees(Model model) 
	{
		model.addAttribute("list", service.getAllEmployees());
		return "EmployeeData";
	}


	@GetMapping("/edit/{id}")
	public String editEmployees(@PathVariable Integer id,Model model) 
	{
		String page = null;
		Optional<Employee> opt = service.getOneEmployee(id);
		if(opt.isPresent()) {
			Employee e = opt.get(); 
			setupCaptcha(e);
			model.addAttribute("employee", e);
			page = "EmployeeRegister";
		}else {
			page ="redirect:all";
		}

		return page;
	}
}
