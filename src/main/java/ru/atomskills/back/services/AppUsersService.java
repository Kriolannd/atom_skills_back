package ru.atomskills.back.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.atomskills.back.repositories.AppUsersRepository;

@Service
@RequiredArgsConstructor
public class AppUsersService implements UserDetailsService {
    private final AppUsersRepository appUsersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUsersRepository.findByUsername(username).orElseThrow();
    }
}
