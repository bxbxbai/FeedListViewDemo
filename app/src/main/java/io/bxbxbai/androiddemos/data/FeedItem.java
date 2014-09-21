package io.bxbxbai.androiddemos.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by baia on 14-9-11.
 */
public class FeedItem {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("status")
    private String status;

    @SerializedName("image")
    private String imageUrl;

    @SerializedName("profilePic")
    private String profilePic;

    @SerializedName("timeStamp")
    private String timeStamp;

    @SerializedName("url")
    private String url;

    public FeedItem() {
    }

    public FeedItem(int id, String name, String imageUrl, String status,
                    String profilePic, String timeStamp, String url) {
        super();
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.status = status;
        this.profilePic = profilePic;
        this.timeStamp = timeStamp;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
