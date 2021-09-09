package com.KingsStory.Application

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

// 지도뷰 밑 RecyclerView 어뎁터
class UCareListAdapter:ListAdapter<UcareModel,UCareListAdapter.ItemViewHolder>(differ) {
    inner class ItemViewHolder(val view : View): RecyclerView.ViewHolder(view){
        fun bind(UcareModel: UcareModel){
                val addrTextView: TextView = view.findViewById(R.id.address_TextView)
                val rankTextView: TextView = view.findViewById(R.id.rank_TextView)
                val parttimeTextView: TextView = view.findViewById(R.id.parttime_TextView)
                val genderTextView: TextView = view.findViewById(R.id.gender_TextView)
                val phoneTextView: TextView = view.findViewById(R.id.phone_TextView)
                addrTextView.text = UcareModel.address
                rankTextView.text = UcareModel.rank.toString()
                parttimeTextView.text = UcareModel.parttime
                genderTextView.text = UcareModel.gender
                phoneTextView.text = UcareModel.company
                phoneTextView.setOnClickListener {
                    Log.d("testt", "클릭")
                }


        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(inflater.inflate(R.layout.ucarelist,parent,false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
    private fun dpToPx(context: Context, dp:Int):Int{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp.toFloat(),context.resources.displayMetrics).toInt()
    }
    companion object{
        val differ = object : DiffUtil.ItemCallback<UcareModel>(){
            override fun areItemsTheSame(oldItem: UcareModel, newItem: UcareModel): Boolean {
                return oldItem.no == newItem.no
            }

            override fun areContentsTheSame(oldItem: UcareModel, newItem: UcareModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}


