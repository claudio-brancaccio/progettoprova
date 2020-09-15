package com.springboot.cruddemo.rest;

import com.springboot.cruddemo.entity.Employee;
import com.springboot.cruddemo.service.EmployeeService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/")
public class EmployeeRestController {

   // Logger logger = LoggerFactory.getLogger(EmployeeRestController.class);

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees(){
        ciao();
        List<Employee> employees = employeeService.findAll();
        for(Employee e: employees)
            log.debug(e.toString());
        return employeeService.findAll();
    }

    public int ciao() {
        int c = 1+1;
        return c;
    }



    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId){
        Employee theEmployee = employeeService.findById(employeeId);
        if(theEmployee == null){
            log.error("Employee id not found - "+employeeId);
            throw new RuntimeException("Employee id not found - "+employeeId);
        }
        log.debug("Employee with id "+employeeId+" has name "+theEmployee.getFirstName());
        return  theEmployee;
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee theEmployee){
        theEmployee.setId(0);

        employeeService.save(theEmployee);

        log.debug("Salvato "+theEmployee.toString());

        return theEmployee;
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee theEmployee){
        employeeService.save(theEmployee);
        log.debug("Aggiornato "+theEmployee.toString());
        return theEmployee;
    }

    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId){
        Employee tempEmployee = employeeService.findById(employeeId);
        if(tempEmployee == null) {
            log.error("Employee id not found - "+employeeId);
            throw new RuntimeException("Employee id not found - " + employeeId);
        }
        employeeService.deleteById(employeeId);
        log.debug("Cancellato "+tempEmployee.toString());
        return "Deleted employee id - "+employeeId;
    }
}
