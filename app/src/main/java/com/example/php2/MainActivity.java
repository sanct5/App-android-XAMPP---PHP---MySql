package com.example.php2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listPC;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listPC = findViewById(R.id.listPC);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        getAll();
    }

    private void getAll() {
        new GetAllComputersTask().execute();
    }

    private class GetAllComputersTask extends AsyncTask<Void, Void, List<Computador>> {
        @Override
        protected List<Computador> doInBackground(Void... voids) {
            List<Computador> computers = new ArrayList<>();
            String url = Constants.URL + "computadores/getAll.php";
            try {
                // Make HTTP GET request
                String result = APIHandler.GET(url);

                // Parse JSON response
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("componentes");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject computerObject = jsonArray.getJSONObject(i);
                    Computador computador = new Computador(computerObject);
                    computers.add(computador);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return computers;
        }

        @Override
        protected void onPostExecute(List<Computador> computers) {
            super.onPostExecute(computers);
            // Update UI with the retrieved data
            ArrayAdapter<Computador> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, computers);
            listPC.setAdapter(adapter);
        }
    }
}