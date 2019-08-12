package com.example.cpt.more

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.cpt.R
import kotlinx.android.synthetic.main.activity_cpt__more__news.*

class CPT_More_News : AppCompatActivity() {

    var newsList : ArrayList<News_Model>? = null
    var newsAdapter : News_Adapter? = null
    var bottomSheetBehavior : BottomSheetBehavior<View>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cpt__more__news)
        supportActionBar!!.hide()

        setBusttons()
        newsList = getNews()
        buildCardListView()

        bottomSheetBehavior = BottomSheetBehavior.from(nsmore_news_newsdetails)
    }

    private fun buildCardListView() {
        newsAdapter = News_Adapter(newsList!!)
        rvmore_news_newslist.layoutManager = LinearLayoutManager(this)
        rvmore_news_newslist.adapter = newsAdapter!!

        newsAdapter!!.setOnItemClickListener(object: News_Adapter.OnItemClickListener {
            override fun onNextClick(position: Int) {
                wvmore_news_newsdetail.loadUrl(newsList!!.get(position).url)
                bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
            }
        })
    }
    private fun setBusttons() {
        tvmore_news_cancel.setOnClickListener {
            onBackPressed()
        }
    }
    private fun getNews() : ArrayList<News_Model>{
        var list = ArrayList<News_Model>()
        var card1 = News_Model("New Transit App Coming Soon!!","https://www.calgarytransit.com/news/", R.drawable.next)
        var card2 = News_Model("Transit Operator Rescues Rescue Dog","https://www.calgarytransit.com/news/transit-operator-rescues-rescue-dog", R.drawable.next)
        var card3 = News_Model("2019 Calgary Transit Service Review","https://www.calgarytransit.com/news/2019-calgary-transit-service-review", R.drawable.next)
        var card4 = News_Model("Spring Service Changes","https://www.calgarytransit.com/news/spring-service-changes", R.drawable.next)

        list.add(card1)
        list.add(card2)
        list.add(card3)
        list.add(card4)
        return list
    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}



class News_Model (var newsTitle: String, var url: String, var next: Int) {

    fun getnewsTitle() : String {
        return newsTitle.toString()
    }
    fun geturl() : String {
        return url.toString()
    }
    fun getnext() : Int{
        return next
    }
}

class News_Adapter: RecyclerView.Adapter<News_Adapter.NewsViewHolder> {

    var NewsList : ArrayList<News_Model>? = null
    var listener : OnItemClickListener? = null

    constructor(newsList: ArrayList<News_Model>) {
        NewsList = newsList
    }

    interface OnItemClickListener {
        fun onNextClick(position: Int)
    }

    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.customview_more_news_newslist, parent, false)
        return NewsViewHolder(view, listener!!)
    }

    override fun getItemCount(): Int {
        return NewsList!!.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item: News_Model = NewsList!![position]

        holder.newsTitle.setText(item.getnewsTitle())
        holder.url = item.geturl()
        holder.next.setImageResource(item.getnext())

    }

    class NewsViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView){
        var newsTitle: TextView
        var url: String
        var next: ImageView

        init {
            newsTitle = itemView.findViewById(R.id.tvmore_news_newstitle)
            url = ""
            next = itemView.findViewById(R.id.ivmore_news_next)

            itemView.setOnClickListener(object: View.OnClickListener{
                override fun onClick(v: View?) {
                    val position: Int = adapterPosition
                    if(position != RecyclerView.NO_POSITION) {
                        listener.onNextClick(position)
                    }
                }
            })
        }
    }
}