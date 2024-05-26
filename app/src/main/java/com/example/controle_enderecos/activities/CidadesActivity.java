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

public class CidadesActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> listaCidades;
    private AppDatabase db;
    private Button btnAddCidade;
    private Button btnViewEnderecos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cidades);

        listView = findViewById(R.id.listViewCidades);
        Button btnAddCidade = findViewById(R.id.btnAddCidade);
        Button btnViewEnderecos = findViewById(R.id.btnVerEnderecos);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_database")
                .allowMainThreadQueries().build();

        List<Cidade> cidades = db.cidadeDao().getAllCidades();

        listaCidades = new ArrayList<>();
        for (Cidade cidade : cidades) {
            listaCidades.add(cidade.getCidade());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCidades);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Abre edição de cidade
                Intent intent = new Intent(CidadesActivity.this, EdtCidadeActivity.class);
                intent.putExtra("cidadeId", cidades.get(position).getCidadeId());
                startActivity(intent);
            }
        });

        btnAddCidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abre adição de cidade
                startActivity(new Intent(CidadesActivity.this, EdtCidadeActivity.class));
            }
        });

        btnViewEnderecos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abre a lista de enderecos
                startActivity(new Intent(CidadesActivity.this, EnderecosActivity.class));
            }
        });

    }
}