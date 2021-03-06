package com.suxin.ddlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.suxin.ddlite.view.main.RestaurantListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, RestaurantListFragment.newInstance())
                    .commitNow()
        }
    }
}