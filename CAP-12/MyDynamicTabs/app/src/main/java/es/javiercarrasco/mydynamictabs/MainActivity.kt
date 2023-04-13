package es.javiercarrasco.mydynamictabs

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import es.javiercarrasco.mydynamictabs.adapters.CustomPagerAdapter
import es.javiercarrasco.mydynamictabs.databinding.ActivityMainBinding
import es.javiercarrasco.mydynamictabs.effects.DepthPageTransformer

const val ARG_NAME = "name"
const val ARG_TEXT = "text"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val NUM_FRAGMENTS = 5 // Número de fragments a crear.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se instancia el adaptador para el ViewPager2.
        val adapter = CustomPagerAdapter(supportFragmentManager, lifecycle)

        // Se añaden los fragments y los títulos de pestañas.
        for (num in 1..NUM_FRAGMENTS) {
            adapter.addFragment(
                PageFragment.newInstance(
                    getString(R.string.txt_fragment, num.toString()),
                    getString(R.string.txt_to_show, num.toString())
                ),
                "Frg $num",
                AppCompatResources.getDrawable(this, R.drawable.ic_emoticon)!!
            )
        }

        // Se asigna el adapter al ViewPager2.
        binding.viewPager2.adapter = adapter

        // Permite elegir la dirección del paginado.
        binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, pos ->
            tab.text = adapter.getPageTitle(pos)
            tab.icon = adapter.getIconTab(pos)
        }.attach()

        // Efectos para el ViewPager2.
        binding.viewPager2.setPageTransformer(DepthPageTransformer())
    }

    override fun onStart() {
        super.onStart()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.i("TabSelected", tab!!.text.toString())
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.i("TabUnselected", tab!!.text.toString())
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.i("TabReselected", tab!!.text.toString())
            }
        })
    }
}