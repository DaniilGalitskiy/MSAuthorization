package com.dang.msautorization.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dang.msautorization.R
import com.dang.msautorization.domain.user_info.entity.DynamicUser
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_dialog_bottom_sheet_home.view.*


class SignedUsersAdapter(private val onItemClick: (user: DynamicUser) -> Unit,
                         private val onLogoutClick: (user: DynamicUser) -> Unit) :

        ListAdapter<DynamicUser, SignedUsersAdapter.ListenItemViewHolder>(object :
                DiffUtil.ItemCallback<DynamicUser>() {

            override fun areItemsTheSame(oldItem: DynamicUser, newItem: DynamicUser) =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: DynamicUser, newItem: DynamicUser) =
                    oldItem == newItem

        }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListenItemViewHolder =
            ListenItemViewHolder(
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_dialog_bottom_sheet_home, parent, false)
            )

    override fun onBindViewHolder(holder: ListenItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class ListenItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var user: DynamicUser

        private val avatarBottomSheetView = itemView.avatarBottomSheetImageView
        private val loginBottomSheetTextView = itemView.loginBottomSheetTextView
        private val logoutBottomSheetButton = itemView.logoutBottomSheetButton

        init {
            itemView.setOnClickListener {
                onItemClick(user)
            }

            logoutBottomSheetButton.setOnClickListener {
                onLogoutClick(user)
            }
        }

        fun bind(listItem: DynamicUser) {
            this.user = listItem

            loginBottomSheetTextView.text = listItem.name
            Picasso.get()
                    .load(listItem.avatar)
                    .placeholder(R.drawable.ic_account_unknown)
                    .into(avatarBottomSheetView)

            itemView.isSelected = user.isActive
        }
    }
}