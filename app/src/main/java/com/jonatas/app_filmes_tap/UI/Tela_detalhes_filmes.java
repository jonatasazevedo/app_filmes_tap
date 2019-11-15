package com.jonatas.app_filmes_tap.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.jonatas.app_filmes_tap.Model.FilmeTransition;
import com.jonatas.app_filmes_tap.R;

import java.io.ByteArrayInputStream;

public class Tela_detalhes_filmes extends AppCompatActivity {
    private TextView sinopse,nota,titulo;
    private ImageView imagemDoFilme;
    private FilmeTransition filmeTransition;
    private byte[] imgBytes;
    private Bitmap imgBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_detalhes_filmes);
        Intent it = getIntent();
        filmeTransition = (FilmeTransition) it.getSerializableExtra("filme");
        imgBytes = it.getByteArrayExtra("imagem");
        getComponents();
        setDados();
    }

    private void setDados(){
        sinopse.setText("Sinopse:"+filmeTransition.getSinopse());
        titulo.setText(filmeTransition.getTitulo());
        nota.setText("Nota: "+filmeTransition.getNota());
        imagemDoFilme.setImageBitmap(imgBitmap);
    }


    private void getComponents(){
        sinopse = findViewById(R.id.id_sinopse);
        nota = findViewById(R.id.id_nota);
        titulo = findViewById(R.id.id_titulo);
        imagemDoFilme = findViewById(R.id.id_img_filme);
        imgBitmap = converterArrayBytesParaBitmap(imgBytes);
    }

    private Bitmap converterArrayBytesParaBitmap(byte[] imagemByte){
        ByteArrayInputStream imagemstream = new ByteArrayInputStream(imagemByte);
        Bitmap imagemBitmap = BitmapFactory.decodeStream(imagemstream);
        //Usa-se stream para converter o array de bites que representa uma imagem JPEG em um bitmap para colocar no imageview
        return imagemBitmap;
    }
}
