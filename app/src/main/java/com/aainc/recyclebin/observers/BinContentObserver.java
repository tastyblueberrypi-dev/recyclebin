package com.aainc.recyclebin.observers;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;

import com.aainc.recyclebin.storage.FileSystemHandler;
import com.aainc.recyclebin.util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class BinContentObserver extends ContentObserver {

    public String TAG = BinContentObserver.class.getSimpleName();
    public FileSystemHandler mFileSystemHandler = null;
    Context thisc;

    public HashMap<Integer, String> hashmapMedia = new HashMap<Integer, String>();
    public HashMap<String, Integer> mapOpenedFileDisc = new HashMap<>();
    public HashMap<String, Long> mapOpenedFileSize = new HashMap<>();

    Uri mediaUri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    //TODO need to closeFD if called, or service was destroyed
    boolean showlog = true;

    public BinContentObserver() {
        super(null);
        thisc = Util.getSing().thisactivity;
        this.mFileSystemHandler = FileSystemHandler.getInstance();
        if (mFileSystemHandler == null) {

        }
        //before initialize db, clear the FD list
        mapOpenedFileDisc.clear();
        mapOpenedFileSize.clear();
        hashmapMedia.clear();
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        if (showlog)
            System.out.println("BinContentObserver onChange()  URI: " + uri.toString());

        try {
            checkdbstate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onChange(boolean selfChange) {
        onChange(selfChange, null);
    }

    public void initializedb() {
        ContentResolver cresolver = thisc.getContentResolver();
        initializehashmap(cresolver, mediaUri, hashmapMedia);
    }

    public void initializehashmap(ContentResolver r, Uri u, HashMap h) {
        h.clear();

        Cursor cursor = r.query(u, null, null, null, null);

        int numrow = cursor.getCount();
        if (numrow == 0) {

        } else if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                String path = cursor.getString(cursor.getColumnIndex("_data"));

                if (path == null) {
                    continue;
                }
                if (showlog)
                    //System.out.println("mtag BINCONTENTOBSERVER initializedb id:" + id + " path:" +path);

                    h.put(id, path);

                if (mapOpenedFileDisc.containsKey(path)) {
                    //this is already in the hashmap, do nothing
                } else {
                    addFD(path);
                }

            } while (cursor.moveToNext());
        }
        if (showlog)
            System.out.println("BinContentObserver initializehashmap db hashmap " + h.size());
    }

    public synchronized void addFD(String openFileDir) {
        int fileDescriptor = -1;
        try {

            if (openFileDir == null) {

            } else if (mFileSystemHandler == null) {

            } else {
                if (showlog)
                    //System.out.println("mtag BINCONTENTOBSERVER openFileDir is " +openFileDir);

                    fileDescriptor = mFileSystemHandler.nativeOpen(openFileDir);

                if (fileDescriptor < 0) {
                    throw new IOException("File with name: " + openFileDir + " can't be opened and protected.");
                }
                if (showlog) {
                    //System.out.println("mtag BINCONTENTOBSERVER fileDescriptor is " +fileDescriptor);
                }

                long fileSize = mFileSystemHandler.nativeGetFileSize(fileDescriptor);

                if (fileSize < 0) {
                    throw new IOException("Can't identify size of file with name: " + openFileDir + ". File can't be protected.");
                }

                mapOpenedFileDisc.put(openFileDir, fileDescriptor);
                mapOpenedFileSize.put(openFileDir, fileSize);
            }
        } catch (UnsupportedOperationException | IllegalArgumentException | ClassCastException | IOException ex) {

            if (showlog)
                System.out.println(TAG + " Error message: " + ex.getMessage() + ex.getCause());
            try {
                if (fileDescriptor != -1) {
                    mFileSystemHandler.closeFileDescriptor(fileDescriptor);
                }

            } catch (IOException ioEx) {
                if (showlog)
                    System.out.println(TAG + " Error message: " + ex.getMessage() + ex.getCause());
            }
        }
    }

    public void checkdbstate() {
        if (showlog)
            System.out.println("BinContentObserver checkdbstate called ");

        ContentResolver cresolver = thisc.getContentResolver();

        if (showlog)
            System.out.println("BinContentObserver checkdbstate contentresolver not null ");
        checkdbstatehashmap(cresolver, mediaUri, hashmapMedia);
    }

    public void checkdbstatehashmap(ContentResolver r, Uri u, HashMap h) {
        Cursor cursor = r.query(u, null, null, null, null);
        int numrow = cursor.getCount();
        if (numrow == 0) {
            if (showlog)
                System.out.println("BinContentObserver checkstate db numrow was 0, size of hashmap " + h.size());
        } else {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex("_id"));
                    String path = cursor.getString(cursor.getColumnIndex("_data"));

                    if (showlog)
                        //System.out.println("mtag BINCONTENTOBSERVER checkstate id:" + id + " path:" +path);

                        if (h.containsKey(id)) {
                            // Logic here is to wittle down the hashmap, whats left over has obviously been deleted
                            h.remove(id);

                            if (showlog) {
                                //System.out.println("mtag BINCONTENTOBSERVER checkstate hashmapMedia contains this id: " + id + ", removes it from the hash map");
                            }
                        } else {
                            // The case when ID is not in the hashmap, do nothing because it will later get added through final initializedb call
                        }
                } while (cursor.moveToNext());
            }
        }

        if (showlog)
            System.out.println("BinContentObserver checkdbstatehashmap db hashmap " + h.size());

        // handle delete for all the leftover keys, these are items that are in open as FD but nolonger in contentprovider db
        Set set = h.entrySet();
        Iterator iterator = set.iterator();
        int c = 0;
        while (iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry) iterator.next();
            handleDeleteEvent((String) mentry.getValue());
            c++;
        }

        if (showlog)
            System.out.println("BinContentObserverR checkstate number of items restored: " + c);

        //repopulate the hashmap after the restore
        initializedb();
    }

    public void setContext(Context c) {
        thisc = c;
    }

    public void setObserveURI(Uri u) {
        mediaUri = u;
    }

    private synchronized void handleDeleteEvent(final String eventObjectPath) throws ClassCastException, IllegalArgumentException {

        if (eventObjectPath == null) {
            throw new IllegalArgumentException("Parameter is invalid. Event path can't be null.");
        }

        Integer openedFileDescriptor = mapOpenedFileDisc.get(eventObjectPath);
        Long openedFileSize = mapOpenedFileSize.get(eventObjectPath);

        if (openedFileDescriptor != null && openedFileSize != null) {
            if (showlog)
                System.out.println("BinContentObserver" + " " +
                        " openedFileDescriptor " + openedFileDescriptor + " openedFileSize " + openedFileSize + " eventObjectPath " + eventObjectPath);
            mFileSystemHandler.protectFileFromDeleting(openedFileDescriptor, openedFileSize, eventObjectPath);
            mapOpenedFileDisc.remove(eventObjectPath);
            mapOpenedFileSize.remove(eventObjectPath);
        } else {
        }
    }
}