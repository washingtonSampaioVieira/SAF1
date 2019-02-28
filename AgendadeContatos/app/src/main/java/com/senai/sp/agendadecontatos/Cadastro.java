package com.senai.sp.agendadecontatos;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
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
    TextInputLayout layoutNome;
    TextInputLayout layoutEndereco;
    TextInputLayout layoutTelefone;
    TextInputLayout layoutEmail;

    ContatoHelper helper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        nome = findViewById(R.id.edit_nome);
        endereco = findViewById(R.id.edit_endereco);
        telefone = findViewById(R.id.edit_telefone);
        email = findViewById(R.id.edit_email);
        layoutNome = findViewById(R.id.layout_txt_nome);
        layoutEmail = findViewById(R.id.layout_txt_email);
        layoutEndereco = findViewById(R.id.layout_txt_endereco);
        layoutTelefone = findViewById(R.id.layout_txt_telefone);

        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true); // mostra o botao de voltar
        getSupportActionBar().setHomeButtonEnabled(true); // faz o botao existir
        getSupportActionBar().setTitle("Agenda De Contatos");*/




        Intent intencao = getIntent();
        Contato contato = (Contato) intencao.getSerializableExtra("contato");
        helper = new ContatoHelper(this);
        if(contato != null){

            helper.mostarContato(contato);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(validar()) {

            switch (item.getItemId()) {
                case R.id.menu_salvar:


                    ContatoDAO dao = new ContatoDAO(this);

                    Contato contato = new Contato();
                    contato = helper.getContato();

                    if (contato.getId() == 0) {
                        dao.salvar(contato);
                        Toast.makeText(this, "Salvo", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(this, String.valueOf(contato.getId()), Toast.LENGTH_SHORT).show();
                    } else {

                        dao.atualizar(contato);
                        Toast.makeText(this, "Atualizado", Toast.LENGTH_SHORT).show();
                    }



                break;

            }
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_cadastro, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean validar(){

        boolean validado = true;

        if(nome.getText().toString().isEmpty()){
            layoutNome.setErrorEnabled(true);
            layoutNome.setError("Um contato precisa de um nome!");
            validado= false;
        }else{
            layoutNome.setErrorEnabled(false);
        }
        if(endereco.getText().toString().isEmpty()) {

            layoutEndereco.setErrorEnabled(true);
            layoutEndereco.setError("O endereço é obrigatorio");
            validado= false;
        }else{
            layoutEndereco.setErrorEnabled(false);
        }

        if(telefone.getText().toString().isEmpty()){
            layoutTelefone.setErrorEnabled(true);
            layoutTelefone.setError("O telefone é obrigatorio");
            validado= false;
        }else{
            layoutTelefone.setErrorEnabled(false);
        }

        if(email.getText().toString().isEmpty()){
            layoutEmail.setErrorEnabled(true);
            layoutEmail.setError("O e E-mail é obrigatorio");
            validado= false;
        }else{
            layoutEmail.setErrorEnabled(false);
        }


        return validado;
    }






}

