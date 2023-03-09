package es.javiercarrasco.myfragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FragmentOne : Fragment() {
    val TAG = "FragmentONE"

    override fun onAttach(context: Context) {
        Log.println(Log.INFO, TAG, "onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.println(Log.INFO, TAG, "onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        Log.println(Log.INFO, TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.println(Log.INFO, TAG, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        Log.println(Log.INFO, TAG, "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.println(Log.INFO, TAG, "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.println(Log.INFO, TAG, "onPause")
        super.onPause()
    }

    override fun onDestroyView() {
        Log.println(Log.INFO, TAG, "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.println(Log.INFO, TAG, "onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.println(Log.INFO, TAG, "onDetach")
        super.onDetach()
    }
}