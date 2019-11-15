package com.jonatas.app_filmes_tap.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jonatas.app_filmes_tap.Conexao.FilmeRetrofit;
import com.jonatas.app_filmes_tap.Conexao.Resultados.FilmeResultado;
import com.jonatas.app_filmes_tap.Model.FilmeAdapter;
import com.jonatas.app_filmes_tap.Model.FilmeDAO;
import com.jonatas.app_filmes_tap.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tela_lista_filmes extends AppCompatActivity {
    private FilmeAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_lista_filmes);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        configuraRecycler();

    }
    private void puxaDados(){
        FilmeDAO filmeDAO = new FilmeDAO(this);
        if(filmeDAO.bancoIsEmpty()) conectaAPI(filmeDAO);
        else adapter.setFilmes(filmeDAO.retornarTodos());
        //verifica se o banco está vazio, caso esteja vazio ele irá puxar os dados da API e salvar no banco para o funcionamento offline
        //se nao estiver vazio apenas puxa os dados do banco
        adapter.setFilmes(filmeDAO.retornarTodos());
    }

    private void conectaAPI(final FilmeDAO filmeDAO){
        progressBar.setVisibility(View.VISIBLE);
        FilmeRetrofit apiService = FilmeRetrofit.retrofit.create(FilmeRetrofit.class);
        //Criando instancia do retrofit direcionado à lista de filmes
        final Call<FilmeResultado> call = apiService.getFilmesAvaliados("ea4f76ce3fe1aee422c82d5097c0f651");
        //Chamada assincrona acompanhada da API KEY para regastar os dados da API
        call.enqueue(new Callback<FilmeResultado>() {
            @Override
            public void onResponse(Call<FilmeResultado> call, Response<FilmeResultado> response) {
                if(response.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    filmeDAO.salvar(response.body().getResultadofilmes());
                    adapter.setFilmes(filmeDAO.retornarTodos());
                    //puxa da API os dados, salva no banco e configura o adapter com a nova lista
                }
            }

            @Override
            public void onFailure(Call<FilmeResultado> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(Tela_lista_filmes.this,"Erro ao obter os filmes",Toast.LENGTH_SHORT).show();
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
