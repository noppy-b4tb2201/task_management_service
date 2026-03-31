package com.example.auth_service.service;

import com.example.auth_service.dto.request.LoginRequestDto;
import com.example.auth_service.dto.request.RegisterRequestDto;
import com.example.auth_service.entity.User;
import com.example.auth_service.exception.InvalidPasswordException;
import com.example.auth_service.exception.UserAlreadyExistsException;
import com.example.auth_service.exception.UserDontExistException;
import com.example.auth_service.repository.UserRepository;
import com.example.auth_service.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    //ユーザー登録
    @Transactional
    public void register(RegisterRequestDto request) {

        //email重複チェック
        if(userRepository.existsByEmail(request.getEmail())) {

            throw new UserAlreadyExistsException("Email already exists");
        }

        //userエンティティに値をDTOから詰め替える、その際パスワードはハッシュ化する
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

    }

    //ログイン処理
    public String login(LoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserDontExistException("User dont exist"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new InvalidPasswordException("Password is not valid");
        }

        String token = jwtService.generateToken(user.getId());
        return token;
    }
}
