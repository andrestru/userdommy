package com.example.dummy.domain.usecase;

import com.example.dummy.domain.errors.BusinessException;
import com.example.dummy.domain.model.LoginRequest;
import com.example.dummy.domain.model.LoginResponse;
import com.example.dummy.domain.model.SaveUser;
import com.example.dummy.domain.model.repository.LoginRepository;
import com.example.dummy.infrastructure.driver_adapters.adapters.DummyAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserUseCaseTest {

    private LoginRepository repository;
    private DummyAdapter dummyAdapter;
    private ObjectMapper mapper;
    private UserUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(LoginRepository.class);
        dummyAdapter = mock(DummyAdapter.class);
        mapper = new ObjectMapper();
        useCase = new UserUseCase(repository, dummyAdapter, mapper);
    }

    @Test
    void login_shouldReturnLoginResponse_andSaveUser_whenSuccessful() throws Exception {
        String username = "emilys";
        String password = "emilyspass";
        String accessToken = "token123";
        String refreshToken = "refresh456";

        String dummyResponse = String.format("""
            {
                "accessToken": "%s",
                "refreshToken": "%s"
            }
            """, accessToken, refreshToken);

        when(dummyAdapter.loginDummy(username, password))
                .thenReturn(ResponseEntity.ok(dummyResponse));

        when(repository.save(any(SaveUser.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        LoginRequest request = new LoginRequest(username, password);

        LoginResponse response = useCase.login(request);

        assertEquals("Ingreso exitoso", response.getMessage());
        assertEquals(HttpStatus.OK, response.getHttpStatus());

        SaveUser savedUser = (SaveUser) response.getData();
        assertEquals(username, savedUser.getUsername());
        assertEquals(accessToken, savedUser.getAccessToken());
        assertEquals(refreshToken, savedUser.getRefreshToken());
        assertNotNull(savedUser.getLoginTime());


        verify(dummyAdapter).loginDummy(username, password);
        verify(repository).save(any(SaveUser.class));
    }

    @Test
    void login_shouldThrowException_whenDummyReturnsError() {

        when(dummyAdapter.loginDummy(anyString(), anyString()))
                .thenReturn(ResponseEntity.status(401).body("Unauthorized"));

        LoginRequest request = new LoginRequest("user", "wrongpass");

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            useCase.login(request);
        });

        assertEquals("Petición a Dummy erronea, credenciales incorrectas", exception.getMessage());
    }
}
