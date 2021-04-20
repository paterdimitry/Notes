package com.geekbrain.notes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrain.notes.CardData;
import com.geekbrain.notes.R;
import com.geekbrain.notes.interfaces.CardSource;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    private CardSource dataSource;
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;
    private Fragment fragment;
    private int menuPosition;

    public NoteListAdapter(CardSource dataSource, Fragment fragment) {
        this.dataSource = dataSource;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public NoteListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_note_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListAdapter.ViewHolder holder, int position) {
        holder.setData(dataSource.getSource(position));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public void setItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public int getMenuPosition() {
        return menuPosition;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTV;
        TextView descriptionTV;
        TextView dateTV;

        public TextView getTitleTV() {
            return titleTV;
        }

        public TextView getDescriptionTV() {
            return descriptionTV;
        }

        public TextView getDateTV() {
            return dateTV;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.note_title);
            descriptionTV = itemView.findViewById(R.id.description_note);
            dateTV = itemView.findViewById(R.id.date_note);

            //Слушатель для короткого нажатия
            titleTV.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            });

            //Слушатель для долгого нажатия
            titleTV.setOnLongClickListener(v -> {
                menuPosition = getLayoutPosition();
                v.showContextMenu();
                return true;
            });

            if(fragment != null) {
                itemView.setOnLongClickListener(v -> {
                    menuPosition = getLayoutPosition();
                    return false;
                });
                fragment.registerForContextMenu(itemView);
            }
        }

        public void setData(CardData cardData) {
            titleTV.setText(cardData.getTitle());
            descriptionTV.setText(cardData.getDescription());
            dateTV.setText(cardData.getDate().toString());
        }
    }
}
