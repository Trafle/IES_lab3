package ua.kpi.comsys.factorio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ua.kpi.comsys.ip8311.Fermat
import ua.kpi.comsys.ip8311.Genetic
import ua.kpi.comsys.ip8311.Perceptron


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val vp2 : ViewPager2 = findViewById(R.id.viewpager)

        // Init margin parameters
        val booksPageMargins = vp2.layoutParams as ViewGroup.MarginLayoutParams
        booksPageMargins.setMargins(0, 0, 30, 0)
        val defaultMarignParams = vp2.layoutParams as ViewGroup.MarginLayoutParams
        defaultMarignParams.setMargins(0, 0, 0, 0)

        vp2.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 3;
            override fun createFragment(position: Int): Fragment {
                if (position == 0) {
                    vp2.layoutParams = defaultMarignParams
                    return Fermat()
                } else if (position == 1) {
                    vp2.layoutParams = defaultMarignParams
                    return Perceptron()
                } else if (position == 2) {
                    vp2.layoutParams = defaultMarignParams
                    return Genetic()
                } else {
                    return error("bad argument")
                }
            }

            var tabLayout : TabLayout = findViewById(R.id.tabLayout)
            TabLayoutMediator(tabLayout, vp2) { tab, _ ->
                vp2.setCurrentItem(tab.position, true)
            }.attach()

            tabLayout.getTabAt(0)?.apply{
                customView?.setOnClickListener(){
                    vp2.setCurrentItem(0)
                }
            }?.setIcon(R.drawable.fermat)?.setText("GENERAL")

            tabLayout.getTabAt(1)?.apply {
                customView?.setOnClickListener(){
                    vp2.setCurrentItem(1)
                }
            }?.setIcon(R.drawable.perceptron)?.setText("GRAPHS")

            tabLayout.getTabAt(2)?.apply {
                customView?.setOnClickListener(){
                    vp2.setCurrentItem(2)
                }
            }?.setIcon(R.drawable.genes)?.setText("BOOKS")

    }
}

