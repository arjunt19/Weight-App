package com.example.mark2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WeightDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Weight weight);

    @Query("DELETE FROM weight_table")
    void deleteAll();

    @Delete
    void deleteWeight(Weight weight);

    @Query("SELECT * from weight_table LIMIT 1")
    Weight[] getAnyWeight();

    @Query("SELECT * from weight_table ORDER BY date DESC")
    LiveData<List<Weight>> getAllWeights();

    @Update
    void update(Weight... weight);
}
