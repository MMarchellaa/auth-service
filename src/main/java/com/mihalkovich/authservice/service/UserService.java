package com.mihalkovich.authservice.service;

import com.mihalkovich.authservice.dto.UserDto;
import com.mihalkovich.authservice.entity.User;
import com.mihalkovich.authservice.entity.enums.Role;
import com.mihalkovich.authservice.mapper.UserMapper;
import com.mihalkovich.authservice.payload.request.SignupRequest;
import com.mihalkovich.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserDto createUser(SignupRequest userIn) {
        User user = new User();
        user.setUsername(userIn.getUsername());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.setRole(Role.ROLE_TEACHER);
        System.out.println(user);
        return userMapper.toDto(userRepository.save(user));
    }

    public UserDto updateUser(UserDto userDto) {
        User user = userRepository.getById(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return userMapper.toDto(userRepository.save(user));
    }

    public UserDto getCurrentUser(Principal principal) {
        return getUserByPrincipal(principal);
    }

    public UserDto getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userMapper.toDto(findUserByUsername(username));

    }

    public User findUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username: " + username));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserDto deleteUser(Long id) {
        User user = userRepository.getById(id);
        userRepository.delete(user);
        return userMapper.toDto(user);
    }

    public User loadUserById(Long id) {
        return userRepository.findUserById(id).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username: " + username));

        return build(user);
    }

    public static User build(User user) {

        return new User(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
    }
}
