package com.example.karaoke_app.Fragment

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.AsyncTask
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.example.karaoke_app.Adaptertheloai.Adptertbaihat
import com.example.karaoke_app.Adaptertheloai.Adptertheloai
import com.example.karaoke_app.Adaptertheloai.theloai
import com.example.karaoke_app.R
import com.example.karaoke_app.entity.User
import com.example.karaoke_app.model.Suggestion
import com.example.searchkey.core.api.ApiService
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var ascending =true
    val users = ArrayList<User>()
    lateinit var adapter: Adptertbaihat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val suggestions: MutableList<Suggestion> = ArrayList()
        EDTSeach.setOnQueryChangeListener(
                object : FloatingSearchView.OnQueryChangeListener {
                    override fun onSearchTextChanged(oldQuery: String, newQuery: String) {
                        if (!oldQuery.equals("") && newQuery.equals("")) {
                            EDTSeach.clearSuggestions();
                        } else {
                            EDTSeach.showProgress()
                            ApiService.mAPIServices("http://suggestqueries.google.com/")
                                    .getKeyword(newQuery)
                                    .enqueue(object : Callback<Any> {
                                        override fun onResponse(call: Call<Any>, response: Response<Any>) {
                                            println(Gson().toJson(response.body()))
                                            try {
                                                //arr_main chính là từ khoá bạn muốn được suggest
                                                //arr_sub chính là kết quả suggest được trả về từ google
                                                val arr_main: JSONArray = JSONArray(Gson().toJson(response.body()))
                                                val arr_sub: JSONArray = JSONArray(arr_main.getString(1))
                                                for (i in 0 until arr_sub.length() ) {
                                                    suggestions.add(Suggestion(arr_sub.getString(i)))
                                                }
                                            } catch (e: JSONException) {
                                                e.printStackTrace()
                                            }
                                            //gởi dữ liệu sang MainActivity.java
                                            EDTSeach.clearSuggestions()
                                            getSuggestion(suggestions)
                                        }

                                        override fun onFailure(call: Call<Any>, t: Throwable) {
                                            throw t
                                        }
                                    })
                        }
                    }
                }
        )

        EDTSeach.setOnMenuItemClickListener(
                object : FloatingSearchView.OnMenuItemClickListener {
                    override fun onActionMenuItemSelected(item: MenuItem) {
                        if (item.itemId == R.id.action_voice_rec) {
                            val intent: Intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                            intent.putExtra(
                                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                            )
                            startActivityForResult(intent, 0)
                        }

                    }
                })
        EDTSeach.setOnBindSuggestionCallback(
                object : SearchSuggestionsAdapter.OnBindSuggestionCallback {
                    override fun onBindSuggestion(
                            suggestionView: View?,
                            leftIcon: ImageView?,
                            textView: TextView?,
                            item: SearchSuggestion?,
                            itemPosition: Int
                    ) {
                    }

                })
        EDTSeach.setOnSearchListener(
                object : FloatingSearchView.OnSearchListener{
                    override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) {

                        onSearchAction(searchSuggestion?.body).toString()

                    }

                    override fun onSearchAction(currentQuery: String?) {
                        var key_serch: String = currentQuery.toString()

                        //var key_serch2 : String = key_serch.trim()

                        if(key_serch.isNullOrBlank() == true)
                        {
                            //Toast.makeText(applicationContext,loi, Toast.LENGTH_LONG).show()
                        }else {
                            var key_serch2:String = key_serch.replace(' ','+')
                            var urlyoutube: String = "https://byyswag.000webhostapp.com/?keyword="
                            urlyoutube = urlyoutube.plus(key_serch2)
                            clearListVideo()
                            Getdata().execute(urlyoutube)
                            initAdapter()
                            initRecyclerView()
                            EDTSeach.clearSuggestions()
                            EDTSeach.clearQuery()
                            EDTSeach.clearSearchFocus()
                        }
                    }
                }
        )



        val  listtheloai = ArrayList<theloai>()
        listtheloai.add(theloai(R.drawable.nhactre , "Nhạc Trẻ" ))
        listtheloai.add(theloai(R.drawable.trutinh , "Trữ Tình" ))
        listtheloai.add(theloai(R.drawable.cachmang , "Cách Mạng"))
        listtheloai.add(theloai(R.drawable.quehuong , "Quê Hương" ))
        listtheloai.add(theloai(R.drawable.raphiphop , "Rap/HipHop"))
        listtheloai.add(theloai(R.drawable.rock , "Rock Việt"))
        listtheloai.add(theloai(R.drawable.dance , "Dance Việt"))
        listtheloai.add(theloai(R.drawable.aumy , "Âu Mỹ"))


        recyclerview.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL , false)
        recyclerview.adapter = Adptertheloai(listtheloai)
        val urlGetDatamusic : String = "https://byyswag.000webhostapp.com/?keyword=top karaoke nhạc trẻ 2020"
        Getdata().execute(urlGetDatamusic)
        initAdapter()
        initRecyclerView()
        Sortname.setOnClickListener{
            users.sortWith(compareBy{
                it.name.toString()
            })
            adapter.notifyDataSetChanged()
        }
        SortID.setOnClickListener{
            users.sortWith(compareBy{
                it.id.toString()
            })
            adapter.notifyDataSetChanged()
        }
//        searchBtn.setOnClickListener {
//            var key_serch: String = EDTSeach.text.toString()
//
//            //var key_serch2 : String = key_serch.trim()
//
//            if(key_serch.isNullOrBlank() == true)
//            {
//                //Toast.makeText(applicationContext,loi, Toast.LENGTH_LONG).show()
//            }else {
//                var key_serch2:String = key_serch.replace(' ','+')
//                var urlyoutube: String = "https://byyswag.000webhostapp.com/?keyword="
//                urlyoutube = urlyoutube.plus(key_serch2)
//                clearListVideo()
//                Getdata().execute(urlyoutube)
//                initAdapter()
//                initRecyclerView()
//            }
//        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_home, container, false)
        return v
    }
    private fun initRecyclerView() {
        rcvbaihat.layoutManager = LinearLayoutManager(frmhome.context)
        rcvbaihat.setHasFixedSize(true)
        rcvbaihat.adapter = adapter
    }
    private fun initAdapter() {
        adapter = Adptertbaihat(users)
    }
    fun clearListVideo(){
        users.clear()
    }

    inner class Getdata : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg p0: String?): String {
            return getContentURL(p0[0])
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            var idd: String=""
            var namee: String = ""
            var url_s: String = ""
            var jsonArray: JSONArray = JSONArray(result)
            for (video in 0..jsonArray.length() - 1) {
                var objectVD: JSONObject = jsonArray.getJSONObject(video)
                idd= objectVD.getString("ID")
                namee = objectVD.getString("song")
                url_s = objectVD.getString("songkey")
                users.add(User("",idd,namee,url_s))
                //listview.Name.text = name
                //mangbaihat.add(id+"\n"+name+"")
            }
            adapter.notifyDataSetChanged()
        }
    }
    private fun getContentURL(url: String?) : String{
        var content: StringBuilder = StringBuilder();
        val url: URL = URL(url)
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        val inputStreamReader = InputStreamReader(urlConnection.inputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)

        var line: String = ""
        try {
            do {
                line = bufferedReader.readLine()
                if(line != null){
                    content.append(line)
                }
            }while (line != null)
            bufferedReader.close()
        }catch (e: Exception){
            Log.d("AAA", e.toString())
        }
        return content.toString()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode === 0 && resultCode === AppCompatActivity.RESULT_OK) {
            val results: ArrayList<String> = data!!.getStringArrayListExtra(
                RecognizerIntent.EXTRA_RESULTS
            ) as ArrayList<String>
            EDTSeach.setSearchFocused(true)
            EDTSeach.setSearchText(results[0])
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

//    public fun checkVoiceRecognition() {
//        val pm: PackageManager =
//        val activities: MutableList<ResolveInfo> = pm.queryIntentActivities(
//            Intent(
//                RecognizerIntent.ACTION_RECOGNIZE_SPEECH
//            ), 0
//        )
//        if (activities.size == 0) {
//            Toast.makeText(
//                this, "Voice recognizer not present",
//                Toast.LENGTH_SHORT
//            ).show();
//        }
//    }
    fun getSuggestion(suggestions: MutableList<Suggestion>) {
        EDTSeach.swapSuggestions(suggestions)
        EDTSeach.hideProgress()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}

