package com.senai.sp.agendadecontatos;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.senai.sp.DAO.ContatoDAO;
import com.senai.sp.adapter.AdapterContato;
import com.senai.sp.modal.Contato;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    ListView listaContatos;
    Button btnNovo;
    AlertDialog alerta;


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

        registerForContextMenu(listaContatos);

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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_contexto_excluir, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override

        public boolean onContextItemSelected(MenuItem item) {

        final ContatoDAO dao = new ContatoDAO(this);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Contato contato = (Contato) listaContatos.getItemAtPosition(info.position);

        if(item.getItemId() == R.id.menu_excluir){
            //Cria o gerador do AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //define o titulo
            builder.setTitle("Excluir");
            //define a mensagem
            builder.setMessage("Deseja o Contato " + '"' + contato.getNome() + '"' + "?");
            //define um botão como Sim

            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    dao.excluir(contato);
                    carregarLista();
                    Toast.makeText(MainActivity.this, "Registro excluido", Toast.LENGTH_SHORT).show();


                }
            });
            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {

                    Toast.makeText(MainActivity.this, "Registro não excluido", Toast.LENGTH_SHORT).show();
                }
            });
            //define um botão como negativo.
            //cria o AlertDialog
            alerta = builder.create();
            //Exibe
            alerta.show();
        }else if(item.getItemId() == R.id.menu_ligar){
            ligarParaTelefone(contato.getTelefone());
        }else if(item.getItemId() == R.id.menu_email){
            enviarEmail(contato.getEmail());
        }



        return true;

    }
    public void ligarParaTelefone(String telefone){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + telefone));
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }else{
            Toast.makeText(this, "IMPOSSIBILITADO DE FAZER CHAMADAS", Toast.LENGTH_SHORT).show();
        }
    }
    public void enviarEmail (String email){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, email);
//        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        intent.putExtra(Intent.EXTRA_TEXT, " ");

        startActivity(Intent.createChooser(intent, "Enviar Email"));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }else{
            Toast.makeText(this, "NÃO FOI POSSIVEL ENVIAR E-MAIL", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        carregarLista();
    }

    private void carregarLista() {
        ContatoDAO dao = new ContatoDAO(this);

        List<Contato> contatos = dao.getContatos();
        dao.close();
        AdapterContato adapterContato = new AdapterContato(this, contatos);

        listaContatos.setAdapter(adapterContato);

    }
}
