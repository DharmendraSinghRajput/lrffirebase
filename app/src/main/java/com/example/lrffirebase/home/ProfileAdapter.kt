package com.example.lrffirebase.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lrffirebase.R
import com.example.lrffirebase.databinding.ItemDisplayBinding
import com.example.util.GeneralFunctions


class ProfileAdapter(val onClick: (position: Int, viewId: Int) -> Unit): RecyclerView.Adapter<ProfileAdapter.DisplayProfileViewHolder>() {

    private var listDataUserResponse = listOf<ProfileResponse>()

    class DisplayProfileViewHolder(val mBinding:ItemDisplayBinding):RecyclerView.ViewHolder(mBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisplayProfileViewHolder = DisplayProfileViewHolder(ItemDisplayBinding.inflate(
        LayoutInflater.from(parent.context), parent, false))


    override fun getItemCount(): Int {
        return listDataUserResponse.size
    }

    override fun onBindViewHolder(holder: DisplayProfileViewHolder, position: Int) {
        holder.mBinding.apply {
        listDataUserResponse[position].also {

            GeneralFunctions.loadImage(root.context,it.image, ivEducation)
            tvEducationTitle.text= it.name
            tvEmail.text=it.email
            tvNumber.text=it.address
        }

            imgEdit.setOnClickListener {
                onClick(position, R.id.imgEdit)
            }

            imgDelete.setOnClickListener {
               // onClick(position, R.id.imgDelete)

                onClick(position, R.id.imgDelete)


            }

        }
    }

    fun setData(listProfile: List<ProfileResponse>) {
        this.listDataUserResponse = listProfile
        notifyDataSetChanged()
    }

}
