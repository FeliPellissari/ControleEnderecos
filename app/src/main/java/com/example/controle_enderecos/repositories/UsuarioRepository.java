package com.example.controle_enderecos.repositories;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.controle_enderecos.dao.UsuarioDAO;
import com.example.controle_enderecos.database.AppDatabase;
import com.example.controle_enderecos.entities.Usuario;

public class UsuarioRepository {
    private UsuarioDAO usuarioDAO;

    public UsuarioRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        usuarioDAO = db.usuarioDao();
    }

    public Usuario login(String email, String senha) {
        return usuarioDAO.getUsuarioByEmailAndSenha(email, senha);
    }
}
