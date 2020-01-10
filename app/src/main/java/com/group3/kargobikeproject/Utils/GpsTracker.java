package com.group3.kargobikeproject.Utils;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import static android.content.Context.LOCATION_SERVICE;

public class GpsTracker implements LocationListener {

    Context context;

    public GpsTracker(Context context) {
        super();
        this.context = context;
    }

    public Location getLocation(){
        if (ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            Log.e("fist","error");
            Toast.makeText(context, "Cannot get location", Toast.LENGTH_SHORT).show();
            return null;
        }
        try {
            LocationManager lm = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (isGPSEnabled){
                Toast.makeText(context, "gps ok", Toast.LENGTH_SHORT).show();
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000,10,this);
                Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                return loc;
            }else{
                Log.e("sec","errpr");
                Toast.makeText(context, "Cannot get location", Toast.LENGTH_SHORT).show();

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(context, "location changed", Toast.LENGTH_SHORT).show();

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