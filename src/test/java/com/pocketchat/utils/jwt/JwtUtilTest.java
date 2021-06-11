package com.pocketchat.utils.jwt;

import com.pocketchat.db.models.user_authentication.UserAuthentication;
import com.pocketchat.db.models.user_privilege.UserPrivilege;
import com.pocketchat.db.models.user_role.UserRole;
import com.pocketchat.server.configurations.security.service.MyUserDetailsService;
import com.pocketchat.services.user_authentication.UserAuthenticationService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JwtUtilTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${jwt.secret.key}")
    private final String SECRET_KEY = "pocketChat";

    @Value("${jwt.alive.seconds}")
    private final int jwtAliveSeconds = 36000000;

    private JwtUtil jwtUtil;

    @Mock
    private MyUserDetailsService myUserDetailsService;

    @Mock
    private UserAuthenticationService userAuthenticationService;

    @BeforeEach
    void setup() {
        //if we don't call below, we will get NullPointerException
        MockitoAnnotations.openMocks(this);
        jwtUtil = new JwtUtil(
                SECRET_KEY,
                jwtAliveSeconds
        );

        myUserDetailsService = new MyUserDetailsService(userAuthenticationService);
    }

    /**
     * Test a generated JWT with all correct information within the string itself.
     * Covered all methods in JWTUtils.
     */
    @Test
    void testGenerateRandomJWT() {
        String username = UUID.randomUUID().toString();

        UserDetails userDetails = generateUserDetailsObject(username);
        List<String> grantedAuthorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        String jwt = jwtUtil.generateToken(userDetails);

        logger.info("jwt: {}", jwt);

        Boolean tokenIsValid = jwtUtil.validateToken(jwt, userDetails);
        Boolean tokenIsExpired = jwtUtil.isTokenExpired(jwt);
        String extractedUsername = jwtUtil.extractUsername(jwt);
        Date jwtExpiryDate = jwtUtil.extractExpiration(jwt);
        Date jwtIssuedDate = jwtUtil.extractJWTIssuedDate(jwt);

        LocalDateTime jwtExpiryDateConverted = LocalDateTime.ofInstant(jwtExpiryDate.toInstant(), ZoneId.systemDefault());
        LocalDateTime now = LocalDateTime.now();

        Claims claims = jwtUtil.extractAllClaims(jwt);

        verify(userAuthenticationService).findByUsername(eq(username));

        assertNotNull(jwt);
        assertTrue(tokenIsValid);
        assertFalse(tokenIsExpired);
        assertEquals(extractedUsername, username);
        assertTrue(jwtExpiryDateConverted.isAfter(now));
        assertEquals(5, claims.size()); // Include Subject, expiration and issued at.
        assertEquals(claims.get("grantedAuthorities"), grantedAuthorities);
        assertEquals(claims.get("password"), userDetails.getPassword());
        assertEquals(claims.getExpiration(), jwtExpiryDate);
        assertEquals(claims.getIssuedAt(), jwtIssuedDate);
    }

    private UserDetails generateUserDetailsObject(String username) {
        UserAuthentication userAuthentication = generateUserAuthenticationObject(2, 2, username);
        when(userAuthenticationService.findByUsername(eq(username))).thenReturn(userAuthentication);
        return myUserDetailsService.loadUserByUsername(username);
    }

    private UserAuthentication generateUserAuthenticationObject(int numberOfUserRoles, int numberOfUserPrivileges, String username) {
        List<UserRole> userRoleList = new ArrayList<>();

        for (int i = 0; i < numberOfUserRoles; i++) {
            userRoleList.add(generateUserRole(numberOfUserPrivileges));
        }

        return UserAuthentication.builder()
                .id(UUID.randomUUID().toString())
                .username(username)
                .password(UUID.randomUUID().toString())
                .userId(UUID.randomUUID().toString())
                .userRoles(userRoleList)
                .build();
    }

    private UserRole generateUserRole(int numberOfUserPrivileges) {
        List<UserPrivilege> userPrivilegeList = new ArrayList<>();

        for (int i = 0; i < numberOfUserPrivileges; i++) {
            userPrivilegeList.add(generateUserPrivilege());
        }

        return UserRole.builder()
                .name(UUID.randomUUID().toString())
                .userPrivileges(userPrivilegeList)
                .build();
    }

    private UserPrivilege generateUserPrivilege() {
        return UserPrivilege.builder()
                .id(UUID.randomUUID().toString())
                .name(UUID.randomUUID().toString())
                .build();
    }
}
