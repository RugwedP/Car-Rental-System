package com.project.carrentalsystem.Controller;

//import  org.springframework.http.HttpStatus;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties.ShowSummary;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.project.carrentalsystem.Model.Employee;
//import com.project.carrentalsystem.Service.EmployeeService;
//
//@Controller
//public class HomeController {
//
//	
//	@Autowired
//	private EmployeeService employeeService;
//	
//	@GetMapping("/")
//	public String homePage(Model model)
//	{
//		return "index";
//	}
//	
//	
//	
//	@PostMapping("/registerEmployee") 
//    public String addEmployee(@ModelAttribute("employee") Employee employee) {
//        Employee savedEmployee = employeeService.addEmployee(employee);
//        System.out.println("............................."+employee.getAccountType());
//        System.out.println("saved employee ----------------"+savedEmployee);
//        return "registrationSuccess"; 
//    }
//	
//	
//	 	@GetMapping("/registerEmployee")
//	    public String showRegisterEmployeePage(Model model) {
//	        model.addAttribute("employee", new Employee());
//	        return "registerEmployee";
//	    }
//}

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.carrentalsystem.Model.Employee;

import jakarta.servlet.http.HttpSession;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.ui.Model;
import org.springframework.http.HttpHeaders;

import java.util.*;
import java.text.*;






@Controller
public class HomeController {
	
	
	  private final RestTemplate restTemplate;
	    
	    // Injecting backend URL from application.properties
	    
	    private String backendUrl = "http://localhost:8081";

	    // Constructor injection of RestTemplate
	    public HomeController(RestTemplate restTemplate) {
	        this.restTemplate = restTemplate;
	    }

	    @GetMapping("/")
	    public String homePage() {
	    	System.out.println("INside ????????????????????////");
	        return "index"; // Home page mapping
	    }

	    @GetMapping("/registerEmployee")
	    public String registerEmployee(Model model) {
	        model.addAttribute("employee", new Employee());
	    	
	        return "registerEmployee";
	    }

	    @PostMapping("/registerEmployee")
	    public String addEmployee(@ModelAttribute Employee employee, Model model) {
	    	System.out.println("INside register");
	        String apiEndpoint = backendUrl + "/registerEmployee";

	        try {
	            ResponseEntity<Map> response = restTemplate.postForEntity(apiEndpoint, new HttpEntity<>(employee), Map.class);
	            System.out.println("response************"+response);

	            if (response.getStatusCode().is2xxSuccessful()) {
	                Map<String, Object> responseBody = response.getBody();
//	                String employeeId = responseBody.get("employeeId").toString();
	                String defaultPassword = responseBody.get("defaultPassword").toString();
	                String employeeId =  responseBody.get("employeeId").toString();

	                model.addAttribute("message", "Employee added successfully!");
//	                model.addAttribute("employeeId", employeeId);
	                model.addAttribute("defaultPassword", defaultPassword);
	                model.addAttribute("employeeId", employeeId);

//	                return "redirect:/loginPage?email=" + employeeEmail + "&defaultPassword=" + defaultPassword;
	                return "loginPage";

	            } else {
	                model.addAttribute("error", "Failed to add employee. Please try again.");
	            }
	        } catch (HttpClientErrorException | HttpServerErrorException e) {
	            model.addAttribute("error", "Error occurred while adding employee. Please try again.");
	        }

	        return "registerEmployee";
//	        return "index";
	    }

	    @GetMapping("/firstLogin")
	    public String firstLoginPage(@RequestParam String empId, @RequestParam String defaultPassword, Model model) {
	        model.addAttribute("empId", empId);
	        model.addAttribute("defaultPassword", defaultPassword);
	        return "firstLogin";
	    }

//	    @PostMapping("/firstLogin")
//	    public String handleFirstLogin(@RequestParam Map<String, String> allParams, Model model) {
//	    	
//	    	  String employeeId = allParams.get("employeeId");
//	    	    String defaultPassword = allParams.get("defaultPassword");
//	    	    String newPassword = allParams.get("newPassword");
//
//	    	    model.addAttribute("defaultPassword", defaultPassword);
//	    	    model.addAttribute("employeeId", employeeId);
//	    	    
//	        String apiEndpoint = backendUrl + "/firstLogin/" + employeeId;
//
//	        System.out.println("email"+employeeId);
//	        System.out.println("oPass"+defaultPassword);
//	        System.out.println("nPass"+newPassword);
//	        
//	        
//	        // Prepare password data to send in the request body
//	        Map<String, String> passwordData = new HashMap<>();
//	        passwordData.put("defaultPassword", defaultPassword);
//	        passwordData.put("newPassword", newPassword);
//	        System.out.println(passwordData.get("defaultPassword"));
//	        System.out.println(passwordData.get("newPassword"));
//
//	        try {
//	            HttpEntity<Map<String, String>> request = new HttpEntity<>(passwordData);
//	            System.out.println("my request"+request);
//	            ResponseEntity<String> response = restTemplate.exchange(apiEndpoint, HttpMethod.PUT, request, String.class);
//	            System.out.println("response is"+response);
//
//	            if (response.getStatusCode().is2xxSuccessful()) {
//	                model.addAttribute("message", "Password updated successfully. Please log in with your new password.");
//	                return "dashboard";
//	            } else {
//	                model.addAttribute("error", "New password must be at least 8 characters long, and include an uppercase letter, a lowercase letter, a number, and a special character.");
//	            }
//	        } catch (HttpClientErrorException | HttpServerErrorException e) {
//	            model.addAttribute("error", "New password must be at least 8 characters long, and include an uppercase letter, a lowercase letter, a number, and a special character.");
//	        }
//
//	        return "loginPage";
//	    }
	    
	    @PostMapping("/firstLogin")
	    public String handleFirstLogin(@RequestParam Map<String, String> allParams, Model model) {
	        String employeeId = allParams.get("employeeId");
	        String defaultPassword = allParams.get("defaultPassword");
	        String newPassword = allParams.get("newPassword");

	        model.addAttribute("defaultPassword", defaultPassword);
	        model.addAttribute("employeeId", employeeId);

	        String apiEndpoint = backendUrl + "/firstLogin/" + employeeId;

	        System.out.println("email: " + employeeId);
	        System.out.println("oPass: " + defaultPassword);
	        System.out.println("nPass: " + newPassword);

	        // Prepare password data to send in the request body
	        Map<String, String> passwordData = new HashMap<>();
	        passwordData.put("defaultPassword", defaultPassword);
	        passwordData.put("newPassword", newPassword);
	        System.out.println(passwordData.get("defaultPassword"));
	        System.out.println(passwordData.get("newPassword"));

	        try {
	            HttpEntity<Map<String, String>> request = new HttpEntity<>(passwordData);
	            System.out.println("my request: " + request);
	            ResponseEntity<String> response = restTemplate.exchange(apiEndpoint, HttpMethod.PUT, request, String.class);
	            System.out.println("response is: " + response);

	            if (response.getStatusCode().is2xxSuccessful()) {
	                // Call the viewAllEmployees logic to fetch employee data
	                String employeeApiEndpoint = backendUrl + "/getAllEmployees";
	                ResponseEntity<List<Employee>> employeeResponse = restTemplate.exchange(
	                    employeeApiEndpoint,
	                    HttpMethod.GET,
	                    null,
	                    new ParameterizedTypeReference<List<Employee>>() {}
	                );
	                List<Employee> employees = employeeResponse.getBody();
	                if (employees != null && !employees.isEmpty()) {
	                    model.addAttribute("employees", employees);
	                } else {
	                    model.addAttribute("errorMessage", "No employee records found.");
	                    return "statuspage";
	                }

	                model.addAttribute("message", "Password updated successfully. Please log in with your new password.");
	                return "dashboard"; // Render the dashboard with employee data
	            } else {
	                model.addAttribute("error", "New password must be at least 8 characters long, and include an uppercase letter, a lowercase letter, a number, and a special character.");
	            }
	        } catch (HttpClientErrorException | HttpServerErrorException e) {
	            model.addAttribute("error", "New password must be at least 8 characters long, and include an uppercase letter, a lowercase letter, a number, and a special character.");
	        }

	        return "loginPage";
	    }


	    @GetMapping("/getAllEmployees")
	    public String viewAllEmployees(Model model) {
	    	System.out.println("INside get employee");
	        String url = backendUrl + "/getAllEmployees";
	        
	        try {
	            // Fetching employee data from backend API
	            ResponseEntity<List<Employee>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {});
	            System.out.println("response"+response);
	            List<Employee> employees = response.getBody();
	            if (employees != null && !employees.isEmpty()) {
	                model.addAttribute("employees", employees);
	                return "dashboard";
	            } else {
	                model.addAttribute("errorMessage", "No employee records found.");
	                return "statuspage";
	            }
	        } catch (HttpClientErrorException | HttpServerErrorException e) {
	            model.addAttribute("errorMessage", "Error occurred while fetching employee data. Please try again later.");
	            return "statuspage";
	        }
	    }
	    
//	    @GetMapping("/getAllEmployees")
//		public String viewAllEmployees(Model model) {
//			String url = backendUrl + "/getAllEmployees";
//
//			try {
//				// Fetching employee data from backend API
//				ResponseEntity<List<Employee>> response = restTemplate.exchange(
//						url,
//						HttpMethod.GET,
//						null,
//						new ParameterizedTypeReference<List<Employee>>() {
//						});
//
//				List<Employee> employees = response.getBody();
//				if (employees != null && !employees.isEmpty()) {
//					model.addAttribute("employees", employees);
//					return "dashboard"; // Returning the "dashboard" view
//				} else {
//					model.addAttribute("errorMessage", "No employee records found.");
//					return "statuspage"; // Returning the error view if no records
//				}
//			} catch (HttpClientErrorException | HttpServerErrorException e) {
//				model.addAttribute("errorMessage", "Error occurred while fetching employee data. Please try again later.");
//				return "statuspage"; // Returning the error view
//			}
//		}


	    @GetMapping("/viewProfile")
		public String viewProfile(Model model, HttpSession session) {
			// Retrieve the employee ID from the session
			String empId = (String) session.getAttribute("loggedInEmployeeId");

			if (empId == null) {
				model.addAttribute("errorMessage", "Session expired. Please log in again.");
				return "login"; // Redirect to login page if session expired
			}

			// Construct backend API endpoint
			String apiEndpoint = backendUrl + "/getEmployeeById/" + empId;

			try {
				// Call backend to fetch employee details
				ResponseEntity<Employee> response = restTemplate.exchange(apiEndpoint, HttpMethod.GET, null,
						Employee.class);

				if (response.getStatusCode().is2xxSuccessful()) {
					Employee employee = response.getBody();
					model.addAttribute("employee", employee); // Add employee details to model
					return "profile"; // Render profile page
				} else {
					model.addAttribute("errorMessage", "Employee not found.");
					return "statuspage"; // Show error if employee not found
				}
			} catch (HttpClientErrorException | HttpServerErrorException e) {
				model.addAttribute("errorMessage", "Error occurred while fetching employee profile.");
				return "statuspage"; // Handle error scenarios
			}
		}


}

