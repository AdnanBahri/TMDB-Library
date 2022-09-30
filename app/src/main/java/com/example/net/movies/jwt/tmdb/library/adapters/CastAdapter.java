package com.example.net.movies.jwt.tmdb.library.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.net.movies.jwt.tmdb.library.callbacks.CastListener;
import com.example.net.movies.jwt.tmdb.library.databinding.MovieCastLayoutBinding;
import com.example.net.movies.jwt.tmdb.library.model.credits.CastItem;
import com.example.net.movies.jwt.tmdb.library.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {

    private List<CastItem> castItems;
    private CastListener listener;

    public CastAdapter(CastListener listener) {
        castItems = new ArrayList<>();
        this.listener = listener;
    }

    public void setCastItems(List<CastItem> castItems) {
        this.castItems = castItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieCastLayoutBinding binding = MovieCastLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.populateUI(castItems.get(position));
    }

    @Override
    public int getItemCount() {
        return castItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MovieCastLayoutBinding binding;

        public ViewHolder(@NonNull MovieCastLayoutBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void populateUI(CastItem item) {
            Picasso.get()
                    .load(Constants.IMAGE_BASE_URL + item.getProfilePath())
                    .into(binding.avatar);
            binding.avatar.setOnClickListener(v -> {
                Log.d("Cast Adapter", item.getName() + " , " + item.getId());
                listener.onCastClick(item, binding.getRoot());
            });
        }
    }
}
