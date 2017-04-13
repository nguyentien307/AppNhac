package com.example.tiennguyen.appnhac;

/**
 * Created by TIENNGUYEN on 3/24/2017.
 */
public class Song {
    private String name;
    private String artist;
    private String url;
    private String bgcover;
    private String mv;
    private String artisturl;

    public Song(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public Song(String name, String artist, String url, String bgcover, String artisturl) {
        this.name = name;
        this.artist = artist;
        this.url = url;
        this.bgcover = bgcover;
        this.artisturl = artisturl;
    }

    public Song(String name, String artist, String url, String bgcover, String mv, String artisturl) {
        this.name = name;
        this.artist = artist;
        this.url = url;
        this.bgcover = bgcover;
        this.mv = mv;
        this.artisturl = artisturl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBgcover() {
        return bgcover;
    }

    public void setBgcover(String bgcover) {
        this.bgcover = bgcover;
    }

    public String getMv() {
        return mv;
    }

    public void setMv(String mv) {
        this.mv = mv;
    }

    public String getArtisturl() {
        return artisturl;
    }

    public void setArtisturl(String artisturl) {
        this.artisturl = artisturl;
    }
}