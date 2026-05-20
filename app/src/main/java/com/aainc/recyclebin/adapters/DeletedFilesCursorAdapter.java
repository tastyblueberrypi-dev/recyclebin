package com.aainc.recyclebin.adapters;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.media.MediaScannerConnection;
import android.net.Uri;

import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.cursoradapter.widget.CursorAdapter;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aainc.recyclebin.util.Str;
import com.aainc.recyclebin.util.Util;
import com.aainc.recyclebin.R;
import com.aainc.recyclebin.database.FeedEntry;
import com.aainc.recyclebin.database.FilesProtectionContentProvider;
import com.aainc.recyclebin.util.DateConverter;
import com.aainc.recyclebin.util.FileHelper;
import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLDataException;

public class DeletedFilesCursorAdapter extends CursorAdapter {

    private static final String TAG = DeletedFilesCursorAdapter.class.getSimpleName();

    //TODO
    private boolean showlog = false;

    private SparseBooleanArray mExpandState = new SparseBooleanArray();

    public DeletedFilesCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);

        for (int i = 0; i < cursor.getCount(); i++) {
            mExpandState.append(i, false);
        }
    }

    public DeletedFilesCursorAdapter(Context context, Cursor cursor, boolean autoRequery) {
        super(context, cursor, autoRequery);

        for (int i = 0; i < cursor.getCount(); i++) {
            mExpandState.append(i, false);
        }
    }

    private class ViewHolder {
        private TextView textViewFileName;
        private TextView textViewDeletedDate;
        private TextView textViewOriginPath;

        private Button buttonActionRestore;
        private Button buttonActionDelete;
        private Button buttonActionInfo;

        private MaterialCardView expandLinLayout;
        private RelativeLayout expandButtonLayout;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.listview_item_deleted_files, parent, false);

        MaterialCardView expandLinLayout =
                view.findViewById(R.id.expandablelinearlayout);
        RelativeLayout expandButtonLayout = (RelativeLayout) view.findViewById(R.id.button_expand_layout_info);

        TextView fileName = (TextView) view.findViewById(R.id.textview_file_name);
        //TextView fileSize = (TextView) view.findViewById(R.id.textview_deleted_size);
        TextView deletedDate = (TextView) view.findViewById(R.id.textview_deleted_date);
        TextView originPath = (TextView) view.findViewById(R.id.textview_original_path);
        Button actionRestore = (Button) view.findViewById(R.id.button_action_restore);
        Button actionDelete = (Button) view.findViewById(R.id.button_action_delete);
        Button actionInfo = (Button) view.findViewById(R.id.button_action_info);

        fileName.setSelected(true);
        originPath.setSelected(true);

        ViewHolder holder = new ViewHolder();
        holder.textViewFileName = fileName;
        holder.textViewOriginPath = originPath;
        holder.textViewDeletedDate = deletedDate;

        holder.buttonActionRestore = actionRestore;
        holder.buttonActionDelete = actionDelete;
        holder.buttonActionInfo = actionInfo;

        holder.expandLinLayout = expandLinLayout;
        holder.expandButtonLayout = expandButtonLayout;

        view.setTag(R.id.VIEW_HOLDER_TAG_KEY, holder);

        return view;
    }

    @Override
    public void bindView(final View view, final Context context, final Cursor cursor) {

        //Util.getSing().logMessage(TAG, "bindView method");

        final int position = cursor.getPosition();

        final ViewHolder holder = (ViewHolder) view.getTag(R.id.VIEW_HOLDER_TAG_KEY);

        holder.textViewFileName.setText(cursor.getString(1));
        //holder.textViewOriginPath.setText(cursor.getString(2));
        holder.textViewOriginPath.setText(Str.getSing().bin_str_datedeleted);

        String date = DateConverter.formatString2StringConsideringTimeZone(cursor.getString(4));
        holder.textViewDeletedDate.setText(date);

        holder.expandButtonLayout.setRotation(mExpandState.get(position) ? 180f : 0f);
        holder.expandButtonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                boolean isExpanded = mExpandState.get(position, false); // default false
                mExpandState.put(position, !isExpanded); // toggle

                holder.expandLinLayout.setVisibility(!isExpanded ? View.VISIBLE : View.GONE);

                // Optional: rotate the expand button
                ObjectAnimator animator = ObjectAnimator.ofFloat(holder.expandButtonLayout, "rotation",
                        isExpanded ? 180f : 0f, isExpanded ? 0f : 180f);
                animator.setDuration(300);
                animator.setInterpolator(new FastOutSlowInInterpolator());
                animator.start();
            }
        });

        Integer columnID = Integer.valueOf(cursor.getInt(0));
        holder.buttonActionRestore.setTag(R.id.ROW_ID, columnID);
        holder.buttonActionDelete.setTag(R.id.ROW_ID, columnID);
        holder.buttonActionInfo.setTag(R.id.ROW_ID, columnID);

        holder.buttonActionRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    // ads related
                    Util.getSing().increment_ads_restore();

                    Integer rowID = (Integer) v.getTag(R.id.ROW_ID);

                    String fileName = holder.textViewFileName.getText().toString();
                    String date = holder.textViewDeletedDate.getText().toString();
                    date = "";
                    String restoredFileName = FileHelper.getFileNameWithDeletedDate(fileName, date);
                    //String destinationPath = holder.textViewOriginPath.getText().toString() + restoredFileName;
                    String destinationPath = cursor.getString(2) + restoredFileName;

                    synchronized (this) {
                        String sourceFilePath = getTrashPathFromDB(context, rowID);

                        FileHelper.copyingFile(sourceFilePath, destinationPath);

                        // necessary for gallery to re-see this file
                        MediaScannerConnection.scanFile(context,
                                new String[]{destinationPath}, null,
                                new MediaScannerConnection.OnScanCompletedListener() {
                                    public void onScanCompleted(String path, Uri uri) {
                                        if (showlog)
                                            System.out.println("mtag file restored content updated ");
                                    }
                                });

                        if (!FileHelper.removeFile(sourceFilePath)) {
                            //Toast.makeText(context, "File was restoring, but data about restored file can't be removed from a database and device storage.", Toast.LENGTH_LONG).show();
                            throw new IOException("Restored file can't be deleted from a database protected files and device storage.");
                        }

                        int countDeletedRows = deleteRowFromDB(context, rowID);

                        if (countDeletedRows == 0) {
                            //Toast.makeText(context, "File was restoring, but data about restored file can't be removed from a database.", Toast.LENGTH_LONG).show();
                            throw new SQLiteException("Data about restored file can't be removed from a database.");
                        }
                    }
                    Toast.makeText(context, Str.getSing().bin_str_restored, Toast.LENGTH_SHORT).show();

                    Cursor newCursor = getUpdatedCursor(context);

                    holder.expandLinLayout.setVisibility(mExpandState.get(position) ? View.VISIBLE : View.GONE);
                    mExpandState.put(cursor.getPosition(), false);

                    DeletedFilesCursorAdapter.this.changeCursor(newCursor);

                } catch (SQLException | SQLDataException | FileNotFoundException | IllegalArgumentException ex) {


                } catch (IOException ex) {

                }
            }
        });

        holder.buttonActionDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (context != null) {
                    // ads related
                    Util.getSing().increment_ads_delete();

                    AlertDialog alertDialog = new AlertDialog.Builder(context)
                            .create();
                    alertDialog.setTitle(Str.getSing().bin_alerttitle_delete);
                    alertDialog.setMessage(Str.getSing().bin_alertbody_delete);

                    alertDialog.setButton(Dialog.BUTTON_POSITIVE, Str.getSing().bin_str_delete,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        Integer rowID = (Integer) v.getTag(R.id.ROW_ID);
                                        String sourceFilePath = getTrashPathFromDB(context, rowID);

                                        if (!FileHelper.removeFile(sourceFilePath)) {
                                            throw new IOException("Can't delete file from devices storage.");
                                        }

                                        int countDeletedRows = deleteRowFromDB(context, rowID);

                                        if (countDeletedRows == 0) {
                                            throw new SQLiteException("File was deleting, but data about restored file can't be removed from a database.");
                                        }

                                        Toast.makeText(context, Str.getSing().bin_str_deleted, Toast.LENGTH_SHORT).show();

                                        Cursor newCursor = getUpdatedCursor(context);

                                        holder.expandLinLayout.setVisibility(mExpandState.get(position) ? View.VISIBLE : View.GONE);
                                        mExpandState.put(cursor.getPosition(), false);

                                        DeletedFilesCursorAdapter.this.changeCursor(newCursor);
                                    } catch (SQLiteException | SQLDataException | IOException ex) {

                                    }
                                }
                            });

                    alertDialog.setButton(Dialog.BUTTON_NEGATIVE, Str.getSing().tra_alertbutton_back,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                    alertDialog.show();
                }
            }
        });

        holder.buttonActionInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    // ads related
                    Util.getSing().increment_ads_info();

                    Integer rowID = (Integer) v.getTag(R.id.ROW_ID);
                    String sourceFilePath = getTrashPathFromDB(context, rowID);
                    File clickedFile = new File(sourceFilePath);
                    System.out.println("bob " + sourceFilePath + " bob "+clickedFile.getAbsolutePath());
                    if (clickedFile.isDirectory()) {

                    } else {
                        if (showlog)
                            System.out.println("mtag clicked: " + clickedFile.getName());

                        String ext = MimeTypeMap.getFileExtensionFromUrl(clickedFile.getName());
                        try {
                            // Open based on file extension, able to distinguish all file
                            // types
                            MimeTypeMap extmap = MimeTypeMap.getSingleton();
                            String type = extmap.getMimeTypeFromExtension(ext);

                            if (type == null)
                                type = "*/*";

                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            Uri data = Util.getSing().getUrifromFile(clickedFile);
                            System.out.println("mtag type: " + type + " uri " + data);
                            context.grantUriPermission(context.getPackageName(), data, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            intent.setDataAndType(data, type);
                            //This is necsesary for things to work
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            context.startActivity(intent);
                        } catch (Exception e) {
                            // The reason this is here is because some phones can't open zipped files and will break
                            // Other phones will simply redirect you to open the zip file with another program
                            if (ext.equals("zip")) {

                                //Util.getSing().toastMessage(Str.getSing().tra_alertbody_error19);
                            } else {
                                e.printStackTrace();
                                //Util.getSing().toastMessage(Str.getSing().tra_alertbody_error20);
                            }
                        }
                    }

                    Cursor newCursor = getUpdatedCursor(context);

                    DeletedFilesCursorAdapter.this.changeCursor(newCursor);

                } catch (SQLiteException | SQLDataException ex) {
                    if (showlog)
                        System.out.println(TAG + "Error :" + ex.getMessage() + ex.getCause());
                }
            }
        });
    }

    private int deleteRowFromDB(Context context, int rowID) {

        Uri uri = FilesProtectionContentProvider.URI;

        String where = FeedEntry.COLUMN_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(rowID)};

        return context.getContentResolver().delete(uri, where, selectionArgs);
    }

    public Cursor getUpdatedCursor(Context context) throws IllegalArgumentException {

        Uri uri = FilesProtectionContentProvider.URI;
        String sortOrder = FeedEntry.COLUMN_FILE_NAME + " ASC";
        final Cursor cursor = context.getContentResolver().query(uri, null, null, null, sortOrder);
        return cursor;
    }

    public String getTrashPathFromDB(Context context, int rowID) throws SQLDataException {

        Uri.Builder uriBuilder = new Uri.Builder();

        uriBuilder.scheme(FilesProtectionContentProvider.URI.getScheme());
        uriBuilder.authority(FilesProtectionContentProvider.URI.getAuthority());

        for (String pathSegment : FilesProtectionContentProvider.URI.getPathSegments()) {
            uriBuilder.appendPath(pathSegment);
        }
        uriBuilder.appendEncodedPath(String.valueOf(rowID));

        String[] projection = {FeedEntry.COLUMN_TRASH_PATH};
        Cursor itemRowCursor = context.getContentResolver().query(uriBuilder.build(), projection, null, null, null);

        if (itemRowCursor != null && itemRowCursor.moveToFirst()) {
            String trashFilePath = itemRowCursor.getString(0);

            if (trashFilePath != null) {
                return trashFilePath;

            } else {
                throw new SQLDataException("Can't get data from database.");
            }
        } else {
            throw new SQLDataException("Can't get data from database.");
        }

    }

    private ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(new android.view.animation.LinearInterpolator());
        return animator;
    }
}
