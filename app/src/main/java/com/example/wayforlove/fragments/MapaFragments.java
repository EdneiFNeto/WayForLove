package com.example.wayforlove.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.wayforlove.dao.UsuarioDao;
import com.example.wayforlove.modelo.Usuario;
import com.example.wayforlove.util.ChecaPermisaoUtil;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapaFragments extends SupportMapFragment implements OnMapReadyCallback, LocationListener {

    ;
    private static final Integer CODE_1 = 100;
    public static final String MAPA_LOG = "MapaLog";
    public static final String MAP_FRAGEMT_LOG = "MapFragemtLog";
    public static final String TAG = "TAG";
    private LocationManager manager;
    private Usuario usuario;
    private GoogleMap googleMap;
    private String nome;
    private String sexo;
    private String cor;
    private String tipoFisico;
    private List<Usuario> listaDeUsuarios;
    private UsuarioDao dao;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getMapAsync(this);
        //usuario.setPocisao(new LatLng(-22.90, -43.26));
    }

    @Override
    public void onResume() {
        super.onResume();
        dao = new UsuarioDao(getContext());
        List<Usuario> usuarios = dao.selecionaUsuario();

        if(usuarios!=null){
            if(usuarios.size() > 0){
                for (Usuario usuario: usuarios){
                    Log.e(TAG, "Usuarios: "+usuario.toString());
                }
            }
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;

        //cria maapa
        CameraUpdate update  = CameraUpdateFactory.newCameraPosition(posicao);

        manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ChecaPermisaoUtil.checkPermission(getActivity()))
            return;

        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }

    private void addMarkerMapa(GoogleMap googleMap, Usuario usuario) {

        //ecebe um carcador
        MarkerOptions marker = getMarcador(usuario);
        googleMap.addMarker(marker);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(usuario.getPocisao(), 17));
    }

    private MarkerOptions getMarcador(Usuario usuario) {

        MarkerOptions marker = new MarkerOptions();
        marker.position(usuario.getPocisao());
        marker.title(usuario.getNome());
        return marker;
    }

    //marca a minha localização
    @Override
    public void onLocationChanged(Location location) {
        int speed = (int) ((location.getSpeed() * 3600) / 1000);

        if (speed > 5 || speed > 2) {
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
