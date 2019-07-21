package com.example.mymovie.server;

import com.example.mymovie.model.MoviesResponse;
import com.example.mymovie.model.PersonResponse;
import com.example.mymovie.model.movie_cast.CastAndCrew;
import com.example.mymovie.model.trailer.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {
    //https://api.themoviedb.org/3/search/movie?api_key=dd104acf25822bb6442481f4cde05a64&query=MOVIE_NAME
    //https://api.themoviedb.org/3/search/person?api_key=dd104acf25822bb6442481f4cde05a64&query=ACTOR_NAME

    String BASE_URL = "https://api.themoviedb.org/3/";

    //Create service to get results and parse results => model
    @GET("search/movie")
    Call<MoviesResponse> getMoviesByQuery(@Query("api_key") String api_key, @Query("query") String query);

    //service for person response
    @GET("search/query")
    Call<MoviesResponse> getPersonsByQuery(@Query("api_key") String api_key, @Query("query") String query);

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String api_key);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String api_key);

    @GET("movie/upcoming")
    Call<MoviesResponse> getUpComingMovies(@Query("api_key") String api_key);

    @GET("movie/{movie_id}/credits")
    Call<CastAndCrew> getCastsByMovieId(@Path("movie_id") int id, @Query("api_key") String api_key);

    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getMovieTrailer(@Path("movie_id") int id, @Query("api_key") String api_key);

}
