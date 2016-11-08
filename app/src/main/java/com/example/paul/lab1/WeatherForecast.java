package com.example.paul.lab1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WeatherForecast extends AppCompatActivity {
    TextView minTxt,maxTxt,currentTxt,time;
    ProgressBar pBar;
    Button refresh;
    String formattedDate;
    ImageView weatherImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_weather_forecast);
        refresh = (Button)findViewById(R.id.weather);
        minTxt = (TextView)findViewById(R.id.minTemp);
        maxTxt = (TextView)findViewById(R.id.maxTemp);
        currentTxt = (TextView)findViewById(R.id.currentTemp);
        time = (TextView)findViewById(R.id.currentTime);
        weatherImage = (ImageView)findViewById(R.id.weatherImage);
        pBar =(ProgressBar)findViewById(R.id.progressBar);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(c.getTime());

        new ForecastQuery().execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");




        // button used for refresh
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new ForecastQuery().execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
                Calendar c = Calendar.getInstance();
                System.out.println("Current time => "+c.getTime());

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                formattedDate = df.format(c.getTime());
                Log.i(" Weather Forecast","Refreshed");

            }
        });



    } // end of onCreate method

    public void refeshForcast()
    {

    }

    public class ForecastQuery extends AsyncTask<String,Integer,String>
    {
        String min,max,current,icon;
        Bitmap bitmap;


        @Override
        protected String doInBackground(String... args) {

            try {
                URL url = new URL(args[0]); // url connection passed to the args param of method do inbackground when execute is called
                HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // opening the url connection
                InputStream stream = conn.getInputStream(); // setting up the input stream
                conn.connect(); // connecting

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(stream, "UTF8"); // settig the input stream for the Xml parser.
                boolean finished = false;
                int type = XmlPullParser.START_DOCUMENT; // setting int type to the start of the document

                while(type != XmlPullParser.END_DOCUMENT) // while the parser is not at the end of the document run
                {
                    if(type == XmlPullParser.START_TAG) // if the start tag is found enter if
                    {
                        String temperature = xpp.getName();
                        if(temperature.equals("temperature")) // if temperature string equals xml tag "temperature" enter if
                        {
                            max = xpp.getAttributeValue(null,"max"); // setting the max to the value on the max id in the temperature tag
                            publishProgress(25); // publishing progress to progress bar
                            min = xpp.getAttributeValue(null,"min"); // setting the min to the value on the max id in the temperature tag
                            publishProgress(50); // publishing progress to progress bar
                            current = xpp.getAttributeValue(null,"value"); // setting current string to the value on the value id in the temperature tag
                            publishProgress(75); // publishing progress to progress bar
                        }
                        if(temperature.equals("weather")) // if string temperature = weather enter if
                        {
                            icon = xpp.getAttributeValue(null,"icon"); // pass the value of icon in the xml to the string icon
                            publishProgress(100);
                            bitmap = getBitmapFromUrl("http://openweathermap.org/img/w/" + icon + ".png"); // pull the correct icon from the url
                            // usig the icon string
                            // bitmap = getbitmapfromurl method see method below
                            fileExistance(icon); // checking to see if the file already exists
                            FileOutputStream outputStream;

                            try {
                                outputStream = openFileOutput(icon, Context.MODE_PRIVATE);
                                outputStream.write(icon.getBytes());
                                outputStream.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    type = xpp.next();
                }



            }catch(Exception e){
                Log.e("XML PARSING", e.getMessage());
            }

            return null;
        }


        // a method that will return a bitmap from a url connection
        public Bitmap getBitmapFromUrl(String src){
            try{
                URL bit = new URL(src); // new url from the param passed to the method
                URLConnection urlConnection = bit.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.connect();
                InputStream input = urlConnection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);


                return myBitmap;


            }catch(Exception e){
                e.printStackTrace();
                return null;
            }

        }

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);


            FileInputStream fis = null;
            try {
                fis = new FileInputStream(icon);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.i(icon + " Weather Forecast","file does not exist");
            }
            Bitmap bm = BitmapFactory.decodeStream(fis);

            return file.exists();

        }


        // on progressUpdate to set the progress and visibility of progress bar
        public void onProgressUpdate(Integer ...value)
        {
           pBar.setVisibility(View.VISIBLE);
            pBar.setProgress(value[0]);




        }

        // onpostexecute will set the textviews to the strings for min max and current
        // setting the weatherimage to the bitmap object
        // setting progress bar to invisible
        @Override
        protected void onPostExecute(String s) {
            currentTxt.setText("Current: " +current);
            minTxt.setText("Minumum: " +min);
            maxTxt.setText("Maximum: " +max);
            time.setText("Current Time: "+" "+ formattedDate);
            weatherImage.setImageBitmap(bitmap);
          pBar.setVisibility(View.INVISIBLE);
        }
    } // end of forcastQuery method

} // end of class weather forcast
