package es.javiercarrasco.myokhttp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import es.javiercarrasco.myokhttp.model.Words
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private var _wordsList: MutableStateFlow<List<Words>> = MutableStateFlow(emptyList())
    val wordsList: Flow<List<Words>>
        get() = _wordsList.asStateFlow()

    init {
        getWords()
    }

    fun getWords() {
        viewModelScope.launch(Dispatchers.Main) {
            val pageContent = downloadWebPage("https://www.javiercarrasco.es/api/words/read")
            if (!pageContent.isBlank()) {
                val gson = GsonBuilder().create()
                val wordsList = gson.fromJson(pageContent, Array<Words>::class.java).toList()
                _wordsList.value = wordsList
            }
        }
    }
}