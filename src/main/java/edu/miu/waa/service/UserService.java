package edu.miu.waa.service;

import edu.miu.waa.dto.response.UserOutDto;
import edu.miu.waa.dto.request.UserInDto;
import edu.miu.waa.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserOutDto> findAllUsers();
    User addUser(UserInDto user);
    User findByUsername(String username);
    boolean updateUserStatus(Long id, boolean status) ;
    User addUser(User user);
    User findById(Long id);
    
}
