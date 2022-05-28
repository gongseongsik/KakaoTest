package com.example.kakaotest

import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.Fragment.DetailFragment
import com.example.Fragment.SearchFragment

class MainActivity : AppCompatActivity() {
    private var searchEditText: EditText? = null
    private var fragmentManager: FragmentManager? = null
    private var searchFragment: SearchFragment? = null
    private var detailFragment: DetailFragment? = null
    private var transaction: FragmentTransaction? = null
    private var toolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initSearchEditText()
        toolbar()
    }

    fun toolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.searchmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                setfragment()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun setfragment() {
        fragmentManager = supportFragmentManager
        searchFragment = SearchFragment()
        detailFragment = DetailFragment()
        val bundle = Bundle() // 파라미터의 숫자는 전달하려는 값의 갯수
        bundle.putString("qurey", searchEditText!!.text.toString())
        searchFragment!!.setArguments(bundle)
        transaction = fragmentManager!!.beginTransaction()
        transaction!!.replace(R.id.frameLayout, searchFragment!!).commitAllowingStateLoss()
        transaction!!.addToBackStack(null)
    }


    private fun initSearchEditText() {
        searchEditText = findViewById<View>(R.id.searchEditText) as EditText
        searchEditText!!.setOnEditorActionListener { v, actionId, event ->
            setfragment()
            true
        }
    }
}