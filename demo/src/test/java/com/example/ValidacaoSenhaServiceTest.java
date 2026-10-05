package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidacaoSenhaServiceTest {

    private final ValidacaoSenhaService service =
            new ValidacaoSenhaService();

    @Test
    void deveAceitarSenhaValida() {
        assertTrue(service.validarSenha("Java@12345"));
    }

    @Test
    void deveRejeitarSenhaNula() {
        assertFalse(service.validarSenha(null));
    }

    @Test
    void deveRejeitarSenhaVazia() {
        assertFalse(service.validarSenha(""));
    }

    @Test
    void deveRejeitarSenhaEmBranco() {
        assertFalse(service.validarSenha("           "));
    }

    @Test
    void deveRejeitarSenhaComMenosDeDezCaracteres() {
        assertFalse(service.validarSenha("Ab@123456"));
    }

    @Test
    void deveRejeitarSenhaComMaisDeDozeCaracteres() {
        assertFalse(service.validarSenha("Ab@1234567890"));
    }

    @Test
    void deveAceitarSenhaComDezCaracteres() {
        assertTrue(service.validarSenha("Ab@1234567"));
    }

    @Test
    void deveAceitarSenhaComDozeCaracteres() {
        assertTrue(service.validarSenha("Ab@123456789"));
    }

    @Test
    void deveRejeitarSenhaSemNumero() {
        assertFalse(service.validarSenha("Java@abcdef"));
    }

    @Test
    void deveRejeitarSenhaSemLetra() {
        assertFalse(service.validarSenha("123456789@"));
    }

    @Test
    void deveRejeitarSenhaSemCaractereEspecial() {
        assertFalse(service.validarSenha("Java123456"));
    }
}