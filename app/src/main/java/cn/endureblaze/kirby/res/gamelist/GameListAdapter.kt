package cn.endureblaze.kirby.res.gamelist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import cn.ednureblaze.glidecache.GlideCache
import cn.endureblaze.kirby.Kirby
import cn.endureblaze.kirby.R
import cn.endureblaze.kirby.databinding.ItemConsoleBinding
import cn.endureblaze.kirby.res.dataclass.ResItem
import cn.endureblaze.kirby.utils.ActManager
import com.bumptech.glide.Glide

class GameListAdapter(private val gameList: List<ResItem>) :
    RecyclerView.Adapter<GameListAdapter.ViewHolder>() {

    private var mContext: Context? = null

    inner class ViewHolder(itemGameListBinding: ItemConsoleBinding) : RecyclerView.ViewHolder(itemGameListBinding.root) {
        val itemGameList = itemGameListBinding.root
        val linearLayout = itemGameListBinding.LinearLayout
        val gameListImage = itemGameListBinding.consoleImage
        val blurImage = itemGameListBinding.blurImage
        val gameListName = itemGameListBinding.consoleText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mContext == null) {
            mContext = parent.context
        }
        val itemGameListBinding = ItemConsoleBinding.inflate(LayoutInflater.from(mContext), parent, false)
        val holder = ViewHolder(itemGameListBinding)
        holder.linearLayout.setOnClickListener {

        }
        return holder
    }

    override fun getItemCount() = gameList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gameList = gameList[position]

        val animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_recycler_item_show)
        val alphaAnimation = AlphaAnimation(0.1f, 1.0f)
        alphaAnimation.duration = 500

        holder.itemGameList.startAnimation(animation)
        holder.gameListImage.animation = alphaAnimation
        holder.blurImage.animation = alphaAnimation
        holder.gameListName.animation = alphaAnimation

        holder.gameListName.text = gameList.name
        Glide.with(mContext!!)
            .load(gameList.imageUrl)
            .apply(Kirby.getGlideRequestOptions())
            .into(holder.gameListImage)
        GlideCache.setBlurImageViaGlideCache(ActManager.currentActivity, holder.blurImage, gameList.imageUrl, "8")
    }
}