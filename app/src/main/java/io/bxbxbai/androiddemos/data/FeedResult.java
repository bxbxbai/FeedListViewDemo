package io.bxbxbai.androiddemos.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by baia on 14-9-14.
 */
public class FeedResult implements Serializable {

    @SerializedName("feed")
    private List<FeedItem> feedItems;


    public List<FeedItem> getFeedItems() {
        return feedItems;
    }

    private void setFeedItems(List<FeedItem> feedItems) {
        this.feedItems = feedItems;
    }
}
