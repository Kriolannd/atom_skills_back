package ru.atomskills.back.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.atomskills.back.dto.LoginResponse;
import ru.atomskills.back.models.AppUser;
import ru.atomskills.back.repositories.AppUsersRepository;
import ru.atomskills.back.utils.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AppUsersRepository appUsersRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    private final Map<String, String> refreshStorage = new HashMap<>();

    public AppUser signup(String username, String password) {
        return appUsersRepository.save(
                AppUser.builder()
                        .username(username)
                        .password(passwordEncoder.encode(password))
                        .build()
        );
    }

    public LoginResponse authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return buildTokensResponse(username);
    }

    public LoginResponse refresh(String refreshToken) {
        if (jwtUtil.isTokenExpired(refreshToken)) {
            throw new BadCredentialsException("Expired refresh token");
        }

        String username = jwtUtil.extractUsername(refreshToken);
        String savedToken = refreshStorage.get(username);

        if (savedToken == null || !savedToken.equals(refreshToken)) {
            throw new BadCredentialsException("Refresh token not found");
        }

        return buildTokensResponse(username);
    }

    private LoginResponse buildTokensResponse(String username) {
        UserDetails user = appUsersRepository.findByUsername(username).orElseThrow();

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        refreshStorage.put(username, refreshToken);

        return LoginResponse.builder()
                .token(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(jwtUtil.getExpirationTime())
                .build();
    }
}
