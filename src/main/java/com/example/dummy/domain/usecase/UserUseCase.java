package com.example.dummy.domain.usecase;

import com.example.dummy.domain.errors.BusinessException;
import com.example.dummy.domain.model.LoginRequest;
import com.example.dummy.domain.model.LoginResponse;
import com.example.dummy.domain.model.SaveUser;
import com.example.dummy.domain.model.repository.LoginRepository;
import com.example.dummy.infrastructure.driver_adapters.adapters.DummyAdapter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;

@AllArgsConstructor
@Service
public class UserUseCase {

    private final LoginRepository repository;
    private final DummyAdapter dummyAdapter;
    private final ObjectMapper mapper;

    public LoginResponse login(LoginRequest request) throws JsonProcessingException {
        ResponseEntity<String> response = dummyAdapter.loginDummy(request.getUsername(), request.getPassword());

        if (response.getStatusCode().is2xxSuccessful()){
            JsonNode json = mapper.readTree(response.getBody());

            SaveUser user = new SaveUser();
            user.setUsername(request.getUsername());
            user.setLoginTime(Instant.now());
            user.setAccessToken(json.get("accessToken").asText());
            user.setRefreshToken(json.has("refreshToken") ? json.get("refreshToken").asText() : "");

            SaveUser saveUser = repository.save(user);
            return new LoginResponse("Ingreso exitoso", HttpStatus.OK, saveUser);
        }else{
            throw new BusinessException("Petición a Dummy erronea, credenciales incorrectas");
        }
    }


}
