package com.jonatas.app_filmes_tap.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.jonatas.app_filmes_tap.Conexao.FilmeRetrofit;
import com.jonatas.app_filmes_tap.Conexao.Resultados.FilmeResposta;
import com.jonatas.app_filmes_tap.Conexao.Resultados.FilmeResultado;
import com.jonatas.app_filmes_tap.Model.Filme;
import com.jonatas.app_filmes_tap.Model.FilmeAdapter;
import com.jonatas.app_filmes_tap.Parser.FilmeParser;
import com.jonatas.app_filmes_tap.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tela_lista_filmes extends AppCompatActivity {
    FilmeAdapter adapter;
    List<Filme> lista;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lista = new ArrayList<>();
        setContentView(R.layout.activity_tela_lista_filmes);
        configuraRecycler();

    }
    private void puxaDados(){
        FilmeRetrofit apiService = FilmeRetrofit.retrofit.create(FilmeRetrofit.class);
        //Criando instancia do retrofit direcionado à lista de filmes
        final Call<FilmeResultado> call = apiService.getFilmesAvaliados("ea4f76ce3fe1aee422c82d5097c0f651");
        //Chamada assincrona acompanhada da API KEY para regastar os dados da API
        call.enqueue(new Callback<FilmeResultado>() {
            @Override
            public void onResponse(Call<FilmeResultado> call, Response<FilmeResultado> response) {
                if(response.isSuccessful()) adapter.setFilmes(FilmeParser.respostaParaOriginal(response.body().getResultadofilmes()));
                //adapter setado com lista da classe filme nativa
            }

            @Override
            public void onFailure(Call<FilmeResultado> call, Throwable t) {
                Toast.makeText(Tela_lista_filmes.this,"Erro ao puxar filmes",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void configuraRecycler(){
        adapter = new FilmeAdapter(this);
        recyclerView = findViewById(R.id.recyclerView);
        if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT) recyclerView.setLayoutManager(new GridLayoutManager(Tela_lista_filmes.this,3));
        else recyclerView.setLayoutManager(new GridLayoutManager(Tela_lista_filmes.this,5));
        //verifica se a tela está em orientação vertical ou horizontal e configura a quantidade de itens por linha
        //horizontal - 5 itens por linha
        //vertical - 3 itens por linha
        puxaDados();
        recyclerView.setAdapter(adapter);
    }

}
