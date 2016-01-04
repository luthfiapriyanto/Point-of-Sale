package com.example.luthf.pointofsale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

public class Done extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final String amount = intent.getStringExtra("amount");
        final String total = intent.getStringExtra("total");

        Log.d("nilai", "nilai"+total);

        int a = Integer.valueOf(total);
        int b = Integer.valueOf(amount);

        int c = b-a;

        TextView change = (TextView) findViewById(R.id.textView4);
        NumberFormat numFormat = NumberFormat.getInstance(Locale.GERMAN);
        change.setText("Change Rp "+numFormat.format(c));

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Done.this, MainActivity.class));
            }
        });

        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email = (EditText) findViewById(R.id.editText2);
                String emai = email.getText().toString();

                if(emai.trim().length() <= 0){
                    Toast.makeText(getApplicationContext(), "Email address is empty", Toast.LENGTH_LONG).show();
                }
                else {
                    RelativeLayout pertama = (RelativeLayout) findViewById(R.id.relatif);
                    RelativeLayout kedua = (RelativeLayout) findViewById(R.id.relatifpop);
                    pertama.setVisibility(View.INVISIBLE);
                    kedua.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
