# city-name-solitaire

apk 下载：[城市名接龙][city_name_solitaire_apk]

## 城市名数据：

- 来源：[YY天气][weather_api]

``` json
{
    "city_id": "CH",
    "name": "中国",
    "en": "China",
    "list": [
        {
            "city_id": "CH01",
            "name": "北京",
            "en": "",
            "list": [
                {
                    "city_id": "CH010100",
                    "name": "北京",
                    "en": "Beijing"
                },
                {
                    "city_id": "CH010200",
                    "name": "海淀",
                    "en": "haidian"
                },
                ....
```

- 城市数据预处理：

``` kotlin
yyWeather.cities()
		// 将多层次数据转换为单层数据源
        .map { it.list }
        .flatMap({ Flowable.fromIterable(it) })
        .flatMap({ Flowable.fromIterable(it.list) })
        // 逐个对数据源执行操作：获取首字拼音
        .map { CityInfo(this@MainActivity, it) }
        // 按首字拼音分组转换为 Map<String, List>结构
        // 其中 key 为首字拼音，List 为 FilterList，基于 LinkedHashMap 实现，提供去重功能
        .toMultimap({ it.first }, { it }, { TreeMap() }, { FilterList<CityInfo>() })
```

- 城市名接龙：

``` kotlin
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
```

[city_name_solitaire_apk]: http://os8g0rmq0.bkt.clouddn.com/cn.lspush.spruce_release_1.0.apk
[weather_api]: http://api.yytianqi.com/citylist/id/2