
package com.example;

import java.util.HashMap;
import java.util.Map;

public class AutenticacaoService {

    private final Map<String, Usuario> usuarios = new HashMap<>();
    private final ValidacaoSenhaService validadorSenha =
            new ValidacaoSenhaService();

    public Usuario cadastrarUsuario(String nome, String email,
                                     String senha, String nivel) {

        if (nome == null || nome.isBlank()
                || email == null || email.isBlank()
                || senha == null || senha.isBlank()
                || nivel == null || nivel.isBlank()) {
            throw new IllegalArgumentException(
                    "Todos os campos são obrigatórios.");
        }

        if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException(
                    "E-mail inválido.");
        }

        if (!validadorSenha.validarSenha(senha)) {
            throw new IllegalArgumentException(
                    "A senha não atende aos requisitos.");
        }

        if (!nivel.equals("ADMIN")
                && !nivel.equals("GERENTE")
                && !nivel.equals("CLIENTE")) {
            throw new IllegalArgumentException(
                    "Nível de acesso inválido.");
        }

        if (usuarios.containsKey(email)) {
            throw new IllegalArgumentException(
                    "E-mail já cadastrado.");
        }

        Usuario usuario = new Usuario(nome, email, senha, nivel);
        usuarios.put(email, usuario);

        return usuario;
    }

    public Usuario autenticar(String email, String senha) {

        if (email == null || email.isBlank()
                || senha == null || senha.isBlank()) {
            throw new IllegalArgumentException(
                    "E-mail e senha são obrigatórios.");
        }

        Usuario usuario = usuarios.get(email);

        if (usuario == null) {
            throw new IllegalArgumentException(
                    "Usuário não encontrado.");
        }

        if (usuario.isBloqueado()) {
            throw new IllegalStateException(
                    "Usuário bloqueado após três tentativas.");
        }

        if (!usuario.getSenha().equals(senha)) {
            usuario.incrementarTentativas();

            if (usuario.isBloqueado()) {
                throw new IllegalStateException(
                        "Usuário bloqueado após três tentativas.");
            }

            throw new IllegalArgumentException(
                    "Senha incorreta. Tentativas: "
                    + usuario.getTentativasInvalidas());
        }

        usuario.zerarTentativas();
        return usuario;
    }
}