package com.example.controle_enderecos.activities;

import android.annotation.SuppressLint;
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
import com.example.controle_enderecos.entities.Endereco;

import java.util.ArrayList;
import java.util.List;

public class EnderecoActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> listaEnderecos;
    private AppDatabase db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_endereco);


        listView = findViewById(R.id.listViewEnderecos);
        Button btnAddEndereco = findViewById(R.id.btnAddEndereco);


        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_database")
                .allowMainThreadQueries().build();

        List<Endereco> enderecos = db.enderecoDao().getAllEnderecos();

        listaEnderecos = new ArrayList<>();
        for (Endereco endereco : enderecos) {
            listaEnderecos.add(endereco.getDescricao());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaEnderecos);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Abre edição de cidade
                Intent intent = new Intent(EnderecoActivity.this, EdtEnderecoActivity.class);
                startActivity(intent);
            }
        });

        btnAddEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(EnderecoActivity.this, EdtEnderecoActivity.class));
            }
        });



    }
}