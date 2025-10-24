import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var statusTextView: android.widget.TextView
    private lateinit var userNameTextView: android.widget.TextView
    private lateinit var userEmailTextView: android.widget.TextView
    private lateinit var userCompanyTextView: android.widget.TextView
    private lateinit var refreshButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            // Initialize views
            statusTextView = findViewById(R.id.statusTextView)
            userNameTextView = findViewById(R.id.userNameTextView)
            userEmailTextView = findViewById(R.id.userEmailTextView)
            userCompanyTextView = findViewById(R.id.userCompanyTextView)
            refreshButton = findViewById(R.id.refreshButton)

            refreshButton.setOnClickListener {
                makeApiCall()
            }

            Log.d("RETROFIT_DEMO", "App started successfully")

            // Thực thi request
            Log.d("RETROFIT_DEMO", "Starting API call...")
            makeApiCall()

        } catch (e: Exception) {
            Log.e("RETROFIT_DEMO", "Error in onCreate: ${e.message}", e)
            statusTextView.text = "Error: ${e.message}"
        }
    }

    private fun makeApiCall() {
        try {
            val call = RetrofitClient.instance.getUserById(1)

            call.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    runOnUiThread {
                        try {
                            if (response.isSuccessful) {
                                val user = response.body()
                                // Cập nhật UI ở đây
                                Log.d("API_SUCCESS", "User name: ${user?.name}")
                                Log.d("API_SUCCESS", "User email: ${user?.email}")
                                Log.d("API_SUCCESS", "User company: ${user?.company?.name}")
                                statusTextView.text = "✅ API Success!"
                                userNameTextView.text = "👤 Name: ${user?.name}"
                                userEmailTextView.text = "📧 Email: ${user?.email}"
                                userCompanyTextView.text = "🏢 Company: ${user?.company?.name}"
                                userNameTextView.visibility = View.VISIBLE
                                userEmailTextView.visibility = View.VISIBLE
                                userCompanyTextView.visibility = View.VISIBLE
                            } else {
                                // Xử lý lỗi từ server (ví dụ: 404, 500)
                                Log.e("API_ERROR", "Error code: ${response.code()}")
                                statusTextView.text = "❌ API Error: ${response.code()}\n\nCheck Logcat for details"
                                hideUserDetails()
                            }
                        } catch (e: Exception) {
                            Log.e("RETROFIT_DEMO", "Error updating UI: ${e.message}", e)
                            statusTextView.text = "❌ UI Update Error: ${e.message}"
                            hideUserDetails()
                        }
                    }
                
                    private fun hideUserDetails() {
                        userNameTextView.visibility = View.GONE
                        userEmailTextView.visibility = View.GONE
                        userCompanyTextView.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    runOnUiThread {
                        try {
                            // Xử lý lỗi mạng (ví dụ: mất kết nối)
                            Log.e("API_FAILURE", "Error: ${t.message}")
                            statusTextView.text = "❌ Network Error:\n\n${t.message}\n\nCheck internet connection"
                            hideUserDetails()
                        } catch (e: Exception) {
                            Log.e("RETROFIT_DEMO", "Error in onFailure: ${e.message}", e)
                            statusTextView.text = "❌ Unexpected Error"
                            hideUserDetails()
                        }
                    }
                }
            })
        } catch (e: Exception) {
            Log.e("RETROFIT_DEMO", "Error making API call: ${e.message}", e)
            runOnUiThread {
                statusTextView.text = "❌ API Call Setup Error:\n\n${e.message}"
                hideUserDetails()
            }
        }
    }
}