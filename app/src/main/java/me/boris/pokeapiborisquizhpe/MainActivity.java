package me.boris.pokeapiborisquizhpe;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.boris.pokeapiborisquizhpe.models.Pokemon;
import me.boris.pokeapiborisquizhpe.models.PokemonRespuesta;
import me.boris.pokeapiborisquizhpe.pokeapi.ListaPokemonAdapter;
import me.boris.pokeapiborisquizhpe.pokeapi.PokeapiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "POKEDEX";
    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private ListaPokemonAdapter listaPokemonAdapter;
    private int offset;
    private boolean aptoParaCargar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaPokemonAdapter = new ListaPokemonAdapter(this);
        recyclerView = findViewById(R.id.listRecycletView);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listaPokemonAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (aptoParaCargar) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i(TAG, " Llegamos al final.");
                            aptoParaCargar = false;
                            offset += 20;
                            obtenerDatos(offset);
                        }
                    }
                }
            }
        });


        retrofit = new Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        aptoParaCargar = true;
        offset = 0;
        obtenerDatos(offset);
    }

    private void obtenerDatos(int offset) {
        PokeapiService pokeapiService = retrofit.create(PokeapiService.class);
        Call<PokemonRespuesta> pokemonRespuestaCall = pokeapiService.obtenerListaPokemos(20, offset);
        pokemonRespuestaCall.enqueue(new Callback<PokemonRespuesta>() {
            @Override
            public void onResponse(Call<PokemonRespuesta> call, Response<PokemonRespuesta> response) {
                aptoParaCargar = true;
                if (response.isSuccessful()) {
                    PokemonRespuesta pokemonRespuesta = response.body();
                    ArrayList<Pokemon> pokemonArrayList = pokemonRespuesta.getResults();
                    listaPokemonAdapter.addicionarListPokemon(pokemonArrayList);
                } else {
                    Log.d(TAG, "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonRespuesta> call, Throwable t) {
                aptoParaCargar = true;
                Log.d(TAG, "onFailure" + t.getMessage());
                System.out.println(t.getMessage());
            }
        });


    }
}