package com.aainc.recyclebin.storage;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;

import com.aainc.recyclebin.observers.BinContentObserver;
import com.aainc.recyclebin.util.Util;
import com.aainc.recyclebin.database.FeedEntry;
import com.aainc.recyclebin.database.FilesProtectionContentProvider;
import com.aainc.recyclebin.util.Constants;

import java.io.File;
import java.io.IOException;

public class FileSystemHandler {

    private static final String TAG = FileSystemHandler.class.getSimpleName();

    private static FileSystemHandler sFileSystemHandler = null;
    private static Context sContext = null;
    //TODO
    public static boolean showlog = true;

    static {
        System.loadLibrary("native-lib");
    }

    public native int nativeClose(int fileDescriptor) throws IOException;

    public native int nativeOpen(String fileName) throws IOException;

    public native long nativeGetFileSize(int fileDescriptor) throws IOException;

    public native boolean nativeCopy(int fileDescriptor, long fileSize, String fileName) throws IOException;

    public void closeFileDescriptor(int fileDescriptor) throws IOException {
        int returnCode = nativeClose(fileDescriptor);
        if (returnCode != 0) {
            throw new IOException("File with file descriptor " + fileDescriptor + "can't be released.");
        }
    }

    private FileSystemHandler(Context context) {
        sContext = context;
    }

    public static synchronized void initializeFileSystemHandler(Context context) throws NullPointerException {
        if (context == null) {
            throw new NullPointerException("Parameter is invalid. Context can't be null.");
        }
        sContext = context;
    }

    public static synchronized FileSystemHandler getInstance() {

        if (sFileSystemHandler == null) {
            if (sContext == null) {
                return null;
            }
            sFileSystemHandler = new FileSystemHandler(sContext);
        }

        return sFileSystemHandler;
    }

    public void startlisten()
    {
        BinContentObserver imageobserver = new BinContentObserver();
        BinContentObserver videoobserver = new BinContentObserver();
        imageobserver.setContext(sContext);
        imageobserver.setObserveURI(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        videoobserver.setContext(sContext);
        videoobserver.setObserveURI(android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        imageobserver.initializedb();
        videoobserver.initializedb();

        sContext
                .getApplicationContext()
                .getContentResolver()
                .registerContentObserver(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        true, imageobserver);
        sContext
                .getApplicationContext()
                .getContentResolver()
                .registerContentObserver(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        true, videoobserver);
        /*sContext
                .getApplicationContext()
                .getContentResolver()
                .registerContentObserver(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        true, b);
        sContext
                .getApplicationContext()
                .getContentResolver()
                .registerContentObserver(
                        MediaStore.Files.getContentUri("external"),
                        true, b);*/
    }

    //This only puts the FD (or the file) in the local trash folder, to be viewed by my deletedfiles fragment
    //When I press restore is when the file is actually restored.
    public synchronized void protectFileFromDeleting(int fileDescriptor, long fileSize,
                                                     final String path) throws IllegalArgumentException {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("argument. Arguments can't be null or empty");
        }

        String[] parsedPath = path.split(File.separator);
        String fullFileName = parsedPath[parsedPath.length - 1];

        if (showlog)
            System.out.println(TAG + " " +
                    " parsedPath " + path + " fullfilename " +fullFileName );

        String trashFilePath = sContext.getFilesDir() + File.separator + fullFileName;

        try {
            //Prior to Android11, we can simply create new file like this: File newFolder = new File(trashFolder);
            File newFolder = sContext.getFilesDir();
            if (newFolder.mkdirs() || newFolder.isDirectory()) {
                if (showlog)
                {
                    System.out.println(TAG + " before native copy");
                }

                nativeCopy(fileDescriptor, fileSize, trashFilePath);

                if (showlog)
                {
                    System.out.println(TAG + " after native copy");
                }
            } else {
                throw new IOException("Can't create new folder.");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("File with path: " + path + " can't be protected. Please, reopen application.");
        }

        try {
            String[] originalPath = path.split(fullFileName);
            Uri insertUri = FilesProtectionContentProvider.URI;
            ContentValues contentValues = new ContentValues();
            contentValues.put(FeedEntry.COLUMN_FILE_NAME, fullFileName);
            contentValues.put(FeedEntry.COLUMN_ORIGINAL_PATH, originalPath[0]);
            contentValues.put(FeedEntry.COLUMN_TRASH_PATH, trashFilePath);
            if (showlog)
            {
                System.out.println("mtag fullfile name: " + fullFileName);
                System.out.println("mtag originalPath[0]: " + originalPath[0]);
                System.out.println("mtag trashFilePath: " + trashFilePath);
                System.out.println("mtag insertUri: " + insertUri);
            }
            sContext.getContentResolver().insert(insertUri, contentValues);
        } catch (NullPointerException ex) {
            if (showlog)
                System.out.println(TAG + " Error message: " + ex.getMessage() + ex.getCause());
        }

        try {
            nativeClose(fileDescriptor);

        } catch (IOException ex) {
            if (showlog)
                System.out.println(TAG + " Error message: " + ex.getMessage() + ex.getCause());
        }
    }

    // Gets the .trash folder path
    /*public static String getTrashFolder(String pathFile) throws IllegalArgumentException {

        if (pathFile == null) {
            throw new IllegalArgumentException("Parameter is invalid. File path can't be null.");
        }

        StringBuilder pathBuilder = new StringBuilder();
        try {
            StorageHelper storageHelper = StorageHelper.getInstance();
            String appDataDir = storageHelper.getApplicationDataDirectory(sContext, pathFile);

            pathBuilder.append(appDataDir);

        } catch (IllegalArgumentException | NullPointerException ex) {
            if (showlog)
                System.out.println(TAG + "Error message: " + ex.getMessage() + ex.getCause());
            if (pathFile.indexOf(Constants.INNER_DIR_PREFIX) == 0) {
                pathBuilder.append(Constants.INNER_DIR_PREFIX);

            } else {
                String[] pathDirsName = pathFile.split(File.separator);
                pathBuilder.append(File.separator);
                pathBuilder.append(pathDirsName[1]);
                pathBuilder.append(Constants.EXTERNAL_DIR_PREFIX);

            }
            pathBuilder.append(File.separator);
            pathBuilder.append(Constants.PACKAGE_PREFIX);

        }

        pathBuilder.append(File.separator);
        pathBuilder.append(Constants.TRASH_FOLDER_NAME);
        pathBuilder.append(File.separator);

        if (showlog)
            System.out.println("mtag trash folder is " + pathBuilder.toString());

        return pathBuilder.toString();
    }*/
}