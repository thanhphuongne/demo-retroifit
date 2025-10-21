import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Thực thi request
        val call = RetrofitClient.instance.getUserById(1)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    // Cập nhật UI ở đây
                    Log.d("API_SUCCESS", "User name: ${user?.name}")
                } else {
                    // Xử lý lỗi từ server (ví dụ: 404, 500)
                    Log.e("API_ERROR", "Error code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                // Xử lý lỗi mạng (ví dụ: mất kết nối)
                Log.e("API_FAILURE", "Error: ${t.message}")
            }
        })
    }
}