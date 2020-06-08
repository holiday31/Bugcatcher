package net.skhu.bugcatcher;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.CustomViewHolder> {

    private ArrayList<progress> mList;

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView call_success;
        protected TextView contact;
        protected TextView catch_success;
        protected TextView name;


        public CustomViewHolder(View view) {
            super(view);
            this.call_success = (TextView) view.findViewById(R.id.call_success);
            this.contact = (TextView) view.findViewById(R.id.contact);
            this.catch_success = (TextView) view.findViewById(R.id.catch_success);
            this.name = (TextView) view.findViewById(R.id.name);
        }
    }


    public ProgressAdapter(ArrayList<progress> list) {
        this.mList = list;
    }



    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.progress_list, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.call_success.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        viewholder.contact.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        viewholder.catch_success.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        viewholder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);

        viewholder.call_success.setGravity(Gravity.CENTER);
        viewholder.contact.setGravity(Gravity.CENTER);
        viewholder.catch_success.setGravity(Gravity.CENTER);
        viewholder.name.setGravity(Gravity.CENTER);



        viewholder.call_success.setText(mList.get(position).getCall_success());
        viewholder.contact.setText(mList.get(position).getContact());
        viewholder.catch_success.setText(mList.get(position).getCatch_success());
        viewholder.name.setText(mList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}