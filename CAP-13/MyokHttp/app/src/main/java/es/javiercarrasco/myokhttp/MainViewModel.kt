package es.javiercarrasco.myokhttp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private var _pageContent: MutableLiveData<String> = MutableLiveData()
    val pageContent: MutableLiveData<String>
        get() = _pageContent

    init {
        viewModelScope.launch(Dispatchers.Main) {
            _pageContent.value = downloadWebPage("https://www.javiercarrasco.es/")
        }
    }
}