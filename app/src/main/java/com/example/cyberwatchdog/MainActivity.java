package com.example.cyberwatchdog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cyberwatchdog.api.ApiClient;
import com.example.cyberwatchdog.api.ApiInterface;
import com.example.cyberwatchdog.models.Article;
import com.example.cyberwatchdog.models.News;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Adapter.OnItemClickListener {
public static final  String API_KEY ="b2d14cb9042f4590aa199ede7234da18";
private RecyclerView recyclerView;
private RecyclerView.LayoutManager layoutManager;
private List<Article> articles = new ArrayList<>();
private Adapter adapter;
    private ProgressDialog progressDialog2;
    String u_name;

private String TAG = MainActivity.class.getSimpleName();
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



ImageButton chat = findViewById(R.id.chat);




        progressDialog2 = new ProgressDialog(this);
        progressDialog2.setMessage("Loading fresh news");
        progressDialog2.show();


        // Displaying general scams page.
Button generalScam = findViewById(R.id.generalScam);
generalScam.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

       Intent intent = new Intent(getApplicationContext(), ScamActivity.class);
       startActivity(intent);

    }
});
        Intent n = getIntent();
        u_name = n.getStringExtra("name");
TextView name = findViewById(R.id.name);






name.setText("Welcome, "+u_name);

recyclerView = findViewById(R.id.recyclerView);
layoutManager = new LinearLayoutManager(MainActivity.this);
recyclerView.setLayoutManager(layoutManager);
recyclerView.setItemAnimator(new DefaultItemAnimator());
recyclerView.setNestedScrollingEnabled(false);


        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(getApplicationContext(), ChatActivity.class);
                n.putExtra("name", u_name);
                startActivity(n);
                finish();
            }
        });




loadJson();


    }

    @Override
    protected void onStart() {
        super.onStart();
        String score =  Read();
int scoreInt = Integer.parseInt(score.trim());


        scoreInt+=1;
        score = Integer.toString(scoreInt);
        Write(score);
        TextView scoreTxt = findViewById(R.id.score);



        scoreTxt.setText("Your Score: "+scoreInt);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    public void loadJson(){

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
String q = "cyber scam";
        Call<News> call;
        call = apiInterface.getNews(q, API_KEY);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if(response.isSuccessful() && response.body().getArticles() != null){

                    if(!articles.isEmpty()){
                        articles.clear();
                    }

                    articles = response.body().getArticles();
                    adapter=new Adapter(articles,MainActivity.this,MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    progressDialog2.hide();
                }
                else{

                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });




    }


    @Override
    public void onItemClick( View v ,int position) {

            Intent n = new Intent(getApplicationContext(), WebViewer.class);
        n.putExtra("web", Integer.toString(position));
            startActivity(n);
            finish();











    }







    public void Write(String value)
    {
        String valueStr =value.toString();
        try {


            FileOutputStream fileOutputStream = openFileOutput("Score.txt",MODE_PRIVATE);
            fileOutputStream.write((valueStr+"\n").getBytes());
            fileOutputStream.close();


        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }






    }

    public String  Read() {
        try {
            FileInputStream fileInputStream = openFileInput("Score.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            String lines;
            String score="";

            while ((lines = bufferedReader.readLine()) != null) {
              score = score+   stringBuffer.append(lines + "\n");
            }
            return score;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "0";
    }



    public String  Read2() {
        try {
            FileInputStream fileInputStream = openFileInput("nameOfPerson.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            String lines;
            String score="";

            while ((lines = bufferedReader.readLine()) != null) {
                score = score+   stringBuffer.append(lines + "\n");
            }
            return score;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "0";
    }






    public void Write2(String value)
    {
        String valueStr =value;
        try {


            FileOutputStream fileOutputStream = openFileOutput("nameOfPerson.txt",MODE_PRIVATE);
            fileOutputStream.write((valueStr+"\n").getBytes());
            fileOutputStream.close();


        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }






    }








}



















