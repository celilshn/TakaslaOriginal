package com.cengcelil.takaslaoriginal.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.cengcelil.takaslaoriginal.Manager.Products.ProductPreviewActivity;
import com.cengcelil.takaslaoriginal.Models.Product;
import com.cengcelil.takaslaoriginal.R;
import com.cengcelil.takaslaoriginal.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.ArrayList;

public class ProductsFragmentProductAdapter extends RecyclerView.Adapter<ProductsFragmentProductAdapter.MyHolder> {
    private static final String TAG = "ProductsFragmentProduct";
    private RequestOptions requestOptions;
    private CircularProgressDrawable circularProgressDrawable;

    public ProductsFragmentProductAdapter(Context context, ArrayList<Product> products) {
        this.products = products;
        this.context = context;
        circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setColorSchemeColors(R.color.colorPrimary, R.color.colorDarkYellow, R.color.colorAccent);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.setStrokeWidth(5f);
        // set all other properties as you would see fit and start it
        circularProgressDrawable.start();

        requestOptions = new RequestOptions()
                .placeholder(circularProgressDrawable)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

    }

    private ArrayList<Product> products;
    private Context context;

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.products_fragment_product_adapter_single_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product = products.get(position);
                Intent intent = new Intent(context, ProductPreviewActivity.class);
                intent.putExtra("product",product);
                context.startActivity(intent);
            }
        });
        if (products.get(position).getIsLikedFrom().size() != 0) {
            holder.likesCount.setText(String.valueOf(products.get(position).getIsLikedFrom().size()));

            if (products.get(position).getIsLikedFrom().contains(FirebaseAuth.getInstance().getUid())) {
                Log.d(TAG, "onBindViewHolder: true");
                holder.heartButton.setChecked(true);
            } else {
                Log.d(TAG, "onBindViewHolder: false");
                holder.heartButton.setChecked(false);

            }
        } else
            holder.likesCount.setVisibility(View.GONE);
        holder.heartButton.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                if (checked) {
                    holder.likesCount.setVisibility(View.VISIBLE);
                    products.get(position).getIsLikedFrom().add(FirebaseAuth.getInstance().getUid());
                } else {
                    products.get(position).getIsLikedFrom().remove(FirebaseAuth.getInstance().getUid());
                    holder.heartButton.setBtnColor(R.color.colorSoftGray);
                }
                if (products.get(position).getIsLikedFrom().size()==0)
                    holder.likesCount.setVisibility(View.GONE);

                holder.likesCount.setText(String.valueOf(products.get(position).getIsLikedFrom().size()));
                Utils.FIREBASE_FIRESTORE
                        .collection(context.getString(R.string.collection_products))
                        .document(products.get(position).getDocumentId())
                        .update("isLikedFrom", products.get(position).getIsLikedFrom())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: updated");

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: " + e.getMessage());
                            }
                        });

            }
        });


        Utils.STORAGE_REFERENCE.child(products.get(position).getDocumentId() + "/" + 0).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                products.get(position).setUri(String.valueOf(uri));
                Glide.with(context)
                        .load(String.valueOf(uri)).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.heartButton.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                        .apply(requestOptions)
                        .into(holder.imageView);
                Log.d(TAG, "onSuccess: ");

            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        RelativeLayout layout;
        ImageView imageView;
        ShineButton heartButton;
        TextView likesCount;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.itemMainLayout);
            likesCount = itemView.findViewById(R.id.likesCount);
            imageView = itemView.findViewById(R.id.itemImageview);
            heartButton = itemView.findViewById(R.id.shine);
            heartButton.init((Activity) context);


        }
    }
}
