package com.example.wayforlove.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.wayforlove.R;
import com.example.wayforlove.modelo.Usuario;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaFragments extends SupportMapFragment implements OnMapReadyCallback, LocationListener {


    public static final String SEXO = "Sexo ";
    public static final String TIPO_FISICO = "Tipo fisico ";
    public static final String COR = "Cor ";
    private static final Integer CODE_1 = 100;
    public static final String MAPA_LOG = "MapaLog";
    private LocationManager manager;
    private Usuario usuario;
    private GoogleMap googleMap;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getMapAsync(this);

        usuario = new Usuario();
        usuario.setNome("Ednei F Neto");
        usuario.setSexo("M");
        usuario.setTipoFisico("Sarado");
        usuario.setCor("Negro");
        usuario.setPocisao(new LatLng(-22.90, -43.26));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;

        if (checkPermission())
            return;

        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        LatLng posicao = usuario.getPocisao();

        if (posicao != null) {
            addMarkerMapa(googleMap, posicao);

        } else {
            Log.e(MAPA_LOG, "Posicao null" + usuario.getPocisao());
        }
    }

    private void addMarkerMapa(GoogleMap googleMap, LatLng posicao) {
        MarkerOptions marker = getMarcador(usuario);
        googleMap.addMarker(marker);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicao, 17));
    }

    private boolean checkPermission() {
        manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return true;
        }
        return false;
    }

    private MarkerOptions getMarcador(Usuario usuario) {

        MarkerOptions marker = new MarkerOptions();
        marker.position(usuario.getPocisao());
        marker.title(usuario.getNome());
        //marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin));
        marker.snippet(SEXO + usuario.getSexo() + "\n" + TIPO_FISICO + usuario.getTipoFisico() + "\n" + COR + usuario.getCor());

        return marker;
    }

    //marca a minha localização
    @Override
    public void onLocationChanged(Location location) {
        int speed = (int) ((location.getSpeed() * 3600) / 1000);

        if (speed > 5 || speed > 3) {
            try {
                addMarkerMapa(googleMap, new LatLng(location.getLatitude(), location.getLongitude()));
                Toast.makeText(getContext(), "Mudou localizacao: " + location.getLatitude() + "\n" + location.getLongitude(),
                        Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getContext(), "Erro locationChanged: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
