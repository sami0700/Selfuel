package com.panshul.selfuel.Model;

public class PlaylistModel {
    String playListName;
    String playListLink;
    String playListBy;

    public PlaylistModel(String playListName, String playListLink, String playListBy) {
        this.playListName = playListName;
        this.playListLink = playListLink;
        this.playListBy = playListBy;
    }

    public String getPlayListName() {
        return playListName;
    }

    public void setPlayListName(String playListName) {
        this.playListName = playListName;
    }

    public String getPlayListLink() {
        return playListLink;
    }

    public void setPlayListLink(String playListLink) {
        this.playListLink = playListLink;
    }

    public String getPlayListBy() {
        return playListBy;
    }

    public void setPlayListBy(String playListBy) {
        this.playListBy = playListBy;
    }
}
