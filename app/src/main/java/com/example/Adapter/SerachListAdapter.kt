package com.example.Adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.Fragment.DetailFragment
import android.widget.AdapterView.OnItemClickListener
import android.view.ViewGroup
import android.view.View
import android.view.LayoutInflater
import com.example.kakaotest.R
import com.example.Adapter.SerachListAdapter.SearchLikeViewHolder
import com.bumptech.glide.Glide
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.Model.Document
import okhttp3.internal.notify

class SerachListAdapter(private val context: Context, documents: List<Document>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var documents: List<Document>? = null
    private var detailFragment: DetailFragment? = null
    private var transaction: FragmentTransaction? = null
    private var fragmentManager: FragmentManager? = null
    private var cntType = 0
    private val mListener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, i: Int): RecyclerView.ViewHolder {
        val view: View
        val context = parent.context
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

//        if(cntType==1)
//        {
//            view = layoutInflater.inflate(R.layout.item_like, parent, false);
//            return new SearchLikeViewHolder(view);
//        }
        view = layoutInflater.inflate(R.layout.item_like, parent, false)
        return SearchLikeViewHolder(view)
    }

    override fun onBindViewHolder(Holder: RecyclerView.ViewHolder, position: Int) {
//        if(Holder instanceof SearchLikeViewHolder)
//        {
        val document = documents!![position]
        val searchLikeViewHolder = Holder as SearchLikeViewHolder
        val idx = document.datetime!!.indexOf("T")
        Glide.with(Holder.itemView.context).load(document.thumbnail).placeholder(R.drawable.setting)
            .thumbnail(0.1f).into(searchLikeViewHolder.ivbook)
        searchLikeViewHolder.tvbooknamevlaue.text = document.authors!![0]
        searchLikeViewHolder.tvbookdescription.text = document.contents
        searchLikeViewHolder.tvbookpricevlaue.text = document.price
        searchLikeViewHolder.tvreleasedatevalue.text = document.datetime!!.substring(0, idx)
        searchLikeViewHolder.btnlike.setImageResource(R.drawable.unlike)
        if(cntType==position){
            searchLikeViewHolder.btnlike.setImageResource(R.drawable.like)
        }
        Holder.itemView.setOnClickListener {
            fragmentManager = (context as AppCompatActivity).supportFragmentManager
            detailFragment = DetailFragment()
            val bundle = Bundle()
            bundle.putString("title", document.title)
            bundle.putString("sale_price", document.salePrice.toString())
            bundle.putString("publisher", document.publisher)
            bundle.putString("contents", document.contents)
            bundle.putString("datetime", document.datetime!!.substring(0, idx))
            bundle.putString("url", document.thumbnail)
            bundle.putInt("position", Holder.getBindingAdapterPosition())
            detailFragment!!.arguments = bundle
            transaction = fragmentManager!!.beginTransaction()
            transaction!!.replace(R.id.frameLayout, detailFragment!!).commitAllowingStateLoss()
            transaction!!.addToBackStack(null)
        }

        //        }else
//        {
//            Document document = documents.get(position);
//            SearchListViewHolder searchListViewHolder =(SearchListViewHolder)Holder;
//            int idx = document.getDatetime().indexOf("T");
//            Glide.with(Holder.itemView.getContext()).load(document.getThumbnail()).placeholder(R.drawable.setting).thumbnail(0.1f).into(searchListViewHolder.ivbook);
//            searchListViewHolder.tvbooknamevlaue.setText(document.getAuthors().get(0));
//            searchListViewHolder.tvbookdescription.setText(document.getContents());
//            searchListViewHolder.tvbookpricevlaue.setText(document.getPrice());
//            searchListViewHolder.tvreleasedatevalue.setText(document.getDatetime().substring(0,idx));
//            Holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
//                    detailFragment = new DetailFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("title", document.getTitle());
//                    bundle.putString("sale_price", String.valueOf(document.getSalePrice()));
//                    bundle.putString("publisher", document.getPublisher());
//                    bundle.putString("contents", document.getContents());
//                    bundle.putString("datetime", document.getDatetime().substring(0,idx));
//                    bundle.putString("url", document.getPublisher());
//                    bundle.putInt("position", Holder.getBindingAdapterPosition());
//                    detailFragment.setArguments(bundle);
//                    transaction = fragmentManager.beginTransaction();
//                    transaction.replace(R.id.frameLayout, detailFragment).commitAllowingStateLoss();
//                    transaction.addToBackStack(null);
//
//                }
//            });
//            }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        if (payloads.isEmpty()) {
            //If payloads is empty, it means that the entire viewholder is updated
            onBindViewHolder(holder, position)
        } else {
            val searchLikeViewHolder = holder as SearchLikeViewHolder
            searchLikeViewHolder.btnlike.setImageResource(R.drawable.like)
        }
    }

    fun setType(type: Int) {
        cntType = type
    }

    override fun getItemCount(): Int {
        return documents!!.size
    }

    internal inner class SearchListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvbooknamevlaue: TextView
        var tvbookdescription: TextView
        var tvbookpricevlaue: TextView
        var tvreleasedatevalue: TextView
        var ivbook: ImageView

        init {
            ivbook = view.findViewById<View>(R.id.ivbook) as ImageView
            tvbooknamevlaue = view.findViewById<View>(R.id.tvbooknamevlaue) as TextView
            tvbookdescription = view.findViewById<View>(R.id.tvbookdescription) as TextView
            tvbookpricevlaue = view.findViewById<View>(R.id.tvbookpricevlaue) as TextView
            tvreleasedatevalue = view.findViewById<View>(R.id.tvreleasedatevalue) as TextView
        }
    }

    internal inner class SearchLikeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvbooknamevlaue: TextView
        var tvbookdescription: TextView
        var tvbookpricevlaue: TextView
        var tvreleasedatevalue: TextView
        var ivbook: ImageView
        var btnlike: ImageView

        init {
            ivbook = view.findViewById<View>(R.id.ivbook) as ImageView
            tvbooknamevlaue = view.findViewById<View>(R.id.tvbooknamevlaue) as TextView
            tvbookdescription = view.findViewById<View>(R.id.tvbookdescription) as TextView
            tvbookpricevlaue = view.findViewById<View>(R.id.tvbookpricevlaue) as TextView
            tvreleasedatevalue = view.findViewById<View>(R.id.tvreleasedatevalue) as TextView
            btnlike = view.findViewById<View>(R.id.ivlike) as ImageView
        }
    }

    companion object {
        private const val TAG = "list"
    }

    init {
        this.documents = documents
    }
}