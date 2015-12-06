package com.zc741.thinking.domain;

/**
 * Created by chong on 2015/12/6.
 */
public class VersionData {
    private String versionName;
    private int versionCode;
    private String description;
    private String downLoadUrl;

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }

    public VersionData(String versionName, int versionCode, String description, String downLoadUrl) {
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.description = description;
        this.downLoadUrl = downLoadUrl;
    }

    @Override
    public String toString() {
        return "versionName=" + versionName + " versionCode=" + versionCode + " description=" + description + " downLoadUrl=" + downLoadUrl;
    }
}
