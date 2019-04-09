package com.senai.sp.agendadecontatos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.FileUriExposedException;
import android.provider.MediaStore;
import android.service.media.MediaBrowserService;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.senai.sp.DAO.ContatoDAO;
import com.senai.sp.helper.ContatoHelper;
import com.senai.sp.modal.Contato;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Cadastro extends AppCompatActivity {

    public static final int GALERIA_REQUEST = 1010;
    public static final int CAMERA_REQUEST = 2020;
    private EditText nome;
    private EditText endereco;
    private EditText telefone;
    private EditText email;
    private TextInputLayout layoutNome;
    private TextInputLayout layoutEndereco;
    private TextInputLayout layoutTelefone;
    private TextInputLayout layoutEmail;
    private ContatoHelper helper;
    private Button btnGaleria;
    private Button btnCamera;
    private ImageView fotoContato;
    private String caminhoDaFoto;



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

        btnGaleria = findViewById(R.id.btn_galeria);
        btnCamera = findViewById(R.id.btn_camera);
        fotoContato = findViewById(R.id.img_foto_cadastro);


        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                String nomeDoArquivo  = "/IMG_"+System.currentTimeMillis()+".jpg";

                caminhoDaFoto = getExternalFilesDir(null)+nomeDoArquivo;

                File arquivoFoto = new File(caminhoDaFoto);


                Uri fotoUri = FileProvider.getUriForFile(
                        Cadastro.this,
                        BuildConfig.APPLICATION_ID+ ".provider",
                        arquivoFoto
                );

                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);

                startActivityForResult(intentCamera, CAMERA_REQUEST);
            }
        });
        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGaleria = new Intent(Intent.ACTION_GET_CONTENT);
                intentGaleria.setType("image/*");
                startActivityForResult(intentGaleria, GALERIA_REQUEST);
            }
        });

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      if(resultCode == -1){


              try {

                  if(requestCode == GALERIA_REQUEST){
                      InputStream inputStream = getContentResolver().openInputStream(data.getData());
                      Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                      Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);

                      fotoContato.setImageBitmap(bitmapReduzido);
                  }

                  if(requestCode == CAMERA_REQUEST){
                      Bitmap bitmap = BitmapFactory.decodeFile(caminhoDaFoto);
                      Bitmap bitmap1Reduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                      fotoContato.setImageBitmap(bitmap1Reduzido);
                  }

              } catch (FileNotFoundException e) {
                  e.printStackTrace();
              }



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

