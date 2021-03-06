package cn.endureblaze.kirby.ui.res

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import cn.ednureblaze.glidecache.GlideCache
import cn.endureblaze.kirby.Kirby
import cn.endureblaze.kirby.R
import cn.endureblaze.kirby.databinding.ItemResBinding
import cn.endureblaze.kirby.logic.model.ResItem
import cn.endureblaze.kirby.ui.cheatcode.CheatCodeActivity
import cn.endureblaze.kirby.ui.gamelist.GameListActivity
import cn.endureblaze.kirby.utils.ActManager
import com.bumptech.glide.Glide

class ResListAdapter(private val resList: List<ResItem>) :
    RecyclerView.Adapter<ResListAdapter.ViewHolder>() {

    private var mContext: Context? = null

    inner class ViewHolder(itemResBinding: ItemResBinding) : RecyclerView.ViewHolder(itemResBinding.root) {
        val itemRes = itemResBinding.root
        val linearLayout = itemResBinding.LinearLayout
        val name = itemResBinding.name
        val image = itemResBinding.image
        val blurImage = itemResBinding.blurImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mContext == null) {
            mContext = parent.context
        }
        val itemResBinding = ItemResBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return ViewHolder(itemResBinding)
    }

    override fun getItemCount(): Int = resList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val res = resList[position]
        val animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_recycler_item_show)
        val alphaAnimation = AlphaAnimation(0.1f, 1.0f)
        alphaAnimation.duration = 500

        holder.itemRes.startAnimation(animation)
        holder.image.animation = alphaAnimation
        holder.name.animation = alphaAnimation
        holder.blurImage.animation = alphaAnimation

        holder.name.text = res.name
        Glide.with(mContext!!)
            .load(res.imageUrl)
            .apply(Kirby.getGlideRequestOptions())
            .into(holder.image)

        if (res.type != "console") {
            GlideCache.setBlurImageViaGlideCache(ActManager.currentActivity, holder.blurImage, res.imageUrl, "8")
        }

        holder.linearLayout.setOnClickListener {
            when (res.type) {
                "console" -> mContext?.let { GameListActivity.actionStart(it, res.tag, res.name) }
                "cheatcode" -> CheatCodeActivity.actionStart(mContext!!, res.tag)
            }
        }
    }
}