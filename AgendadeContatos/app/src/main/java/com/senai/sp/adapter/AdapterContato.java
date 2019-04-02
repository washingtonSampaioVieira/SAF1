package com.senai.sp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.senai.sp.agendadecontatos.R;
import com.senai.sp.conversores.Imagem;
import com.senai.sp.modal.Contato;

import java.util.List;

public class AdapterContato extends BaseAdapter {
    private Context context;
    private List<Contato> contatos;

    public AdapterContato (Context context, List<Contato> contatos){
        this.contatos = contatos;
        this.context = context;
    }



    @Override
    public int getCount() {
        return contatos.size();
    }

    @Override
    public Object getItem(int position) {
        return contatos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return contatos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater =LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_lista_contatos, null);

        TextView txtNome = view.findViewById(R.id.txt_nome_contato);
        txtNome.setText(contatos.get(position).getNome());

        TextView txtTelefone = view.findViewById(R.id.txt_telefone_contato);
        txtTelefone.setText(contatos.get(position).getTelefone());

        TextView txtEmail = view.findViewById(R.id.txt_email_contato);
        txtEmail.setText(contatos.get(position).getEmail());


        ImageView imgContato = view.findViewById(R.id.img_contato);
        if(contatos.get(position).getFoto() != null){
            imgContato.setImageBitmap(Imagem.arrayToBitmap(contatos.get(position).getFoto()));
        }


        return view;
    }
}
