package com.example.yskh0.todo_recyclerview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TodoAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private int layout;
    private List<Todo> list;
    public TodoAdapter(Context c, int layout, List<Todo> list){
        inflater=((Activity)c).getLayoutInflater();
        this.layout=layout;
        this.list=list;

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Todo getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Todo todo=this.getItem(position);
        ViewHolder holder;
        if(convertView == null){
            convertView=inflater.inflate(this.layout,null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.iv.setImageResource(todo.getResId());
        holder.tvTodo.setText(todo.getContents());
        return convertView;
    }
    private static class ViewHolder{
        TextView tvTodo;
        ImageView iv;
        public ViewHolder(View v){
            tvTodo=(TextView)v.findViewById(R.id.tvTodo);
            iv=(ImageView)v.findViewById(R.id.iv);
        }
    }

}

