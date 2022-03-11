package me.boris.pokeapiborisquizhpe.pokeapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import me.boris.pokeapiborisquizhpe.R;
import me.boris.pokeapiborisquizhpe.models.Pokemon;

import java.util.ArrayList;

public class ListaPokemonAdapter extends RecyclerView.Adapter<ListaPokemonAdapter.ViewHolder> {

    private ArrayList<Pokemon> dataset;
    private Context context;

    public ListaPokemonAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon pokemon = dataset.get(position);
        holder.nombreTextView.setText(pokemon.getName());

        Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemon
                        .getNumber() + ".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.fotoImageView);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addicionarListPokemon(ArrayList<Pokemon> pokemonArrayList) {
        dataset.addAll(pokemonArrayList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView fotoImageView;
        private TextView nombreTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            fotoImageView = itemView.findViewById(R.id.imageImageView);
            nombreTextView = itemView.findViewById(R.id.nameTextView);
        }
    }
}
