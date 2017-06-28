package cn.lspush.spruce.data.model

import android.content.Context
import cn.lspush.spruce.utility.FilterList
import cn.lspush.spruce.utility.PinyinUtils

/**
 * City info for city
 */
class CityInfo(ctx: Context, city: City) : FilterList.Key {
    val name : String
    val first : String
    val last : String

    init {
        val pinyinUtils = PinyinUtils.getInstance(ctx)
        name = city.name
        if (name.first() == '厦' && name.last() == '门') {
            first = "xia"
            last = "men"
        } else {
            first = pinyinUtils.getFullPinyin(name.first().toString())
            last = pinyinUtils.getFullPinyin(name.last().toString())
        }
    }

    companion object {
        val PINYIN_COMPARATOR = Comparator<CityInfo> {
            o1, o2 -> o1.first.compareTo(o2.first)
        }
    }

    override fun key(): String = name
}