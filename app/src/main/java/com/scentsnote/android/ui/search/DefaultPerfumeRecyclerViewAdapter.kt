package com.scentsnote.android.ui.search

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.scentsnote.android.ScentsNoteApplication
import com.scentsnote.android.data.vo.response.PerfumeInfo
import com.scentsnote.android.databinding.RvItemDefaultPerfumeBinding
import com.scentsnote.android.ui.detail.PerfumeDetailActivity
import com.scentsnote.android.util.createDialog


class DefaultPerfumeRecyclerViewAdapter(val context: Context, val fragmentManager: FragmentManager, val clickBtnHeart:(Int)->Unit) :
    RecyclerView.Adapter<DefaultPerfumeRecyclerViewAdapter.DefaultPerfumeRecyclerViewHolder>() {
    var data = listOf<PerfumeInfo>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DefaultPerfumeRecyclerViewHolder {
        val binding =
            RvItemDefaultPerfumeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DefaultPerfumeRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: DefaultPerfumeRecyclerViewHolder, position: Int) {
        holder.bind(data[position])
    }

    internal fun setData(list: MutableList<PerfumeInfo>) {
        this.data = list
        notifyDataSetChanged()
    }

    inner class DefaultPerfumeRecyclerViewHolder(val binding: RvItemDefaultPerfumeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PerfumeInfo) {
            binding.perfume = data
            binding.root.setOnClickListener {
                    goToPerfumeDetailsWithPerfumeIdx(it, data.perfumeIdx)
            }

            binding.btnHeart.setOnClickListener {
                // 좋아요 누르면 로그인 하게 유도
                if (!ScentsNoteApplication.prefManager.haveToken()) context.createDialog(fragmentManager, "login")
                else {
                    clickBtnHeart(data.perfumeIdx)
                    it.isSelected = !it.isSelected
                }
            }
        }

        private fun goToPerfumeDetailsWithPerfumeIdx(view: View, perfumeIdx: Int) {
            val intent = Intent(view.context, PerfumeDetailActivity::class.java)
            intent.putExtra("perfumeIdx", perfumeIdx)
            view.context.startActivity(intent)
        }
    }
}