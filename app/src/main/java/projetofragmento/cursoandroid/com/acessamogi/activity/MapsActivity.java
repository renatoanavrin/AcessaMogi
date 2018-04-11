package projetofragmento.cursoandroid.com.acessamogi.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import projetofragmento.cursoandroid.com.acessamogi.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    final MarkerOptions markerOptions = new MarkerOptions();
    private FusedLocationProviderClient mFusedLocationClient;
    private double ultimaLatitude = -34;
    private double ultimaLongitude = 151;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
            return;
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
/*
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //LatLng ultimaLocalizacao = new LatLng(40.740637, -74.002039);
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object

                            ultimaLongitude = location.getLongitude();
                            ultimaLatitude = location.getLatitude();
                           // LatLng ultimaLocalizacao = new LatLng(location.getLatitude(), location.getLongitude());
                            LatLng ultimaLocalizacao = new LatLng(location.getLatitude(), location.getLongitude());
                            final CameraPosition position = new CameraPosition.Builder()
                                    .target(ultimaLocalizacao)     //  Localização
                                    .bearing(45)        //  Rotação da câmera
                                    .tilt(0)             //  Ângulo em graus
                                    .zoom(17)           //  Zoom
                                    .build();
                            CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
                            markerOptions.position(ultimaLocalizacao)    // Localização
                                    .title("Sua posição.")    // Título
                                    .snippet("Você esta aqui"); // Descrição

                            mMap.addMarker( markerOptions );
                            mMap.animateCamera(update);
                        }
                    }
                });


        final int PERMISSOES_ACESSO_MAPA = 0;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


// Localização sede da Google
        //LatLng google = new LatLng(40.740637, -74.002039);
      //  LatLng ultimaLocalizacao = new LatLng(location.getLatitude(), location.getLongitude());

// Configuração da câmera
       // LatLng ultimaLocalizacao = new LatLng(ultimaLatitude, ultimaLongitude);
        /*final CameraPosition position = new CameraPosition.Builder()
                .target(ultimaLocalizacao)     //  Localização
                .bearing(45)        //  Rotação da câmera
                .tilt(90)             //  Ângulo em graus
                .zoom(17)           //  Zoom
                .build();
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);*/

        /*markerOptions.position(google)    // Localização
                .title("Google Inc.")    // Título
                .snippet("Sede da Google"); // Descrição

        mMap.addMarker( markerOptions );*/






//        mMap.setMyLocationEnabled(true);




    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // action when permission granted
                    Toast.makeText(this,"permissao garantida",Toast.LENGTH_LONG).show();
                } else {
                    // action when permission denied
                    Toast.makeText(this,"permissao negada",Toast.LENGTH_LONG).show();
                    abrirTelaMensagemErro();
                }
                return;


    }

    protected void abrirTelaMensagemErro(){
        Intent intent = new Intent(this,MensagemLocalizacaoNegada.class);
        startActivity(intent);
        finish();
    }
}

