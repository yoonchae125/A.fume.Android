package com.scentsnote.android.ui.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.scentsnote.android.ScentsNoteApplication
import com.scentsnote.android.R
import com.scentsnote.android.data.vo.response.HomePerfumeItem
import com.scentsnote.android.databinding.RvItemHomePopularBinding
import com.scentsnote.android.ui.detail.PerfumeDetailActivity
import com.scentsnote.android.util.createDialog

class PopularListAdapter(private val context: Context, private val fragmentManager: FragmentManager, val clickBtnLike:(Int)->Unit) : RecyclerView.Adapter<PopularListAdapter.PopularListViewHolder>() {
    var data = mutableListOf<HomePerfumeItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularListViewHolder {
        val binding: RvItemHomePopularBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_home_popular,
            parent,
            false
        )

        return PopularListViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: PopularListViewHolder, position: Int) {
        data[position].let{
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = data.size

    internal fun setCommonPerfume(data : MutableList<HomePerfumeItem>?){
        if(data!=null) this.data = data
        notifyDataSetChanged()
    }

    inner class PopularListViewHolder(val binding : RvItemHomePopularBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: HomePerfumeItem){
            binding.item = item
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                onClickPerfume(it, item.perfumeIdx)
            }

            binding.btnLike.setOnClickListener {
                if (!ScentsNoteApplication.prefManager.haveToken()) context.createDialog(fragmentManager, "login")
                else {
                    clickBtnLike(item.perfumeIdx)
                }
            }
        }

        private fun onClickPerfume(view: View, perfumeIdx: Int){
            val intent = Intent(view.context, PerfumeDetailActivity::class.java)
            intent.putExtra("perfumeIdx", perfumeIdx)
            view.context.startActivity(intent)
        }
    }
}