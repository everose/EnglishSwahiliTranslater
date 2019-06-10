package com.example.swahiliapp;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileNotFoundException;

import java.io.IOException;

import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter myadapter;
    private RecyclerView.LayoutManager layoutManager;

    private Button translate;
	private Button exit;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.result);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        translate = findViewById(R.id.translate);
        editText = findViewById(R.id.editText);

        myadapter = new MyAdapter();
        myadapter.setDataSet(new String[]{""});
        recyclerView.setAdapter(myadapter);

        translate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                myadapter.setDataSet(
                        translations(editText.getText().toString()));
                myadapter.notifyDataSetChanged();

            }
        });

        exit = (Button)findViewById(R.id.exit);

        exit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Finish method is used to close all open activities.
                finish();

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    String[] translations(String word){
        ArrayList<String> swahili = new ArrayList<>();

        try (
                BufferedReader buff = new BufferedReader(
                        new InputStreamReader(getAssets().open("data.csv")))){
            String line;
            while ((line = buff.readLine()) != null) {
                String[] values = line.split(",");
                if (values[1].equals(word)){
                    swahili.add(values[0]);
                }

            }

            String[] arr = new String[swahili.size()];
            for (int i = 0; i < swahili.size(); i++) {
                arr[i] = swahili.get(i);
            }

            if( arr.length == 0){
                arr = new String[]{"Word not found"};
            }

            return arr;

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return new String[]{"Empty"};

    }
}
