package com.salas.restfull.restfull.controller;

import com.salas.restfull.restfull.entity.Employee;
import com.salas.restfull.restfull.handlerException.EmployeeNotFoundException;
import com.salas.restfull.restfull.modelAssembler.EmployeeModelAssembler;
import com.salas.restfull.restfull.repo.EmployeeRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EmployeeController {
    private final EmployeeRepository repository;
    private final EmployeeModelAssembler employeeModelAssembler;

    public EmployeeController(EmployeeRepository repository, EmployeeModelAssembler employeeModelAssembler) {
        this.repository = repository;
        this.employeeModelAssembler = employeeModelAssembler;
    }

    @GetMapping("/employees")
    public CollectionModel<EntityModel<Employee>> all(){

        List<EntityModel<Employee>> employees = repository.findAll()
                .stream()
                .map(employeeModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(employees,
                linkTo(methodOn(EmployeeController.class)
                        .all()).withSelfRel());
    }

    @PostMapping("/employees")
    Employee newEmployee(@RequestBody Employee employee){
        return repository.save(employee);
    }

    @GetMapping("/employees/{id}")
    public EntityModel<Employee> one(@PathVariable Long id){
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return employeeModelAssembler.toModel(employee);
    }

    @PutMapping("/employees/{id}")
    Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });
    }
    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
