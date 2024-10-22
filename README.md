![image.png](https://raw.githubusercontent.com/wgllss/WX-Dex-High-Level/master/pic/q.jpg)

> 本文之天下剑学，一学即通，助你突破剑学上限，自生天剑护墙气体！


## 一、前言

### 1. **客户端应用配置文件数据，或者配置数据, 或者其他固定数据有哪些？**

1.1  首页固定菜单栏数据\
1.2  应用固定配置数据\
1.3  固定省市区数据等

### 2. **这些怎么动态处理？**

第一次启动就把数据下载下来，在本地存好，\
以后每次下次直接使用，有新的数据版本再次从网络拿取，再次替换本地存好

### 3. 有哪些可以执行方案，如果首页就用到，在第一次启动中哪种方案速度最快？

![jjjj2223.png](https://raw.githubusercontent.com/wgllss/WX-Dex-High-Level/master/pic/jjjj2223.png)

本文实例工程将采用 **`性能优化到极致`** 的天剑境界方案进行介绍：\
同时输出对比 **`耗时表格 以此来证明`**

### 4、这样做的好处有哪些？

**`一切都是为了用户体验，让体验做到极致`**

> 试想一下，如果首页用到什么配置数据，或者在启动优化过程中：即点开 **`Launcher`** 桌面图标到首页展示第一个界面之前， 用到某些配置数据，比如插件化模块，先加载哪些插件，加载本地插件哪些版本，新版本还是旧版，还是去网络下载等一系列的本地配置读取怎么做？
>
> 1、如果写死在代码里面没法动态修改\
> 2、如果将配置文件写在 **`assets`** 下，读取慢，读取完还要解析 ，而且无法动态修改\
> 3、如果将配置数据存放到 **`SharedPreferences`** 下，效果还不是很理想，偶尔还可能导致ANR\
> 4、大厂推广的 **`MMKV`** 来存储，拿到存储后json，用 **`fastJson`** 号称最快 **`json`** 解析工具，        强强结合,但是 **`MMKV`** 首次要先初始化， **`fastJson`** 首次很慢，\
> 5、最新一代存储KV工具 **`FastKV`** , 来存储 **`Protobuf`** 数据,但是 **`FastKV`** 还是要先初始化 再来存储，**`Protobuf`** 的首次解析一样的也很慢\
> 6、**`还有什么办法没有？`** 我曾研究过，也曾问过10多个大厂高级工程师，然而好像到了上面第5就已经是他们的最高上限了\
> 7、没有办法，那还得自己来实现，突破极限，本文下面示例工程将来介绍：

## 二、示例项目介绍研究

1.  准备好示例需求json数据：我们假设数据为省市区数据如下：
    ![q75.webp](https://raw.githubusercontent.com/wgllss/WX-Dex-High-Level/master/pic/q75.webp)
2.  为了数据量做到极致小，我们处理成如下格式数据：\
    ![2222222.jpg](https://raw.githubusercontent.com/wgllss/WX-Dex-High-Level/master/pic/2222222.jpg)
3.  我们在处理成一份 **`protobuf`** 数据来方便对比，这个没法截图看了
4.  我们建好项目工程如下图：\
    ![1111.jpg](https://raw.githubusercontent.com/wgllss/WX-Dex-High-Level/master/pic/1111.jpg)
5.  其中 **`WX-Dex-ICityModel`** 和 **`WX-Dex-ICityModel-PluginImpl`** 为lib工程
6.  **`WX-Dex-ICityModel`** 下面只是一个接口如下：

<!---->

    interface ICityModel {

        fun cityMap(): Map<String, List<String>>

    }

7\.  **`WX-Dex-ICityModel-PluginImpl`** 下面实现接口如下（数据太多，这里我删去大部分数据后贴出来）：

```

class PluginCityModelImpl : ICityModel {

    override fun cityMap() = mapOf(
        "北京" to listOf(
            "东城区2", "西城区", "崇文区", "宣武区", "朝阳区", "丰台区", "石景山区", "海淀区", "门头沟区", "房山区", "通州区", "顺义区", "昌平区", "大兴区", "平谷区", "怀柔区", "密云县", "延庆县"
        ), "天津" to listOf(
            "和平区", "河东区", "河西区", "南开区", "河北区", "红挢区", "滨海新区", "东丽区", "西青区", "津南区", "北辰区", "宁河区", "武清区", "静海县", "宝坻区", "蓟县"
        ), "河北" to listOf(
            "石家庄", "唐山", "秦皇岛", "邯郸", "邢台", "保定", "张家口", "承德", "沧州", "廊坊", "衡水"
        ), "山西" to listOf(
            "太原", "大同", "阳泉", "长治", "晋城", "朔州", "晋中", "运城", "忻州", "临汾", "吕梁"
        )
}
```

8.  把 **`WX-Dex-ICityModel-PluginImpl`** lib工程处理成插件dex文件，具体怎么操作原理，怎么使用，请看我前面写的插件化文章： **[Compose插件化：一个Demo带你入门Compose，同时带你入门插件化开发](https://juejin.cn/post/7425434773026537483 "https://juejin.cn/post/7425434773026537483")**
9.  然后我们看下\
    原始json数据（`city.txt`）,\
    处理后的json数据（`city_out.txt`）\
    处理后的Protobuf数据（`city_out_p`）\
    出来后的dex插件文件（`city_model_lib_dex`）
    ![333.jpg](https://raw.githubusercontent.com/wgllss/WX-Dex-High-Level/master/pic/333.jpg)
    可以看出 Protobuf数据是最小的，原始数据最大，但实际相差其实并不是很大
10. 示例工程app截图如下：\
    ![555.jpg](https://raw.githubusercontent.com/wgllss/WX-Dex-High-Level/master/pic/555.jpg)



## 三、研究分析
1\.  使用FastKV + FastJson 输出结果:\
![6666.jpg](https://raw.githubusercontent.com/wgllss/WX-Dex-High-Level/master/pic/6666.jpg)
2\.  使用FastKV + Protobuf 输出结果:\
![777.webp](https://raw.githubusercontent.com/wgllss/WX-Dex-High-Level/master/pic/777.webp)
3\.  使用加载外部插件dex方案读取输出结果：\
![888.jpg](https://raw.githubusercontent.com/wgllss/WX-Dex-High-Level/master/pic/888.jpg)
4\.  测试15次尝试平均值比较结果：
![999.jpg](https://raw.githubusercontent.com/wgllss/WX-Dex-High-Level/master/pic/999.jpg)
5\.  可以看到结果：\
首次加载结果，由于FastKV 和 FastJson 和Protobuf 他们其实相对还是太慢了\
dex加载数据集速度可以说是比他们

**`快10倍以上`**

即便是他们都不是第一次加载结果，也不会比dex加载快：
![10.jpg](https://raw.githubusercontent.com/wgllss/WX-Dex-High-Level/master/pic/10.jpg)

## 四、插件化之策略模式方案

1.  **上面深入分析研究了dex插件化加载速度是最快的**
2.  **dex文件需要从服务器下载，第一次怎么办？**
3.  **可以第一次加载代码里面的，当插件文件存在时直接使用插件，这是一种策略思想**

<!---->

    fun strategyMode() {
        // 这里我就不用标准策略模式写法了，用到策略思想
        val startTime = System.currentTimeMillis()
        val file = DynamicManageUtils.getDxFile(context, "d_dex", getDlfn("city_model_lib_dex", 1000))
        val cityModelImpl = if (file.exists()) WXClassLoader(file.absolutePath, null, context.classLoader).getInterface(ICityModel::class.java, "com.wx.city.model.impl.PluginCityModelImpl")
        else CityModelImpl()
        val endTime = System.currentTimeMillis()
        log(" 策略模式使用的 耗时：${(endTime - startTime)} ms ${cityModelImpl.cityMap().get("北京")!!.get(0)}")
    }

4\.  **这样第一次走代码里面默认，有新数据时候下载插件下来，自动加载插件**
5\.  **`深度思考`:如果开发一个SDK包，默认走本地包里面，需要修改SDK内容时候，直接更新插件，这样不是做到动态化了吗？**

6.  **`再次深度思考：`上面SDK的下载逻辑，判断下载插件，下载配置逻辑默认都在包内实现，如果这些下载逻辑，加载插件逻辑需要修改，这块逻辑也做成插件下载下来，真正的SDK插件也可以下载下来，这样不是可以做到全动态化了吗**
7.  **`原来：全动态化原理是这样的，不仅插件可以下载，连接入宿主的那一部分逻辑都可以动态修改的`**
8.  看到这里，可以参考我前面的文章，即头部 **`8篇全动态系列 WXDynamicPlugin 开源框架的文章`**

## 五 总结

1.  **本位带领大家探索了 应用配置数据，怎样首次加载最：**\
    抛弃：**`Json`**\
    抛弃：**`Protobuf`**\
    抛弃：**`SharedPreferences`**\
    抛弃：**`MMKV`**\
    抛弃：**`FastKV`**\
    使用：**`Dex插件加载技术`**
2.  **怎么动态化使用Dex插件加载策略方案**
3.  **深入领悟到全动态插件化是怎么实现的**


#### 感谢阅读：

#### 欢迎    关注，点赞、收藏

## 全动态插件化框架WXDynamicPlugin介绍文章：

#### [(一) 插件化框架开发背景：零反射，零HooK,全动态化，插件化框架，全网唯一结合启动优化的插件化架构](https://juejin.cn/post/7347994218235363382)

#### [(二）插件化框架主要介绍：零反射，零HooK,全动态化，插件化框架，全网唯一结合启动优化的插件化架构](https://juejin.cn/post/7367676494976532490)

#### [(三）插件化框架内部详细介绍: 零反射，零HooK,全动态化，插件化框架，全网唯一结合启动优化的插件化架构](https://juejin.cn/post/7368397264026370083)

#### [(四）插件化框架接入详细指南：零反射，零HooK,全动态化，插件化框架，全网唯一结合启动优化的插件化架构](https://juejin.cn/post/7372393698230550565)

#### [(五) 大型项目架构：全动态插件化+模块化+Kotlin+协程+Flow+Retrofit+JetPack+MVVM+极限瘦身+极限启动优化+架构示例+全网唯一](https://juejin.cn/post/7381787510071934985)

#### [(六) 大型项目架构：解析全动态插件化框架WXDynamicPlugin是如何做到全动态化的？](https://juejin.cn/post/7388891131037777929)

#### [(七) 还在不断升级发版吗？从0到1带你看懂WXDynamicPlugin全动态插件化框架？](https://juejin.cn/post/7412124636239904819)

#### [(八) Compose插件化：一个Demo带你入门Compose，同时带你入门插件化开发](https://juejin.cn/post/7425434773026537483)

## 本人其他开源：

#### [Kotlin+协程+Flow+Retrofit+OkHttp这么好用，不运行安装到手机可以调试接口吗?可以自己搭建一套网络请求工具](https://juejin.cn/post/7406675078810910761)

#### [花式封装：Kotlin+协程+Flow+Retrofit+OkHttp +Repository，倾囊相授,彻底减少模版代码进阶之路](https://juejin.cn/post/7417847546323042345)

#### [注解处理器在架构，框架中实战应用：MVVM中数据源提供Repository类的自动生成](https://juejin.cn/post/7392258195089162290)

#### 感谢阅读，欢迎给给个星，你们的支持是我开源的动力

## 欢迎光临：

#### **[我的掘金地址](https://juejin.cn/user/356661835082573)**

#### 关于我

**VX号：wgllss**  ,如果想更多交流请加我VX
