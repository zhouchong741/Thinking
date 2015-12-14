package com.zc741.thinking.domain;

/**
 * Created by chong on 2015/12/1.
 */
public class Content {
    private int num;
    private String word;
    private String pic;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "num=" + num + ", word=" + word + ", pic=" + pic;
    }

    public Content(int num, String word, String pic) {
        this.num = num;
        this.word = word;
        this.pic = pic;
    }
}
