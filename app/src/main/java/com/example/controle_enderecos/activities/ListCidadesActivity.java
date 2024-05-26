package com.example.controle_enderecos.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.controle_enderecos.R;
import com.example.controle_enderecos.database.AppDatabase;
import com.example.controle_enderecos.entities.Cidade;

import java.util.ArrayList;
import java.util.List;

public class ListCidadesActivity extends AppCompatActivity {


    private List<String> listaCidades;
    private AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_cidades);


        ListView listView = findViewById(R.id.listViewCidades);


        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_database")
                .allowMainThreadQueries().build();

        List<Cidade> cidades = db.cidadeDao().getAllCidades();

        listaCidades = new ArrayList<>();
        for (Cidade cidade : cidades) {
            listaCidades.add(cidade.getCidade());
        }




    }
}