package com.example.controle_enderecos.database;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.controle_enderecos.dao.UsuarioDAO;
import com.example.controle_enderecos.dao.CidadeDAO;
import com.example.controle_enderecos.dao.EnderecoDAO;
import com.example.controle_enderecos.entities.Usuario;
import com.example.controle_enderecos.entities.Cidade;
import com.example.controle_enderecos.entities.Endereco;

@Database(entities = {Usuario.class, Cidade.class, Endereco.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract UsuarioDAO usuarioDao();
    public abstract CidadeDAO cidadeDao();
    public abstract EnderecoDAO enderecoDao();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
