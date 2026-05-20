package com.aainc.recyclebin.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.InterstitialAd;

import com.aainc.recyclebin.R;
import com.aainc.recyclebin.ui.MainActivity;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
//import com.aainc.recyclebin.observers.FileSystemObserver;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Queue;

/*
 * Util is a Singleton class, only one in existence so we don't have to create many
 * copies this utility class. Contains methods that are reused or uses variables
 * that are reused in this class
 */

@SuppressLint("DefaultLocale")
public class Util implements FileFilter {
    // Util related
    // (10-10-2016 okay to be in util)
    static final String TAG = Util.class.getSimpleName();
    private static Util globalHandler;
    public Resources resources;
    public Fragment fragment;
    public Context thisactivity;
    public String lastToast;
    public boolean tra;
    public boolean pr;
    public boolean ar;
    public boolean dr;
    public boolean pr_sd;
    public boolean bin_photo;
    public boolean bin_video;
    public boolean bin_audio;
    public boolean bin_doc;
    public boolean vr;
    public boolean recommendnews;
    public boolean showlog;
    public int sdkVersion;
    public String[] extension_image;
    public String[] extension_video;
    public String[] extension_audio;
    public String[] extension_doc;
    public String[] extension_all;
    public File homedirectoryfile;
    public String homedirectorypath;
    public String homeDirectoryPathExt;

    // Recovery related
    // (10-10-2016 okay to be in util)
    public boolean sd_checked;
    public boolean sdexist;
    public String pr_all_recovered;
    public String pr_destdir;
    public String vr_all_recovered;
    public String vr_destdir;
    public String ar_all_recovered;
    public String ar_destdir;
    public String dr_all_recovered;
    public String dr_destdir;

    // File Browsing related
    // (10-10-2016 okay to be in util)
    public String fb_currentdirectorypath;
    public List<String> fb_currentdirectoryitems;
    public ArrayList<String> fb_filebackstack;
    public boolean displayrootfilebrowser;
    public boolean limitview;

    // Recycle Bin related
    // (10-10-2016 okay to be in util)
    //public FileSystemObserver recyclebinfileobserver;
    public String bindir;
    public String binsubdir1;
    public String binsubdir2;
    public String binsysinternaldir;
    public Queue<String> sysinternalque_photo;
    public Queue<String> sysinternalque_video;
    public Queue<String> sysinternalque_doc;
    public Queue<String> sysinternalque_audio;

    // Gooogle Ads related variables
    // (10-10-2016 okay to be in util)
    public int aabin_restorecounter;
    public int aabin_restorecountermax;
    private InterstitialAd aabin_restore_interstitialadunit;
    public String aabin_restore_interstitialadid;
    public int aabin_deletecounter;
    public int aabin_deletecountermax;
    private InterstitialAd aabin_delete_interstitialadunit;
    public String aabin_delete_interstitialadid;
    public int aabin_infocounter;
    public int aabin_infocountermax;
    private InterstitialAd aabin_info_interstitialadunit;
    public String aabin_info_interstitialadid;

    public Util() {

    }

    public static synchronized Util getSing() {
        if (globalHandler == null)
            globalHandler = new Util();

        return globalHandler;
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    // Initializes variables
    public void initializeVar(Resources r, Context con) {
        // These must come first or else you can't initlize the other variables
        Str.getSing().initializeVar(r);
        resources = r;
        thisactivity = con;
        extension_image = resources.getStringArray(R.array.extension_image);
        extension_video = resources.getStringArray(R.array.extension_video);
        extension_audio = resources.getStringArray(R.array.extension_audio);
        extension_doc = resources.getStringArray(R.array.extension_doc);
        extension_all = resources.getStringArray(R.array.extension_all);
        getAndroidVersion();

        // Try catch needed because some phones root directory will throw error
        try {
            homedirectoryfile = Environment.getExternalStorageDirectory();
            homedirectorypath = homedirectoryfile.getAbsolutePath();

            //logMessage(TAG, "homedirectorypath is: " + homedirectorypath);
            if (homedirectoryfile == null || homedirectorypath == "") {
                //logMessage(TAG, Str.getSing().tra_alertbody_error01);
            }
        } catch (Exception e) {
            //logMessage(TAG, Str.getSing().tra_alertbody_error01);
            homedirectorypath = "";
            e.printStackTrace();
        }

        // TODO Set this when changing app name
        // TODO only binvideo has settings done
        pr = false;
        bin_video = true;
        vr = false;
        bin_photo = false;
        tra = false;
        pr_sd = false;
        bin_audio = false;
        bin_doc = false;
        ar = false;
        dr = false;

        // TODO Set False when Deploy
        showlog = true; // also in deletedfilescursor, bincontentobserver, filesystemhandler
        recommendnews = true;

        // Bin set up
        sysinternalque_photo = new LinkedList<String>();
        sysinternalque_video = new LinkedList<String>();
        sysinternalque_audio = new LinkedList<String>();
        sysinternalque_doc = new LinkedList<String>();
        try {
            bindir = homedirectorypath + "/" + Str.getSing().bin_str_recyclebin
                    + "/";
            if (bin_photo || tra) {
                binsubdir1 = bindir + Str.getSing().bin_photo_str_photo + "/";
                binsubdir2 = binsubdir1 + Str.getSing().bin_photo_str_photo
                        + "/";
                binsysinternaldir = binsubdir1
                        + Str.getSing().bin_str_systeminternal + "/";
            } else if (bin_video) {
                binsubdir1 = bindir + Str.getSing().bin_video_str_video + "/";
                binsubdir2 = binsubdir1 + Str.getSing().bin_video_str_video
                        + "/";
                binsysinternaldir = binsubdir1
                        + Str.getSing().bin_str_systeminternal + "/";
            } else if (bin_audio) {
                binsubdir1 = bindir + Str.getSing().bin_audio_str_audio + "/";
                binsubdir2 = binsubdir1 + Str.getSing().bin_audio_str_audio
                        + "/";
                binsysinternaldir = binsubdir1
                        + Str.getSing().bin_str_systeminternal + "/";
            } else if (bin_doc) {
                binsubdir1 = bindir + Str.getSing().bin_doc_str_doc + "/";
                binsubdir2 = binsubdir1 + Str.getSing().bin_doc_str_doc + "/";
                binsysinternaldir = binsubdir1
                        + Str.getSing().bin_str_systeminternal + "/";
            }
        } catch (Exception e) {
            //logMessage(TAG, Str.getSing().tra_alertbody_error21);
        }

        // Recovery Initialize
        /*pr_all_recovered = (String) resources.getText(R.string.pr_str_allrecovered);
        vr_all_recovered = (String) resources.getText(R.string.vr_str_allrecovered);
        dr_all_recovered = (String) resources.getText(R.string.dr_str_allrecovered);
        ar_all_recovered = (String) resources.getText(R.string.ar_str_allrecovered);*/
        pr_destdir = Util.getSing().homedirectorypath + "/" + Util.getSing().pr_all_recovered;
        vr_destdir = Util.getSing().homedirectorypath + "/" + Util.getSing().vr_all_recovered;
        dr_destdir = Util.getSing().homedirectorypath + "/" + Util.getSing().dr_all_recovered;
        ar_destdir = Util.getSing().homedirectorypath + "/" + Util.getSing().ar_all_recovered;

        // FB Initialize
        fb_currentdirectoryitems = new ArrayList<String>();
        aabin_restorecounter = 0;
        aabin_restorecountermax = 3;
        aabin_infocounter = 0;
        aabin_infocountermax = 3;
        aabin_deletecounter = 0;
        aabin_deletecountermax = 3;

        // SD Initialize
        sd_checked = false;
        sdexist = false;
        ExternalSDExist();
    }

    // Check if a file is of image, doc, audio or video type extension
    @Override
    public boolean accept(File file) {
        for (String extension : extension_all) {
            if (file.getName().toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Utilites
     */

    public void ExternalSDExist() {
        try {
            // If the size of external is NOT 0, then it exists!
            String[] dir = getExternalMounts();
            //Util.getSing().logMessage(TAG, "number of external storages detected: " + dir.length);
            for (int j = 0; j < dir.length; j++) {
                //Util.getSing().logMessage(TAG, "path of external storage " + dir[j]);
                if (!TextUtils.isEmpty(dir[j])) {
                    String[] separated = dir[j].split("/");
                    homeDirectoryPathExt = "storage/" + separated[separated.length - 1];
                    sdexist = true;
                }
            }
            //Util.getSing().logMessage(TAG, "value of sdexist: " + sdexist);
        } catch (Exception e) {
            //logMessage(TAG, Str.getSing().tra_alertbody_error02 + e);
            e.printStackTrace();
        }
    }

    public static String[] getExternalMounts() {
        final HashSet<String> out = new HashSet<String>();
        String reg = "(?i).*vold.*(vfat|ntfs|exfat|fat32|ext3|ext4).*rw.*";
        String s = "";
        try {
            final Process process = new ProcessBuilder().command("mount")
                    .redirectErrorStream(true).start();
            process.waitFor();
            final InputStream is = process.getInputStream();
            final byte[] buffer = new byte[1024];
            while (is.read(buffer) != -1) {
                s = s + new String(buffer);
            }
            is.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }

        // parse output
        final String[] lines = s.split("\n");
        for (String line : lines) {
            if (!line.toLowerCase(Locale.US).contains("asec")) {
                if (line.matches(reg)) {
                    String[] parts = line.split(" ");
                    for (String part : parts) {
                        if (part.startsWith("/"))
                            if (!part.toLowerCase(Locale.US).contains("vold"))
                                out.add(part);
                    }
                }
            }
        }
        return out.toArray(new String[out.size()]);
    }

    public void getAndroidVersion() {
        String release = Build.VERSION.RELEASE;
        sdkVersion = Build.VERSION.SDK_INT;
        //logMessage(TAG, "Android SDK: " + sdkVersion + " (" + release + ")");
    }

    public Uri getUrifromFile(File f)
    {
        Uri data = null;

        // In andoird 7.0 they changed file://
        if (Util.getSing().sdkVersion < 24) {
            data = Uri.fromFile(f);
        } else{
            data = FileProvider.getUriForFile(Util.getSing().thisactivity, Util.getSing().thisactivity.getApplicationContext().getPackageName() + ".provider", f);
        }
        //Util.getSing().logMessage(TAG, "data uri: " + data.getPath());

        return data;
    }

    // Opens a URL, prompting user to select which app to use
    public void openURL(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (thisactivity != null) {
            thisactivity.startActivity(intent);
        }
    }

    // If app the installed, open it, otherwise open url to Google Play download
    public boolean openApp(Context context, String packagename, String url) {

        PackageManager manager = context.getPackageManager();

        try {
            Intent i = manager.getLaunchIntentForPackage(packagename);
            if (i == null) {
                openURL(url);
                return false;
            }
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            context.startActivity(i);
            return true;
        } catch (Exception e) {
            // Don't put alert dialogs here
            //logMessage(TAG, Str.getSing().tra_alertbody_error04);
            e.printStackTrace();
            return false;
        }
    }

    // Toast any message
    public void toastMessage(String message) {
        if (message.equals(lastToast)) {

        } else {
            try {
                if (thisactivity != null) {
                    // Toasting doesn't seem to work while app is closed like
                    // when service is running, its not a big deal
                    Toa mToastHandler = new Toa(
                            thisactivity.getApplicationContext());
                    mToastHandler.showToast(message, Toast.LENGTH_LONG);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // prevent repeat of toasting
        lastToast = message;
    }

    //Fuck this method, it sometimes doesn't print anything, just fucking do system.out.println
    /*public void logMessage(String tag, String message) {
        if (showlog) {
            System.out.println(tag + " " + message);
            //Log.d(tag, message);
        }
    }*/

    public boolean isconnected() {
        boolean isConnected = true;
        try {
            ConnectivityManager cm = (ConnectivityManager) thisactivity
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            isConnected = activeNetwork != null
                    && activeNetwork.isConnectedOrConnecting();
        } catch (Exception e) {
            //logMessage(TAG, "error calling isconnected");
        }

        return isConnected;
    }

    public static Drawable getAssetImage(Context context, String filename)
            throws IOException {
        AssetManager assets = context.getResources().getAssets();
        InputStream buffer = new BufferedInputStream((assets.open(filename
                + ".png")));
        Bitmap bitmap = BitmapFactory.decodeStream(buffer);
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    /*public void housead(final Context a) {
        if (a != null) {
            final Dialog dialog = new Dialog(a);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.ad_custominterstitial);
            dialog.setCanceledOnTouchOutside(true);

            final ImageView image = (ImageView) dialog
                    .findViewById(R.id.ad_custominterstitial);
            image.setClickable(true);
            image.setOnClickListener(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // String ad = "binad";
                    String packagename = Str.getSing().packagename_saver;
                    String url = Str.getSing().url_saver;
                    openApp(a, packagename, url);
                    dialog.dismiss();
                }
            });

            String ad = "ad_md";

            try {
                image.setImageDrawable(getAssetImage(a, ad));
            } catch (IOException e) {
                e.printStackTrace();
            }
            dialog.show();
        }
    }*/

    public void housead(final Context a) {
        if (a != null) {
            final Dialog dialog = new Dialog(a);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.ad_custominterstitial);
            dialog.setCanceledOnTouchOutside(true);

            final ImageView image = (ImageView) dialog
                    .findViewById(R.id.ad_custominterstitial);
            image.setClickable(true);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // String ad = "binad";
                    String packagename = Str.getSing().packagename_saver;
                    String url = Str.getSing().url_saver;
                    openApp(a, packagename, url);
                    dialog.dismiss();
                }
            });

            String ad = "ad_md";

            try {
                image.setImageDrawable(getAssetImage(a, ad));
            } catch (IOException e) {
                e.printStackTrace();
            }
            dialog.show();
        }
    }

    // Google Ad related: load a new interstitial
    /*public void requestNewInterstitial(InterstitialAd thisad) {
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("YOUR_DEVICE_HASH").build();

        thisad.loadAd(adRequest);
    }*/

    public void increment_ads_restore() {
        aabin_restorecounter++;
        if (aabin_restorecounter >= aabin_restorecountermax) {
        	show_ads(aabin_restore_interstitialadunit);
            aabin_restorecountermax = aabin_restorecountermax + 5;
            aabin_restorecounter = 0;
        }
    }

    public void increment_ads_delete() {
        aabin_deletecounter++;
        if (aabin_deletecounter >= aabin_deletecountermax) {
            show_ads(aabin_delete_interstitialadunit);
            aabin_deletecountermax = aabin_deletecountermax + 5;
            aabin_deletecounter = 0;
        }
    }

    public void increment_ads_info() {
        aabin_infocounter++;
        if (aabin_infocounter >= aabin_infocountermax) {
            show_ads(aabin_info_interstitialadunit);
            aabin_infocountermax = aabin_infocountermax + 5;
            aabin_infocounter = 0;
        }
    }
    
    public void show_ads(InterstitialAd myad)
    {
        try {
            if (isconnected()) {
                if (myad != null) {
            myad.show((Activity) thisactivity);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
            } else {
                housead(thisactivity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setup_ads() {
        MobileAds.initialize(thisactivity, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(thisactivity,(String) Util.getSing().resources
                        .getText(R.string.aabin_restore_interstitialadid), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        aabin_restore_interstitialadunit = interstitialAd;

                        aabin_restore_interstitialadunit.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdClicked() {
                                // Called when a click is recorded for an ad.
                                Log.d(TAG, "Ad was clicked.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d(TAG, "Ad dismissed fullscreen content.");
                                aabin_restore_interstitialadunit = null;
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.e(TAG, "Ad failed to show fullscreen content.");
                                aabin_restore_interstitialadunit = null;
                            }

                            @Override
                            public void onAdImpression() {
                                // Called when an impression is recorded for an ad.
                                Log.d(TAG, "Ad recorded an impression.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d(TAG, "Ad showed fullscreen content.");
                            }
                        });
                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d(TAG, loadAdError.toString());
                        aabin_restore_interstitialadunit = null;
                    }
                });

        InterstitialAd.load(thisactivity,(String) Util.getSing().resources
                        .getText(R.string.aabin_delete_interstitialadid), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        aabin_delete_interstitialadunit = interstitialAd;

                        aabin_delete_interstitialadunit.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdClicked() {
                                // Called when a click is recorded for an ad.
                                Log.d(TAG, "Ad was clicked.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d(TAG, "Ad dismissed fullscreen content.");
                                aabin_delete_interstitialadunit = null;
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.e(TAG, "Ad failed to show fullscreen content.");
                                aabin_delete_interstitialadunit = null;
                            }

                            @Override
                            public void onAdImpression() {
                                // Called when an impression is recorded for an ad.
                                Log.d(TAG, "Ad recorded an impression.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d(TAG, "Ad showed fullscreen content.");
                            }
                        });
                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d(TAG, loadAdError.toString());
                        aabin_delete_interstitialadunit = null;
                    }
                });

        InterstitialAd.load(thisactivity,(String) Util.getSing().resources
                        .getText(R.string.aabin_info_interstitialadid), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        aabin_info_interstitialadunit = interstitialAd;

                        aabin_info_interstitialadunit.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdClicked() {
                                // Called when a click is recorded for an ad.
                                Log.d(TAG, "Ad was clicked.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d(TAG, "Ad dismissed fullscreen content.");
                                aabin_info_interstitialadunit = null;
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.e(TAG, "Ad failed to show fullscreen content.");
                                aabin_info_interstitialadunit = null;
                            }

                            @Override
                            public void onAdImpression() {
                                // Called when an impression is recorded for an ad.
                                Log.d(TAG, "Ad recorded an impression.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d(TAG, "Ad showed fullscreen content.");
                            }
                        });
                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d(TAG, loadAdError.toString());
                        aabin_info_interstitialadunit = null;
                    }
                });
    }
}