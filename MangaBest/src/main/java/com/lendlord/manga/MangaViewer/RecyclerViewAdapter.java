package com.lendlord.manga.MangaViewer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lendlord.manga.R;

import java.util.List;

/**
 * Created by lendlord on 11.10.15.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {
    List<MangaChapterData> contents;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    public RecyclerViewAdapter(List<MangaChapterData> contents) {

        this.contents = contents;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }


    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_card_small, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.chapterName.setText(this.contents.get(position).getChapterName());
        holder.chkSelected.setSelected(this.contents.get(position).isChecked());
        holder.chkSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                CheckBox cb = (CheckBox) v;
                contents.get(position).setIsChecked(cb.isChecked());
            }
        });
        notifyItemChanged(position);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView chapterName;

        public CheckBox chkSelected;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            chapterName = (TextView) itemLayoutView.findViewById(R.id.info_text);

            chkSelected = (CheckBox) itemLayoutView
                    .findViewById(R.id.checkBox);

        }

    }
}

