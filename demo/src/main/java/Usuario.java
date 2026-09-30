
package com.example;

public class Usuario {
    private String nome;
    private String email;
    private String senha;
    private String nivel;
    private int tentativasInvalidas;
    private boolean bloqueado;

    public Usuario(String nome, String email, String senha, String nivel) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.nivel = nivel;
        this.tentativasInvalidas = 0;
        this.bloqueado = false;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getNivel() {
        return nivel;
    }

    public int getTentativasInvalidas() {
        return tentativasInvalidas;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void incrementarTentativas() {
        tentativasInvalidas++;

        if (tentativasInvalidas >= 3) {
            bloqueado = true;
        }
    }

    public void zerarTentativas() {
        tentativasInvalidas = 0;
    }

    public void bloquear() {
        bloqueado = true;
    }
}