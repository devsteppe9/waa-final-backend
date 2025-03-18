package edu.miu.waa.controller;

import edu.miu.waa.model.Role;
import edu.miu.waa.repo.RoleRepo;
import edu.miu.waa.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/system")
@RequiredArgsConstructor
public class SystemController {

    @Autowired
    private RoleRepo roleRepository;

    @GetMapping("/roles")
    public List<Role> getRegularRoles() {
       return roleRepository.findAll().stream().filter(r -> !r.getRole().equals("ADMIN")).toList();
    }
}
