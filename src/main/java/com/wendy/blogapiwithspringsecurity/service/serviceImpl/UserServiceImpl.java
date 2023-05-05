package com.wendy.blogapiwithspringsecurity.service.serviceImpl;

import com.wendy.blogapiwithspringsecurity.configuration.securityConfig.JwtService;
import com.wendy.blogapiwithspringsecurity.dtos.AuthenticationResponse;
import com.wendy.blogapiwithspringsecurity.dtos.UserDto;
import com.wendy.blogapiwithspringsecurity.enums.Roles;
import com.wendy.blogapiwithspringsecurity.exceptionHandler.CustomUserException;
import com.wendy.blogapiwithspringsecurity.models.Token;
import com.wendy.blogapiwithspringsecurity.models.Users;
import com.wendy.blogapiwithspringsecurity.repositories.TokenRepository;
import com.wendy.blogapiwithspringsecurity.repositories.UserRepository;
import com.wendy.blogapiwithspringsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.authenticator.SpnegoAuthenticator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;


    public Users getUser(Long id) {
        Optional< Users> user = userRepository.findById(id);
        return user.get();

    }

    @Override
    public AuthenticationResponse createUser(UserDto userDto) {
        Users users = map2Entity(userDto);
        users.setPassword(passwordEncoder.encode(userDto.getPassword()));
        users.setRole(Roles.REGULAR);
        Users savedUser = userRepository.save(users);
        String jwtToken = jwtService.generateToken(savedUser);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build();
    }

    private void saveUserToken(Users savedUser, String jwtToken) {
        Token token = Token.builder()
                .token(jwtToken)
                .users(savedUser)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenRepository.save(token);

    }

    @Override
    public AuthenticationResponse loginUser(UserDto userDto){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getEmail(),
                        userDto.getPassword()
                )
        );
        Users users = map2Entity(userDto);
       Users users1 =  userRepository.findByEmail(userDto.getEmail())
               .orElseThrow();
       log.info("========> " + userDto.getEmail());
        String jwtToken = jwtService.generateToken(users1);

        log.info("this is the user token: --------->" + jwtToken);
        revokeAllToken(users1);

        saveUserToken(users1, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void revokeAllToken(Users users) {
        List<Token> tokenList = tokenRepository.findAllValidTokenByUser(users.getId());
        log.info(users.getId()+" =====> This is the id of the user");
        if(tokenList.isEmpty()){
            return;
        }
        for(Token token : tokenList){
            token.setRevoked(true);
            token.setExpired(true);
            tokenRepository.saveAll(tokenList);
        }
        log.info("============== Saved tokens ===============");
    }

    @Override
    public AuthenticationResponse createAdmin(UserDto userDto) {
        Users users = map2Entity(userDto);
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setRole(Roles.ADMIN);
        Users savedAdmin = userRepository.save(users);
        String jwtToken = jwtService.generateToken(savedAdmin);
        saveUserToken(savedAdmin, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private UserDto mapToUserDto(Users user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());
        userDto.setUserName(user.getUsername());
        return userDto;
    }


    public UserDto mapToDto(Users users){
        return UserDto.builder()
                .userName(users.getUsername())
                .email(users.getEmail())
                .password(users.getPassword())
                .build();
    }

    public static Users map2Entity(UserDto users){
        Users user = new Users();
        user.setId(users.getId());
        user.setUsername(users.getUserName());
        user.setEmail(users.getEmail());
        user.setPassword(users.getPassword());
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }

}

