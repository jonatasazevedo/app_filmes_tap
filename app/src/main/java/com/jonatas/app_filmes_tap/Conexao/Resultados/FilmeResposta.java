package com.jonatas.app_filmes_tap.Conexao.Resultados;

import com.squareup.moshi.Json;

public class FilmeResposta {

    @Json(name = "poster_path")
    private final String url;

    @Json(name = "title")
    private final String titulo;

    @Json(name = "vote_average")
    private final String nota;

    @Json(name = "overview")
    private final String sinopse;

    public FilmeResposta(String url, String titulo, String nota, String sinopse) {
        this.url = url;
        this.titulo = titulo;
        this.nota = nota;
        this.sinopse = sinopse;
    }

    public String getUrl() {
        return url;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getNota() {
        return nota;
    }

    public String getSinopse() {
        return sinopse;
    }
}
