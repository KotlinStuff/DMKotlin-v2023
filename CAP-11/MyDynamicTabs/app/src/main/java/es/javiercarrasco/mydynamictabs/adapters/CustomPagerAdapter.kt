package es.javiercarrasco.mydynamictabs.adapters

import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class CustomPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()
    private val mFragmentIcon = ArrayList<Drawable>()

    // Devuelve el tamaño del array de fragments.
    override fun getItemCount(): Int {
        return mFragmentList.size
    }

    // Devuelve el frament en la posición indicada.
    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }

    // Añade un fragment a la lista.
    fun addFragment(fragment: Fragment, title: String, icon: Drawable) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
        mFragmentIcon.add(icon)
    }

    // Devuelve el título de la pestaña.
    fun getPageTitle(position: Int): CharSequence {
        return mFragmentTitleList[position]
    }

    // Devuelve el icono de la pestaña.
    fun getIconTab(position: Int): Drawable {
        return mFragmentIcon[position]
    }
}