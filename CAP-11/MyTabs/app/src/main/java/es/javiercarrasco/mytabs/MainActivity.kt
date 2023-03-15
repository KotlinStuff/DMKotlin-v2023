package es.javiercarrasco.mytabs

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import es.javiercarrasco.mytabs.databinding.ActivityMainBinding
import es.javiercarrasco.mytabs.fragments.AnimalsFragment
import es.javiercarrasco.mytabs.fragments.FoodFragment
import es.javiercarrasco.mytabs.fragments.PersonsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se instancia el adaptador para el ViewPager2.
        val adapter = CustomPagerAdapter(supportFragmentManager, lifecycle)

        adapter.addFragment(
            AnimalsFragment(),
            getString(R.string.txt_animals),
            AppCompatResources.getDrawable(this, R.drawable.ic_animals)!!
        )
        adapter.addFragment(
            FoodFragment(),
            getString(R.string.txt_food),
            AppCompatResources.getDrawable(this, R.drawable.ic_food)!!
        )
        adapter.addFragment(
            PersonsFragment(),
            getString(R.string.txt_persons),
            AppCompatResources.getDrawable(this, R.drawable.ic_persons)!!
        )

        // Se asigna el adapter al ViewPager2.
        binding.viewPager2.adapter = adapter

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