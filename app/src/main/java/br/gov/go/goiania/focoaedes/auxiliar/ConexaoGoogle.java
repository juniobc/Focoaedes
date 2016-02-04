package br.gov.go.goiania.focoaedes.auxiliar;


import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

import br.gov.go.goiania.focoaedes.localizacao.GerenciaLocalizacao;

public class ConexaoGoogle implements ConnectionCallbacks, OnConnectionFailedListener {

    public static GoogleApiClient mGoogleApiClient;
    private Context contexto;
    private static final String TAG = "ConexaoGoogle";
    private LocationListener locationListener;

    public ConexaoGoogle(Context contexto){

        this.contexto = contexto;

        this.mGoogleApiClient = new GoogleApiClient.Builder(contexto)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }

    public ConexaoGoogle(Context contexto, LocationListener locationListener){

        this.contexto = contexto;
        this.locationListener = locationListener;

        this.mGoogleApiClient = new GoogleApiClient.Builder(contexto)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }

    public void conecta(){

        mGoogleApiClient.connect();

    }

    @Override
    public void onConnected(Bundle bundle) {

        GerenciaLocalizacao localiza = new GerenciaLocalizacao(contexto, locationListener);
        localiza.startLocationUpdates();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
