package com.aainc.recyclebin.ui;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.cursoradapter.widget.CursorAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aainc.recyclebin.R;
import com.aainc.recyclebin.adapters.DeletedFilesCursorAdapter;
import com.aainc.recyclebin.database.FeedEntry;
import com.aainc.recyclebin.database.FilesProtectionContentProvider;
import com.aainc.recyclebin.util.Util;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class DeletedFilesViewFragment extends Fragment {

    private static final String TAG = DeletedFilesViewFragment.class.getSimpleName();

    private ProtectedFilesObserver mProtectedFileObserver;
    private ListView mListViewDeletedFiles;
    private DeletedFilesCursorAdapter mCursorAdapter;

    boolean showlog = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.listview_deleted_files, container, false);

        mListViewDeletedFiles = view.findViewById(R.id.listview_deleted_files);
        mListViewDeletedFiles.setEmptyView(view.findViewById(R.id.empty_list_view));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Context context = getContext();
        try {
            Uri uri = FilesProtectionContentProvider.URI;
            String sortOrder = FeedEntry.COLUMN_FILE_NAME + " ASC";

            Cursor cursor = context.getContentResolver().query(uri, null, null, null, sortOrder);

            // Create adapter with proper FLAG_REGISTER_CONTENT_OBSERVER
            mCursorAdapter = new DeletedFilesCursorAdapter(context, cursor, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            mListViewDeletedFiles.setAdapter(mCursorAdapter);

            // Set up content observer to automatically refresh when DB changes
            Handler handler = new Handler(context.getMainLooper());
            mProtectedFileObserver = new ProtectedFilesObserver(handler);

        } catch (Exception ex) {
            if (showlog)
                System.out.println(TAG + " Error: " + ex.getMessage());
        }

        setupAds();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getContext() != null && mProtectedFileObserver != null) {
            getContext().getContentResolver().registerContentObserver(
                    FilesProtectionContentProvider.URI, false, mProtectedFileObserver);
        }

        // Refresh adapter cursor safely
        if (mCursorAdapter != null) {
            Cursor newCursor = mCursorAdapter.getUpdatedCursor(getContext());
            mCursorAdapter.changeCursor(newCursor);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (getContext() != null && mProtectedFileObserver != null) {
            getContext().getContentResolver().unregisterContentObserver(mProtectedFileObserver);
        }
    }

    /**
     * Content observer to watch for changes in deleted/protected files.
     */
    private class ProtectedFilesObserver extends ContentObserver {

        public ProtectedFilesObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            if (showlog)
                System.out.println(TAG + " ContentObserver detected DB change");

            if (mCursorAdapter != null && getContext() != null) {
                Cursor newCursor = mCursorAdapter.getUpdatedCursor(getContext());
                mCursorAdapter.changeCursor(newCursor);
                mCursorAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * Sets up Google Ads Banner.
     */
    public void setupAds() {
        Util.getSing().setup_ads();
        AdView mAdView = getActivity().findViewById(R.id.aabin_banner_adview);
        if (mAdView != null) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }
    }
}
