package com.example.dummy.infrastructure.entry_points;

import com.example.dummy.domain.model.LoginRequest;
import com.example.dummy.domain.model.LoginResponse;
import com.example.dummy.domain.usecase.UserUseCase;
import com.example.dummy.infrastructure.driver_adapters.adapters.DummyAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserUseCase useCase;
    private final DummyAdapter adapter;
    private final ObjectMapper objectMapper;

    public UserController(UserUseCase useCase, DummyAdapter adapter, ObjectMapper objectMapper) {
        this.useCase = useCase;
        this.adapter = adapter;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) throws Exception {
        LoginResponse responseBody = useCase.login(request);
        return new ResponseEntity<>(responseBody, responseBody.getHttpStatus());
    }

    @GetMapping("/me")
    public ResponseEntity<String> getMe(@RequestHeader("Authorization") String token) {
        return adapter.getUserDummy(token);
    }

}
