package com.aopcloud.palmproject.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cily.utils.base.StrUtils;

import java.util.HashMap;
import java.util.Map;

public class WeatherUtils {

    private static Map<String, String> cityMap = new HashMap<>();
    static {
        String city = "{\n" +
                "  \"北京\":\"1010100\",\n" +
                "  \"上海\":\"1010200\",\n" +
                "  \"天津\":\"1010300\",\n" +
                "  \"重庆\":\"1010400\",\n" +
                "  \"七台河\":\"1010510\",\n" +
                "  \"伊春\":\"1010508\",\n" +
                "  \"大庆\":\"1010509\",\n" +
                "  \"佳木斯\":\"1010504\",\n" +
                "  \"绥化\":\"1010505\",\n" +
                "  \"黑河\":\"1010506\",\n" +
                "  \"大兴安岭\":\"1010507\",\n" +
                "  \"双鸭山\":\"1010513\",\n" +
                "  \"哈尔滨\":\"1010501\",\n" +
                "  \"鸡西\":\"1010511\",\n" +
                "  \"齐齐哈尔\":\"1010502\",\n" +
                "  \"鹤岗\":\"1010512\",\n" +
                "  \"牡丹江\":\"1010503\",\n" +
                "  \"松原\":\"1010608\",\n" +
                "  \"白山\":\"1010609\",\n" +
                "  \"四平\":\"1010604\",\n" +
                "  \"通化\":\"1010605\",\n" +
                "  \"白城\":\"1010606\",\n" +
                "  \"辽源\":\"1010607\",\n" +
                "  \"长春\":\"1010601\",\n" +
                "  \"吉林\":\"1010602\",\n" +
                "  \"延边\":\"1010603\",\n" +
                "  \"营口\":\"1010708\",\n" +
                "  \"阜新\":\"1010709\",\n" +
                "  \"抚顺\":\"1010704\",\n" +
                "  \"本溪\":\"1010705\",\n" +
                "  \"丹东\":\"1010706\",\n" +
                "  \"锦州\":\"1010707\",\n" +
                "  \"盘锦\":\"1010713\",\n" +
                "  \"葫芦岛\":\"1010714\",\n" +
                "  \"沈阳\":\"1010701\",\n" +
                "  \"大连\":\"1010702\",\n" +
                "  \"铁岭\":\"1010711\",\n" +
                "  \"鞍山\":\"1010703\",\n" +
                "  \"朝阳\":\"1010712\",\n" +
                "  \"辽阳\":\"1010710\",\n" +
                "  \"呼伦贝尔\":\"1010810\",\n" +
                "  \"巴彦淖尔\":\"1010808\",\n" +
                "  \"锡林郭勒\":\"1010809\",\n" +
                "  \"乌兰察布\":\"1010804\",\n" +
                "  \"通辽\":\"1010805\",\n" +
                "  \"赤峰\":\"1010806\",\n" +
                "  \"鄂尔多斯\":\"1010807\",\n" +
                "  \"呼和浩特\":\"1010801\",\n" +
                "  \"兴安盟\":\"1010811\",\n" +
                "  \"包头\":\"1010802\",\n" +
                "  \"阿拉善盟\":\"1010812\",\n" +
                "  \"乌海\":\"1010803\",\n" +
                "  \"邯郸\":\"1010910\",\n" +
                "  \"衡水\":\"1010908\",\n" +
                "  \"邢台\":\"1010909\",\n" +
                "  \"承德\":\"1010904\",\n" +
                "  \"唐山\":\"1010905\",\n" +
                "  \"廊坊\":\"1010906\",\n" +
                "  \"沧州\":\"1010907\",\n" +
                "  \"石家庄\":\"1010901\",\n" +
                "  \"秦皇岛\":\"1010911\",\n" +
                "  \"保定\":\"1010902\",\n" +
                "  \"张家口\":\"1010903\",\n" +
                "  \"忻州\":\"1011010\",\n" +
                "  \"运城\":\"1011008\",\n" +
                "  \"朔州\":\"1011009\",\n" +
                "  \"晋中\":\"1011004\",\n" +
                "  \"长治\":\"1011005\",\n" +
                "  \"晋城\":\"1011006\",\n" +
                "  \"临汾\":\"1011007\",\n" +
                "  \"太原\":\"1011001\",\n" +
                "  \"吕梁\":\"1011011\",\n" +
                "  \"大同\":\"1011002\",\n" +
                "  \"阳泉\":\"1011003\",\n" +
                "  \"铜川\":\"1011110\",\n" +
                "  \"汉中\":\"1011108\",\n" +
                "  \"宝鸡\":\"1011109\",\n" +
                "  \"榆林\":\"1011104\",\n" +
                "  \"渭南\":\"1011105\",\n" +
                "  \"商洛\":\"1011106\",\n" +
                "  \"安康\":\"1011107\",\n" +
                "  \"西安\":\"1011101\",\n" +
                "  \"杨凌\":\"1011111\",\n" +
                "  \"咸阳\":\"1011102\",\n" +
                "  \"延安\":\"1011103\",\n" +
                "  \"泰安\":\"1011208\",\n" +
                "  \"临沂\":\"1011209\",\n" +
                "  \"聊城\":\"1011217\",\n" +
                "  \"德州\":\"1011204\",\n" +
                "  \"烟台\":\"1011205\",\n" +
                "  \"日照\":\"1011215\",\n" +
                "  \"潍坊\":\"1011206\",\n" +
                "  \"莱芜\":\"1011216\",\n" +
                "  \"济宁\":\"1011207\",\n" +
                "  \"威海\":\"1011213\",\n" +
                "  \"枣庄\":\"1011214\",\n" +
                "  \"济南\":\"1011201\",\n" +
                "  \"青岛\":\"1011202\",\n" +
                "  \"滨州\":\"1011211\",\n" +
                "  \"淄博\":\"1011203\",\n" +
                "  \"东营\":\"1011212\",\n" +
                "  \"菏泽\":\"1011210\",\n" +
                "  \"阿克苏\":\"1011308\",\n" +
                "  \"喀什\":\"1011309\",\n" +
                "  \"昌吉\":\"1011304\",\n" +
                "  \"吐鲁番\":\"1011305\",\n" +
                "  \"克州\":\"1011315\",\n" +
                "  \"巴州\":\"1011306\",\n" +
                "  \"博州\":\"1011316\",\n" +
                "  \"阿拉尔\":\"1011307\",\n" +
                "  \"和田\":\"1011313\",\n" +
                "  \"阿勒泰\":\"1011314\",\n" +
                "  \"乌鲁木齐\":\"1011301\",\n" +
                "  \"克拉玛依\":\"1011302\",\n" +
                "  \"塔城\":\"1011311\",\n" +
                "  \"石河子\":\"1011303\",\n" +
                "  \"哈密\":\"1011312\",\n" +
                "  \"伊犁\":\"1011310\",\n" +
                "  \"林芝\":\"1011404\",\n" +
                "  \"昌都\":\"1011405\",\n" +
                "  \"那曲\":\"1011406\",\n" +
                "  \"阿里\":\"1011407\",\n" +
                "  \"拉萨\":\"1011401\",\n" +
                "  \"日喀则\":\"1011402\",\n" +
                "  \"山南\":\"1011403\",\n" +
                "  \"海北\":\"1011508\",\n" +
                "  \"格尔木\":\"1011509\",\n" +
                "  \"海南\":\"1011504\",\n" +
                "  \"果洛\":\"1011505\",\n" +
                "  \"玉树\":\"1011506\",\n" +
                "  \"海西\":\"1011507\",\n" +
                "  \"西宁\":\"1011501\",\n" +
                "  \"海东\":\"1011502\",\n" +
                "  \"黄南\":\"1011503\",\n" +
                "  \"酒泉\":\"1011608\",\n" +
                "  \"天水\":\"1011609\",\n" +
                "  \"庆阳\":\"1011604\",\n" +
                "  \"武威\":\"1011605\",\n" +
                "  \"金昌\":\"1011606\",\n" +
                "  \"张掖\":\"1011607\",\n" +
                "  \"白银\":\"1011613\",\n" +
                "  \"嘉峪关\":\"1011614\",\n" +
                "  \"兰州\":\"1011601\",\n" +
                "  \"定西\":\"1011602\",\n" +
                "  \"临夏\":\"1011611\",\n" +
                "  \"平凉\":\"1011603\",\n" +
                "  \"甘南\":\"1011612\",\n" +
                "  \"陇南\":\"1011610\",\n" +
                "  \"固原\":\"1011704\",\n" +
                "  \"中卫\":\"1011705\",\n" +
                "  \"银川\":\"1011701\",\n" +
                "  \"石嘴山\":\"1011702\",\n" +
                "  \"吴忠\":\"1011703\",\n" +
                "  \"开封\":\"1011808\",\n" +
                "  \"洛阳\":\"1011809\",\n" +
                "  \"三门峡\":\"1011817\",\n" +
                "  \"许昌\":\"1011804\",\n" +
                "  \"济源\":\"1011818\",\n" +
                "  \"平顶山\":\"1011805\",\n" +
                "  \"漯河\":\"1011815\",\n" +
                "  \"信阳\":\"1011806\",\n" +
                "  \"驻马店\":\"1011816\",\n" +
                "  \"南阳\":\"1011807\",\n" +
                "  \"濮阳\":\"1011813\",\n" +
                "  \"周口\":\"1011814\",\n" +
                "  \"郑州\":\"1011801\",\n" +
                "  \"安阳\":\"1011802\",\n" +
                "  \"焦作\":\"1011811\",\n" +
                "  \"新乡\":\"1011803\",\n" +
                "  \"鹤壁\":\"1011812\",\n" +
                "  \"商丘\":\"1011810\",\n" +
                "  \"连云港\":\"1011910\",\n" +
                "  \"徐州\":\"1011908\",\n" +
                "  \"淮安\":\"1011909\",\n" +
                "  \"苏州\":\"1011904\",\n" +
                "  \"南通\":\"1011905\",\n" +
                "  \"扬州\":\"1011906\",\n" +
                "  \"盐城\":\"1011907\",\n" +
                "  \"宿迁\":\"1011913\",\n" +
                "  \"南京\":\"1011901\",\n" +
                "  \"常州\":\"1011911\",\n" +
                "  \"无锡\":\"1011902\",\n" +
                "  \"泰州\":\"1011912\",\n" +
                "  \"镇江\":\"1011903\",\n" +
                "  \"荆州\":\"1012008\",\n" +
                "  \"宜昌\":\"1012009\",\n" +
                "  \"潜江\":\"1012017\",\n" +
                "  \"孝感\":\"1012004\",\n" +
                "  \"黄冈\":\"1012005\",\n" +
                "  \"天门\":\"1012015\",\n" +
                "  \"黄石\":\"1012006\",\n" +
                "  \"仙桃\":\"1012016\",\n" +
                "  \"咸宁\":\"1012007\",\n" +
                "  \"随州\":\"1012013\",\n" +
                "  \"荆门\":\"1012014\",\n" +
                "  \"武汉\":\"1012001\",\n" +
                "  \"襄阳\":\"1012002\",\n" +
                "  \"十堰\":\"1012011\",\n" +
                "  \"鄂州\":\"1012003\",\n" +
                "  \"神农架\":\"1012012\",\n" +
                "  \"恩施\":\"1012010\",\n" +
                "  \"衢州\":\"1012110\",\n" +
                "  \"丽水\":\"1012108\",\n" +
                "  \"金华\":\"1012109\",\n" +
                "  \"宁波\":\"1012104\",\n" +
                "  \"绍兴\":\"1012105\",\n" +
                "  \"台州\":\"1012106\",\n" +
                "  \"温州\":\"1012107\",\n" +
                "  \"杭州\":\"1012101\",\n" +
                "  \"舟山\":\"1012111\",\n" +
                "  \"湖州\":\"1012102\",\n" +
                "  \"嘉兴\":\"1012103\",\n" +
                "  \"阜阳\":\"1012208\",\n" +
                "  \"亳州\":\"1012209\",\n" +
                "  \"池州\":\"1012217\",\n" +
                "  \"淮南\":\"1012204\",\n" +
                "  \"马鞍山\":\"1012205\",\n" +
                "  \"六安\":\"1012215\",\n" +
                "  \"安庆\":\"1012206\",\n" +
                "  \"宿州\":\"1012207\",\n" +
                "  \"铜陵\":\"1012213\",\n" +
                "  \"宣城\":\"1012214\",\n" +
                "  \"合肥\":\"1012201\",\n" +
                "  \"蚌埠\":\"1012202\",\n" +
                "  \"滁州\":\"1012211\",\n" +
                "  \"芜湖\":\"1012203\",\n" +
                "  \"淮北\":\"1012212\",\n" +
                "  \"黄山\":\"1012210\",\n" +
                "  \"三明\":\"1012308\",\n" +
                "  \"南平\":\"1012309\",\n" +
                "  \"莆田\":\"1012304\",\n" +
                "  \"泉州\":\"1012305\",\n" +
                "  \"漳州\":\"1012306\",\n" +
                "  \"龙岩\":\"1012307\",\n" +
                "  \"福州\":\"1012301\",\n" +
                "  \"厦门\":\"1012302\",\n" +
                "  \"宁德\":\"1012303\",\n" +
                "  \"新余\":\"1012410\",\n" +
                "  \"景德镇\":\"1012408\",\n" +
                "  \"萍乡\":\"1012409\",\n" +
                "  \"抚州\":\"1012404\",\n" +
                "  \"宜春\":\"1012405\",\n" +
                "  \"吉安\":\"1012406\",\n" +
                "  \"赣州\":\"1012407\",\n" +
                "  \"南昌\":\"1012401\",\n" +
                "  \"鹰潭\":\"1012411\",\n" +
                "  \"九江\":\"1012402\",\n" +
                "  \"上饶\":\"1012403\",\n" +
                "  \"娄底\":\"1012508\",\n" +
                "  \"邵阳\":\"1012509\",\n" +
                "  \"衡阳\":\"1012504\",\n" +
                "  \"郴州\":\"1012505\",\n" +
                "  \"湘西\":\"1012515\",\n" +
                "  \"常德\":\"1012506\",\n" +
                "  \"益阳\":\"1012507\",\n" +
                "  \"永州\":\"1012514\",\n" +
                "  \"长沙\":\"1012501\",\n" +
                "  \"湘潭\":\"1012502\",\n" +
                "  \"张家界\":\"1012511\",\n" +
                "  \"株洲\":\"1012503\",\n" +
                "  \"怀化\":\"1012512\",\n" +
                "  \"岳阳\":\"1012510\",\n" +
                "  \"六盘水\":\"1012608\",\n" +
                "  \"黔西南\":\"1012609\",\n" +
                "  \"黔南\":\"1012604\",\n" +
                "  \"黔东南\":\"1012605\",\n" +
                "  \"铜仁\":\"1012606\",\n" +
                "  \"毕节\":\"1012607\",\n" +
                "  \"贵阳\":\"1012601\",\n" +
                "  \"遵义\":\"1012602\",\n" +
                "  \"安顺\":\"1012603\",\n" +
                "  \"广安\":\"1012708\",\n" +
                "  \"巴中\":\"1012709\",\n" +
                "  \"阿坝\":\"1012719\",\n" +
                "  \"雅安\":\"1012717\",\n" +
                "  \"绵阳\":\"1012704\",\n" +
                "  \"甘孜\":\"1012718\",\n" +
                "  \"南充\":\"1012705\",\n" +
                "  \"眉山\":\"1012715\",\n" +
                "  \"达州\":\"1012706\",\n" +
                "  \"凉山\":\"1012716\",\n" +
                "  \"遂宁\":\"1012707\",\n" +
                "  \"资阳\":\"1012713\",\n" +
                "  \"乐山\":\"1012714\",\n" +
                "  \"成都\":\"1012701\",\n" +
                "  \"攀枝花\":\"1012702\",\n" +
                "  \"宜宾\":\"1012711\",\n" +
                "  \"自贡\":\"1012703\",\n" +
                "  \"内江\":\"1012712\",\n" +
                "  \"广元\":\"1012721\",\n" +
                "  \"德阳\":\"1012720\",\n" +
                "  \"泸州\":\"1012710\",\n" +
                "  \"佛山\":\"1012808\",\n" +
                "  \"肇庆\":\"1012809\",\n" +
                "  \"揭阳\":\"1012819\",\n" +
                "  \"中山\":\"1012817\",\n" +
                "  \"梅州\":\"1012804\",\n" +
                "  \"阳江\":\"1012818\",\n" +
                "  \"汕头\":\"1012805\",\n" +
                "  \"潮州\":\"1012815\",\n" +
                "  \"深圳\":\"1012806\",\n" +
                "  \"东莞\":\"1012816\",\n" +
                "  \"珠海\":\"1012807\",\n" +
                "  \"清远\":\"1012813\",\n" +
                "  \"云浮\":\"1012814\",\n" +
                "  \"广州\":\"1012801\",\n" +
                "  \"韶关\":\"1012802\",\n" +
                "  \"江门\":\"1012811\",\n" +
                "  \"惠州\":\"1012803\",\n" +
                "  \"河源\":\"1012812\",\n" +
                "  \"汕尾\":\"1012821\",\n" +
                "  \"茂名\":\"1012820\",\n" +
                "  \"湛江\":\"1012810\",\n" +
                "  \"楚雄\":\"1012908\",\n" +
                "  \"普洱\":\"1012909\",\n" +
                "  \"曲靖\":\"1012904\",\n" +
                "  \"保山\":\"1012905\",\n" +
                "  \"德宏\":\"1012915\",\n" +
                "  \"文山\":\"1012906\",\n" +
                "  \"西双版纳\":\"1012916\",\n" +
                "  \"玉溪\":\"1012907\",\n" +
                "  \"迪庆\":\"1012913\",\n" +
                "  \"丽江\":\"1012914\",\n" +
                "  \"昆明\":\"1012901\",\n" +
                "  \"大理\":\"1012902\",\n" +
                "  \"临沧\":\"1012911\",\n" +
                "  \"红河\":\"1012903\",\n" +
                "  \"怒江\":\"1012912\",\n" +
                "  \"昭通\":\"1012910\",\n" +
                "  \"贵港\":\"1013008\",\n" +
                "  \"玉林\":\"1013009\",\n" +
                "  \"来宾\":\"1013004\",\n" +
                "  \"桂林\":\"1013005\",\n" +
                "  \"梧州\":\"1013006\",\n" +
                "  \"贺州\":\"1013007\",\n" +
                "  \"北海\":\"1013013\",\n" +
                "  \"防城港\":\"1013014\",\n" +
                "  \"南宁\":\"1013001\",\n" +
                "  \"崇左\":\"1013002\",\n" +
                "  \"钦州\":\"1013011\",\n" +
                "  \"柳州\":\"1013003\",\n" +
                "  \"河池\":\"1013012\",\n" +
                "  \"百色\":\"1013010\",\n" +
                "  \"海南\":\"1013101\",\n" +
                "  \"香港\":\"1013201\",\n" +
                "  \"澳门\":\"1013301\",\n" +
                "  \"台中\":\"1013404\",\n" +
                "  \"台北\":\"1013401\",\n" +
                "  \"高雄\":\"1013402\"\n" +
                "}";

        JSONObject jo = JSON.parseObject(city);
        for (String k : jo.keySet()){
            cityMap.put(k, jo.getString("k"));
        }
    }

    public static String getCityCode(String city){
        if (StrUtils.isEmpty(city)){
            return null;
        }
        for (String key : cityMap.keySet()) {
            if (!StrUtils.isEmpty(key)) {
                if (city.contains(key)){
                    return cityMap.get(city);
                }
            }
        }
        return null;
    }

}