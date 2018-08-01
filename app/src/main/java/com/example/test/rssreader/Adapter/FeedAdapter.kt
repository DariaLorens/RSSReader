package com.example.test.rssreader.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.constraint.R.id.parent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.TextView
import com.example.test.rssreader.Interface.ItemClickListner
import com.example.test.rssreader.Model.RSSObject
import com.example.test.rssreader.R
import java.text.FieldPosition

class FeedViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {
    var txtTitle:TextView
    var txtPubdate:TextView
    var txtContent:TextView

    private var itemClickListner : ItemClickListner?=null

    init {
        txtTitle = itemView.findViewById<TextView>(R.id.txtTitle)
        txtPubdate = itemView.findViewById<TextView>(R.id.txtPubdate)
        txtContent = itemView.findViewById<TextView>(R.id.txtContent)

        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    fun setItemClickListener(itemClickListner: ItemClickListner){
        this.itemClickListner = itemClickListner
    }

    override fun onClick(v: View?) {
        itemClickListner!!.onClick(v, adapterPosition, false)
    }

    override fun onLongClick(v: View?): Boolean {
        itemClickListner!!.onClick(v, adapterPosition, true)
        return true
    }
}

class FeedAdapter(private val rssObject: RSSObject, private val mContext:Context):RecyclerView.Adapter<FeedViewHolder>(){
   private val inflater:LayoutInflater

    init {
        inflater = LayoutInflater.from(mContext)
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FeedViewHolder {
        val itemView = inflater.inflate(R.layout.row, parent, false)
        return FeedViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return rssObject.items.size
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.txtTitle.text = rssObject.items[position].title
        holder.txtContent.text = rssObject.items[position].content
        holder.txtPubdate.text = rssObject.items[position].pubData

        holder.setItemClickListener(ItemClickListner { view, position, isLongClick ->

            if(!isLongClick){
                val browserIntent = Intent (Intent.ACTION_VIEW, Uri.parse(rssObject.items[position].link))
                mContext.startActivity(browserIntent)
            }
        })
    }

}