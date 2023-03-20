package peaksoft.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.jwt.JwtUtil;
import peaksoft.dto.AuthRequest;
import peaksoft.dto.AuthResponse;
import peaksoft.dto.RegisterRequest;
import peaksoft.entity.AuthInfo;
import peaksoft.exception.AlreadyExistsException;
import peaksoft.exception.BadCredentialsException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.AuthInfoRepository;
import peaksoft.service.AuthService;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthInfoRepository authInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        if (authInfoRepository.existsByEmail(registerRequest.getEmail())) {
            throw new AlreadyExistsException(String.format
                    ("User with email: %s already exists", registerRequest.getEmail()));
        }
        AuthInfo authInfo = AuthInfo.builder()
                .email(registerRequest.getEmail())
                .role(registerRequest.getRole())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();
        authInfoRepository.save(authInfo);
        String token = jwtUtil.generateToken(authInfo);

        return AuthResponse.builder()
                .token(token)
                .email(authInfo.getEmail())
                .build();
    }


    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );

        AuthInfo authInfo=authInfoRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(()->new BadCredentialsException(String.format
                        ("User with email: %s doesn't exists", authRequest.getEmail())));
        String token=jwtUtil.generateToken(authInfo);

        return AuthResponse.builder()
                .token(token)
                .email(authInfo.getEmail())
                .build();
    }
}
