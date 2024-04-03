package com.rahi.sqlite_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.rahi.sqlite_database.Adapter.Adapter;
import com.rahi.sqlite_database.Database.DatabaseHelper;
import com.rahi.sqlite_database.POJO_Classes.GetData;

import java.util.ArrayList;

import jp.wasabeef.blurry.Blurry;

public class MainActivity2 extends AppCompatActivity {

    RecyclerView recyclerView;
    ConstraintLayout cons;
    ArrayList<GetData> getData ;
    DatabaseHelper databaseHelper;
    Adapter adapter;
    String name,email,pass,prof;
    int age;
    LinearLayoutManager llm;
    private int page = 1, limit = 15;
    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private int previousTotal = 0;
    private int visibleThreshold = 10;
    boolean loading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recyclerView = findViewById(R.id.recyclerview);
        cons = findViewById(R.id.cons);
        llm = new LinearLayoutManager(MainActivity2.this);
        llm.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerview();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy>0){
                    visibleItemCount = recyclerView.getChildCount();
                    totalItemCount = llm.getItemCount();
                    firstVisibleItem = llm.findFirstVisibleItemPosition();
                    if(totalItemCount>previousTotal){
                        previousTotal = totalItemCount;
                        page++;
                    }
                    if((firstVisibleItem + visibleThreshold + visibleItemCount) >= totalItemCount){
                        page++;
                    }
                }
            }
        });

    }

    private void recyclerview() {
        getData = new ArrayList<>();
        adapter = new Adapter(getData,this);
        recyclerView.setAdapter(adapter);

        databaseHelper = new DatabaseHelper(this);
        Cursor cursor =databaseHelper.getData();
        while (cursor.moveToNext()){
            name = cursor.getString(0);
            email = cursor.getString(1);
            pass = cursor.getString(2);
            prof = cursor.getString(3);
            age = cursor.getInt(4);
            getData.add(new GetData(name,email,pass,prof,age));
        }
        cursor.close();

    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter.notifyDataSetChanged();
    }
}