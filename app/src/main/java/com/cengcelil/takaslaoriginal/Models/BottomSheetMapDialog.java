package com.cengcelil.takaslaoriginal.Models;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;

import com.cengcelil.takaslaoriginal.R;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.maps.CameraUpdateFactory;
import com.google.android.libraries.maps.GoogleMap;
import com.google.android.libraries.maps.MapView;
import com.google.android.libraries.maps.OnMapReadyCallback;
import com.google.android.libraries.maps.model.LatLng;
import com.google.android.libraries.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Locale;


public class BottomSheetMapDialog extends BottomSheetDialogFragment implements OnMapReadyCallback {
    private static final String TAG = "BottomSheetDialog";
    MapView mapView;
    FragmentManager fragmentManager;
    private GoogleMap googleMap;

    public BottomSheetMapDialog(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.details_bottom_sheet_map_dialog, container, false);
        v.setBackground(getActivity().getDrawable(R.drawable.details_bottom_sheet_top_bar_background));
        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        if (!Places.isInitialized()) {
            Places.initialize(getActivity().getApplicationContext(), getString(R.string.api_key), Locale.US);
        }
        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                fragmentManager.findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS_COMPONENTS));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NotNull Place place) {
                // TODO: Get info about the selected place.
                AddPlace(place, 1);
                Log.i(TAG, "Place: " + place.getLatLng().latitude + ", " + place.getLatLng().longitude);
            }


            @Override
            public void onError(@NotNull Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });


        return v;
    }

    public void AddPlace(Place place, int pno) {
        try {
            if (googleMap == null) {
                Toast.makeText(getActivity(), "Please check your API key for Google Places SDK and your internet conneciton", Toast.LENGTH_LONG).show();
            } else {
                googleMap.clear();
                LatLng latLng = new LatLng(place.getLatLng().latitude,place.getLatLng().longitude);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,
                        15));

                googleMap.addMarker(new MarkerOptions().position(latLng)
                        .title("Name:" + place.getName() + ". Address:" + place.getAddress()));


            }
        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Error:" + ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}