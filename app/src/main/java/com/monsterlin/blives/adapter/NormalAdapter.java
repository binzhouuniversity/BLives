package com.monsterlin.blives.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.monsterlin.blives.R;
import com.monsterlin.blives.entity.SchoolNews;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by monsterLin on 2016/4/5.
 */
public class NormalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;


    private Context mContext;
    private List<SchoolNews> newsList;


    public NormalAdapter(Context mContext, List<SchoolNews> newsList) {
        this.mContext = mContext;
        this.newsList = newsList;
    }


    /**
     * 声明一个接口，用于实现点击事件
     */
    public interface  OnItemClickListener{
        void OnItemClick(int position, View view);
        void OnItemLongClick(int position, View view);
    }

    private OnItemClickListener mOnItemClickListener;

    public  void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener=listener;
    }



    // RecyclerView的count设置为数据总条数+ 1（footerView）

    @Override
    public int getItemCount() {
        return newsList.size()+1;
    }


    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_news, parent,
                    false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_foot, parent,
                    false);
            return new FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {

            if (newsList.get(position).getNewsimg()!=null){
                BmobFile imgFile = newsList.get(position).getNewsimg();
                imgFile.loadImage(mContext,((ItemViewHolder) holder).iv_show_img);
            }else {
                ((ItemViewHolder) holder).iv_show_img.setImageResource(R.drawable.ic_nopic);
            }

            ((ItemViewHolder) holder).tv_title.setText(cutText(newsList.get(position).getTitle()));
            ((ItemViewHolder) holder).tv_content.setText(cutText(newsList.get(position).getContent()));
            ((ItemViewHolder) holder).tv_date.setText(StringFormate(newsList.get(position).getNewsdate().getDate()));



            if (mOnItemClickListener!=null){

                holder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int LayoutPosition=holder.getLayoutPosition(); //得到布局的position
                        mOnItemClickListener.OnItemClick(LayoutPosition,holder.itemView);

                    }
                });

                //longclickListener
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int LayoutPosition=holder.getLayoutPosition(); //得到布局的position
                        mOnItemClickListener.OnItemLongClick(LayoutPosition,holder.itemView);
                        return false;
                    }
                });
            }
        }

    }


    /**
     * 格式化时间
     * @param date
     * @return
     */
    private String StringFormate (String date){
        String dateString;
        dateString = date.substring(0,10);
        return dateString ;
    }
    /**
     * 剪切文本
     * @param allText
     * @return
     */
    private String cutText(String allText) {
        int length = allText.length();
        if(length>=11){
            String text = allText.substring(0,10)+"....";
            return text;
        }else {
            return  allText;
        }

    }
    //布局ViewHolder

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_show_img;
        TextView tv_title , tv_content , tv_date ;

        public ItemViewHolder(View itemView) {
            super(itemView);
            iv_show_img= (ImageView) itemView.findViewById(R.id.iv_show_img);
            tv_title= (TextView) itemView.findViewById(R.id.tv_title);
            tv_content= (TextView) itemView.findViewById(R.id.tv_content);
            tv_date= (TextView) itemView.findViewById(R.id.tv_date);
        }

    }

    static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }

}
