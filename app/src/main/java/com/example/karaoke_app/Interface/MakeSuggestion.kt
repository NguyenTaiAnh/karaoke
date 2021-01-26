package com.example.karaoke_app.Interface
import com.example.karaoke_app.model.Suggestion
interface MakeSuggestion {
    fun getSuggestion(suggestions: MutableList<Suggestion>)
}