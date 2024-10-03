package com.example.php2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText txtUsername, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtUsername.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "Ingrese un usuario", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (txtPassword.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "Debe ingresar una contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }
                new Login().execute();
            }
        });
    }

    private boolean login(String username, String password) {
        String url = Constants.URL + "computadores/loginAdmin.php";
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("admin", username));
        nameValuePairs.add(new BasicNameValuePair("pass", password));
        String response = APIHandler.POSTRESPONSE(url, nameValuePairs, true);
        Log.d("LoginResponse", response);
        if (response != null) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").equals("ok")) {
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    class Login extends AsyncTask<String, String, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            return login(txtUsername.getText().toString(), txtPassword.getText().toString());
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                Intent intent = new Intent(LoginActivity.this, ManageActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        }
    }

}