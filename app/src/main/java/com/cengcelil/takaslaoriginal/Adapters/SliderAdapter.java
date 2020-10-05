package com.cengcelil.takaslaoriginal.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.cengcelil.takaslaoriginal.R;
import com.google.firebase.storage.StorageReference;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class SliderAdapter extends SliderViewAdapter<SliderAdapter.MyHolder> {
    private Context context;
    private List<com.google.firebase.storage.StorageReference> storageReferences;
    private StorageReference StorageReference;
    public SliderAdapter(Context context, List<StorageReference> storageReferences) {
        this.context = context;
        this.storageReferences = storageReferences;
    }


    public void renewItems(List<StorageReference> storageReferences) {
        this.storageReferences = storageReferences;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.storageReferences.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(StorageReference storageReferences) {
        this.storageReferences.add(storageReferences);
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, parent,false);
        return new MyHolder(inflate);    }

    @Override
    public void onBindViewHolder(MyHolder viewHolder, int position) {
        StorageReference storageReference = storageReferences.get(position);
        Glide.with(viewHolder.itemView)
                .load(storageReference)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(viewHolder.imageView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getCount() {
        return storageReferences.size();
    }

    public class MyHolder extends SliderViewAdapter.ViewHolder {
        ImageView imageView;
        public MyHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.slider_iv);
        }
    }
}
