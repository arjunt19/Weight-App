package com.example.mark2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class fragment_log extends Fragment {


public static final int CLICKED_ACTIVITY_NEW_ENTRY = 1;
    public static final int CLICKED_ACTIVITY_UPDATE_ENTRY = 2;

    public static final String UPDATE_DATE = "date_updated";
    public static final String UPDATE_WEIGHT = "weight_updated";
    public static final String UPDATE_DESCRIPTION = "description_updated";
    public static final String weight_id = "weight_id";
    public static final String reply_id = "reply_id";



    private WeightViewModel vm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_log, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerview);
        final RecyclerAdapter adapter = new RecyclerAdapter(rootView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(rootView.getContext(), ClickedActivity.class);

                startActivityForResult(intent, CLICKED_ACTIVITY_NEW_ENTRY);
            }
        });

        vm = ViewModelProviders.of(this).get(WeightViewModel.class);
        vm.getAllWeights().observe(this, new Observer<List<Weight>>() {
            @Override
            public void onChanged(@Nullable final List<Weight> weights) {
                // Update the cached copy of the words in the adapter.
                adapter.setWeights(weights);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    // We are not implementing onMove() in this app.
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    // When the use swipes a word,
                    // delete that word from the database.
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Weight weight = adapter.getWeightAtPosition(position);
                        Toast.makeText(rootView.getContext(),
                                "Deleting Entry", Toast.LENGTH_LONG).show();

                        // Delete the word.
                        WeightViewModel.deleteWeight(weight);
                    }
                });
        // Attach the item touch helper to the recycler view.
        helper.attachToRecyclerView(recyclerView);

        adapter.setOnClickListener(new RecyclerAdapter.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                launchUpdateActivity(adapter.getWeightAtPosition(position));
            }
        });

        return rootView;
    }

    private void launchUpdateActivity(Weight weightAtPosition) {
        Intent intent = new Intent(getContext(), ClickedActivity.class);
        intent.putExtra(UPDATE_DATE, weightAtPosition.getDate());
        intent.putExtra(UPDATE_DESCRIPTION, weightAtPosition.getDescription());
        intent.putExtra(UPDATE_WEIGHT,weightAtPosition.getWeight());
        intent.putExtra(weight_id,weightAtPosition.getId());
        startActivityForResult(intent, CLICKED_ACTIVITY_UPDATE_ENTRY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CLICKED_ACTIVITY_NEW_ENTRY && resultCode== -1){
            int weight = data.getIntExtra(ClickedActivity.WEIGHT,0) ;
            String description = data.getStringExtra(ClickedActivity.DESCRIPTION);
            long date = data.getLongExtra(ClickedActivity.LONG,0);
            vm.insert(new Weight(weight,date,description));
        }
        else if(requestCode==CLICKED_ACTIVITY_UPDATE_ENTRY && resultCode == -1){
            assert data != null;
            int weight = data.getIntExtra(ClickedActivity.WEIGHT,0) ;
            String description = data.getStringExtra(ClickedActivity.DESCRIPTION) + "";
            long date = data.getLongExtra(ClickedActivity.LONG,0);
            int id = data.getIntExtra(ClickedActivity.reply_id, -1);
            if(id != -1){
                vm.update(new Weight(id, weight, date, description));
            }
            else{
                Toast.makeText(getContext(), "Entry not saved", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(getContext(), "Entry not saved", Toast.LENGTH_LONG).show();
        }
    }

    private String[] longToDate(long date){
        Date tempDate = new java.util.Date(date*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
        String formattedDate = sdf.format(tempDate);
        return formattedDate.split("-");

    }
}
