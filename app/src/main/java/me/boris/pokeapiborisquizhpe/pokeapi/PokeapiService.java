package me.boris.pokeapiborisquizhpe.pokeapi;

import me.boris.pokeapiborisquizhpe.models.PokemonRespuesta;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokeapiService {

    @GET("pokemon-species")
    Call<PokemonRespuesta> obtenerListaPokemos(@Query("limit") int limit, @Query("offset") int offset);

}
