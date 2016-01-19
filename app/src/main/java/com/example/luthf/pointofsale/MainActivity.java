package com.example.luthf.pointofsale;

import android.animation.LayoutTransition;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.luthf.pointofsale.Adapter.Event;
import com.example.luthf.pointofsale.Adapter.EventAdapter;
import com.example.luthf.pointofsale.Adapter.NumberTextWatcher;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private List<Event> eventList = new ArrayList<Event>();
    private ListView listView;
    private EventAdapter adapter;
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    ArrayAdapter<String> adapterforsearch;
    private ListView lv;
    ArrayList<HashMap<String, String>> productList;

    int p = 0;

    //arrayall
    String[] nameall = new String[]{
            "Burger", "Cappuccino", "Es Cincau", "Es Krim", "Nasi Goreng", "Pizza", "Sate Ayam"
    };
    private Integer[] priceall = new Integer[]{
            25000, 30000, 40000, 12000, 32000, 23000, 50000
    };

    //arrayminuman
    private String[] namemakanan = new String[]{
            "Burger", "Nasi Goreng", "Pizza", "Sate Ayam"
    };
    private Integer[] pricemakanan = new Integer[]{
            25000, 32000, 23000, 50000
    };

    //arraymakanan
    private String[] nameminuman = new String[]{
            "Cappuccino", "Es Cincau", "Es Krim"
    };
    private Integer[] priceminuman = new Integer[]{
            30000, 40000, 12000
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText ed = (EditText) findViewById(R.id.editText);
        ed.addTextChangedListener(new NumberTextWatcher(ed));


        //action bar button category
        findViewById(R.id.product).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout calc = (RelativeLayout) findViewById(R.id.calc);
                GridView grid = (GridView) findViewById(R.id.gv_item);
                LinearLayout tet = (LinearLayout) findViewById(R.id.tet);
                grid.setNumColumns(4);
                grid.setVisibility(View.VISIBLE);
                calc.setVisibility(View.GONE);
                tet.setVisibility(View.VISIBLE);
            }
        });

        //action bar custom amount
        findViewById(R.id.customamount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GridView grid = (GridView) findViewById(R.id.gv_item);
                RelativeLayout calculator = (RelativeLayout) findViewById(R.id.calc);
                LinearLayout tet = (LinearLayout) findViewById(R.id.tet);
                grid.setVisibility(View.GONE);
                tet.setVisibility(View.GONE);
                calculator.setVisibility(View.VISIBLE);
            }
        });

        final Button product = (Button) findViewById(R.id.product);
        final Button favorit = (Button) findViewById(R.id.favorite);
        final Button customamount = (Button) findViewById(R.id.customamount);

        final EditText search = (EditText) findViewById(R.id.editTextsearch);

        //action bar search
        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.setVisibility(View.INVISIBLE);
                favorit.setVisibility(View.INVISIBLE);
                customamount.setVisibility(View.INVISIBLE);
                search.setVisibility(View.VISIBLE);
            }
        });


        final TextView result = (TextView) findViewById(R.id.result);
        final Button btdel = (Button) findViewById(R.id.buttinDone);
        final Button btdone = (Button) findViewById(R.id.buttonDel);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //int d = 0;

        gridView = (GridView) findViewById(R.id.gv_item);
        gridAdapter = new GridViewAdapter(this, R.layout.item_grid, getData());
        gridView.setAdapter(gridAdapter);

        //search method
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                String text = search.getText().toString().toLowerCase(Locale.getDefault());
                //filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });


        Button amount = (Button) findViewById(R.id.total);

        amount.setBackgroundColor(getResources().getColor(R.color.dead));
        amount.setEnabled(false);

        final ArrayList<String> Jumlah = new ArrayList<String>();
        final ArrayList<String> Nama = new ArrayList<String>();
        final ArrayList<String> Harga = new ArrayList<String>();


        //buton delete in custom amount
        findViewById(R.id.buttonDel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resu = result.getText().toString();
                if (resu.length() > 3) {
                    resu = resu.substring(0, resu.length() - 1);
                    result.setText(resu);
                }
            }
        });

        //custom amount insert to list
        findViewById(R.id.buttinDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ListView list = (ListView) findViewById(R.id.list);
                list.setBackgroundResource(R.color.white);

                TextView stri = (TextView) findViewById(R.id.str);
                String strin = stri.getText().toString();

                TextView result = (TextView) findViewById(R.id.result);
                String resu = result.getText().toString();

                if (resu.length() <= 3) {
                    Log.d("lenh", "lenh" + resu.length());
                    GridView grid = (GridView) findViewById(R.id.gv_item);
                    RelativeLayout calc = (RelativeLayout) findViewById(R.id.calc);
                    LinearLayout tet = (LinearLayout) findViewById(R.id.tet);
                    grid.setVisibility(View.VISIBLE);
                    grid.setNumColumns(4);
                    tet.setVisibility(View.VISIBLE);
                    calc.setVisibility(View.GONE);
                    result.setText("Rp");
                }

                else {
                    replace(resu);

                    Event event = new Event();
                    event.setName("Other");
                    event.setPrice(resu);
                    event.setSum(String.valueOf(1));

                    eventList.add(event);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    TextView subtotal = (TextView) findViewById(R.id.subtotal);
                    NumberFormat numFormat = NumberFormat.getInstance(Locale.GERMAN);
                    String subtot = subtotal.getText().toString();

                    replace(subtot);


                    //Subtotal
                    int sub = Integer.valueOf(subtot);
                    int re = Integer.valueOf(resu);
                    sub = sub + re;
                    subtotal.setText("Rp " + numFormat.format(sub));

                    //VAT
                    TextView vat = (TextView) findViewById(R.id.vat);
                    String vati = subtotal.getText().toString();

                    replace(vati);

                    double click = Double.parseDouble(String.valueOf(vati));
                    double ma = click * (10.0 / 100.0);
                    int mab = (int) Math.round(ma);
                    vat.setText("Rp " + numFormat.format(mab));

                    Button amount = (Button) findViewById(R.id.total);
                    amount.setText("Charge Rp " + numFormat.format(sub + (mab)));
                    TextView totalpayment = (TextView) findViewById(R.id.textView2);
                    TextView strp = (TextView) findViewById(R.id.str);
                    totalpayment.setText("Rp " + numFormat.format(sub + (mab)));
                    strp.setText(String.valueOf(sub + mab));

                    GridView grid = (GridView) findViewById(R.id.gv_item);
                    RelativeLayout calc = (RelativeLayout) findViewById(R.id.calc);
                    LinearLayout tet = (LinearLayout) findViewById(R.id.tet);
                    grid.setVisibility(View.VISIBLE);
                    grid.setNumColumns(4);
                    tet.setVisibility(View.VISIBLE);
                    calc.setVisibility(View.GONE);
                    result.setText("Rp");

                    Button amount1 = (Button) findViewById(R.id.total);
                    amount1.setBackgroundResource(R.drawable.charge);
                    amount1.setEnabled(true);
                }

            }
        });

        final ListView list = (ListView) findViewById(R.id.list);


        list.setBackgroundResource(R.drawable.empty);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            int clickcount = 0;
            int b = 0;
            int a = 0;
            int h = 0;


            ArrayList<String> User = new ArrayList<String>();
            final TextView strp = (TextView) findViewById(R.id.str);


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                list.setBackgroundResource(R.color.white);

                int newItem = priceall[position];
                String harga = String.valueOf(newItem);
                String namamakanan = nameall[position];

                if (p == 1) {
                    newItem = priceall[position];
                    namamakanan = nameall[position];
                } else if (p == 2) {
                    newItem = pricemakanan[position];
                    namamakanan = namemakanan[position];
                } else if (p == 3) {
                    newItem = priceminuman[position];
                    namamakanan = nameminuman[position];
                }

                Button amount = (Button) findViewById(R.id.total);
                amount.setBackgroundResource(R.drawable.charge);
                amount.setEnabled(true);

                clickcount = clickcount + newItem;

                Event event = new Event();
                event.setName(namamakanan);
                event.setPrice(harga);
                event.setSum(String.valueOf(1));

                User.add(namamakanan);
                //Jumlah.add(amount);
                //Nama.add(namamakanan);
                Harga.add(harga);

                if (a >= 1) {
                    h = h + newItem;
                    if (namamakanan.equals(User.get(a - 1))) {
                        b = b + 1;
                        h = newItem * (b + 1);
                        int len = eventList.size();
                        event.setSum(String.valueOf(b + 1));
                        event.setPrice(String.valueOf(h));
                        eventList.add(event);
                        eventList.remove(len - 1);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        listView.setSelection(adapter.getCount() - 1);

                        Jumlah.add(String.valueOf(h));
                        Nama.add(namamakanan);
                        Harga.add(harga);

                    } else {
                        b = 0;
                        eventList.add(event);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        listView.setSelection(adapter.getCount() - 1);
                        //Jumlah.add(amount);
                        Nama.add(namamakanan);
                        Harga.add(harga);
                    }
                } else {
                    eventList.add(event);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    listView.setSelection(adapter.getCount() - 1);
                    //Jumlah.add(amount);
                    Nama.add(namamakanan);
                    Harga.add(harga);

                }
                NumberFormat numFormat = NumberFormat.getInstance(Locale.GERMAN);
                TextView result = (TextView) findViewById(R.id.result);
                String resu = result.getText().toString();

                if (resu.contains("R")) {
                    TextView subtotal = (TextView) findViewById(R.id.subtotal);
                    subtotal.setText("Rp " + numFormat.format(clickcount));
                } else {
                    int re = Integer.valueOf(resu);
                    TextView subtotal = (TextView) findViewById(R.id.subtotal);
                    subtotal.setText("Rp " + numFormat.format(clickcount + re));
                }

                double click = Double.parseDouble(String.valueOf(clickcount));
                double ma = click * (10.0 / 100.0);
                int mab = (int) Math.round(ma);
                TextView vat = (TextView) findViewById(R.id.vat);
                vat.setText("Rp " + numFormat.format(mab));

                amount.setText("Charge Rp " + numFormat.format(clickcount + (mab)));
                a = a + 1;
                TextView totalpayment = (TextView) findViewById(R.id.textView2);
                totalpayment.setText("Rp " + numFormat.format(clickcount + (mab)));

                strp.setText(String.valueOf(clickcount + mab));

/*
                //sugestion amount
                EditText pay = (EditText) findViewById(R.id.editText);
                RadioButton first = (RadioButton) findViewById(R.id.first);
                RadioButton second = (RadioButton) findViewById(R.id.second);
                RadioButton third = (RadioButton) findViewById(R.id.third);
                RadioButton fourth = (RadioButton) findViewById(R.id.fourth);

                final String m = String.valueOf(clickcount);
                String ca = m.charAt(m.length() - 4) + "";

                int n = Integer.valueOf(m);

                if (n<5){
                    first.setVisibility(View.VISIBLE);
                    first.setText();
                }
*/

            }
        });

        listView = (ListView) findViewById(R.id.list);
        adapter = new EventAdapter(this, eventList);
        listView.setAdapter(adapter);
        listView.setSelection(adapter.getCount() - 1);

        findViewById(R.id.pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());

                TextView stri = (TextView) findViewById(R.id.str);


                String strin = stri.getText().toString();
                String edit = ed.getText().toString();

                if (edit.contains(",")) {
                    edit = edit.replaceAll(",", "");
                }

                int ro = Integer.valueOf(strin);
                if (edit.trim().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Amount must have value", Toast.LENGTH_LONG).show();
                } else if (Integer.valueOf(edit) < ro) {
                    Toast.makeText(getApplicationContext(), "Amount must valid", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, Done.class);
                    intent.putExtra("amount", edit);
                    intent.putExtra("total", strin);
                    startActivity(intent);
                }
            }
        });

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout pay = (LinearLayout) findViewById(R.id.payment);
                LinearLayout gone = (LinearLayout) findViewById(R.id.gone);
                Button total = (Button) findViewById(R.id.total);
                //TextView totalamoount = (TextView) findViewById((R.id.totalamount));

                pay.setVisibility(View.GONE);
                gone.setVisibility(View.VISIBLE);
                total.setVisibility(View.VISIBLE);
                //totalamoount.setVisibility(View.GONE);
            }
        });

        findViewById(R.id.total).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout pay = (LinearLayout) findViewById(R.id.payment);
                LinearLayout gone = (LinearLayout) findViewById(R.id.gone);
                Button total = (Button) findViewById(R.id.total);
                //TextView totalamoount = (TextView) findViewById( (R.id.totalamount));

                pay.setVisibility(View.VISIBLE);
                gone.setVisibility(View.GONE);
                total.setVisibility(View.GONE);
                //totalamoount.setVisibility(View.VISIBLE);
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    //int total = 0;
    int u = 0;

    public void onButtonClick(View v) {
        Button button = (Button) v;
        String bText = button.getText().toString();
        //int value = Integer.parseInt(bText);
        //total += value;
        TextView myTextView = (TextView) findViewById(R.id.result);

        if (u == 0) {
            myTextView.setText("");
            myTextView.setText("Rp " + myTextView.getText() + bText);
            u = u + 1;
        } else {
            myTextView.setText(myTextView.getText() + bText);
        }
        //myTextView.setText(Integer.toString(total));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_open) {

            return true;
        } else if (id == R.id.action_save) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.all:
                if (checked) {
                    gridAdapter = new GridViewAdapter(this, R.layout.item_grid, getData());
                    gridView.setAdapter(gridAdapter);
                    p = 1;
                }
                break;
            case R.id.makanan:
                if (checked) {
                    gridAdapter = new GridViewAdapter(this, R.layout.item_grid, getDataMakanan());
                    gridView.setAdapter(gridAdapter);
                    p = 2;
                }
                break;
            case R.id.minuman:
                if (checked) {
                    gridAdapter = new GridViewAdapter(this, R.layout.item_grid, getDataMinuman());
                    gridView.setAdapter(gridAdapter);
                    p = 3;
                }
                break;
        }
    }

    //Search Method Class
    /*
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        gridAdapter.clear();
        if (charText.length() == 0) {
            gridView.setAdapter(gridAdapter);
        } else {
            for (GridViewAdapter wp : gridAdapter) {
                if (gridAdapter.getData().toLowerCase(Locale.getDefault()).contains(charText)) {
                    gridView.add(wp);
                }
            }
        }
        gridAdapter.notifyDataSetChanged();
    }
*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_register) {

        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(MainActivity.this, History.class);
            startActivity(intent);

        } else if (id == R.id.nav_report) {
            Intent intent = new Intent(MainActivity.this, Report.class);
            startActivity(intent);

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_signout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //class to make pure amount value without any symbol
    public void replace (String value){
        if (value.contains("R")) {
            value = value.replace("R", "");
            value = value.replace("p", "");
            value = value.replace(" ", "");
            value = value.replace(".", "");
            //subtot = subtot.replace("0","");
        }
    }

    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.all);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, nameall[i]));
        }
        return imageItems;
    }

    private ArrayList<ImageItem> getDataMakanan() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.makanan);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, namemakanan[i]));
        }
        return imageItems;
    }

    private ArrayList<ImageItem> getDataMinuman() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.minuman);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, nameminuman[i]));
        }
        return imageItems;
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.luthf.pointofsale/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.luthf.pointofsale/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
