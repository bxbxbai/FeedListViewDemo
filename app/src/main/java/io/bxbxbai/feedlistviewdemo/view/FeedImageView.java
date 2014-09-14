package io.bxbxbai.feedlistviewdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

/**
 * Created by baia on 14-9-11.
 */
public class FeedImageView extends ImageView {


    public interface ResponseObserver {
        public void onError();

        public void onSuccess();
    }

    private ResponseObserver mObserver;

    public void setResponseObserver(ResponseObserver observer) {
        this.mObserver = observer;
    }

    /**
     * the url of the network image to load
     */
    private String mUrl;

    /**
     * resource ID of the image to be used as a placeholder until the network image is loaded.
     */
    private int mDefaultImageId;
    /**
     * resource ID of the image to be used if the network response fails.
     */
    private int mErrorImageId;

    /**
     * Local copy of the ImageLoader
     */
    private ImageLoader mImageLoader;

    /**
     * Current ImageContainer. (either in-flight or finished)
     */
    private ImageLoader.ImageContainer mImageContainer;

    public FeedImageView(Context context) {
        super(context);
    }

    public FeedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FeedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Sets URL of the image that should be loaded into this view. Note that calling this will
     * immediately either set the cached image (if available) or the default image specified by
     *
     * @param url Url
     * @param loader Image Loader
     */
    public void setImageUrl(String url, ImageLoader loader) {
        mUrl = url;
        mImageLoader = loader;
        loadImageIfNecessary(false);
    }

    /**
     * sets the default image resource ID to be used for this view until the attempt to load it
     * completely
     * @param defaultImageResId
     */
    public void setDefaultImageResId(int defaultImageResId) {
        mDefaultImageId = defaultImageResId;
    }

    public void setErrorImageResId(int errorImageResId) {
        mErrorImageId = errorImageResId;
    }

    private void loadImageIfNecessary(final boolean isInLayoutPass) {
        final int width = getWidth();
        int height = getHeight();

        boolean isFullyWrapContent = getLayoutParams() != null
                && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT
                && getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT;
        //if the view's bounds aren't known yet, and this is not a wrap-content/wrap-content view,
        // hold off on loading the image.
        if (width == 0 && height == 0 && !isFullyWrapContent) {
            return;
        }

        //if the URL to be loaded in this view is empty, cancel any old requests and clear the
        //currently loaded image.
        if (TextUtils.isEmpty(mUrl)) {
            if (mImageContainer != null) {
                mImageContainer.cancelRequest();
                mImageContainer = null;
            }
            setDefaultImageOrNot();
            return;
        }

        // if there was an old request in the view, check if it needs canceled.
        if (mImageContainer != null && mImageContainer.getRequestUrl() != null) {
            if (mImageContainer.getRequestUrl().equals(mUrl)) {
                return;
            } else {
                // if there is a pre-existing request, cancel it if it's fetching a different URL
                mImageContainer.cancelRequest();
                setDefaultImageOrNot();
            }
        }

        // The Pre-existing content of this view didn't match the current URL. Load the new image
        // from the network
        //如果先前那个URL不等于现在需要加载的URL，那么就重新下载图片
        ImageLoader.ImageContainer newContainer = mImageLoader.get(mUrl,
                new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(final ImageLoader.ImageContainer response, boolean isImmediate) {
                        if (isImmediate && isInLayoutPass) {
                            post(new Runnable() {
                                @Override
                                public void run() {
                                    onResponse(response, false);
                                }
                            });
                            return;
                        }
                        int bWidth = 0, bHeight = 0;
                        Bitmap bitmap = response.getBitmap();
                        if (bitmap != null) {
                            setImageBitmap(bitmap);
                            bWidth = bitmap.getWidth();
                            bHeight = bitmap.getHeight();
                            adjustImageAspect(bWidth, bHeight);
                        } else if (mDefaultImageId != 0) {
                            setImageResource(mDefaultImageId);
                        }

                        if (mObserver != null) {
                            mObserver.onSuccess();
                        }

                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (mErrorImageId != 0 ) {
                            setImageResource(mErrorImageId);
                        }
                        if (mObserver != null) {
                            mObserver.onError();
                        }
                    }
                });
        mImageContainer = newContainer;
    }

    private void setDefaultImageOrNot() {
        if (mDefaultImageId != 0) {
            setImageResource(mDefaultImageId);
        } else {
            setImageBitmap(null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        loadImageIfNecessary(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        //取消request
        if (mImageContainer != null) {
            mImageContainer.cancelRequest();
            setImageBitmap(null);
            mImageContainer = null;
        }
        super.onDetachedFromWindow();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }

    private void adjustImageAspect(int width, int height) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)getLayoutParams();
        if (width == 0 || height == 0) {
            return;
        }
        int sWidth = getWidth();
        int newHeight = sWidth * width / height;
        params.width = sWidth;
        params.height = newHeight;
        setLayoutParams(params);

    }
}
