package com.edu.ustc.ustcschedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.edu.ustc.ustcschedule.databinding.ActivityMainBinding;
import com.edu.ustc.ustcschedule.dialogs.AboutDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    BottomNavigationView bottomNavigation;
    @SuppressLint("StaticFieldLeak")
    public static Drawer result;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate((getLayoutInflater()));
        setContentView(binding.getRoot());

        bottomNavigation = findViewById(R.id.bottom_navigation);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigation, navController);
        result = onCrateDrawer();
    }

    public Drawer onCrateDrawer() {
        @SuppressLint("UseCompatLoadingForDrawables")
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withDrawerGravity(GravityCompat.END)
                .withSelectedItem(-1)
                .withDrawerWidthDp(180)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.move)
                                .withIdentifier(1)
                                .withIcon(getResources().getDrawable(R.drawable.ic_bulletpoint))
                                .withSelectable(false))
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.save_template)
                                .withIdentifier(2)
                                .withIcon(getResources().getDrawable(R.drawable.ic_save))
                                .withSelectable(false))
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.share)
                                .withIdentifier(3)
                                .withIcon(getResources().getDrawable(R.drawable.ic_share))
                                .withSelectable(false))
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.remind_book)
                                .withIdentifier(4)
                                .withIcon(getResources().getDrawable(R.drawable.ic_chat))
                                .withSelectable(false))
                .addDrawerItems(new DividerDrawerItem())
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.setting)
                                .withIdentifier(5)
                                .withIcon(getResources().getDrawable(R.drawable.ic_setting))
                                .withSelectable(false))
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.help)
                                .withIdentifier(6)
                                .withIcon(getResources().getDrawable(R.drawable.ic_help_circle))
                                .withSelectable(false))
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.about)
                                .withIdentifier(7)
                                .withIcon(getResources().getDrawable(R.drawable.ic_info_circle))
                                .withSelectable(false))
                .addDrawerItems(new DividerDrawerItem())
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.copyright)
                                .withEnabled(false)
                                .withTextColor(getResources().getColor(R.color.div_line)))
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    if (drawerItem!=null) {
                        if (drawerItem.getIdentifier() == 1) {

                        } else if (drawerItem.getIdentifier() == 2) {

                        } else if (drawerItem.getIdentifier() == 3) {

                        } else if (drawerItem.getIdentifier() == 4) {

                        } else if (drawerItem.getIdentifier() == 5) {

                        } else if (drawerItem.getIdentifier() == 6) {

                        } else if (drawerItem.getIdentifier() == 7) {
                            AboutDialog aboutDialog = new AboutDialog();
                            aboutDialog.show(getSupportFragmentManager(), "about");
                        }
                    }

                    return false;
                })
                .build();
        result.getDrawerLayout().setStatusBarBackgroundColor(getResources().getColor(R.color.div_line));
        return result;
    }
}