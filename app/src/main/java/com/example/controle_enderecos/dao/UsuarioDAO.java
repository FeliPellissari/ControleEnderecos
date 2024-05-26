package com.example.controle_enderecos.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.controle_enderecos.entities.Usuario;

import java.util.List;


@Dao
public interface UsuarioDAO {

    @Insert
    void insert(Usuario usuario);

    @Update
    void update(Usuario usuario);

    @Delete
    void delete(Usuario usuario);

    @Query("SELECT * FROM usuario WHERE usuarioId = :id")
    Usuario getUsuarioById(int id);

    @Query("SELECT * FROM usuario")
    List<Usuario> getAllUsuarios();
}
