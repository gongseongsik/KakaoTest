package com.example.Fragment

import android.widget.ImageView
import android.widget.TextView
import android.widget.ImageButton
import android.app.Activity
import android.content.Context
import com.example.Fragment.SearchFragment
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import com.example.kakaotest.R
import android.view.MenuItem
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.kakaotest.MainActivity
import com.bumptech.glide.Glide

class DetailFragment : Fragment() {
    private var imageurl: ImageView? = null
    private var tvtitle: TextView? = null
    private var tvprice: TextView? = null
    private var tvpublisher: TextView? = null
    private var tvreleasedate: TextView? = null
    private var tvcontents: TextView? = null
    private var title: String? = null
    private var publisher: String? = null
    private var contents: String? = null
    private var datetime: String? = null
    private var url: String? = null
    private var sale_price: String? = null
    private var qurey: String? = null
    private var position = 0
    private var btnlikes: ImageButton? = null
    private var mContext: Context? = null
    private var mActivity: Activity? = null
    private var searchFragment: SearchFragment? = null
    private val transaction: FragmentTransaction? = null
    private val cilck = false
    private var buttonon = false

    // TODO: Rename and change types of parameters
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detailmenu, menu)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        imageurl = view.findViewById<View>(R.id.imageurl) as ImageView
        tvtitle = view.findViewById<View>(R.id.tvtitle) as TextView
        tvprice = view.findViewById<View>(R.id.tvprice) as TextView
        tvpublisher = view.findViewById<View>(R.id.tvpublisher) as TextView
        tvreleasedate = view.findViewById<View>(R.id.tvreleasedate) as TextView
        tvcontents = view.findViewById<View>(R.id.tvcontents) as TextView
        btnlikes = view.findViewById<View>(R.id.btnlikes) as ImageButton
        btnlikes!!.setImageResource(R.drawable.unlike)
        val actionBar = (activity as MainActivity?)!!.supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        val bundle = arguments
        if (bundle != null) {
            title = bundle.getString("title")
            sale_price = bundle.getString("sale_price")
            publisher = bundle.getString("publisher")
            contents = bundle.getString("contents")
            datetime = bundle.getString("datetime")
            url = bundle.getString("url")
            position = bundle.getInt("position")
            qurey = bundle.getString("qurey")
        }
        tvtitle!!.text = title
        tvprice!!.text = sale_price
        tvpublisher!!.text = publisher
        tvcontents!!.text = contents
        tvreleasedate!!.text = datetime
        mActivity?.let {
            Glide.with(it)
                .load(url)
                .placeholder(R.drawable.setting)
                .into(imageurl!!)
        }
        btnlikes!!.setOnClickListener {
            if (buttonon) {
                buttonon = false
                btnlikes!!.setImageResource(R.drawable.unlike)
                searchFragment = SearchFragment()
                val bundle = Bundle() // 파라미터의 숫자는 전달하려는 값의 갯수
                bundle.putBoolean("cilck", buttonon)
                bundle.putInt("position", position)
                searchFragment!!.arguments = bundle
                requireActivity().supportFragmentManager.setFragmentResult("detailresult", bundle)
            } else {
                buttonon = true
                btnlikes!!.setImageResource(R.drawable.like)
                searchFragment = SearchFragment()
                val bundle = Bundle() // 파라미터의 숫자는 전달하려는 값의 갯수
                bundle.putBoolean("cilck", buttonon)
                bundle.putInt("position", position)
                searchFragment!!.arguments = bundle
                requireActivity().supportFragmentManager.setFragmentResult("detailresult", bundle)
            }
        }
        return view
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
    }
}