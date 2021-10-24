package com.example.mark2;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;


public class WeightRepository {

    private WeightDao mWeightDao;
    private LiveData<List<Weight>> mAllWeights;

    WeightRepository(Application application) {
        WeightDatabase db = WeightDatabase.getDatabase(application);
        mWeightDao = db.weightDao();
        mAllWeights = mWeightDao.getAllWeights();
    }

    LiveData<List<Weight>> getAllWeights() {
        return mAllWeights;
    }

    public void insert(Weight Weight) {
        new insertAsyncTask(mWeightDao).execute(Weight);
    }

    public void update(Weight Weight)  {
        new updateWeightAsyncTask(mWeightDao).execute(Weight);
    }

    public void deleteAll()  {
        new deleteAllWeightsAsyncTask(mWeightDao).execute();
    }

    // Must run off main thread
    public void deleteWeight(Weight Weight) {
        new deleteWeightAsyncTask(mWeightDao).execute(Weight);
    }

    // Static inner classes below here to run database interactions in the background.

    /**
     * Inserts a Weight into the database.
     */
    private static class insertAsyncTask extends AsyncTask<Weight, Void, Void> {

        private WeightDao mAsyncTaskDao;

        insertAsyncTask(WeightDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Weight... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    /**
     * Deletes all Weights from the database (does not delete the table).
     */
    private static class deleteAllWeightsAsyncTask extends AsyncTask<Void, Void, Void> {
        private WeightDao mAsyncTaskDao;

        deleteAllWeightsAsyncTask(WeightDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    /**
     *  Deletes a single Weight from the database.
     */
    private static class deleteWeightAsyncTask extends AsyncTask<Weight, Void, Void> {
        private WeightDao mAsyncTaskDao;

        deleteWeightAsyncTask(WeightDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Weight... params) {
            mAsyncTaskDao.deleteWeight(params[0]);
            return null;
        }
    }

    /**
     *  Updates a Weight in the database.
     */
    private static class updateWeightAsyncTask extends AsyncTask<Weight, Void, Void> {
        private WeightDao mAsyncTaskDao;

        updateWeightAsyncTask(WeightDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Weight... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}