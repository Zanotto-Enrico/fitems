package com.example.fitems.Classes;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Pair;

import java.io.IOException;
import java.util.List;

public class LatLonGenerator {

    public static Pair<Double, Double> getCoordinatesFromAddress(String address, Context c) throws IOException {
        Geocoder geocoder = new Geocoder(c);
        List<Address> addresses = geocoder.getFromLocationName(address, 1);
        Address a = addresses.get(0);
        double longitudine = a.getLongitude();
        double latitudine = a.getLatitude();

        return new Pair<>(latitudine, longitudine);
    }
}
