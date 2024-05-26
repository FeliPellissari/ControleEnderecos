package com.example.controle_enderecos.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.controle_enderecos.entities.Cidade;

import java.util.List;

@Dao
public interface CidadeDAO {
    @Insert
    void insert(Cidade cidade);

    @Update
    void update(Cidade cidade);

    @Delete
    void delete(Cidade cidade);

    @Query("SELECT * FROM cidade WHERE cidadeId = :id")
    Cidade getCidadeById(int id);

    @Query("SELECT * FROM cidade")
    List<Cidade> getAllCidades();
}
