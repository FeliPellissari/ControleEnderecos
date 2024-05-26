package com.example.controle_enderecos.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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

public class EdtCidadeActivity extends AppCompatActivity {

    private EditText cidadeEdtText;
    private EditText estadoEdtText;
    private Button btnSalvar;

    private AppDatabase db;
    private Cidade cidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edt_cidade);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_database")
                .allowMainThreadQueries()
                .build();

        if (getIntent().hasExtra("cidadeId")) {
            int cidadeId = getIntent().getIntExtra("cidadeId", -1);
            cidade = db.cidadeDao().getCidadeById(cidadeId);
        }

        cidadeEdtText = findViewById(R.id.edtTextCidade);
        estadoEdtText = findViewById(R.id.edtTextEstado);
        btnSalvar = findViewById(R.id.btnSalvar);

        if (cidade != null) {
            cidadeEdtText.setText(cidade.getCidade());
            estadoEdtText.setText(cidade.getEstado());
        }

        btnSalvar.setOnClickListener(v -> salvarCidade());
    }

    private void salvarCidade() {

        String cidadeNome = cidadeEdtText.getText().toString().trim();
        String estado = estadoEdtText.getText().toString().trim();


        if (cidade != null) {
            cidade.setCidade(cidadeNome);
            cidade.setEstado(estado);
            db.cidadeDao().update(cidade);
        } else {
            cidade = new Cidade(cidadeNome, estado);
            db.cidadeDao().insert(cidade);
        }


        Toast.makeText(this, "Cidade salva", Toast.LENGTH_SHORT).show();
        finish();
    }
}