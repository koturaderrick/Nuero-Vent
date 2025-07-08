package com.nuerovent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.nuerovent.R
import com.nuerovent.databinding.ItemDefaultConditionBinding
import com.nuerovent.model.DefaultConditionItem

class DefaultConditionAdapter(
    private val items: List<DefaultConditionItem>,
    private val onMenuAction: (position: Int, action: String) -> Unit  // callback for menu actions
) : RecyclerView.Adapter<DefaultConditionAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemDefaultConditionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DefaultConditionItem) {
            binding.titleText.text = item.title
            binding.subText.text = item.subtext

            binding.moreIcon.setOnClickListener { view ->
                val popup = PopupMenu(view.context, view)
                popup.menuInflater.inflate(R.menu.condition_menu, popup.menu)
                popup.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.action_set_temp -> {
                            onMenuAction(adapterPosition, "set_temp")
                            true
                        }
                        R.id.action_set_humidity -> {
                            onMenuAction(adapterPosition, "set_humidity")
                            true
                        }
                        R.id.action_set_pressure -> {
                            onMenuAction(adapterPosition, "set_pressure")
                            true
                        }
                        else -> false
                    }
                }
                popup.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDefaultConditionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
