package com.example.tiennguyen.appnhac;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;



public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer = new MediaPlayer();;
    EditText edTenbaihat;
    ListView lvDanhsach;
    Button btnSearch;
    ArrayAdapter<String> adapter;

    String Id = "";
    String name = "";
    ArrayList<String> arrayList;
    ArrayList<String> arrayList1;
    ArrayList<String> arrayTitle;

    ArrayList<Song> arraySong;

    String URL_SEARCH = "http://mp3.zing.vn/tim-kiem/bai-hat.html?q=";
    String URL_SOURCE = "http://mp3.zing.vn/html5/song/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edTenbaihat = (EditText)findViewById(R.id.edTenbaihat);
        lvDanhsach = (ListView)findViewById(R.id.lvDanhsachbaihat);
        btnSearch = (Button)findViewById(R.id.btnSearch);

        arrayList = new ArrayList<>();
        arrayList1 = new ArrayList<>();
        arrayTitle = new ArrayList<>();
        arraySong = new ArrayList<>();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //name = edTenbaihat.getText().toString();

                StringUtils tenbaihat = new StringUtils();
                name = tenbaihat.unAccent(edTenbaihat.getText().toString());
                //Toast.makeText(MainActivity.this, name, Toast.LENGTH_LONG).show();

//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
                      new LoadData().execute( URL_SEARCH + name);
//                    }
//                });

                //InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); //tham chiếu tới INPUT service
                //imm.hideSoftInputFromWindow(edTenbaihat.getWindowToken(), 0); //ẩn bàn phím


            }
        });

//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
//        lvDanhsach.setAdapter(adapter);
//
//        lvDanhsach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });
    }

    class LoadData extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String url = LoadDataFromURL(strings[0]);
            return url;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();

            //String html = "<div><p>Lorem ipsum.</p>";
            Document doc = Jsoup.parse(s);
            Elements div = doc.select("div.item-song");

//            for(int i = 0; i < div.size(); i++) {
//                String data_code = div.get(i).attr("data-code");
//                arrayList1.add(i, data_code);
//            }

            Elements divTitle = doc.select("div.title-song");
            for(int i = 0; i < divTitle.size(); i++) {
                String data_title = divTitle.get(i).tagName("h3").select(".txt-primary").attr("title");
                arrayList.add(i, data_title);
                String data_code = div.get(i).attr("data-code");
                arrayList1.add(i, data_code);
            }

            adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
            lvDanhsach.setAdapter(adapter);

           lvDanhsach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                   Intent it = new Intent(MainActivity.this, PhatNhac.class);
//                   startActivity(it);

                   String linkMp3 = URL_SOURCE + arrayList1.get(position);
                   PhatNhac(linkMp3);
               }
           });
        }
    }

    private static String LoadDataFromURL(String theUrl)
    {
        StringBuilder content = new StringBuilder();

        try
        {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line + "\n");
            }
            bufferedReader.close();

        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }


    public static class StringUtils{
        public static String unAccent(String s) {
            String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replaceAll("đ", "d").replaceAll(" ", "");
        }
    }

    public void PhatNhac(String url){
        //url = "http://khoapham.vn/download/vietnamoi.mp3";
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer = new MediaPlayer();
        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}