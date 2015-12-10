package com.zc741.thinking.domain;

/**
 * Created by chong on 2015/12/10.
 */
public class SplashPic {
    private String splPic;

    public String getSplPic() {
        return splPic;
    }

    public void setSplPic(String splPic) {
        this.splPic = splPic;
    }

    @Override
    public String toString() {
        return "splPic=" + splPic;
    }

    public SplashPic(String splPic) {
        this.splPic = splPic;
    }
}
