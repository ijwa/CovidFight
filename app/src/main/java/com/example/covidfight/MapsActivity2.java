package com.example.covidfight;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button button;

    int[] colors = {
            Color.GREEN,    // green(0-50)
            Color.YELLOW,    // yellow(51-100)
            Color.rgb(255,165,0), //Orange(101-150)
            Color.RED,              //red(151-200)
            Color.rgb(153,50,204), //dark orchid(201-300)
            // Color.rgb(165,42,42) //brown(301-500)
    };
    float[] startPoints = new float[]{
            .2f, .4f, .6f, .8f, 1.0f};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        button = (Button) findViewById(R.id.datAButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap1();
            }
        });
    }
    public void openMap1(){
        Intent intent = new Intent(this, MapsActivity.class );
        startActivity(intent);
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


        MapStyleOptions mapStyleOptions=MapStyleOptions.loadRawResourceStyle(this,R.raw.style);
        googleMap.setMapStyle(mapStyleOptions);

        // Add a marker in Sydney and move the camera
        LatLng richmond = new LatLng(37.5483, -77.4527);
        //mMap.addMarker(new MarkerOptions().position(richmond).title("Marker in Richmond"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(richmond));
        mMap.moveCamera(CameraUpdateFactory.zoomTo((float) 5.75));//5.0 for US
        Gradient gradient = new Gradient(colors,startPoints);
//        WeightedLatLng thingy = new WeightedLatLng(new LatLng(37.5483, -77.4527),2.0);
//        WeightedLatLng thingy2 = new WeightedLatLng(new LatLng(37.5493, -77.4527),5.0);

        List<WeightedLatLng> wDat = loadData2();

        // loadData2();
//        wDat.add(thingy);
//        wDat.add(thingy2);
        HeatmapTileProvider provider = new HeatmapTileProvider.Builder().weightedData(wDat).gradient(gradient).build();
        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(provider));

        //System.exit(0);



    }

    public List<WeightedLatLng> loadData2(){

        ArrayList<WeightedLatLng> wdat = new ArrayList<>();

        try {
            InputStream zipcodeFile = getAssets().open("ZipLatLong.csv");

            System.out.println("hahafun");
            Scanner scan = new Scanner(zipcodeFile);
            String text="";
            ArrayList<String>indexToZipcode = new ArrayList<>();
            ArrayList<String>indexToLong = new ArrayList<>();
            ArrayList<String>indexToLat = new ArrayList<>();
            while (scan.hasNextLine()){
                text+=scan.nextLine();
            }
            text = text.replaceAll(",,","\n");
            String [] datapoints = text.split("\n");
            for (String x : datapoints){
                indexToZipcode.add(x.split(",")[0]);
                indexToLat.add(x.split(",")[1]);
                indexToLong.add(x.split(",")[2]);

            }
            String myUrl = "https://data.virginia.gov/resource/8bkr-zfqv.json";
            String result="";
            HttpGetRequest getRequest = new HttpGetRequest();
            result = getRequest.execute(myUrl).get();

            //String result = getRequest.execute(myUrl).get();
            JSONArray jsonHold = new JSONArray(result);

            //JSONArray jsonHold = jsonObj.getJSONArray("features");
            ArrayList<String> reportDate = new ArrayList<>();
            ArrayList<String> zipcodes = new ArrayList<>();
            ArrayList<String> numCases = new ArrayList<>();
            ArrayList<String> numPCRTesting = new ArrayList<>();
            ArrayList<DataRichmond> dataList = new ArrayList<>();
            for (int i = 0; i < jsonHold.length(); i++) {
                JSONObject obj = jsonHold.getJSONObject(i);
                //JSONObject attributes = obj.getJSONObject("attributes");
                if(!(obj.isNull("report_date")||obj.isNull("zip_code")||obj.isNull("number_of_cases")
                        || obj.isNull("number_of_pcr_testing") )) {
                    reportDate.add((String) obj.get(("report_date")));
                    zipcodes.add((String) obj.get(("zip_code")));
                    numCases.add((String) obj.get(("number_of_cases")));
                    numPCRTesting.add((String) obj.get("number_of_pcr_testing"));
                }
            }

            for( int i = 0;i < reportDate.size();i++){
                DataRichmond dataPoint = new DataRichmond(reportDate.get(i), zipcodes.get(i), numCases.get(i), numPCRTesting.get(i));
                dataList.add(dataPoint);
            }
            for (DataRichmond d : dataList){
                for (int i =0; i<indexToZipcode.size();i++) {
                    if(indexToZipcode.get(i).equals(d.getZip_code()) && !d.getNumber_of_cases().equals("Suppressed")) {
                        WeightedLatLng dataPoint = new WeightedLatLng(new LatLng(Double.valueOf(indexToLat.get(i)), Double.valueOf(indexToLong.get(i))), Double.valueOf(d.getNumber_of_cases()));
                        wdat.add(dataPoint);
                        break;
                    }
                }



            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wdat;
    }

    static class HttpGetRequest extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String stringUrl = params[0];
            String result = "";
            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);
                //Create a connection
                InputStream thing = myUrl.openStream();
                Scanner scan = new Scanner(thing);
                while (scan.hasNextLine()) {
                    result += scan.nextLine();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }}
