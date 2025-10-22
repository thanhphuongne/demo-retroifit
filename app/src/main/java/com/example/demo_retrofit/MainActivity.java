package com.example.demo_retrofit;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private android.widget.ScrollView scrollView;
    private TextView textView;
    private StringBuilder dataBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            // Create a ScrollView with TextView for demo
            scrollView = new android.widget.ScrollView(this);
            textView = new TextView(this);
            textView.setText("Retrofit Demo - Starting...\n\nFetching data...");
            textView.setTextSize(14f);
            textView.setPadding(16, 16, 16, 16);
            textView.setBackgroundColor(android.graphics.Color.WHITE);
            textView.setTextColor(android.graphics.Color.BLACK);
            textView.setTypeface(android.graphics.Typeface.MONOSPACE);

            scrollView.addView(textView);
            setContentView(scrollView);

            dataBuilder = new StringBuilder();
            dataBuilder.append("🚀 Retrofit Demo Started\n");
            dataBuilder.append("========================\n\n");

            Log.d("RETROFIT_DEMO", "App started successfully");

            // Make API call
            makeApiCall();

        } catch (Exception e) {
            Log.e("RETROFIT_DEMO", "Error in onCreate: " + e.getMessage(), e);
            // Fallback if anything goes wrong
            TextView fallbackView = new TextView(this);
            fallbackView.setText("Error: " + e.getMessage());
            fallbackView.setTextSize(16f);
            fallbackView.setPadding(16, 16, 16, 16);
            setContentView(fallbackView);
        }
    }

    private void makeApiCall() {
        try {
            // First get a single user
            Call<User> userCall = RetrofitClient.getInstance().getApiService().getUserById(1);

            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    runOnUiThread(() -> {
                        try {
                            if (response.isSuccessful()) {
                                User user = response.body();
                                Log.d("API_SUCCESS", "User name: " + (user != null ? user.getName() : "null"));

                                dataBuilder.append("✅ Single User Data:\n");
                                dataBuilder.append("👤 Name: ").append(user != null ? user.getName() : "Unknown").append("\n");
                                dataBuilder.append("📧 Email: ").append(user != null ? user.getEmail() : "Unknown").append("\n");
                                dataBuilder.append("🏢 Company: ").append(user != null && user.getCompany() != null ? user.getCompany().getName() : "Unknown").append("\n");
                                dataBuilder.append("📍 City: ").append(user != null && user.getAddress() != null ? user.getAddress().getCity() : "Unknown").append("\n\n");

                                textView.setText(dataBuilder.toString());

                                // Now get all users
                                getAllUsers();
                            } else {
                                Log.e("API_ERROR", "Error code: " + response.code());
                                textView.setText("❌ API Error: " + response.code());
                            }
                        } catch (Exception e) {
                            Log.e("RETROFIT_DEMO", "Error updating UI: " + e.getMessage(), e);
                            textView.setText("❌ UI Update Error: " + e.getMessage());
                        }
                    });
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    runOnUiThread(() -> {
                        Log.e("API_FAILURE", "Error: " + t.getMessage());
                        textView.setText("❌ Network Error:\n\n" + t.getMessage());
                    });
                }
            });
        } catch (Exception e) {
            Log.e("RETROFIT_DEMO", "Error making API call: " + e.getMessage(), e);
            runOnUiThread(() -> textView.setText("❌ API Call Setup Error:\n\n" + e.getMessage()));
        }
    }

    private void getAllUsers() {
        try {
            Call<java.util.List<User>> allUsersCall = RetrofitClient.getInstance().getApiService().getAllUsers();

            allUsersCall.enqueue(new Callback<java.util.List<User>>() {
                @Override
                public void onResponse(Call<java.util.List<User>> call, Response<java.util.List<User>> response) {
                    runOnUiThread(() -> {
                        try {
                            if (response.isSuccessful()) {
                                java.util.List<User> users = response.body();
                                int userCount = users != null ? users.size() : 0;
                                Log.d("API_SUCCESS", "Got " + userCount + " users");

                                dataBuilder.append("✅ All Users Data (").append(userCount).append(" users):\n");
                                if (users != null && !users.isEmpty()) {
                                    for (int i = 0; i < users.size(); i++) { // Show ALL users
                                        User u = users.get(i);
                                        dataBuilder.append("  ").append(i + 1).append(". ").append(u.getName())
                                                  .append(" (").append(u.getEmail()).append(")\n");
                                        dataBuilder.append("      📍 ").append(u.getAddress() != null ? u.getAddress().getCity() : "Unknown")
                                                  .append(", 🏢 ").append(u.getCompany() != null ? u.getCompany().getName() : "Unknown").append("\n");
                                    }
                                }
                                dataBuilder.append("\n");
                                textView.setText(dataBuilder.toString());

                                // Now get posts for user 1
                                getPostsForUser(1);
                            } else {
                                Log.e("API_ERROR", "All users error: " + response.code());
                            }
                        } catch (Exception e) {
                            Log.e("RETROFIT_DEMO", "Error in getAllUsers: " + e.getMessage(), e);
                        }
                    });
                }

                @Override
                public void onFailure(Call<java.util.List<User>> call, Throwable t) {
                    Log.e("API_FAILURE", "All users network error: " + t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.e("RETROFIT_DEMO", "Error getting all users: " + e.getMessage(), e);
        }
    }

    private void getPostsForUser(int userId) {
        try {
            Call<java.util.List<Post>> postsCall = RetrofitClient.getInstance().getApiService().getPosts(userId);

            postsCall.enqueue(new Callback<java.util.List<Post>>() {
                @Override
                public void onResponse(Call<java.util.List<Post>> call, Response<java.util.List<Post>> response) {
                    runOnUiThread(() -> {
                        try {
                            if (response.isSuccessful()) {
                                java.util.List<Post> posts = response.body();
                                int postCount = posts != null ? posts.size() : 0;
                                Log.d("API_SUCCESS", "Got " + postCount + " posts for user " + userId);

                                dataBuilder.append("✅ User Posts Data (").append(postCount).append(" posts):\n");
                                if (posts != null && !posts.isEmpty()) {
                                    for (int i = 0; i < posts.size(); i++) { // Show ALL posts
                                        Post p = posts.get(i);
                                        dataBuilder.append("  ").append(i + 1).append(". 📝 ").append(p.getTitle()).append("\n");
                                        // Show full body
                                        String body = p.getBody();
                                        if (body != null) {
                                            // Format body with line breaks for readability
                                            dataBuilder.append("     💬 ").append(body.replace("\n", "\n        ")).append("\n\n");
                                        }
                                    }
                                }
                                dataBuilder.append("\n🎉 Demo Complete! All data fetched successfully!\n");
                                textView.setText(dataBuilder.toString());
                            } else {
                                Log.e("API_ERROR", "Posts error: " + response.code());
                                textView.setText("❌ Posts API Error: " + response.code());
                            }
                        } catch (Exception e) {
                            Log.e("RETROFIT_DEMO", "Error in getPostsForUser: " + e.getMessage(), e);
                        }
                    });
                }

                @Override
                public void onFailure(Call<java.util.List<Post>> call, Throwable t) {
                    Log.e("API_FAILURE", "Posts network error: " + t.getMessage());
                    runOnUiThread(() -> textView.setText("❌ Posts Network Error:\n\n" + t.getMessage()));
                }
            });
        } catch (Exception e) {
            Log.e("RETROFIT_DEMO", "Error getting posts: " + e.getMessage(), e);
        }
    }
}