@file:Suppress("UNCHECKED_CAST")

package cn.lspush.spruce

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import cn.lspush.spruce.data.model.CityInfo
import cn.lspush.spruce.data.remote.YYWeatherService
import cn.lspush.spruce.utility.FilterList
import cn.lspush.spruce.utility.JLog
import cn.lspush.spruce.utility.PinyinUtils
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class MainActivity : AppCompatActivity() {
    val random by lazy { Random(System.currentTimeMillis()) }
    var cityMap : TreeMap<String, FilterList<CityInfo>>? = null

    val cityEt by lazy { findViewById(R.id.city_et) as EditText }
    val cityTv by lazy { findViewById(R.id.city_tv) as TextView }
    val submitBtn by lazy { findViewById(R.id.submit_btn) as Button }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cityEt.isEnabled = false
        cityTv.isEnabled = false
        submitBtn.isEnabled = false

        submitBtn.setOnClickListener({
            val key = PinyinUtils.getInstance(this).getFullPinyin(cityEt.text.last().toString())
            if (cityMap != null && cityMap?.get(key) != null) {
                val cities = cityMap!!.get(key)
                val size = cities!!.size
                if (size != 0) {
                    val index = random.nextInt(size)
                    val cityInfo = cities.get(index)
                    cityTv.text = cityInfo.name
                } else {
                    cityTv.text = "无匹配城市"
                }
            }
        })

        val yyWeather = YYWeatherService.apis
        yyWeather.cities()
                .map { it.list }
                .flatMap({ Flowable.fromIterable(it) })
                .flatMap({ Flowable.fromIterable(it.list) })
                .map { CityInfo(this@MainActivity, it) }
                .toMultimap({ it.first }, { it }, { TreeMap() }, { FilterList<CityInfo>() })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    JLog.i("cities", YYWeatherService.gson.toJson(it))
                    cityMap = it as TreeMap<String, FilterList<CityInfo>>

                    cityEt.isEnabled = true
                    cityTv.isEnabled = true
                    submitBtn.isEnabled = true
                }, {
                    JLog.e("cities", "deal cities failed", it)
                    Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                })
    }
}
