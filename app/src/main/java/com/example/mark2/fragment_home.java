package com.example.mark2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class fragment_home extends Fragment {

    private WeightViewModel vm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_home, container, false);

        final TextView weightText  = rootView.findViewById(R.id.home_weight);
        final TextView lostText = rootView.findViewById(R.id.total_lost);
        final TextView daysText = rootView.findViewById(R.id.days_passed);
        final TextView avgText = rootView.findViewById(R.id.avg_pace);
        vm = ViewModelProviders.of(this).get(WeightViewModel.class);
        vm.getAllWeights().observe(this, new Observer<List<Weight>>() {
            @Override
            public void onChanged(@Nullable final List<Weight> weights) {
                // Update the cached copy of the words in the adapter.
                assert weights != null;
                if(weights.size()>0) {
                    weightText.setText(Integer.toString(weights.get(0).getWeight()));

                }else{
                    weightText.setText("N/A");
                }
                if(weights.size()>1){
                    daysText.setText(Integer.toString(dateToDiff(weights.get(weights.size() - 1).getDate(), weights.get(0).getDate())));
                    avgText.setText(String.format("%.1f",-1*((double)(weights.get(0).getWeight() - weights.get(weights.size() - 1).getWeight())/dateToDiff(weights.get(weights.size() - 1).getDate(), weights.get(0).getDate()))));
                    if(weights.get(0).getWeight() <= weights.get(weights.size()-1).getWeight()) {
                        lostText.setText(Integer.toString(Math.abs(weights.get(0).getWeight() - weights.get(weights.size() - 1).getWeight())));
                    }
                    else{
                        lostText.setText("N/A");
                    }
                }
                else{
                    lostText.setText("N/A");
                    daysText.setText("N/A");
                    avgText.setText("N/A");
                }

            }
        });

        return rootView;
    }
    private int dateToDiff(long date1, long date2){
        Date tempDate1 = new java.util.Date(date1*1000L);
        Date tempDate2 = new java.util.Date(date2*1000L);
        return (int)TimeUnit.DAYS.convert(Math.abs(tempDate1.getTime()-tempDate2.getTime()), TimeUnit.MILLISECONDS);
    }
}
