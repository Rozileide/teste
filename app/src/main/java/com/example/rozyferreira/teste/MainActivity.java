package com.example.rozyferreira.teste;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

// aqui começo o codigo do botao Alert

    public void alert (View View) {


        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        startActivityForResult(intent, 0);


    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Toast.makeText(this, contents, Toast.LENGTH_LONG).show();

                if(!contents.isEmpty()){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(contents);

                        String mesa = jsonObject.getString("mesa");

                        Intent intentPedido = new Intent(this, PedidoActivity.class);
                        intentPedido.putExtra("mesa",mesa);
                        startActivity(intentPedido);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        alertFail();
                    }
                }

            } else if (resultCode == RESULT_CANCELED) {
                alertFail();
            }
        }
    }

    private void alertFail() {
        AlertDialog Alert;
        Alert = new AlertDialog.Builder(this).create();
        Alert.setTitle("GarFood");
        Alert.setMessage("Falha ao ler o qrcode");
        Alert.show();
    }
}
