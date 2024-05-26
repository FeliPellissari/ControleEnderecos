package com.example.controle_enderecos.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

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

import java.util.List;

public class EdtEnderecoActivity extends AppCompatActivity {

    private EditText descricaoEdtText;
    private EditText latitudeEdtText;
    private EditText longitudeEdtText;
    private Spinner cidadeSpinner;
    private Button btnSalvar;
    private Button btnNovaCidade;
    private List<Cidade> listaCidades;

    private AppDatabase db;

    private Endereco endereco;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edt_endereco);


        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_database")
                .allowMainThreadQueries()
                .build();

        descricaoEdtText = findViewById(R.id.editTextDescricao);
        latitudeEdtText = findViewById(R.id.editTextLatitude);
        longitudeEdtText = findViewById(R.id.editTextLongitude);
        cidadeSpinner = findViewById((R.id.spinnerCidade));
        btnSalvar = findViewById(R.id.btnSalvar);
        btnNovaCidade = findViewById(R.id.btnNovaCidade);

        // Carrega a lista de cidades para o spinner
        listaCidades = db.cidadeDao().getAllCidades();
        ArrayAdapter<Cidade> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, listaCidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cidadeSpinner.setAdapter(adapter);

        int enderecoId = getIntent().getIntExtra("enderecoId", -1);
        if (enderecoId != -1) {
            endereco = db.enderecoDao().getEnderecoById(enderecoId);
            if (endereco != null) {
                //Preenche as views com os dados do endereço
                descricaoEdtText.setText(endereco.getDescricao());
                latitudeEdtText.setText(String.valueOf(endereco.getLatitude()));
                longitudeEdtText.setText(String.valueOf(endereco.getLongitude()));

                //Seleciona a cidade no spinner
                for (int i = 0; i < listaCidades.size(); i++) {
                    if (listaCidades.get(i).getCidadeId() == endereco.getCidadeId()) {
                        cidadeSpinner.setSelection(i);
                        break;
                    }
                }
            }
        }

        btnSalvar.setOnClickListener(v -> salvarEndereco());

        btnNovaCidade.setOnClickListener(v -> {
            Intent intent = new Intent(EdtEnderecoActivity.this, EdtCidadeActivity.class);
            startActivity(intent);
        });

    }


    private void salvarEndereco() {

        String descricao = descricaoEdtText.getText().toString().trim();
        String latitudeStr = latitudeEdtText.getText().toString().trim();
        String longitudeStr = longitudeEdtText.getText().toString().trim();
        Cidade cidadeSelecionada = (Cidade) cidadeSpinner.getSelectedItem();

        // Valida os campos
        if (descricao.isEmpty()) {
            descricaoEdtText.setError("Descrição não pode estar vazia");
            return;
        }
        if (latitudeStr.isEmpty()) {
            latitudeEdtText.setError("Latitude não pode estar vazia");
            return;
        }
        if (longitudeStr.isEmpty()) {
            longitudeEdtText.setError("Longitude não pode estar vazia");
            return;
        }

        double latitude = Double.parseDouble(latitudeStr);
        double longitude = Double.parseDouble(longitudeStr);

        //Atualiza os dados do endereço (se existir) ou cria um novo endereço
        if (endereco != null) {
            endereco.setDescricao(descricao);
            endereco.setLatitude(latitude);
            endereco.setLongitude(longitude);
            endereco.setCidadeId(cidadeSelecionada.getCidadeId());
            db.enderecoDao().update(endereco);
        } else {
            endereco = new Endereco(descricao, latitude, longitude, cidadeSelecionada.getCidadeId());
            db.enderecoDao().insert(endereco);
        }

        Toast.makeText(this, "Endereço salvo", Toast.LENGTH_SHORT).show();
        finish();
    }
}