package com.jonatas.app_filmes_tap.Conexao.Resultados;

import com.squareup.moshi.Json;

import java.util.List;

public class FilmeResultado {

    //Conversão dos dados com o direcionamento do nome do atributo vindo da API
    @Json(name = "results")
    private final List<FilmeResposta> listaFilmes;

    public FilmeResultado(List<FilmeResposta> listaFilmes) {
        this.listaFilmes = listaFilmes;
    }

    public List<FilmeResposta> getResultadofilmes(){
         return listaFilmes;
    }

}
