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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapaFragments extends SupportMapFragment implements OnMapReadyCallback, LocationListener {

    ;
    private static final Integer CODE_1 = 100;
    public static final String MAPA_LOG = "MapaLog";
    public static final String MAP_FRAGEMT_LOG = "MapFragemtLog";
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

        dao = new UsuarioDao();
        listaDeUsuarios = dao.lista();
        //Log.e(MAP_FRAGEMT_LOG, listaDeUsuarios.toString());
        //getUsuarios(listaDeUsuarios);
    }

    private void getUsuarios(List<Usuario> listaDeUsuarios) {

        if(listaDeUsuarios != null){
            if(listaDeUsuarios.size() > 0){

                nome = listaDeUsuarios.get(0).getNome();
                sexo = listaDeUsuarios.get(0).getSexo();
                cor = listaDeUsuarios.get(0).getCor();
                tipoFisico = listaDeUsuarios.get(0).getTipoFisico();
            }
        }else{
            Log.e(MAP_FRAGEMT_LOG, "listaDeUsuarios null");
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;

        if (checkPermission())
            return;

        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        //add marker no mapa
        //addMarkerMapa(googleMap, new LatLng(-22.90, -43.26));
    }

    private void addMarkerMapa(GoogleMap googleMap, LatLng posicao) {

        //ecebe um carcador
        MarkerOptions marker = getMarcador(posicao);
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

    private MarkerOptions getMarcador( LatLng latLng) {


        MarkerOptions marker = new MarkerOptions();
        marker.position(latLng);
        marker.title("Ednei");
        //marker.snippet("Sexo: "+usuario.lista().get(index).getSexo());

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
