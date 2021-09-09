package com.KingsStory.Application

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

// 지도뷰 밑 RecyclerView 어뎁터
class UCaregiverListAdapter:ListAdapter<CaregiverDto,UCaregiverListAdapter.ItemViewHolder>(differ) {
    inner class ItemViewHolder(val view : View): RecyclerView.ViewHolder(view){
        fun bind(CaregiverDto: CaregiverDto){
            val nameTextView:TextView = view.findViewById(R.id.caregiver_name_TextView)
            val genderTextView:TextView = view.findViewById(R.id.caregiver_gender_TextView)
            val addressTextView : TextView = view.findViewById(R.id.caregiver_address_TextView)
            val parttimeTextView : TextView = view.findViewById(R.id.caregiver_parttime_TextView)
            val phoneTextView : TextView = view.findViewById(R.id.caregiver_phone_TextView)
            nameTextView.text = CaregiverDto.name
            genderTextView.text = CaregiverDto.gender
            addressTextView.text = CaregiverDto.objectivearea
            parttimeTextView.text = CaregiverDto.parttime
            phoneTextView.text = CaregiverDto.phone
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ItemViewHolder(inflater.inflate(R.layout.ucaregiverlist,parent,false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
    private fun dpToPx(context: Context, dp:Int):Int{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp.toFloat(),context.resources.displayMetrics).toInt()
    }
    companion object{
        val differ = object : DiffUtil.ItemCallback<CaregiverDto>(){
            override fun areItemsTheSame(oldItem: CaregiverDto, newItem: CaregiverDto): Boolean {
                return oldItem.no == newItem.no
            }

            override fun areContentsTheSame(oldItem: CaregiverDto, newItem: CaregiverDto): Boolean {
                return oldItem == newItem
            }
        }
    }
}


