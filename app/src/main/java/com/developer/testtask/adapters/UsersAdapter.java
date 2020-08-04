package com.developer.testtask.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.developer.testtask.R;
import com.developer.testtask.models.User;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    // GroupAdapter для категории сервиси
    private Context mContext;
    private List<User> modulList;
    private ClickListener clickListener;

    public UsersAdapter(Context mContext, List<User> modulList) {
        this.mContext = mContext;
        this.modulList = modulList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        User groupModel = modulList.get(position);
        holder.name.setText(modulList.get(position).getName());
        holder.email.setText(modulList.get(position).getEmail());
        holder.pass.setText(modulList.get(position).getPassword());


    }

    @Override
    public int getItemCount() {
        return modulList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, email, pass;
        CardView mCardView;
        LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            pass = itemView.findViewById(R.id.password);
            mCardView = itemView.findViewById(R.id.card);
            layout = itemView.findViewById(R.id.linear);
            mCardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {//Обработчик нажатия на адрес
                clickListener.itemClicked(modulList.get(getPosition()), getPosition());
            }
        }
    }

    public User getItem(int pos){

        return modulList.get(pos);
    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    public interface ClickListener{
        void itemClicked(User user, int position);
    }
}