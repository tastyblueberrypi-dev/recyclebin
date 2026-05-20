package com.aainc.recyclebin.model;


public class RootInfo {

    private boolean mIsRecursive;
    private boolean mIsFile;
    private String mRootPath;

    public RootInfo(final String rootPath, boolean isRecursive,  boolean isFile) {

        this.mRootPath = rootPath;
        this.mIsFile = isFile;
        this.mIsRecursive = isRecursive;
    }

    public RootInfo(RootInfo obj) {
        this.mRootPath = new String(obj.getRootPath());
        this.mIsFile = obj.isFile();
        this.mIsRecursive = obj.isRecursive();
    }

    public boolean isRecursive() {
        return mIsRecursive;
    }

    public void setRecursive(boolean recursive) {
        this.mIsRecursive = recursive;
    }

    public String getRootPath() {
        return mRootPath;
    }

    public boolean isFile() {
        return mIsFile;
    }
}
