package com.klimaatmobiel.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projecten3android.databinding.ListGroupmemberItemBinding
import com.klimaatmobiel.domain.Pupil

class AddGroupListAdapter: ListAdapter<Pupil, AddGroupListAdapter.ViewHolder>(GroupDiffCallBack()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.Companion.from(parent)
    }

    class ViewHolder private constructor(binding: ListGroupmemberItemBinding): RecyclerView.ViewHolder(binding.root) {
        val pupilName: EditText = binding.inputPupilName

        fun bind(item: Pupil) {
            pupilName.setText(item.firstName)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = ListGroupmemberItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class GroupDiffCallBack: DiffUtil.ItemCallback<Pupil>() {
        override fun areItemsTheSame(oldItem: Pupil, newItem: Pupil): Boolean {
            return oldItem.pupilId == newItem.pupilId
        }

        override fun areContentsTheSame(oldItem: Pupil, newItem: Pupil): Boolean {
            return oldItem.equals(newItem)
        }

    }

}

