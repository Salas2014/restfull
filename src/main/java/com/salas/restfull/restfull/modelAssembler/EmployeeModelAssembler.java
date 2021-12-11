package com.salas.restfull.restfull.modelAssembler;

import com.salas.restfull.restfull.controller.EmployeeController;
import com.salas.restfull.restfull.entity.Employee;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {
    @Override
    public EntityModel<Employee> toModel(Employee entity) {

        return EntityModel.of(entity,
                linkTo(methodOn(EmployeeController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).all()).withRel("employees")
                );
    }
}
