package com.nhattuan.growrichs.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.nhattuan.growrichs.R;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by fifu on 05-Feb-18.
 */

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Uri> UriList;
    private UriAdapterListener listener;
    private Context context;

    public ImageAdapter(List<Uri> UriList, Context context, UriAdapterListener listener) {
        this.UriList = UriList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);
        return new UriHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Uri uri = this.UriList.get(position);
        if (holder instanceof UriHolder) {
            ((UriHolder) holder).bind(uri,position);
        }
    }

    @Override
    public int getItemCount() {
        return UriList == null ? 0 : UriList.size();
    }

    public void ChangeList(List<Uri> UriList) {
        this.UriList=UriList;
        notifyDataSetChanged();
    }

    private class UriHolder extends RecyclerView.ViewHolder {

        public  ImageView imageView;
        public ImageButton delete;
        UriHolder(final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            delete= itemView.findViewById(R.id.delete);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onClick(UriList.get(getAdapterPosition()));
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onLongClick(UriList.get(getAdapterPosition()), v);
                    return true;
                }
            });
        }

        void bind(Uri uri, final int position) {
            Picasso.with(context)
                    .load(uri)
                    .centerCrop()
                    .resize(250, 250)
                    .into(imageView);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Deleted image", Toast.LENGTH_SHORT).show();
                    UriList.remove(position);
                    ChangeList( UriList);
                }
            });
        }
    }

    public interface UriAdapterListener {
        public void onClick(Uri uri);
        public boolean onLongClick(Uri uri, View v);
    }

}

