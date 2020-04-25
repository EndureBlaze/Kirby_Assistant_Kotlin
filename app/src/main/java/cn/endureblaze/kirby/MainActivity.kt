package cn.endureblaze.kirby

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import cn.endureblaze.kirby.adapter.ViewPagerAdapter
import cn.endureblaze.kirby.base.BaseActivity
import cn.endureblaze.kirby.base.BaseFragment
import cn.endureblaze.kirby.customview.NoScrollViewPager
import cn.endureblaze.kirby.res.MainResFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : BaseActivity() {

    private var fragmentList: MutableList<BaseFragment> = ArrayList<BaseFragment>();
    private var pagerTitleList: MutableList<String> = ArrayList<String>();

    private lateinit var toolbar: Toolbar
    private lateinit var mainFragmentViewPager: NoScrollViewPager

    private var fragmentPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        initFragmentPager()
        initBottomBar()
    }

    /**
     * 初始化 Toolbar
     */
    private fun initToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setSubtitle(R.string.title_res)
    }

    /**
     * 初始化 Fragment
     */
    private fun initFragmentPager() {
        val mainResFragment = MainResFragment()

        fragmentList.add(mainResFragment)
        pagerTitleList.add(resources.getString(R.string.title_res))

        mainFragmentViewPager = findViewById(R.id.main_fragment_viewpager)
        mainFragmentViewPager.adapter = ViewPagerAdapter(
            supportFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
            fragmentList
        )
        mainFragmentViewPager.setScrool(false)
        mainFragmentViewPager.offscreenPageLimit = 4
    }

    /**
     * 初始化底栏
     */
    private fun initBottomBar() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.main_bottom_navigation_bar)
        bottomNavigationView.setOnNavigationItemSelectedListener {

            when(it.itemId){
                R.id.res -> {
                    mainFragmentViewPager.currentItem = 0
                    toolbar.subtitle = pagerTitleList[0]
                }
            }

            return@setOnNavigationItemSelectedListener true
        }
        // 为ViewPager添加页面改变事件
        mainFragmentViewPager.addOnPageChangeListener(object : OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                // 将当前的页面对应的底部标签设为选中状态
                bottomNavigationView.menu.getItem(position).isChecked = true
                fragmentPos = position
            }

            override fun onPageSelected(position: Int) {

            }

        })
    }

    /**
     * 用于重置 MainActivity
     */
    private fun reloadMain() {
        val reloadMain = Intent(this, MainActivity::class.java)
        reloadMain.putExtra("theme", true)
        startActivity(reloadMain)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}
