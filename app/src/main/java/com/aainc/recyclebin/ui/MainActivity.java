package com.aainc.recyclebin.ui;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.aainc.recyclebin.R;
import com.aainc.recyclebin.services.ManagerService;
import com.aainc.recyclebin.storage.FileSystemHandler;
import com.aainc.recyclebin.util.Str;
import com.aainc.recyclebin.util.Util;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    // ✅ Permission request codes should NOT be R.id
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1001;
    private static final int REQUEST_READ_MEDIA_IMAGES = 1002;

    private FileSystemHandler fileSystemHandler = null;
    private ManagerService mService;
    private Intent mServiceIntent;

    private Context ctx;
    private Menu mMenu;
    private boolean showlog = true;

    private enum ServiceState { START, STOP }
    private ServiceState mState = ServiceState.STOP;

    // Navigation Drawer
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    public Context getCtx() { return ctx; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Util.getSing().initializeVar(getResources(), this);
        ctx = this;

        Bundle extras = getIntent().getExtras();
        if (extras != null && "hide".equals(extras.getString("notification"))) {
            alertDialogDouble("Hide Notifications",
                    "Click settings to go to Settings page, and turn notification off to hide the notification. The application will continue to work even with notification hidden",
                    Str.getSing().tra_alertbutton_okay,
                    "Settings");
        }

        setContentView(R.layout.activity_main);
        Util.getSing().thisactivity = this;

        mService = new ManagerService();
        mServiceIntent = new Intent(ctx, mService.getClass());

        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Drawer
        mDrawer = findViewById(R.id.drawer_layout);
        nvDrawer = findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
        mDrawer.openDrawer(GravityCompat.START);

        // Initial Fragment
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            DeletedFilesViewFragment delFilesFragment = new DeletedFilesViewFragment();
            final String tag = DeletedFilesViewFragment.class.getName();

            fragmentTransaction.add(R.id.flContent, delFilesFragment, tag);
            fragmentTransaction.addToBackStack(tag);
            int result = fragmentTransaction.commit();

            if (result < 0) finish();
        }

        // Permission checks
        checkPermissions();

        // Initialize FileSystemHandler
        if (fileSystemHandler == null) {
            FileSystemHandler.initializeFileSystemHandler(getApplicationContext());
            fileSystemHandler = FileSystemHandler.getInstance();
        }

        // Service status
        boolean isManagerServiceRunning = isServiceRunning(ManagerService.class);
        if (showlog) System.out.println("isManagerServiceRunning " + isManagerServiceRunning);

        if (isManagerServiceRunning) {
            mState = ServiceState.START;
            updateServicesStatusIcon(true);
        } else {
            mState = ServiceState.STOP;
            updateServicesStatusIcon(false);
        }

        // Start service on launch
        changeServiceStatus(ServiceState.START);
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_EXTERNAL_STORAGE);
                return;
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                        REQUEST_READ_MEDIA_IMAGES);
            }
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            selectDrawerItem(menuItem);
            return true;
        });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_first_fragment) {
            alertDialogSingle(Str.getSing().bin_alerttitle_quickinstructions,
                    Str.getSing().bin_alertbody_quickinstructions,
                    Str.getSing().tra_alertbutton_okay,
                    Str.getSing().tra_alertbutton_instructionvideo);
        } else if (id == R.id.nav_second_fragment) {
            // do nothing
        } else if (id == R.id.nav_third_fragment) {
            finish();
        }

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        mMenu = menu;

        boolean isManagerServiceRunning = isServiceRunning(ManagerService.class);
        updateServicesStatusIcon(isManagerServiceRunning);
        mState = isManagerServiceRunning ? ServiceState.START : ServiceState.STOP;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.instruction) {
            alertDialogSingle(Str.getSing().bin_alerttitle_quickinstructions,
                    Str.getSing().bin_alertbody_quickinstructions,
                    Str.getSing().tra_alertbutton_okay,
                    Str.getSing().tra_alertbutton_instructionvideo);
            return true;
        } else if (id == android.R.id.home) {
            mDrawer.openDrawer(GravityCompat.START);
            return true;
        } else if (id == R.id.service_state) {
            changeServiceStatus(mState == ServiceState.START ? ServiceState.STOP : ServiceState.START);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_WRITE_EXTERNAL_STORAGE:
            case REQUEST_READ_MEDIA_IMAGES:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    changeServiceStatus(ServiceState.START);
                    updateServicesStatusIcon(true);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void changeServiceStatus(ServiceState state) {
        switch (state) {
            case START:
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1
                        && Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                REQUEST_WRITE_EXTERNAL_STORAGE);
                        return;
                    }
                }
                if (mState == ServiceState.STOP) {
                    if (!isServiceRunning(mService.getClass())) startService(mServiceIntent);
                    mState = ServiceState.START;
                    updateServicesStatusIcon(true);
                    Toast.makeText(this, Str.getSing().bin_str_started, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Bin was already running", Toast.LENGTH_SHORT).show();
                }
                break;

            case STOP:
                if (isServiceRunning(mService.getClass())) stopService(mServiceIntent);
                mState = ServiceState.STOP;
                updateServicesStatusIcon(false);
                Toast.makeText(this, Str.getSing().bin_str_stopped, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void updateServicesStatusIcon(boolean isRun) {
        if (mMenu == null) return;

        MenuItem item = mMenu.findItem(R.id.service_state);
        if (item == null) return;

        String stateTitle = isRun ? getString(R.string.bin_str_stopped) : getString(R.string.bin_str_started);
        int iconResource = isRun ? R.drawable.icon_stop : R.drawable.icon_start;

        Drawable icon = Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP
                ? getResources().getDrawable(iconResource)
                : getResources().getDrawable(iconResource, getTheme());

        item.setTitle(stateTitle);
        item.setIcon(icon);
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo serviceInfo : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(serviceInfo.service.getClassName())) return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        if (showlog) System.out.println(TAG + " RecycleBin Main activity ondestroy");
        super.onDestroy();
    }

    // === AlertDialogs ===
    private void alertDialogSingle(String title, String body, String finish, String finish2) {
        try {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(body);

            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, finish, (dialog, which) -> { });
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, finish2, (dialog, which) ->
                    Util.getSing().openURL(Str.getSing().urlvideo_aabin));

            if (!isFinishing()) alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void alertDialogDouble(String title, String body, String finish1, String finish2) {
        try {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(body);

            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, finish1, (dialog, which) -> { });
            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, finish2, (dialog, which) -> {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.fromParts("package", getPackageName(), null));
                startActivity(intent);
            });

            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
