package com.jonatas.app_filmes_tap.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jonatas.app_filmes_tap.Model.FilmeDAO;
import com.jonatas.app_filmes_tap.Model.FilmeTransition;
import com.jonatas.app_filmes_tap.R;

import java.io.ByteArrayInputStream;

public class Tela_detalhes_filmes extends AppCompatActivity {
    private TextView sinopse,nota,titulo;
    private ImageView imagemDoFilme;
    private FloatingActionButton fab;
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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilmeDAO filmeDAO = new FilmeDAO(Tela_detalhes_filmes.this);
                if(filmeTransition.getFavorito()==1){
                    fab.setImageResource(R.drawable.ic_star_bord);
                    filmeDAO.atualiza(filmeTransition.getId(),0);
                    filmeTransition.setFavorito(0);
                    Toast.makeText(Tela_detalhes_filmes.this, "Filme desfavoritado!", Toast.LENGTH_SHORT).show();
                    //Troca a imagem do FloatingActionButton para estrela vazia e atualiza no banco e localmente
                }
                else{
                    fab.setImageResource(R.drawable.ic_star);
                    filmeDAO.atualiza(filmeTransition.getId(),1);
                    filmeTransition.setFavorito(1);
                    Toast.makeText(Tela_detalhes_filmes.this, "Filme favoritado!", Toast.LENGTH_SHORT).show();
                    //Troca a imagem do FloatingActionButton para estrela cheia e atualiza no banco e localmente
                }
            }
        });
    }

    private void setDados(){
        sinopse.setText("Sinopse:"+filmeTransition.getSinopse());
        titulo.setText(filmeTransition.getTitulo());
        nota.setText("Nota: "+filmeTransition.getNota());
        imagemDoFilme.setImageBitmap(imgBitmap);
        if(filmeTransition.getFavorito()==0) fab.setImageResource(R.drawable.ic_star_bord);
        else fab.setImageResource(R.drawable.ic_star);
    }


    private void getComponents(){
        sinopse = findViewById(R.id.id_sinopse);
        nota = findViewById(R.id.id_nota);
        titulo = findViewById(R.id.id_titulo);
        imagemDoFilme = findViewById(R.id.id_img_filme);
        fab = findViewById(R.id.fab);
        imgBitmap = converterArrayBytesParaBitmap(imgBytes);
    }

    private Bitmap converterArrayBytesParaBitmap(byte[] imagemByte){
        ByteArrayInputStream imagemstream = new ByteArrayInputStream(imagemByte);
        Bitmap imagemBitmap = BitmapFactory.decodeStream(imagemstream);
        //Usa-se stream para converter o array de bites que representa uma imagem JPEG em um bitmap para colocar no imageview
        return imagemBitmap;
    }
}
