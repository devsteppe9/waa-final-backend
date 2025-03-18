package edu.miu.waa.dto;

import edu.miu.waa.model.Role;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDto {
    long id;
    String username;
    String email;
    String firstName;
    String lastName;
    Boolean enabled;
    List<Role> roles;
    public UserDto(){}
}
