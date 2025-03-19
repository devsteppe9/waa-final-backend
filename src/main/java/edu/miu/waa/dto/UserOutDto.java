package edu.miu.waa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserOutDto {
    long id;
    String username;
    String email;
    String firstName;
    String lastName;
    Boolean enabled;
    String role;
    public UserOutDto(){}
}
