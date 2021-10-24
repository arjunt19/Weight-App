package com.example.mark2;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "weight_table")
public class Weight {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "weight")
    private int weight;

    @ColumnInfo(name = "date")
    private long date;

    @NonNull
    @ColumnInfo(name = "description")
    private String description;

    public Weight(int weight, long date, @NonNull String description){

        this.weight = weight;
        this.date = date;
        this.description = description;

    }

    @Ignore
    public Weight(int id, int weight, long date, @NonNull String description){
        this.id = id;
        this.weight = weight;
        this.date = date;
        this.description = description;

    }

    public int getId() {
        return id;
    }

    public int getWeight() {
        return weight;
    }

    public long getDate() {
        return date;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

}
