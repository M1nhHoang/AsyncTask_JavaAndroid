package com.example.asynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FetchDataAsyncTask extends AsyncTask<Void, Void, List<ListItem>> {

    private static String API_URL = "https://dummyjson.com/products/";

    private MainActivity mActivity;
    private ListView mListView;

    private int n = 10;

    public FetchDataAsyncTask(MainActivity activity, ListView listView) {
        this.mActivity = activity;
        this.mListView = listView;
    }

    @Override
    protected List<ListItem> doInBackground(Void... voids) {
        List<ListItem> itemList = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();

        for (int i = 1; i <= n; i++) {
            Request request = new Request.Builder()
                    .url(API_URL + i)
                    .build();

            try {
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String jsonData = response.body().string();

                    JSONObject jsonObject = new JSONObject(jsonData);
                    String title = jsonObject.getString("title");
                    String des = jsonObject.getString("description");
                    String images = jsonObject.getJSONArray("images").getString(1);

                    InputStream inputStream = null;
                    try {
                        inputStream = new java.net.URL(images).openStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                        ListItem item = new ListItem(bitmap, title, des);
                        itemList.add(item);
                    } finally {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    }
                } else {
                    mActivity.runOnUiThread(() -> Toast.makeText(mActivity, "Error: " + response.code(), Toast.LENGTH_SHORT).show());
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                mActivity.runOnUiThread(() -> Toast.makeText(mActivity, "Error fetching data", Toast.LENGTH_SHORT).show());
            }
        }
        client.dispatcher().executorService().shutdown();

        return itemList;
    }

    @Override
    protected void onPostExecute(List<ListItem> itemList) {
        if (itemList != null) {
            CustomAdapter adapter = new CustomAdapter(mActivity, itemList);
            mListView.setAdapter(adapter);
        }
    }
}



