package edu.miu.waa.service;

import edu.miu.waa.dto.response.UserOutDto;
import edu.miu.waa.dto.request.UserInDto;
import edu.miu.waa.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserOutDto> findAllUsers();
    public User addUser(UserInDto user);
    public User findByUsername(String username);
    public boolean updateUserStatus(Long id, boolean status) ;
}
