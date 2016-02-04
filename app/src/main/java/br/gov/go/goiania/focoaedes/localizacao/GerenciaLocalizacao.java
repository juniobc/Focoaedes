package br.gov.go.goiania.focoaedes.localizacao;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.util.Date;

import br.gov.go.goiania.focoaedes.auxiliar.ConexaoGoogle;

public class GerenciaLocalizacao {

    private static final String TAG = "GerenciaLocalizacao";

    private LocationRequest mLocationRequest;
    private Context contexto;
    private String mProviderName;
    private LocationManager mLocationManager;
    private double latitude;
    private double longitude;
    private LocationListener locationListener;

    public GerenciaLocalizacao(Context contexto, LocationListener locationListener){

        this.contexto = contexto;
        this.locationListener = locationListener;

    }

    public void startLocationUpdates() {

        createLocationRequest();

        mLocationManager = (LocationManager) this.contexto.getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        mProviderName = mLocationManager.getBestProvider(criteria, true);

        if (ActivityCompat.checkSelfPermission(this.contexto, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.contexto, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }else{

            if (mProviderName == null || mProviderName.equals("")) {
                this.contexto.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }

        }

        LocationServices.FusedLocationApi.requestLocationUpdates(
                ConexaoGoogle.mGoogleApiClient, mLocationRequest, locationListener);
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }



    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
