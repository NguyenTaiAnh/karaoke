package com.example.karaoke_app.asynctask

import android.os.AsyncTask
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit
import com.example.karaoke_app.model.Suggestion
import com.example.karaoke_app.Interface.MakeSuggestion

class asyn() : AsyncTask<String, Void, String>() {
    //Đây là interface khi suggestion được xây dựng xong nó sẽ gọi tới hàm trong MainActivity.java
    private val suggestions: MutableList<Suggestion> = ArrayList()
    lateinit var makeSuggestion: MakeSuggestion
    // Xây dựng okhttp
    var okHttpClient = OkHttpClient.Builder()
        .connectTimeout(25, TimeUnit.SECONDS)
        .writeTimeout(25, TimeUnit.SECONDS)
        .readTimeout(25, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

//    override fun onPostExecute(x: String?) {
//        super.onPostExecute(x)
//        try {
//            //arr_main chính là từ khoá bạn muốn được suggest
//            //arr_sub chính là kết quả suggest được trả về từ google
//            val arr_main: JSONArray = JSONArray(x)
//            val arr_sub: JSONArray = JSONArray(arr_main.getString(1))
//            for (i in 0 until arr_sub.length() ) {
//                suggestions.add(Suggestion(arr_sub.getString(i)))
//            }
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }
//        //gởi dữ liệu sang MainActivity.java
//        makeSuggestion.getSuggestion(suggestions)
//    }
    @RequiresApi(Build.VERSION_CODES.N)
    override fun doInBackground(vararg strings: String): String? {
        try {
            //Thời gian chờ xem thừ người dùng có nhập thêm kí tự nữa hay không.
            Thread.sleep(750)
//                println(URL("http://suggestqueries.google.com/complete/search?output=firefox&hl=vi&q=+sontung").openConnection().content)

            val request: Request = Request.Builder()
                .url(strings[0])
                .build()
            val response: Response = okHttpClient.newCall(request).execute()
            Log.w("Res:", Gson().toJson(response.body))
            return response.body?.string()
//            return ""
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }

}

