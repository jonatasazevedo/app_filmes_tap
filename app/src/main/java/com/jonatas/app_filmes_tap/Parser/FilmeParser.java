package com.jonatas.app_filmes_tap.Parser;

import android.util.Log;

import com.jonatas.app_filmes_tap.Conexao.Resultados.FilmeResposta;
import com.jonatas.app_filmes_tap.Model.Filme;

import java.util.ArrayList;
import java.util.List;

public class FilmeParser {
    public static List<Filme> respostaParaOriginal(List<FilmeResposta> lista){
        List<Filme> filmes= new ArrayList<>();
        for(FilmeResposta filmeResposta: lista){
            Filme filme = new Filme();
            filme.setTitulo(filmeResposta.getTitulo());
            filme.setSinopse(filmeResposta.getSinopse());
            filme.setNota(filmeResposta.getNota());
            filme.setUrl(filmeResposta.getUrl());
            filmes.add(filme);
        }
        //Conversão da Classe filme vinda da conversão JSON
        //para a classe filme nativa
        Log.i("hello",String.valueOf(filmes.size()));
        return filmes;
    }
}
