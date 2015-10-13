package com.lendlord.manga;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.lendlord.manga.MangaLibrary.Library;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

/**
 * Created by lendlord on 09.10.15.
 */
public class CustomNavigationDrawer extends AppCompatActivity{

    private Drawer drawerResult = null;

    public Drawer BuildCustomDrawer(final Activity parentActivity){


        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(parentActivity)
                .withHeaderBackground(R.drawable.dark_header)
                .addProfiles(
                        new ProfileDrawerItem().withName("Lend Lord").withEmail("lendlord07@gmail.com").withIcon(parentActivity.getResources().getDrawable(R.drawable.profile))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();


        //create the drawer and remember the `Drawer` result object
        drawerResult = new DrawerBuilder()
                .withActivity(parentActivity)
                .withToolbar((Toolbar)parentActivity.findViewById(R.id.toolbar))
                .withActionBarDrawerToggleAnimated(true)
                .withHeader(R.layout.drawer_header)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withBadge("99").withTag("Main").withIdentifier(1),
                        new PrimaryDrawerItem().withName("Каталог").withIdentifier(2).withTag("Library"),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {

                            Class nextActivity = null;
                            if (drawerItem.getTag() == null) return false;

                            if (drawerItem.getTag().equals("Library")) {
                                nextActivity = Library.class;
                            }

                            if (drawerItem.getTag() != null && drawerItem.getTag().equals("Main")) {
                                nextActivity = MainActivity.class;
                            }

                            if (parentActivity.getClass() == nextActivity || nextActivity == null) return false;
                            Intent intent = new Intent(parentActivity.getApplicationContext(), nextActivity);
                            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            parentActivity.startActivity(intent);
                            if (!parentActivity.getClass().getSimpleName().equals("MainActivity")) parentActivity.finish();

                            CharSequence text = String.valueOf(drawerItem.getTag());
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(parentActivity, text, duration);
                            toast.show();
                        }
                        return false;
                    }
                })
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        InputMethodManager inputMethodManager = (InputMethodManager) parentActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(parentActivity.getCurrentFocus().getWindowToken(), 0);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {

                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }
                })
                .addStickyDrawerItems(new PrimaryDrawerItem().withName("StickyFooterItem"))
                .build();

        return drawerResult;
    }
}
