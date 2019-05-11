package com.example.wayforlove.fragments;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.wayforlove.dao.UsuarioDao;
import com.example.wayforlove.modelo.Usuario;
import com.example.wayforlove.util.ChecaPermisaoUtil;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapaFragments extends SupportMapFragment implements OnMapReadyCallback, LocationListener {

    ;
    private static final Integer CODE_1 = 100;
    public static final String MAPA_LOG = "MapaLog";
    public static final String MAP_FRAGEMT_LOG = "MapFragemtLog";
    public static final String TAG = "MapLog";
    public static final String RIO_DE_JANEIRO = "Rio de Janeiro";
    public static final int ZOOM = 12;
    private LocationManager manager;
    private Usuario usuario;
    private GoogleMap googleMap;
    private String nome;
    private String sexo;
    private String cor;
    private String tipoFisico;
    private List<Usuario> listaDeUsuarios;
    private UsuarioDao dao;
    private List<Usuario> listaDeUsuario;
    private boolean isAdded;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        dao = new UsuarioDao(getContext());
        listaDeUsuario = dao.selecionaUsuario();

        //recupera os dados via service
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;

        try {

            Address location = getAddress(RIO_DE_JANEIRO);

            if (location != null) {
                CameraUpdate update = CameraUpdateFactory
                        .newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), ZOOM);
                googleMap.moveCamera(update);

            } else {
                Log.e(TAG, "Location null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ChecaPermisaoUtil.checkPermission(getActivity()))
            return;

        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }

    //retorna o as coordenadas do endereco
    private Address getAddress(String address) throws IOException {

        Geocoder geocoder = new Geocoder(getContext());
        List<Address> listAdress = geocoder.getFromLocationName(address, 1);
        Address location = null;

        if (listAdress != null) {
            location = listAdress.get(0);
            return location;
        }

        return null;
    }

    //criar marker
    private MarkerOptions criarMarker(LatLng latLng) {

        MarkerOptions marker = new MarkerOptions();
        marker.position(latLng);
        if (isExisteUsuarioNaLista()) {
            for (Usuario usuario : listaDeUsuario)
                marker.title(usuario.getNome());

            return marker;
        }
        return null;
    }

    private void addMarkerMapa(GoogleMap googleMap, LatLng location) {

        MarkerOptions marker = criarMarker(location);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(ZOOM);
        CameraUpdate update = CameraUpdateFactory.newLatLng(location);
        googleMap.addMarker(marker);
        //googleMap.moveCamera(update);
        //googleMap.animateCamera(zoom);
    }

    private boolean isExisteUsuarioNaLista() {

        if (listaDeUsuario != null) {
            if (listaDeUsuario.size() > 0) {
                return true;
            }
        }

        return false;
    }

    //marca a minha localização
    @Override
    public void onLocationChanged(final Location location) {

        int speed = (int) ((location.getSpeed() * 3600) / 1000);

        //if (speed > 5 || speed > 2) {
            //verifica se exite usuaio

            //Enviar as coorenadas para o firebase
            //if (isExisteUsuarioNaLista())
              //  addMarkerMapa(googleMap, new LatLng(location.getLatitude(), location.getLongitude()));

        //}
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
