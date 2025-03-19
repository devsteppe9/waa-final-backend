package edu.miu.waa.service.impl;

import edu.miu.waa.dto.UserInDto;
import edu.miu.waa.dto.UserOutDto;
import edu.miu.waa.model.Role;
import edu.miu.waa.model.User;
import edu.miu.waa.repo.RoleRepo;
import edu.miu.waa.repo.UserRepo;
import edu.miu.waa.service.UserService;
import edu.miu.waa.util.ListMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepository;
    @Autowired
    private RoleRepo roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    ListMapper listMapper;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    @Transactional(readOnly = true)
    public List<UserOutDto> findAllUsers() {
        return listMapper.mapList( userRepository.findAll(),new UserOutDto());
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public User addUser(UserInDto user) {
        User u = modelMapper.map(user,User.class);
        u.setPassword(passwordEncoder.encode(user.getPassword()));
        //assign roles based on user.getRole
        Role role = roleRepository.findRoleByRole(user.getRole()).orElse(null);
        u.getRoles().add(role);
        return userRepository.save(u);
    }


    public boolean updateUserStatus(Long id, boolean status) {
        User u = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        u.setEnabled(status);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword()
                ,user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getRole()))
                    .collect(Collectors.toList()));
    }
}
