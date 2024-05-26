package com.example.controle_enderecos.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.controle_enderecos.entities.Endereco;

import java.util.List;


@Dao
public interface EnderecoDAO {

    @Insert
    void insert(Endereco endereco);

    @Update
    void update(Endereco endereco);

    @Delete
    void delete(Endereco endereco);

    @Query("SELECT * FROM enderecos WHERE enderecoId = :id")
    Endereco getEnderecoById(int id);

    @Query("SELECT * FROM enderecos")
    List<Endereco> getAllEnderecos();
}
