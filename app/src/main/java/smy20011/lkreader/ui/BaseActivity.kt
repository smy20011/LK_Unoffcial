package smy20011.lkreader.ui

import android.animation.LayoutTransition
import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import smy20011.lkreader.R
import smy20011.lkreader.util.findById

public abstract class BaseActivity : AppCompatActivity() {
    abstract val contentLayout: Int
    var content: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_activity)
        // Setup toolbar
        val toolBar = findById<Toolbar>(this, R.id.toolbar)
        setSupportActionBar(toolBar)
        // Inflate content view
        val inflater = getLayoutInflater()
        val container = findById<ViewGroup>(this, R.id.content)
        content = inflater.inflate(contentLayout, container)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reader, menu)
        return true
    }
}
