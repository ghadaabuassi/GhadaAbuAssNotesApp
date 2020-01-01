package com.notesapp.ghadacoder2015.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.notesapp.ghadacoder2015.R;
import com.notesapp.ghadacoder2015.model.CategoriesClass;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesPageAdabter extends RecyclerView.Adapter<CategoriesPageAdabter.ViewHolder> {
    @NonNull

    private List<CategoriesClass> itemList;
    private Context mcontext;
    private OnItemClickListener onItemClickListener;

    public CategoriesPageAdabter(Context context, ArrayList<CategoriesClass> categoriesAdapterArrayList, OnItemClickListener listener) {
        this.mcontext = context;
        this.itemList = categoriesAdapterArrayList;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(itemList.get(position), onItemClickListener);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public void clear() {
        itemList.clear();
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(CategoriesClass item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_name_categories)
        TextView nameCategories;


        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        private void bind(CategoriesClass item, OnItemClickListener onItemClickListener) {

           // Glide.with(itemView.getContext()).load(Constants.URL_IMAGE + "" + item.imageUrl()).into(imageCategories);

                nameCategories.setText(item.getName());

            itemView.setOnClickListener(view -> {
                onItemClickListener.onItemClick(item);

            });

        }
    }

}