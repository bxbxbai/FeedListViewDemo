package io.bxbxbai.androiddemos.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by baia on 14-9-21.
 */
public abstract class SimpleBaseAdapter<T> extends BaseAdapter {

    protected List<T> mDataList;
    protected Context mContext;
    protected LayoutInflater mInflater;

    public SimpleBaseAdapter(Context context, List<T> dataList) {
        if (context == null) {
            throw new IllegalArgumentException("context Can NOT be null!");
        }
        if (mDataList == null) {
            throw new IllegalArgumentException("dataList Can NOT be null!");
        }
        mContext = context;
        mDataList = dataList;
        mInflater = LayoutInflater.from(context);
    }

    public SimpleBaseAdapter(Context context) {
        this(context, new ArrayList<T>());
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        if (position >= mDataList.size()) {
            return null;
        }
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    /**
     * 布局文件id
     * @return id
     */
    public abstract int getItemResourceId();

    /**
     *
     * @param position
     * @param convertView
     * @param holder
     * @return
     */
    public abstract View getItemView(int position, View convertView, ViewHolder holder);


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = mInflater.inflate(getItemResourceId(), parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return getItemView(position, convertView, viewHolder);
    }

    public void addAll(List<T> elem) {
        mDataList.addAll(elem);
        notifyDataSetChanged();
    }

    public void remove(T data) {
        mDataList.remove(data);
        notifyDataSetChanged();
    }

    public void remove(int id) {
        mDataList.remove(id);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> elem) {
        mDataList.clear();
        mDataList.addAll(elem);
        notifyDataSetChanged();
    }


    public class ViewHolder {
        private SparseArray<View> views = new SparseArray<View>();
        private View convertView;

        public ViewHolder(View convertView) {
            this.convertView = convertView;
        }

        @SuppressWarnings("unchecked")
        public <T extends View> T getView(int id) {
            View v = views.get(id);
            if (null == v) {
                v = convertView.findViewById(id);
                views.put(id, v);
            }
            return (T)v;
        }
    }
}
