package com.senai.sp.agendadecontatos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.senai.sp.DAO.ContatoDAO;
import com.senai.sp.helper.ContatoHelper;
import com.senai.sp.modal.Contato;

public class Cadastro extends AppCompatActivity {

    EditText nome;
    EditText endereco;
    EditText telefone;
    EditText email;
    EditText linkedin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_salvar:

                ContatoHelper helper = new ContatoHelper(this);
                Contato contato = new Contato();
                contato = helper.getContato();

                ContatoDAO dao = new ContatoDAO(this);
                dao.salvar(contato);
                Toast.makeText(this, "Salvo", Toast.LENGTH_SHORT).show();


                break;
        }
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_cadastro, menu);

        return super.onCreateOptionsMenu(menu);
    }
}

