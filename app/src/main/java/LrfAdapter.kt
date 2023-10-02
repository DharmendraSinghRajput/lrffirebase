/*package com.example.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LrfAdapter(private val onClick: (position: Int, viewId: Int) -> Unit) : RecyclerView.Adapter<LrfAdapter.PostLikeViewHolder>() {

    private var postLikeList = listOf<PostLikeResponse.Data>()

    inner class PostLikeViewHolder(val mBinding: ItemsFollowingBinding) : RecyclerView.ViewHolder(mBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostLikeViewHolder = PostLikeViewHolder(ItemsFollowingBinding.inflate(
        LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: PostLikeViewHolder, position: Int) {
        holder.mBinding.apply {

            val followData = postLikeList[position]

            postLikeList[position].apply {

                GeneralFunctions.loadImage(root.context, followData.user.vImage, ivFollowers)
                tvName.text = "${followData.user.vFirstName ?: ""} ${followData.user.vLastName ?: ""}"
                tvUnfollow.text = root.context.getString(R.string.follow)

                root.setOnClickListener { onClick(position, -1) }
            }
        }
    }

    override fun getItemCount(): Int = postLikeList.size

    fun setData(followingList: List<PostLikeResponse.Data>) {
        this.postLikeList = followingList
        notifyDataSetChanged()
    }
}*/
//------------------------------------------------------------------------------------------
/*
postLikeViewModel.getUserDetailsPostLoveSitResponse.observe(viewLifecycleOwner) {
    handleLoader(it) { successResponse ->
        getAllPostResponse = (successResponse.data as PostLikeResponse)
        if (!getAllPostResponse.data.isNullOrEmpty()) {
            ivDataNotFound.visibility = View.GONE
            postLikeAdapter.setData(getAllPostResponse.data ?: arrayListOf())
        } else {
            ivDataNotFound.visibility = View.VISIBLE
        }
    }
}
*/

//---------------------------------------------------------
/*
private val postLikeAdapter by lazy {
    PostLikeAdapter { position, viewId ->
        startActivity(Intent(requireContext(), ViewAsActivity::class.java).apply {
            putExtra(APIConst.iUserId, getAllPostResponse.data[position].iUserId)
        })
    }
}

rvPostLike.apply {
    layoutManager = LinearLayoutManager(requireContext())
    adapter = postLikeAdapter
}*/
