package com.cengcelil.takaslaoriginal.Manager.Profile;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.cengcelil.takaslaoriginal.Models.BottomSheetMapDialog;
import com.cengcelil.takaslaoriginal.Models.CategoryItem;
import com.cengcelil.takaslaoriginal.Models.Product;
import com.cengcelil.takaslaoriginal.R;
import com.cengcelil.takaslaoriginal.Utils;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;
import java.util.Map;

public class EditMyProductActivity extends AppCompatActivity {
    private static final String TAG = "EditMyProductActivity";
    private Intent intent;
    private TextView tvPrice, tvExchangerProductInner, tvPlace, tvCategory, tvDialogText;
    private EditText etPriceProduct, etTitleProduct, etDescriptionProduct;
    private ImageView statusIcon, titleIcon, descriptionIcon, priceIcon, locationIcon;
    private RadioGroup rgExchange, rgStatus;
    private MaterialButton btUpdateProduct, btDeleteProduct, btSoldProduct, btDialogYes, btDialogNo;
    private RelativeLayout rlPlaceLayout, rlStatus, rlTitle, rlDescription, rlPrice, rlPlace, rlCategory;
    private ScrollView svdetails;
    private ProgressBar progressBar;
    private NumberProgressBar numberProgressBar;
    private ImageView ivImageProduct;
    private Product product;
    private RadioButton rbNew, rbLil, rbUsed;
    private Dialog dialog;
    private CategoryItem categoryItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_product);
        intent = getIntent();
        product = intent.getParcelableExtra("product");
        Log.d(TAG, "onCreate: " + product.toString());
        setupViews();
        setListeners();
        setProductProperties();
        Glide.with(this).load(product.getUri()).into(ivImageProduct);

    }

    private void setProductProperties() {
        tvCategory.setText(product.getCategory().getName());
        if (product.getStatus().equals(rbNew.getText().toString()))
            rgStatus.check(R.id.rbNew);
        else if (product.getStatus().equals(rbLil.getText().toString()))
            rgStatus.check(R.id.rbLil);
        else if (product.getStatus().equals(rbUsed.getText().toString()))
            rgStatus.check(R.id.rbUsed);
        etTitleProduct.setText(product.getTitle());
        etDescriptionProduct.setText(product.getDescription());
        if (product.isPriceable()) {
            rgExchange.check(R.id.rb_close_for_exchange);
            etPriceProduct.setText(String.valueOf(product.getPrice()));
        } else
            rgExchange.check(R.id.rb_open_for_exchange);
        tvPlace.setText(product.getAddress());


    }

    private void setListeners() {

        rgStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                statusIcon.setVisibility(View.GONE);
                RadioButton rb = findViewById(i);
                String radioText = rb.getText().toString();
                product.setStatus(radioText);
            }
        });
        etTitleProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String title = editable.toString();
                if (title.isEmpty())
                    titleIcon.setVisibility(View.VISIBLE);
                else
                    titleIcon.setVisibility(View.GONE);

            }
        });

        etDescriptionProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String title = editable.toString();
                if (title.isEmpty())
                    descriptionIcon.setVisibility(View.VISIBLE);
                else
                    descriptionIcon.setVisibility(View.GONE);

            }
        });
        etPriceProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String title = editable.toString();
                if (title.isEmpty())
                    priceIcon.setVisibility(View.VISIBLE);
                else
                    priceIcon.setVisibility(View.GONE);

            }
        });
        rgExchange.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_close_for_exchange) {
                    //  tvPrice.setPaintFlags(tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    etPriceProduct.setBackground(getResources().getDrawable(R.drawable.details_radio_button_unselected_bg));
                    etPriceProduct.setClickable(true);
                    etPriceProduct.setEnabled(true);
                    tvPrice.setPaintFlags(tvPrice.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    tvExchangerProductInner.setText(getString(R.string.pricable));
                    product.setPriceable(true);
                    priceIcon.setVisibility(View.VISIBLE);
                } else {
                    etPriceProduct.setBackground(getResources().getDrawable(R.drawable.details_radio_button_selected_bg));
                    etPriceProduct.setClickable(false);
                    etPriceProduct.setEnabled(false);
                    etPriceProduct.setText("");
                    priceIcon.setVisibility(View.GONE);
                    tvPrice.setPaintFlags(tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    tvExchangerProductInner.setText(getString(R.string.open_for_exchange));
                    product.setPriceable(false);

                }
            }
        });
        rlPlaceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetMapDialog bottomSheetMapDialog = new BottomSheetMapDialog(getSupportFragmentManager(), tvPlace, locationIcon);
                bottomSheetMapDialog.show(getSupportFragmentManager(), "a");
            }
        });
        btDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvDialogText.setText(getString(R.string.dialog_delete_text));
                btDialogNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btDialogYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Utils.uiOff(progressBar, svdetails);
                        Utils.FIREBASE_FIRESTORE
                                .collection(getString(R.string.collection_products))
                                .document(product.getDocumentId())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Utils.STORAGE_REFERENCE
                                                .child(product.getDocumentId())
                                                .delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        dialog.dismiss();
                                                        Utils.uiOn(progressBar, svdetails);
                                                        finish();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(EditMyProductActivity.this, "Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        Utils.uiOn(progressBar, svdetails);
                                                    }
                                                });
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialog.dismiss();

                                        Toast.makeText(EditMyProductActivity.this, "Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        Utils.uiOn(progressBar, svdetails);

                                    }
                                });
                    }
                });
                dialog.show();
            }
        });
        btUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkEmptyFields()) {
                    tvDialogText.setText(getString(R.string.dialog_update_text));
                    btDialogNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    btDialogYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Utils.uiOff(progressBar, svdetails);
                            Map<String, Object> hashMap = new HashMap<>();
                            for (CategoryItem item : Utils.getCategoriesHorizontal(EditMyProductActivity.this))
                                if (item.getName().equals(tvCategory.getText().toString())) {
                                    hashMap.put("category", item);
                                    break;
                                }
                            hashMap.put("address", tvPlace.getText().toString().trim());
                            hashMap.put("description", etDescriptionProduct.getText().toString().trim());
                            if (product.isPriceable()) {
                                hashMap.put("price", Integer.valueOf(etPriceProduct.getText().toString().trim()));
                                hashMap.put("priceable", true);
                            } else
                                hashMap.put("priceable", false);


                            hashMap.put("status", product.getStatus());
                            hashMap.put("title", etTitleProduct.getText().toString().trim());
                            Utils.FIREBASE_FIRESTORE
                                    .collection(getString(R.string.collection_products))
                                    .document(product.getDocumentId())
                                    .update(hashMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            dialog.dismiss();
                                            Utils.uiOn(progressBar, svdetails);
                                            finish();
                                            Toast.makeText(EditMyProductActivity.this, "Güncellendi", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            dialog.dismiss();

                                            Utils.uiOn(progressBar, svdetails);
                                            Toast.makeText(EditMyProductActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    });
                    dialog.show();
                }
            }
        });

        btSoldProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvDialogText.setText(getString(R.string.dialog_sold_text));
                btDialogNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btDialogYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Utils.uiOff(progressBar, svdetails);
                        Utils.FIREBASE_FIRESTORE
                                .collection(getString(R.string.collection_products))
                                .document(product.getDocumentId())
                                .update("activityStatus", Utils.SOLD)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Utils.uiOn(progressBar, svdetails);
                                        Toast.makeText(EditMyProductActivity.this, "Güncellendi", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialog.dismiss();
                                        Utils.uiOn(progressBar, svdetails);
                                        Toast.makeText(EditMyProductActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
                dialog.show();
            }

        });
    }

    private void setupViews() {
        ivImageProduct = findViewById(R.id.ivImageProduct);
        tvPrice = findViewById(R.id.tvPriceProduct);
        rgExchange = findViewById(R.id.rgExchange);
        rgStatus = findViewById(R.id.details_status_rg);
        tvExchangerProductInner = findViewById(R.id.tvExchangerProductInner);
        etPriceProduct = findViewById(R.id.etPriceProduct);
        etTitleProduct = findViewById(R.id.etTitleProduct);
        etDescriptionProduct = findViewById(R.id.etDescriptionProduct);
        statusIcon = findViewById(R.id.status_icon);
        titleIcon = findViewById(R.id.title_icon);
        descriptionIcon = findViewById(R.id.description_icon);
        priceIcon = findViewById(R.id.price_icon);
        locationIcon = findViewById(R.id.location_icon);
        tvPlace = findViewById(R.id.tvPlace);
        tvCategory = findViewById(R.id.tvCategoryProduct);
        btUpdateProduct = findViewById(R.id.btUpdateProduct);
        btSoldProduct = findViewById(R.id.btSoldProduct);
        btDeleteProduct = findViewById(R.id.btDeleteProduct);
        rlPlaceLayout = findViewById(R.id.rlPlaceLayout);
        rlStatus = findViewById(R.id.details_product_status);
        rlTitle = findViewById(R.id.details_product_title);
        rlDescription = findViewById(R.id.details_product_description);
        rlPrice = findViewById(R.id.details_product_price);
        rlPlace = findViewById(R.id.details_product_place);
        rlCategory = findViewById(R.id.details_product_category);
        progressBar = findViewById(R.id.spin_kit);
        numberProgressBar = findViewById(R.id.number_progress_bar);
        svdetails = findViewById(R.id.svdetails);
        rbNew = findViewById(R.id.rbNew);
        rbLil = findViewById(R.id.rbLil);
        rbUsed = findViewById(R.id.rbUsed);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.edit_product_custom_dialog);
        btDialogNo = dialog.findViewById(R.id.customDialogNoBt);
        btDialogYes = dialog.findViewById(R.id.customDialogYesBt);
        tvDialogText = dialog.findViewById(R.id.customDialogText);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


    }

    private boolean checkEmptyFields() {
        Log.d(TAG, "checkEmptyFields: Checking empty fields...");
        if (product.getStatus() == null) {
            ObjectAnimator.ofInt(svdetails, "scrollY", svdetails.getScrollY(), (int) rlStatus.getY()).setDuration(500).start();
            return false;
        }
        if (etTitleProduct.getText().toString().trim().isEmpty()) {
            ObjectAnimator.ofInt(svdetails, "scrollY", svdetails.getScrollY(), (int) rlTitle.getY()).setDuration(500).start();
            return false;
        }
        if (etDescriptionProduct.getText().toString().trim().isEmpty()) {
            YoYo.with(Techniques.Flash)
                    .duration(700)
                    .repeat(1)
                    .playOn(rlDescription);
            ObjectAnimator.ofInt(svdetails, "scrollY", svdetails.getScrollY(), (int) rlDescription.getY()).setDuration(500).start();
            return false;
        }
        if (product.isPriceable() && etPriceProduct.getText().toString().trim().isEmpty()) {
            YoYo.with(Techniques.Flash)
                    .duration(700)
                    .repeat(1)
                    .playOn(rlPrice);
            ObjectAnimator.ofInt(svdetails, "scrollY", svdetails.getScrollY(), (int) rlPrice.getY()).setDuration(500).start();
            return false;
        }
        if (tvPlace.getText().toString().trim().equals(getString(R.string.choose_a_location))) {
            YoYo.with(Techniques.Flash)
                    .duration(700)
                    .repeat(1)
                    .playOn(rlPlace);
            ObjectAnimator.ofInt(svdetails, "scrollY", svdetails.getScrollY(), (int) rlPlace.getY()).setDuration(500).start();
            return false;
        }
        return true;
    }
}