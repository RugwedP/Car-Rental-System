package com.project.carrentalsystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller
public class HomeController {

	@GetMapping("/")
    public String homePage() {
        return "index";
    }

	 @GetMapping("/registerEmployee")
	  public String registerEmployee() {
	      return "registerEmployee"; 
	 }

	// @PostMapping("/registerEmployee")
    // public String handleEmployeeRegistration(String employeeName, String password, Model model) {
    //     String defaultPassword = password; // Implement this method to generate the password
    //     // You can set the password to the model to pass to the view
    //     model.addAttribute("employeeName", employeeName);
    //     model.addAttribute("defaultPassword", defaultPassword);
    //     return "registrationSuccess"; // Redirect to the success page
    // }





// @RestController
// @RequestMapping("/api/employees")
// @CrossOrigin(origins = "*") // Allow cross-origin requests from any domain
// public class EmployeeController {
// 
//     @Autowired
//     private EmployeeService employeeService;
// 
//     // Endpoint to fetch all employees
//     @GetMapping
//     public List<Employee> getAllEmployees() {
//         return employeeService.getAllEmployees();
//     }
// 
//     // Endpoint to fetch a specific employee by ID
//     @GetMapping("/{id}")
//     public Employee getEmployeeById(@PathVariable Long id) {
//         return employeeService.getEmployeeById(id);
//     }
// 
//     // Endpoint to register a new employee
//     @PostMapping
//     public Employee registerEmployee(@RequestBody Employee employee) {
//         return employeeService.saveEmployee(employee);
//     }
// 
//     // Endpoint to update an existing employee
//     @PutMapping("/{id}")
//     public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
//         return employeeService.updateEmployee(id, updatedEmployee);
//     }
// 
//     // Endpoint to delete an employee
//     @DeleteMapping("/{id}")
//     public void deleteEmployee(@PathVariable Long id) {
//         employeeService.deleteEmployee(id);
//     }
// }

}
