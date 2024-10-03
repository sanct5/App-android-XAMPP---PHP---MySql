package com.example.php2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManageActivity extends AppCompatActivity {
    ListView listPC;
    EditText ref, cpu, gpu, ram, ssd, psu;
    Button btnAdd, btnFind, btnUpdate, btnDelete, btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        listPC = (ListView) findViewById(R.id.listPC);
        ref = (EditText) findViewById(R.id.txtRef);
        cpu = (EditText) findViewById(R.id.txtCpu);
        gpu = (EditText) findViewById(R.id.txtGpu);
        ram = (EditText) findViewById(R.id.txtRam);
        ssd = (EditText) findViewById(R.id.txtSsd);
        psu = (EditText) findViewById(R.id.txtPsu);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnFind = (Button) findViewById(R.id.btnFind);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnRefresh = (Button) findViewById(R.id.btnRefresh);

        getAll();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((!ref.getText().toString().isEmpty()) && (!cpu.getText().toString().isEmpty()) && (!gpu.getText().toString().isEmpty()) && (!ram.getText().toString().isEmpty()) && (!ssd.getText().toString().isEmpty()) && (!psu.getText().toString().isEmpty())) {
                    new Insertar(ManageActivity.this).execute();
                } else {
                    Toast.makeText(ManageActivity.this, "Hay informacion por llenar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Consultar(ManageActivity.this).execute();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Modificar(ManageActivity.this).execute();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Eliminar(ManageActivity.this).execute();
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAll();
            }
        });
    }

    private void cleanInputs() {
        ref.setText("");
        cpu.setText("");
        gpu.setText("");
        ram.setText("");
        ssd.setText("");
        psu.setText("");
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
            ArrayAdapter<Computador> adapter = new ArrayAdapter<>(ManageActivity.this, android.R.layout.simple_list_item_1, computers);
            listPC.setAdapter(adapter);
        }
    }

    // CRUD from here --------
    private boolean insertar() {
        String url = Constants.URL + "computadores/add.php";
        List<NameValuePair> nameValuePairs = new ArrayList<>(6);
        nameValuePairs.add(new BasicNameValuePair("ref", ref.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("cpu", cpu.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("gpu", gpu.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("ram", ram.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("ssd", ssd.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("psu", psu.getText().toString().trim()));
        try {
            boolean response = APIHandler.POST(url, nameValuePairs);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Computador consultar() throws JSONException, IOException {
        String url = Constants.URL + "computadores/get-by-id.php"; // Ruta

        List<NameValuePair> nameValuePairs; // lista de datos
        nameValuePairs = new ArrayList<NameValuePair>(1);//definimos array
        nameValuePairs.add(new BasicNameValuePair("ref", ref.getText().toString().trim())); // pasamos el ref al servicio php
        String json = APIHandler.POSTRESPONSE(url, nameValuePairs); // creamos var json que se le asocia la respuesta del webs
        if (json != null) { // si la respuesta no es vacia
            JSONObject object = new JSONObject(json); // creamos el objeto json que recorrera el servicio
            JSONArray json_array = object.optJSONArray("componentes");// accedemos al objeto json llamado computadores
            if (json_array.length() > 0) { // si lo encontrado tiene al menos un registro
                Computador computador = new Computador(json_array.getJSONObject(0));// instanciamos la clase multa para obtener los datos jso
                return computador;// retornamos el computador
            }
            return null;
        }
        return null;
    }

    private boolean modificar() {
        String url = Constants.URL + "computadores/update.php";

        List<NameValuePair> nameValuePairs;
        nameValuePairs = new ArrayList<NameValuePair>(6);
        nameValuePairs.add(new BasicNameValuePair("ref", ref.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("cpu", cpu.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("gpu", gpu.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("ram", ram.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("ssd", ssd.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("psu", psu.getText().toString().trim()));
        boolean response = APIHandler.POST(url, nameValuePairs); // enviamos los datos por POST al Webservice PHP
        return response;
    }

    private boolean eliminar() {
        String url = Constants.URL + "computadores/delete.php";

        List<NameValuePair> nameValuePairs;
        nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("ref", ref.getText().toString().trim()));
        boolean response = APIHandler.POST(url, nameValuePairs); // Enviamos el id al webservices
        return response;
    }

    // --- CRUD finalizados --------
    //----Eventos del AsyncTask para los botones ---------
    class Insertar extends AsyncTask<String, String, String> {
        private Activity context;

        Insertar(Activity context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            if (insertar()) context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getAll();
                    cleanInputs();
                    Toast.makeText(context, "Computador insertado", Toast.LENGTH_LONG).show();
                }
            });
            else context.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Toast.makeText(context, "Computador no insertado", Toast.LENGTH_LONG).show();
                }
            });
            return null;
        }
    }

    class Consultar extends AsyncTask<String, String, String> {
        private Activity context;

        Consultar(Activity context) {
            this.context = context;
        }

        protected String doInBackground(String... params) {
            try {
                final Computador computador = consultar();
                if (computador != null) context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ref.setText(computador.getRef());
                        cpu.setText(computador.getCpu());
                        gpu.setText(computador.getGpu());
                        ram.setText(computador.getRam());
                        ssd.setText(computador.getSsd());
                        psu.setText(computador.getPsu());
                    }
                });
                else context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(context, "Computador no encontrado", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    class Modificar extends AsyncTask<String, String, String> {
        private Activity context;

        Modificar(Activity context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            if (modificar()) context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getAll();
                    cleanInputs();
                    Toast.makeText(context, "Computador modificado", Toast.LENGTH_LONG).show();
                }
            });
            else context.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Toast.makeText(context, "Computador no modificado", Toast.LENGTH_LONG).show();
                }
            });
            return null;
        }
    }

    class Eliminar extends AsyncTask<String, String, String> {
        private Activity context;

        Eliminar(Activity context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            if (eliminar()) context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getAll();
                    cleanInputs();
                    Toast.makeText(context, "Computador eliminado", Toast.LENGTH_LONG).show();
                }
            });
            else context.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Toast.makeText(context, "Computador no eliminado", Toast.LENGTH_LONG).show();
                }
            });
            return null;
        }
    }
}