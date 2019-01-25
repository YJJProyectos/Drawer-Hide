package com.example.jyang.navigationdrawer;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener{

    private View viewRootView;
    private MapView mapView;
    private GoogleMap gMap;
    private FloatingActionButton fab;
    private Geocoder geocoder;
    private Address address;
    private List<Address> addresses;
    private MarkerOptions marker;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        checkGPSEnable();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewRootView = inflater.inflate(R.layout.map_fragment, container, false);
        return viewRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) viewRootView.findViewById(R.id.map);
        if ( mapView != null ) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

        checkGPSEnable();
    }

    private void checkGPSEnable() {
        try {
            int gpsSignal = Settings.Secure.getInt(getActivity().getContentResolver(), Settings.Secure.LOCATION_MODE);
            if (gpsSignal == 0) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }
/*
    private void showInfoAlert() {
        new AlertDialog.Builder(getContext())
                .s
    }*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        LatLng place = new LatLng(37, -5);

        marker = new MarkerOptions();
        marker.draggable(true);
        marker.position(place);
        marker.title("Mi marcador");
        marker.snippet("Esto es un texto comentario de datos");
        marker.icon(BitmapDescriptorFactory.fromResource(android.R.drawable.star_on));

        //gMap.addMarker(new MarkerOptions().position(place).title("titulo marcador").draggable(true));
        gMap.addMarker(marker);

        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        gMap.moveCamera(CameraUpdateFactory.newLatLng(place));
        gMap.setMinZoomPreference(7);
        gMap.setMaxZoomPreference(25);
        gMap.animateCamera(zoom);
        gMap.setOnMarkerDragListener(this);

        geocoder = new Geocoder(getContext(), Locale.getDefault());
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        marker.hideInfoWindow();
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        double latitude = marker.getPosition().latitude;
        double longitud = marker.getPosition().longitude;
        try {
            addresses = geocoder.getFromLocation(latitude, longitud, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String direccion = addresses.get(0).getAddressLine(0);
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryCode();
        String postalCode = addresses.get(0).getPostalCode();
        marker.setSnippet(
                "direccion " + direccion + "\n" +
                "city " + city + "\n" +
                "state " + state + "\n" +
                "country " + country + "\n" +
                "postalCode " + postalCode );
        marker.showInfoWindow();
/*
        Toast.makeText(getContext(),
                "direccion " + direccion + "\n" +
                "city " + city + "\n" +
                "state " + state + "\n" +
                "country " + country + "\n" +
                "postalCode " + postalCode + "\n"
                , Toast.LENGTH_LONG).show();*/
    }
}
