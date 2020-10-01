package com.cengcelil.takaslaoriginal.AddProduct.Fragments;

import android.animation.ObjectAnimator;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cengcelil.takaslaoriginal.Models.BottomSheetMapDialog;
import com.cengcelil.takaslaoriginal.Models.CapturedItem;
import com.cengcelil.takaslaoriginal.Models.CategoryItem;
import com.cengcelil.takaslaoriginal.Models.Product;
import com.cengcelil.takaslaoriginal.R;
import com.cengcelil.takaslaoriginal.Utils;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class DetailsFragment extends Fragment {
    private static final String TAG = "DetailsFragment";
    private ArrayList<CapturedItem> capturedItems;
    private CategoryItem categoryItem;
    private TextView tvPrice, tvExchangerProductInner, tvPlace;
    private EditText etPriceProduct, etTitleProduct, etDescriptionProduct;
    private ImageView statusIcon, titleIcon, descriptionIcon, priceIcon, locationIcon;
    private RadioGroup rgExchange, rgStatus;
    private Product product;
    private MaterialButton btConfrirmProduct;
    private ScrollView svdetails;
    private RelativeLayout rlPlaceLayout, rlStatus, rlTitle, rlDescription, rlPrice, rlPlace;
    private ArrayList<String> imagesUriList;
    private CollectionReference productsCollectionReferenece;
    private ProgressBar progressBar;
    private NumberProgressBar numberProgressBar;
    private int count;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagesUriList = new ArrayList<>();
        productsCollectionReferenece = FirebaseFirestore.getInstance().collection(getString(R.string.collection_products));
        if (getArguments() != null) {
            capturedItems = getArguments().getParcelableArrayList("capturedItems");
            categoryItem = getArguments().getParcelable("categoryItem");
            for (CapturedItem capturedItem : capturedItems)
                imagesUriList.add(Uri.fromFile(capturedItem.getFile()).toString());
            product = new Product();
            product.setUid(FirebaseAuth.getInstance().getUid());
            product.setCategory(categoryItem);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    private void setupViews(View view) {
        tvPrice = view.findViewById(R.id.tvPriceProduct);
        rgExchange = view.findViewById(R.id.rgExchange);
        rgStatus = view.findViewById(R.id.details_status_rg);
        tvExchangerProductInner = view.findViewById(R.id.tvExchangerProductInner);
        etPriceProduct = view.findViewById(R.id.etPriceProduct);
        etTitleProduct = view.findViewById(R.id.etTitleProduct);
        etDescriptionProduct = view.findViewById(R.id.etDescriptionProduct);
        statusIcon = view.findViewById(R.id.status_icon);
        titleIcon = view.findViewById(R.id.title_icon);
        descriptionIcon = view.findViewById(R.id.description_icon);
        priceIcon = view.findViewById(R.id.price_icon);
        locationIcon = view.findViewById(R.id.location_icon);
        tvPlace = view.findViewById(R.id.tvPlace);
        btConfrirmProduct = view.findViewById(R.id.btConfirmProduct);
        svdetails = view.findViewById(R.id.details_scrollview);
        rlPlaceLayout = view.findViewById(R.id.rlPlaceLayout);
        rlStatus = view.findViewById(R.id.details_product_status);
        rlTitle = view.findViewById(R.id.details_product_title);
        rlDescription = view.findViewById(R.id.details_product_description);
        rlPrice = view.findViewById(R.id.details_product_price);
        rlPlace = view.findViewById(R.id.details_product_place);
        progressBar = view.findViewById(R.id.spin_kit);
        numberProgressBar = view.findViewById(R.id.number_progress_bar);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews(view);
        progressBar.setIndeterminateDrawable(new WanderingCubes());
        rgStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                statusIcon.setVisibility(View.GONE);
                RadioButton rb = view.findViewById(i);
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
                BottomSheetMapDialog bottomSheetMapDialog = new BottomSheetMapDialog(getFragmentManager(), tvPlace, locationIcon);
                bottomSheetMapDialog.show(getFragmentManager(), "a");
            }
        });
        btConfrirmProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick:1 " + product.toString());
                if (checkEmptyFields()) {
                    setProductClass();
                    Utils.uiOff(progressBar, svdetails);
                    numberProgressBar.setVisibility(View.VISIBLE);
                    productsCollectionReferenece.add(product).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            count = 0;
                            Log.d(TAG, "onSuccess: ");
                            for (String string : imagesUriList) {
                                uploadImages(documentReference.getId(),Uri.parse(string));
                            }
                        }
                    }).
                            addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.getMessage());
                                    Utils.uiOn(progressBar, svdetails);
                                    numberProgressBar.setVisibility(View.GONE);

                                }
                            });
                }
            }
        });

    }

    private void uploadImages(String id, Uri uri) {
        Utils.STORAGE_REFERENCE.child(id + "/" + imagesUriList.indexOf(uri.toString())).putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d(TAG, "onSuccess: " + count + " veri sunucuya aktarıldı.");
                        numberProgressBar.setProgress(count * 100 / (imagesUriList.size()));
                        count++;
                        if (count == imagesUriList.size()) {
                            Log.d(TAG, "onSuccess: finiş");
                            Utils.uiOn(progressBar, svdetails);
                            numberProgressBar.setVisibility(View.GONE);
                            getFragmentManager().beginTransaction().replace(R.id.add_product_container,new PublishedFragment(),getString(R.string.published_fragment))
                                    .addToBackStack(null)
                                    .commit();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.getMessage());
                    }
                });
    }

    private void setProductClass() {
        Log.d(TAG, "setProductClass: Setting product properties...");
        product.setTitle(etTitleProduct.getText().toString().trim());
        product.setDescription(etDescriptionProduct.getText().toString().trim());
        if (product.isPriceable())
            product.setPrice(Integer.parseInt(etPriceProduct.getText().toString().trim()));
        product.setAddress(tvPlace.getText().toString().trim());
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