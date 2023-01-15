package com.example.fitems;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitems.Classes.ApiInterface;
import com.example.fitems.Classes.CustomListAdapter_Home;
import com.example.fitems.Classes.LatLonGenerator;
import com.example.fitems.Classes.Post;
import com.example.fitems.Classes.RetrofitClient;
import com.example.fitems.Classes.User;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnAccount;
    private ImageButton btnNewPost, btnGuide, btnChat;
    private ListView listViewPost;
    private TextView txtBonusPoints;
    private List<Post> posts;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        setContentView(R.layout.activity_main);
        this.posts = new ArrayList<>();
        locationRequest = new LocationRequest.Builder(LocationRequest.PRIORITY_HIGH_ACCURACY, 50000).build();
        getCurrentLocation();

        connectWithGraphic();
        addListenerToWidgets();

        setBonusPoints();
    }

    /**
     * Metodo utilizzato per gestire la visualizzazione dei bonus points nella schermata home.
     * Essi saranno visibili esclusivamente nel caso in cui l'utente autenticato sia minorenne.
     */
    private void setBonusPoints() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(" ");
        ssb.setSpan(new ImageSpan(this, R.drawable.ic_baseline_bonus_points_24), ssb.length() - 1, ssb.length(), 0);
        ssb.append(" ");

        if (User.loggedUser != null && User.loggedUser.getPoints() != -1) {
            ssb.append(String.valueOf(User.loggedUser.getPoints()));
            this.txtBonusPoints.setText(ssb);
        } else
            this.txtBonusPoints.setVisibility(View.INVISIBLE);
    }

    /**
     * Metodo che ha il compito di fare il binding con tutte le View all'interno della
     * nostra activity
     */
    private void connectWithGraphic() {
        this.btnAccount = findViewById(R.id.btnAccount_homepage);
        this.listViewPost = findViewById(R.id.listPosts_home);
        this.btnNewPost = findViewById(R.id.btnAddPost);
        this.txtBonusPoints = findViewById(R.id.txtBonusPoints);
        this.btnGuide = findViewById(R.id.btnGuide);
        this.btnChat = findViewById(R.id.btnChat);
    }

    /**
     * Metodo che ha il compito di raggruppare e inizializzare i listeners
     * sugli elementi dell'activity
     */
    private void addListenerToWidgets() {
        this.btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), UserArea.class);
                view.getContext().startActivity(i);
            }
        });

        this.btnNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), MakePostView.class);
                view.getContext().startActivity(i);
            }
        });

        this.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), Activity_chat_list.class);
                view.getContext().startActivity(i);
            }
        });

        this.listViewPost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, PostView.class);

                // sto cercando di avviare una activity da un punto esterno a quello del contesto corrente
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("username", posts.get(i).getUsername());
                intent.putExtra("stato", posts.get(i).getStato());
                intent.putExtra("data", posts.get(i).getData());
                intent.putExtra("descrizione", posts.get(i).getDescrizione());
                intent.putExtra("titolo", posts.get(i).getTitolo());
                intent.putExtra("id", posts.get(i).getId_post());
                view.getContext().startActivity(intent);
            }
        });

        this.btnGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), Guide.class);
                startActivity(i);
            }
        });
    }

    private void setPosts(Double latitudine, Double longitudine) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        Call<JsonObject> call = apiInterface.post(20, latitudine, longitudine);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                synchronized (posts) {

                    Type listType = new TypeToken<ArrayList<Post>>(){}.getType();
                    ArrayList<Post> post = new Gson().fromJson(response.body().getAsJsonArray("post"), listType);
                    JsonArray array = response.body().getAsJsonArray("post");
                    for (JsonElement var : array)
                    {
                        posts.add( new Gson().fromJson(var, Post.class));
                    }
                    CustomListAdapter_Home adapter = new CustomListAdapter_Home(getApplicationContext(), post);
                    listViewPost.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(MainActivity.this, "[Home]" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Override del metodo originale per vietare la possibilità di muoversi indietro una volta raggiunta
     * questa schermata
     */
    @Override
    public void onBackPressed() { }

    private void getCurrentLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(MainActivity.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(MainActivity.this)
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() >0){

                                        int index = locationResult.getLocations().size() - 1;
                                        double latitude = locationResult.getLocations().get(index).getLatitude();
                                        double longitude = locationResult.getLocations().get(index).getLongitude();

                                        System.out.println("I'm passing this coordinates: Latitudine: " + latitude + ", Longitudine: " + longitude );
                                        setPosts(latitude, longitude);
                                    }
                                }
                            }, Looper.getMainLooper());

                } else {
                    turnOnGPS();
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    private void turnOnGPS(){
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(MainActivity.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(MainActivity.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){

                if (isGPSEnabled()) {
                    getCurrentLocation();
                }else {
                    turnOnGPS();
                }
            }
        }
    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;
    }
}