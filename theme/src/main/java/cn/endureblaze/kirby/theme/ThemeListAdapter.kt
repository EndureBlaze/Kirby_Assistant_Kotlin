package cn.endureblaze.kirby.theme

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import kotlin.properties.Delegates

var checkItem by Delegates.notNull<Int>()

class ThemeListAdapter constructor(private val context: Context, private val themeList: List<Int>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var mConvertView: View? = convertView
        val holder: Holder
        if (mConvertView == null) {
            mConvertView = LayoutInflater.from(context).inflate(R.layout.theme_list_dialog_layout, null)
            holder = Holder(mConvertView)
            mConvertView.tag = holder
        }else{
            holder= mConvertView.tag as Holder
        }
        holder.imageView1.setImageResource(themeList[position])
        if(checkItem == position){
            holder.imageView2.setImageResource(R.drawable.ic_kirby_done)
        }
        return mConvertView
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return themeList[position].toLong()
    }

    override fun getCount(): Int {
        return themeList.size
    }

    fun setCheckItem(checkItemPos: Int) {
        checkItem = checkItemPos
    }

    class Holder(convertView: View) {
        var imageView1: ImageView = convertView.findViewById(R.id.img_1)
        var imageView2: ImageView = convertView.findViewById(R.id.img_2)
    }
}