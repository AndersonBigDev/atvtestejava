
package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AutenticacaoServiceTest {

    private final AutenticacaoService service =
            new AutenticacaoService();

    @Test
    void deveCadastrarUsuarioValido() {
        Usuario usuario = service.cadastrarUsuario(
                "Anderson", "anderson@email.com",
                "Java@12345", "CLIENTE");

        assertNotNull(usuario);
        assertEquals("Anderson", usuario.getNome());
        assertEquals("CLIENTE", usuario.getNivel());
    }

    @Test
    void deveRejeitarCamposObrigatoriosVazios() {
        assertThrows(IllegalArgumentException.class, () ->
                service.cadastrarUsuario(
                        "", "anderson@email.com",
                        "Java@12345", "CLIENTE"));
    }

    @Test
    void deveRejeitarEmailInvalido() {
        assertThrows(IllegalArgumentException.class, () ->
                service.cadastrarUsuario(
                        "Anderson", "emailinvalido",
                        "Java@12345", "CLIENTE"));
    }

    @Test
    void deveRejeitarSenhaInvalida() {
        assertThrows(IllegalArgumentException.class, () ->
                service.cadastrarUsuario(
                        "Anderson", "anderson@email.com",
                        "123", "CLIENTE"));
    }

    @Test
    void deveRejeitarNivelInvalido() {
        assertThrows(IllegalArgumentException.class, () ->
                service.cadastrarUsuario(
                        "Anderson", "anderson@email.com",
                        "Java@12345", "USUARIO"));
    }

    @Test
    void deveRejeitarEmailDuplicado() {
        service.cadastrarUsuario(
                "Anderson", "anderson@email.com",
                "Java@12345", "CLIENTE");

        assertThrows(IllegalArgumentException.class, () ->
                service.cadastrarUsuario(
                        "Outro", "anderson@email.com",
                        "Java@12345", "ADMIN"));
    }

    @Test
    void deveAutenticarUsuarioComSenhaCorreta() {
        service.cadastrarUsuario(
                "Anderson", "anderson@email.com",
                "Java@12345", "ADMIN");

        Usuario usuario = service.autenticar(
                "anderson@email.com", "Java@12345");

        assertNotNull(usuario);
        assertEquals("ADMIN", usuario.getNivel());
    }

    @Test
    void deveRejeitarSenhaIncorreta() {
        service.cadastrarUsuario(
                "Anderson", "anderson@email.com",
                "Java@12345", "CLIENTE");

        assertThrows(IllegalArgumentException.class, () ->
                service.autenticar(
                        "anderson@email.com", "Errada@123"));
    }

    @Test
    void deveBloquearAposTresTentativasInvalidas() {
        service.cadastrarUsuario(
                "Anderson", "anderson@email.com",
                "Java@12345", "CLIENTE");

        for (int i = 0; i < 2; i++) {
            assertThrows(IllegalArgumentException.class, () ->
                    service.autenticar(
                            "anderson@email.com", "Errada@123"));
        }

        assertThrows(IllegalStateException.class, () ->
                service.autenticar(
                        "anderson@email.com", "Errada@123"));
    }

    @Test
    void naoDevePermitirLoginDeUsuarioBloqueado() {
        service.cadastrarUsuario(
                "Anderson", "anderson@email.com",
                "Java@12345", "CLIENTE");

        for (int i = 0; i < 3; i++) {
            try {
                service.autenticar(
                        "anderson@email.com", "Errada@123");
            } catch (IllegalArgumentException |
                     IllegalStateException e) {
                // Exceções esperadas durante as tentativas.
            }
        }

        assertThrows(IllegalStateException.class, () ->
                service.autenticar(
                        "anderson@email.com", "Java@12345"));
    }

    @Test
    void deveZerarTentativasAposLoginCorreto() {
        service.cadastrarUsuario(
                "Anderson", "anderson@email.com",
                "Java@12345", "CLIENTE");

        try {
            service.autenticar(
                    "anderson@email.com", "Errada@123");
        } catch (IllegalArgumentException e) {
            // Senha incorreta esperada.
        }

        Usuario usuario = service.autenticar(
                "anderson@email.com", "Java@12345");

        assertEquals(0, usuario.getTentativasInvalidas());
    }
}