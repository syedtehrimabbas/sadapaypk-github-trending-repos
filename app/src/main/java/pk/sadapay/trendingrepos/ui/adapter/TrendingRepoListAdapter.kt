package pk.sadapay.trendingrepos.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import pk.sadapay.trendingrepos.R
import pk.sadapay.trendingrepos.data.dto.Repo
import pk.sadapay.trendingrepos.databinding.ItemRepoBinding
import javax.inject.Inject

class TrendingRepoListAdapter @Inject constructor() :
    RecyclerView.Adapter<TrendingRepoListAdapter.TrendingRepoViewHolder>() {

    private var list: MutableList<Repo> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingRepoViewHolder {
        return TrendingRepoViewHolder(
            ItemRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TrendingRepoViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<Repo>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class TrendingRepoViewHolder(private val itemRepoBinding: ItemRepoBinding) :
        RecyclerView.ViewHolder(itemRepoBinding.root) {
        init {
            itemView.setOnClickListener {
                if (itemRepoBinding.expandableLayout.isExpanded)
                    itemRepoBinding.expandableLayout.collapse()
                else
                    itemRepoBinding.expandableLayout.expand()
            }
        }

        fun bind(item: Repo) {
            with(itemRepoBinding) {
                tvOwnerName.text = item.name
                tvRepoName.text = item.fullName
                tvDetails.text = item.description
                tvTopicName.text = item.language?.let {
                    it
                } ?: item.name

                tvStarCount.text = item.stargazersCount?.toString()

                Glide.with(ivProfile.context)
                    .load(item.owner?.avatarUrl)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .placeholder(R.drawable.placeholder)
                    .into(ivProfile)
            }
        }
    }
}