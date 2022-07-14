package com.panshul.selfuel.Adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.panshul.selfuel.Fragments.MusicFragment;
import com.panshul.selfuel.Model.PlaylistModel;
import com.panshul.selfuel.R;

import java.util.ArrayList;
import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.MyViewHolder> {

    Context context;
    List<PlaylistModel> playList;
    ArrayList<Integer>randomBack =new ArrayList<>();
    MusicFragment fragment;

    public SongsAdapter(Context context, List<PlaylistModel> playList, MusicFragment fragment) {
        this.context = context;
        this.playList = playList;
        this.fragment = fragment;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView playListName,playListBy;
        ConstraintLayout layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            randomBack.add(R.drawable.gradient1);
            randomBack.add(R.drawable.gradient2);
            randomBack.add(R.drawable.gradient3);
            randomBack.add(R.drawable.gradient4);
            randomBack.add(R.drawable.gradient5);
            randomBack.add(R.drawable.gradient6);
            randomBack.add(R.drawable.gradient7);
            randomBack.add(R.drawable.gradient8);


            playListBy = itemView.findViewById(R.id.playListBy11);
            playListName = itemView.findViewById(R.id.playListName11);
            layout = itemView.findViewById(R.id.playListConstraint);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.playlist_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        PlaylistModel item = playList.get(position);
        holder.playListBy.setText(item.getPlayListBy());
        holder.playListName.setText(item.getPlayListName());
        holder.layout.setBackground(ContextCompat.getDrawable(context,randomBack.get(position%8)));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getPlayListLink()));
                    v.getContext().startActivity(intent);
                }
                catch (ActivityNotFoundException e){
                    Toast.makeText(context, "Invaild Link", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return playList.size();
    }


}
