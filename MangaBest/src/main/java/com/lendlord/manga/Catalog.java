package com.lendlord.manga;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.animation.OvershootInterpolator;

import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.view.IMaterialListAdapter;
import com.dexafree.materialList.view.MaterialListAdapter;
import com.dexafree.materialList.view.MaterialListView;
import com.mikepenz.materialdrawer.Drawer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;


public class Catalog extends AppCompatActivity{

    private Drawer drawerResult = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CustomNavigationDrawer customNavigationDrawer = new CustomNavigationDrawer();
        drawerResult = customNavigationDrawer.BuildCustomDrawer(this);
        drawerResult.setSelection(2, false);

        MaterialListView mListView = (MaterialListView) findViewById(R.id.material_listview);

        File sdPath = Environment.getExternalStorageDirectory();

        Bitmap bitmap;
        ArrayList<BitmapDrawable> bitmapDrawables = new ArrayList<BitmapDrawable>();
        ArrayList<String> titles = new ArrayList<String>();
        FileDataHandler fileDataHandler = new FileDataHandler();
        List<File> files = fileDataHandler.getListFiles(new File(sdPath.getAbsolutePath() + "/MangaBest/"));
        for (File file : files) {
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
            bitmapDrawables.add(bitmapDrawable);
            titles.add(file.getName().replace(".jpg",""));
        }

        Random r = new Random();
        int rnd = 0;
        for (int i=0; i<200; i++) {
            rnd = r.nextInt(bitmapDrawables.size());
            Card card = new Card.Builder(this)
                    .setTag("BIG_IMAGE_CARD")
                    .withProvider(MangaLibCardProvider.class)
                    .setTitle(titles.get(rnd))
                    .setDescription(String.valueOf(rnd)+"/10")
                    .setDrawable(bitmapDrawables.get(rnd))
                    .endConfig()
                    .build();
            mListView.add(card);
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.material_listview);
//        MaterialListAdapter adapter = new MaterialListAdapter();
        recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));

    }

    @Override
    public void onBackPressed() {
        if (drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawerResult.setSelection(2, false);
    }


}
