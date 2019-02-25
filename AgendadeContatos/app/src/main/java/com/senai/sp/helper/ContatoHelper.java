package com.senai.sp.helper;

import android.widget.EditText;

import com.senai.sp.agendadecontatos.Cadastro;
import com.senai.sp.agendadecontatos.R;
import com.senai.sp.modal.Contato;

public class ContatoHelper{

    EditText nome;
    EditText endereco;
    EditText telefone;
    EditText email;
    EditText linkedin;
    Contato contato;


    public ContatoHelper (Cadastro activity){
        nome = activity.findViewById(R.id.edit_nome);
        endereco = activity.findViewById(R.id.edit_endereco);
        telefone = activity.findViewById(R.id.edit_telefone);
        email = activity.findViewById(R.id.edit_email);
        linkedin= activity.findViewById(R.id.edit_linkedin);


        contato = new Contato();
    }
    public Contato getContato(){
        Contato contato = new Contato();

        contato.setNome(nome.getText().toString());
        contato.setEndereco(endereco.getText().toString());
        contato.setTelefone(telefone.getText().toString());
        contato.setEmail(email.getText().toString());
        contato.setLinkedin(linkedin.getText().toString());

        return contato;
    }


}
