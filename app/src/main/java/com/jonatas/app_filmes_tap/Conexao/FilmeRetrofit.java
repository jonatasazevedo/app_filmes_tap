package com.jonatas.app_filmes_tap.Conexao;

import com.jonatas.app_filmes_tap.Conexao.Resultados.FilmeResultado;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FilmeRetrofit {
    @GET("movie/top_rated")
    Call<FilmeResultado> getFilmesAvaliados(@Query("api_key")String chaveAPI,@Query("language")String language);
    //retorna-se um responsebody passando a chaveAPI para a Query String que o MovieDB necessita

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build();
}
