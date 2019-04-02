package com.senai.sp.helper;

import android.media.Image;
import android.widget.EditText;
import android.widget.ImageView;

import com.senai.sp.agendadecontatos.Cadastro;
import com.senai.sp.agendadecontatos.R;
import com.senai.sp.conversores.Imagem;
import com.senai.sp.modal.Contato;

public class ContatoHelper{

    EditText nome;
    EditText endereco;
    EditText telefone;
    EditText email;
    EditText linkedin;
    Contato contato;
    ImageView fotoCadastro;


    public ContatoHelper (Cadastro activity){
        nome = activity.findViewById(R.id.edit_nome);
        endereco = activity.findViewById(R.id.edit_endereco);
        telefone = activity.findViewById(R.id.edit_telefone);
        email = activity.findViewById(R.id.edit_email);
        linkedin= activity.findViewById(R.id.edit_linkedin);
        fotoCadastro = activity.findViewById(R.id.img_foto_cadastro);



        contato = new Contato();
    }
    public Contato getContato(){


        contato.setNome(nome.getText().toString());
        contato.setEndereco(endereco.getText().toString());
        contato.setTelefone(telefone.getText().toString());
        contato.setEmail(email.getText().toString());
        contato.setLinkedin(linkedin.getText().toString());
        contato.setFoto(Imagem.BitmapToArray(fotoCadastro.getDrawable()));

        return this.contato;
    }


    public void mostarContato(Contato contato) {
        nome.setText(contato.getNome());
        endereco.setText(contato.getEndereco());
        telefone.setText(contato.getTelefone());
        email.setText(contato.getEmail());
        linkedin.setText(contato.getLinkedin());

        if(contato.getFoto() != null){
            fotoCadastro.setImageBitmap(Imagem.arrayToBitmap(contato.getFoto()));
        }

        this.contato = contato;

    }
}
