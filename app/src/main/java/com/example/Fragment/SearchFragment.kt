package com.example.Fragment

import androidx.recyclerview.widget.RecyclerView
import com.example.Adapter.SerachListAdapter
import androidx.core.widget.NestedScrollView
import android.widget.ProgressBar
import java.util.ArrayList
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.kakaotest.R
import com.example.kakaotest.MainActivity
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.BackEnd.RetrofitApi
import com.example.BackEnd.ApiClient
import com.example.Model.Document
import com.example.Model.SearchRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var serachListAdapter: SerachListAdapter? = null
    private var nestedScrollView: NestedScrollView? = null
    private var progressBar: ProgressBar? = null
    private val documents: MutableList<Document> = ArrayList()
    private var getdocuments: List<Document> = ArrayList()
    private var qurey: String? = null
    private var page = 1
    private val size = 50
    private var cilck = false
    private var position = 0
    private var mContext: Context? = null
    private var mActivity: Activity? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        recyclerView = view.findViewById<View>(R.id.searchrecyclerView) as RecyclerView
        nestedScrollView = view.findViewById<View>(R.id.scroll_view) as NestedScrollView
        progressBar = view.findViewById<View>(R.id.progress_bar) as ProgressBar
        progressBar!!.isIndeterminate = false
        progressBar!!.progress = 80
        recyclerView!!.setHasFixedSize(true)
        val actionBar = (activity as MainActivity?)!!.supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(false)
        parentFragmentManager.setFragmentResultListener(
            "detailresult",
            this
        ) { requestKey, result ->
            position = result.getInt("position")
            cilck = result.getBoolean("cilck")
            if (cilck == true) {
                serachListAdapter!!.setType(position)
            }
        }
        serachListAdapter = activity?.let { SerachListAdapter(it, documents) }
        val layoutManager: LayoutManager = LinearLayoutManager(activity)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = serachListAdapter
        nestedScrollView!!.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                page++
                progressBar!!.visibility = View.VISIBLE
                serachData(qurey, size, page)
            }
        })
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.searchmenu, menu)
        menu.findItem(R.id.search).isVisible = true
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        if (context is Activity) {
            mActivity = mContext as Activity?
        }
    }

    override fun onDetach() {
        mActivity = null
        mContext = null
        super.onDetach()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            qurey = bundle.getString("qurey")
        } else {
            Toast.makeText(mContext, "qureynull", Toast.LENGTH_LONG).show()
        }
        serachData(qurey, size, page)
    }

    private fun serachData(qurey: String?, size: Int, page: Int) {
        val retrofitApi = ApiClient.getRetrofit2().create(RetrofitApi::class.java)
        val call = retrofitApi.getSearch(qurey, size, page)
        call.enqueue(object : Callback<SearchRequest?> {
            override fun onResponse(
                call: Call<SearchRequest?>,
                response: Response<SearchRequest?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    progressBar!!.visibility = View.GONE
                    val searchRequest = response.body()
                    getdocuments = searchRequest!!.documents!!
                    for (adbt in getdocuments) {
                        adbt.authors
                        adbt.title
                        adbt.contents
                        adbt.url
                        adbt.isbn
                        adbt.datetime
                        adbt.publisher
                        adbt.translators
                        adbt.price
                        adbt.salePrice
                        adbt.thumbnail
                        adbt.status
                        documents.add(adbt)
                    }
                    serachListAdapter = activity?.let { SerachListAdapter(it, documents) }
                    recyclerView!!.adapter = serachListAdapter
                }
            }

            override fun onFailure(call: Call<SearchRequest?>, t: Throwable) {
                Log.e("retrofit", t.message!!)
                Toast.makeText(mContext, "서버와의 연결을 확인해주세요", Toast.LENGTH_LONG).show()
            }
        })
    }
}