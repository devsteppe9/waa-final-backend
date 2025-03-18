package edu.miu.waa.service;

import edu.miu.waa.dto.UserDto;
import edu.miu.waa.model.Property;
import edu.miu.waa.model.User;

import java.util.List;

public interface UserService {
    List<UserDto> findAllUsers();
    public User addUser(User user);
    public User findByUsername(String username);
    public boolean updateUserStatus(Long id, boolean status) ;
}
