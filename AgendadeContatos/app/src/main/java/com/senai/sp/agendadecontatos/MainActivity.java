package com.senai.sp.agendadecontatos;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.senai.sp.DAO.ContatoDAO;
import com.senai.sp.modal.Contato;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    ListView listaContatos;
    Button btnNovo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaContatos = findViewById(R.id.lista_contatos);
        btnNovo = findViewById(R.id.btn_novo);

        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chamarCadastro = new Intent(MainActivity.this , Cadastro.class);
                startActivity(chamarCadastro);
            }
        });

        listaContatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contato contato = (Contato) listaContatos.getItemAtPosition(position);
                Intent intencao = new Intent(MainActivity.this, Cadastro.class);
                intencao.putExtra("contato", contato);

                    startActivity(intencao);

            }
        });
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        carregarLista();

    }

    private void carregarLista() {
        ContatoDAO dao = new ContatoDAO(this);

        List<Contato> contatos = dao.getContatos();

        ArrayAdapter<Contato> arrayAdapterContatos = new ArrayAdapter<Contato>(this, android.R.layout.simple_list_item_1, contatos);
        listaContatos.setAdapter(arrayAdapterContatos);

    }
}
