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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cyberwatchdog.api.ApiClient;
import com.example.cyberwatchdog.api.ApiInterface;
import com.example.cyberwatchdog.models.Article;
import com.example.cyberwatchdog.models.News;

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

private String TAG = MainActivity.class.getSimpleName();
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


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
       String u_name = n.getStringExtra("name");
TextView name = findViewById(R.id.name);
name.setText("Welcome, "+u_name);

recyclerView = findViewById(R.id.recyclerView);
layoutManager = new LinearLayoutManager(MainActivity.this);
recyclerView.setLayoutManager(layoutManager);
recyclerView.setItemAnimator(new DefaultItemAnimator());
recyclerView.setNestedScrollingEnabled(false);






loadJson();


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
        Toast.makeText(getApplicationContext(),"TESTTT",Toast.LENGTH_SHORT).show();







    }
}
