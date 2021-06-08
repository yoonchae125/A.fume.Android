package com.afume.afume_android.ui.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.response.RecentPerfumeItem
import com.afume.afume_android.databinding.RvItemHomeRecentBinding
import com.afume.afume_android.ui.detail.PerfumeDetailActivity

class RecentListAdapter(private val context: Context) : RecyclerView.Adapter<RecentListViewHolder>() {
    var data = mutableListOf<RecentPerfumeItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentListViewHolder {
        val binding : RvItemHomeRecentBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_home_recent,
            parent,
            false
        )

        return RecentListViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: RecentListViewHolder, position: Int) {
        data[position].let{
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = data.size

    internal fun setRecentPerfume(data : MutableList<RecentPerfumeItem>?){
        if(data!=null) this.data = data
        notifyDataSetChanged()
    }
}

class RecentListViewHolder(val binding: RvItemHomeRecentBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(item: RecentPerfumeItem){
        binding.item = item
        binding.executePendingBindings()

        binding.root.setOnClickListener {
            onClickPerfume(it, item.perfumeIdx)
        }
    }

    private fun onClickPerfume(view: View, perfumeIdx: Int){
        val intent = Intent(view.context, PerfumeDetailActivity::class.java)
        intent.putExtra("perfumeIdx", perfumeIdx)
        view.context.startActivity(intent)
    }
}