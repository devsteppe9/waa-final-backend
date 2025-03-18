package edu.miu.waa.service.impl;

import edu.miu.waa.dto.UserDto;
import edu.miu.waa.model.Property;
import edu.miu.waa.model.User;
import edu.miu.waa.repo.UserRepo;
import edu.miu.waa.service.UserService;
import edu.miu.waa.util.ListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    ListMapper listMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAllUsers() {
        return listMapper.mapList( userRepository.findAll(),new UserDto());
    }

    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public boolean updateUserStatus(Long id, boolean status) {
        return userRepository.updateUserStatus(id, status) > 0;
    }
}
