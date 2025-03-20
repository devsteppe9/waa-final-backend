package edu.miu.waa.service.impl;

import edu.miu.waa.dto.request.UserInDto;
import edu.miu.waa.dto.response.UserOutDto;
import edu.miu.waa.model.Role;
import edu.miu.waa.model.User;
import edu.miu.waa.repo.RoleRepo;
import edu.miu.waa.repo.UserRepo;
import edu.miu.waa.service.UserService;
import edu.miu.waa.util.ListMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("userDetailsService")
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepository;

    private final RoleRepo roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final ListMapper listMapper;

    private final ModelMapper modelMapper;


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
        Role role = roleRepository.findRoleByRole(user.getRole()).orElseThrow(() -> new IllegalArgumentException("Invalid Role"));
        u.getRoles().add(role);
        return userRepository.save(u);
    }


    public boolean updateUserStatus(Long id, boolean status) {
        User u = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        u.setEnabled(status);
        return true;
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
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
