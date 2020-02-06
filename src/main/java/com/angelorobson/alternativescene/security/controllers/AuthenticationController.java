package com.angelorobson.alternativescene.security.controllers;

import com.angelorobson.alternativescene.converters.Converters;
import com.angelorobson.alternativescene.dtos.UserAppDto;
import com.angelorobson.alternativescene.entities.UserApp;
import com.angelorobson.alternativescene.response.Response;
import com.angelorobson.alternativescene.security.dto.JwtAuthenticationDto;
import com.angelorobson.alternativescene.security.dto.TokenDto;
import com.angelorobson.alternativescene.security.services.JwtUserDetailsServiceImpl;
import com.angelorobson.alternativescene.security.utils.JwtTokenUtil;
import com.angelorobson.alternativescene.services.UserAppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    private static final String TOKEN_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private JwtUserDetailsServiceImpl jwtUserDetailsService;
    private UserAppService userAppService;

    public AuthenticationController() {
    }

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtTokenUtil jwtTokenUtil,
                                    JwtUserDetailsServiceImpl jwtUserDetailsService,
                                    UserAppService userAppService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.userAppService = userAppService;
    }

    /**
     * Generates and returns a new JWT token.
     *
     * @param authenticationDto
     * @param result
     * @return ResponseEntity<Response                               <                               TokenDto>>
     * @throws AuthenticationException
     */
    @PostMapping
    public ResponseEntity<Response<TokenDto>> generateTokenJwt(
            @Valid @RequestBody JwtAuthenticationDto authenticationDto, BindingResult result)
            throws AuthenticationException {
        Response<TokenDto> response = new Response<>();

        if (result.hasErrors()) {
            log.error("Error validating user: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        log.info("Generating token for email {}.", authenticationDto.getEmail());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationDto.getEmail(), authenticationDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationDto.getEmail());
        String token = jwtTokenUtil.getToken(userDetails);

        UserApp userApp = userAppService.findByEmail(userDetails.getUsername()).get();

        UserAppDto userAppDto = Converters.convertUserAppEntityToDto(userApp);

        TokenDto tokenDto = new TokenDto(token, userAppDto);
        response.setData(tokenDto);

        return ResponseEntity.ok(response);
    }

    /**
     * Generates a new token with a new expiration date.
     *
     * @param request
     * @return ResponseEntity<Response                               <                               TokenDto>>
     */
    @PostMapping(value = "/refresh")
    public ResponseEntity<Response<TokenDto>> generateRefreshTokenJwt(HttpServletRequest request) {
        log.info("Generating refresh token JWT.");
        Response<TokenDto> response = new Response<>();
        Optional<String> token = Optional.ofNullable(request.getHeader(TOKEN_HEADER));

        if (token.isPresent() && token.get().startsWith(BEARER_PREFIX)) {
            token = Optional.of(token.get().substring(7));
        }

        if (!token.isPresent()) {
            response.getErrors().add("Token not informed.");
        } else if (!jwtTokenUtil.validToken(token.get())) {
            response.getErrors().add("Token invalid or expired.");
        }

        if (!response.getErrors().isEmpty()) {
            return ResponseEntity.badRequest().body(response);
        }

        String refreshedToken = jwtTokenUtil.refreshToken(token.get());
        response.setData(new TokenDto(refreshedToken));
        return ResponseEntity.ok(response);
    }

}
