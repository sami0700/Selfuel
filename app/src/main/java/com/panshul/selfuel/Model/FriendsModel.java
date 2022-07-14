package com.panshul.selfuel.Model;

public class FriendsModel {
    String name;
    String score;
    String friendId;

    public FriendsModel(String name, String score, String friendId) {
        this.name = name;
        this.score = score;
        this.friendId = friendId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }
}
