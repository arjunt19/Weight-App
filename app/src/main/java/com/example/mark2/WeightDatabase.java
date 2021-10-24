package com.example.mark2;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Weight.class}, version = 1, exportSchema = false)
public abstract class WeightDatabase extends RoomDatabase {

    public abstract WeightDao weightDao();

    private static  WeightDatabase INSTANCE;

    public static WeightDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (WeightDatabase.class){
                if(INSTANCE ==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WeightDatabase.class, "weight_database").fallbackToDestructiveMigration().addCallback(weightDatabaseCallback).build();
                }
            }
        }

        return INSTANCE;

    }
    private static RoomDatabase.Callback weightDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WeightDao mDao;

        // Initial data set
        private static String [] words = {"dolphin", "crocodile", "cobra"};
        private static int[] weights = {150,151,152};
        private static long[] dates = {1545177600, 1593380212, 1008720000};

        PopulateDbAsync(WeightDatabase db) {
            mDao = db.weightDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // If we have no words, then create the initial list of words.
            if (mDao.getAnyWeight().length < 1) {
                for (int i = 0; i <= words.length - 1; i++) {
                    Weight weight  = new Weight(weights[i], dates[i], words[i]);
                    mDao.insert(weight);
                }
            }
            return null;
        }
    }



}
