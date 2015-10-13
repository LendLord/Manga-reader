package com.lendlord.manga;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.lendlord.manga.MangaLibrary.Library;
import com.mikepenz.materialdrawer.Drawer;

public class MainActivity extends AppCompatActivity {

    private SocketSend con = null;
    private Drawer drawerResult = null;
    private boolean mIsBackEligible = false;
    private Vibrator v = null;

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return con;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        con = (SocketSend) getLastCustomNonConfigurationInstance();
        if (con == null){
            con = new SocketSend(this);
            con.execute();
        }

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CustomNavigationDrawer customNavigationDrawer = new CustomNavigationDrawer();
        drawerResult = customNavigationDrawer.BuildCustomDrawer(this);
        drawerResult.setSelection(1, false);

        final EditText editText = (EditText) findViewById(R.id.editText);

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                con.send(editText.getText().toString());
            }
        });
    }

    @Override
    public void onBackPressed(){
        if(drawerResult.isDrawerOpen()){
            drawerResult.closeDrawer();
        }
        else{

            if (mIsBackEligible) {
                v.vibrate(30);
                super.onBackPressed();

            } else {
                mIsBackEligible = true;
                v.vibrate(30);
                new Runnable() {
                    // Spin up new runnable to reset the mIsBackEnabled var after 3 seconds
                    @Override
                    public void run() {
                        CountDownTimer cdt = new CountDownTimer(3000, 3000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // I don't want to do anything onTick
                            }

                            @Override
                            public void onFinish() {
                                mIsBackEligible = false;
                            }
                        }.start();
                    }
                }.run(); // End Runnable()

                Snackbar
                        .make(findViewById(R.id.clayout), "Для выхода нажмите назад еще раз", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED)
                        .show(); // Don’t forget to show!
            }

        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        drawerResult.setSelection(1,false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openLibrary(){
        Intent intent = new Intent(MainActivity.this, Library.class);
        startActivity(intent);
    }
}
