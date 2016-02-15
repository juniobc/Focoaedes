package br.gov.go.goiania.focoaedes.localizacao;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BuscaEndereco extends IntentService {

    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;
    public static final String PACKAGE_NAME =
            "br.gov.go.goiania.focoaedes.localizacao";
    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    public static final String RESULT_DATA_KEY = PACKAGE_NAME +
            ".RESULT_DATA_KEY";
    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME +
            ".LOCATION_DATA_EXTRA";

    private static final String TAG = "BuscaEndereco";

    protected ResultReceiver mReceiver;

    private Address address;

    public BuscaEndereco() {
        super("BuscaEndereco");
    }

    private void deliverResultToReceiver(int resultCode, String message) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(LOCATION_DATA_EXTRA, address);
        bundle.putString(RESULT_DATA_KEY, message);
        this.mReceiver.send(resultCode, bundle);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        String errorMessage = "";

        this.mReceiver = intent.getParcelableExtra(RECEIVER);

        Location location = intent.getParcelableExtra(
                LOCATION_DATA_EXTRA);

        List<Address> addresses = null;

        try {
            /*addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    // In this sample, get just a single address.
                    1);*/
            addresses = geocoder.getFromLocationName("74230022", 1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            Log.e(TAG, errorMessage, ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            Log.e(TAG, errorMessage + ". " +
                    "Latitude = " + location.getLatitude() +
                    ", Longitude = " +
                    location.getLongitude(), illegalArgumentException);
        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size()  == 0) {
            if (errorMessage.isEmpty()) {
                /*errorMessage = getString(R.string.no_address_found);*/
                Log.e(TAG, errorMessage);
            }
            deliverResultToReceiver(FAILURE_RESULT, errorMessage);
        } else {
            address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<String>();

            // Fetch the address lines using getAddressLine,
            // join them, and send them to the thread.
            for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }

            Address returnAddress = addresses.get(0);

            Log.d(TAG, "onHandleIntent - cep: "+returnAddress.getPostalCode());
            deliverResultToReceiver(SUCCESS_RESULT,
                    "SUCESSO");
        }

    }

}
