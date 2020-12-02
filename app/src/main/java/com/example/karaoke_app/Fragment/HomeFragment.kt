package com.example.karaoke_app.Fragment

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.karaoke_app.Adaptertheloai.Adptertbaihat
import com.example.karaoke_app.Adaptertheloai.Adptertheloai
import com.example.karaoke_app.Adaptertheloai.baihat
import com.example.karaoke_app.Adaptertheloai.theloai
import com.example.karaoke_app.R
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val users = ArrayList<baihat>()
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
        val  listbaihat = ArrayList<baihat>()
        val urlGetDatamusic : String = "http://192.168.0.103:8090/webservice/get_data_music.php"
        Getdata1().execute(urlGetDatamusic)
        initAdapter()
        initRecyclerView()
        btnyoutube.setOnClickListener {
            var key_serch: String = EDTSeach.text.toString()

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
            }
        }
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

    inner class Getdata1 : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg p0: String?): String {
            return getContentURL(p0[0])
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            var id: Int= 0
            var name: String = ""
            var casi: String = ""
            var jsonArray: JSONArray = JSONArray(result)
            for (video in 0..jsonArray.length() - 1) {
                var objectMS: JSONObject = jsonArray.getJSONObject(video)
                id= objectMS.getInt("ID")
                name = objectMS.getString("TenBH")
                casi = objectMS.getString("CaSi")
                users.add(baihat(name,casi,id.toString()))
                //listview.Name.text = name
                //mangbaihat.add(id+"\n"+name+"")
            }
            adapter.notifyDataSetChanged()
        }
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
                users.add(baihat(namee,idd,url_s))
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