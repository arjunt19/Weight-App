package com.example.mark2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WeightViewModel extends AndroidViewModel {

    private static WeightRepository rep;
    private static LiveData<List<Weight>> weights;

    public WeightViewModel(@NonNull Application application) {
        super(application);
        rep = new WeightRepository(application);
        weights = rep.getAllWeights();
    }

    static LiveData<List<Weight>> getAllWeights() {
        return weights;
    }

    public void insert(Weight Weight) {
        rep.insert(Weight);
    }

    public void deleteAll() {
        rep.deleteAll();
    }

    public static void deleteWeight(Weight Weight) {
        rep.deleteWeight(Weight);
    }

    public void update(Weight Weight) {
        rep.update(Weight);
    }
}
