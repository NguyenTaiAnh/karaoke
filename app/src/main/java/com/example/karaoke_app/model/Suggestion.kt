package com.example.karaoke_app.model
import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import java.lang.reflect.Constructor

@SuppressLint("ParcelCreator")
class Suggestion( val mName: String) : SearchSuggestion {

     private var mIsHistory : Boolean = false
         get() = field
         set(value) {
             field = value
         }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
    }

    override fun getBody(): String {
       return mName
    }

    override fun describeContents(): Int {
        return 0
    }

}