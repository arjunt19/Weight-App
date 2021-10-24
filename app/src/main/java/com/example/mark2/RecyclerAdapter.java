package com.example.mark2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.WeightViewHolder> {
    private final LayoutInflater mInflater;
    private List<Weight> weights; // Cached copy of words
    String[] months = {"January", "February","March","April","May","June","July","August","September","October","November","December"};
    private static ClickListener clickListener;

    RecyclerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public WeightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WeightViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.WeightViewHolder holder, int position) {
        if(weights!=null) {
            Weight temp = weights.get(position);
            long date = temp.getDate();
            String[] dates = longToDate(date);
            String description = temp.getDescription();
            int weight = temp.getWeight();
            holder.year.setText(dates[0]);
            holder.month.setText(months[Integer.parseInt(dates[1])-1]);
            holder.day.setText(dates[2]);
            holder.weight.setText(weight+"");
            holder.description.setText(description);

        }



    }

    private String[] longToDate(long date){
        Date tempDate = new java.util.Date(date*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
        String formattedDate = sdf.format(tempDate);
        return formattedDate.split("-");



    }

    @Override
    public int getItemCount() {
        if(weights!=null){
            return weights.size();
        }
        else{
            return 0;
        }
    }

    void setWeights(List<Weight> weights){
        this.weights = weights;
        notifyDataSetChanged();
    }

    public Weight getWeightAtPosition(int position) {
        return weights.get(position);
    }

    class WeightViewHolder extends RecyclerView.ViewHolder{

        private final TextView month;
        private final TextView day;
        private final TextView year;
        private final TextView weight;
        private final TextView description;


        private WeightViewHolder(@NonNull View itemView) {
            super(itemView);
            month = itemView.findViewById(R.id.month);
            day = itemView.findViewById(R.id.day);
            year = itemView.findViewById(R.id.year);
            weight = itemView.findViewById(R.id.weight);
            description = itemView.findViewById(R.id.description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });

        }
    }

    public void setOnClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    public interface ClickListener{
        void onItemClick(View view, int position);
    }
}
