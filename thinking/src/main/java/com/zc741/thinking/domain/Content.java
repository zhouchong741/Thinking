package com.zc741.thinking.domain;

/**
 * Created by chong on 2015/12/1.
 */
public class Content {
    private String word;
    private String pic;

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
        return "word=" + word + "pic=" + pic;
    }

    public Content(String word, String pic) {
        this.word = word;
        this.pic = pic;
    }
}
