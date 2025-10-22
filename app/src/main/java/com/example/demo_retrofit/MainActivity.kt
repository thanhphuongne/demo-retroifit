import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var textView: android.widget.TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            // Create a simple TextView programmatically for demo
            textView = android.widget.TextView(this).apply {
                text = "Retrofit Demo - Starting..."
                textSize = 18f
                setPadding(32, 32, 32, 32)
                gravity = android.view.Gravity.CENTER
                setBackgroundColor(android.graphics.Color.WHITE)
                setTextColor(android.graphics.Color.BLACK)
            }
            setContentView(textView)

            Log.d("RETROFIT_DEMO", "App started successfully")

            // Thực thi request
            Log.d("RETROFIT_DEMO", "Starting API call...")
            makeApiCall()

        } catch (e: Exception) {
            Log.e("RETROFIT_DEMO", "Error in onCreate: ${e.message}", e)
            // Fallback if anything goes wrong
            super.setContentView(android.widget.TextView(this).apply {
                text = "Error: ${e.message}"
                textSize = 16f
                setPadding(16, 16, 16, 16)
            })
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
                                textView.text = "✅ API Success!\n\n👤 User: ${user?.name}\n📧 Email: ${user?.email}\n🏢 Company: ${user?.company?.name}"
                            } else {
                                // Xử lý lỗi từ server (ví dụ: 404, 500)
                                Log.e("API_ERROR", "Error code: ${response.code()}")
                                textView.text = "❌ API Error: ${response.code()}\n\nCheck Logcat for details"
                            }
                        } catch (e: Exception) {
                            Log.e("RETROFIT_DEMO", "Error updating UI: ${e.message}", e)
                            textView.text = "❌ UI Update Error: ${e.message}"
                        }
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    runOnUiThread {
                        try {
                            // Xử lý lỗi mạng (ví dụ: mất kết nối)
                            Log.e("API_FAILURE", "Error: ${t.message}")
                            textView.text = "❌ Network Error:\n\n${t.message}\n\nCheck internet connection"
                        } catch (e: Exception) {
                            Log.e("RETROFIT_DEMO", "Error in onFailure: ${e.message}", e)
                            textView.text = "❌ Unexpected Error"
                        }
                    }
                }
            })
        } catch (e: Exception) {
            Log.e("RETROFIT_DEMO", "Error making API call: ${e.message}", e)
            runOnUiThread {
                textView.text = "❌ API Call Setup Error:\n\n${e.message}"
            }
        }
    }
}