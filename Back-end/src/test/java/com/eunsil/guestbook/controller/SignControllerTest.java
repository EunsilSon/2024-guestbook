package com.eunsil.guestbook.controller;

import com.eunsil.guestbook.service.SignService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class SignControllerTest {

    @Mock
    private SignService signService;

    @InjectMocks
    private SignController signController;


    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("회원가입_성공")
    public void signUpTestSuccess() {
        // given
        HashMap<String, String> req = new HashMap<>();
        req.put("username", "test");
        req.put("password", "12345678");
        req.put("telephone", "01012345678");

        when(signService.signUp("test", "12345678", "01012345678")).thenReturn("success");

        // when
        String result = signController.signUp(req);

        // then
        assertEquals("success", result);
        verify(signService, times(1)).signUp("test", "12345678", "01012345678");
    }

    @Test
    @DisplayName("회원가입_실패")
    public void signUpTestFailure() {
        HashMap<String, String> req = new HashMap<>();
        req.put("username", "son");
        req.put("password", "12345678");
        req.put("telephone", "01039008294");

        when(signService.signUp("son", "12345678", "01039008294")).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> {
            signController.signUp(req);
        });
    }
}
