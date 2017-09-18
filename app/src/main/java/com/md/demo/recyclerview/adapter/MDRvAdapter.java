package com.md.demo.recyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.md.demo.R;

import java.util.ArrayList;

/**
 * This implementation of {@link RecyclerView.Adapter}
 *
 * Created by KyoWang on 2016/06/30 .
 */
public class MDRvAdapter extends RecyclerView.Adapter<MDRvAdapter.ViewHolder>{

    /**
     * 展示数据
     */
    private ArrayList<String> mData;

    /**
     * 事件回调监听
     */
    private OnItemClickListener onItemClickListener;  // 参见下面的内部接口


    public MDRvAdapter(ArrayList<String> data) {
        this.mData = data;  //字符串数组
    }

    public void updateData(ArrayList<String> data) {
        this.mData = data;
        notifyDataSetChanged();  //调用RecyclerView的方法通知，数据变了，要更新
    }

    /**
     * 添加新的Item，在MDLinearRvActivity中调用
     */
    public void addNewItem() {
        if(mData == null) {
            mData = new ArrayList<>();
        }
        mData.add(0, "new Item");
        notifyItemInserted(0);
    }

    /**
     * 删除Item，在MDLinearRvActivity中调用
     */
    public void deleteItem() {
        if(mData == null || mData.isEmpty()) {
            return;
        }
        mData.remove(0);
        notifyItemRemoved(0);
    }

    /**
     * 设置回调监听
     * setter 方法
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) { //参见下面的内部接口，包含2种监听
        this.onItemClickListener = listener;
    }

    /***************************************************/
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {//ViewHolder在下面定义的
        // 实例化展示的view布局
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_rv_item, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);  //实际展示的UI
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) { //ViewHolder在下面定义的
        // 绑定数据
        holder.mTv.setText(mData.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, pos);
                }
                //表示此事件已经消费，不会触发单击事件
                return true;
            }
        });
    }
    /***************************************************/

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }



    //内部 静态类
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTv;

        public ViewHolder(View itemView) { //itemView是一个布局
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.item_tv);  //布局中的一个区域
        }
    }

    //内部 接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);  //一次点击
        void onItemLongClick(View view, int position);  //长按钮
    }
}
