package com.example.duty.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duty.R;

import java.util.ArrayList;

public class ToolbarAdapter extends RecyclerView.Adapter<ToolbarAdapter.tbViewHolder> {
    private ArrayList<String> mData = null ;

    public ToolbarAdapter(ArrayList<String> list){
        mData = list;
    }

    public static class tbViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        tbViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.tb_image1);
            textView = view.findViewById(R.id.tb_tv1);

        }
    }


    @Override
    public ToolbarAdapter.tbViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.toolbar_item, parent, false);
        ToolbarAdapter.tbViewHolder vh = new ToolbarAdapter.tbViewHolder(view);

        return vh;

    }

    @Override
    public void onBindViewHolder(ToolbarAdapter.tbViewHolder holder, int position) {
        String text = mData.get(position);
        holder.textView.setText(text);
    }

    // Recycler 안에 들어갈 ViewHolder의 개수
    @Override
    public int getItemCount() {
        return mData.size();
    }


}

