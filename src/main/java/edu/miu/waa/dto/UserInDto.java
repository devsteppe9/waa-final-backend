package edu.miu.waa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInDto {
    long id;
    String username;
    String password;
    String email;
    String firstName;
    String lastName;
    Boolean enabled;
    String role;
    public UserInDto(){}
}
