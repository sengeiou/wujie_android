package com.txd.hzj.wjlp.distribution.bean;

import java.util.List;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/6/13 16:53
 * 功能描述：小店上货商品信息列表bean类
 * 联系方式：
 */
public class ExhibitGoodsBean {

    /**
     * code : 200
     * message : 获取成功
     * data : {"top_nav":[{"cate_id":0,"short_name":"全部","name":"全部"},{"cate_id":"1","short_name":"食品生鲜","name":"食品生鲜"},{"cate_id":"2","short_name":"个护化妆","name":"个护化妆"},{"cate_id":"3","short_name":"母婴用品","name":"母婴用品"},{"cate_id":"4","short_name":"服饰内衣","name":"服饰内衣"},{"cate_id":"5","short_name":"成人鞋靴","name":"成人鞋靴"},{"cate_id":"6","short_name":"运动休闲","name":"运动休闲"},{"cate_id":"7","short_name":"酒水饮料","name":"酒水饮料"},{"cate_id":"8","short_name":"玩具乐器","name":"玩具乐器"},{"cate_id":"9","short_name":"礼品箱包","name":"礼品箱包"},{"cate_id":"10","short_name":"家居家装","name":"家居家装"},{"cate_id":"11","short_name":"厨房用具","name":"厨房用具"},{"cate_id":"12","short_name":"家用电器","name":"家用电器"},{"cate_id":"13","short_name":"电脑办公","name":"电脑办公"},{"cate_id":"14","short_name":"手机数码","name":"手机数码"},{"cate_id":"15","short_name":"钟表珠宝","name":"钟表珠宝"},{"cate_id":"16","short_name":"汽车用品","name":"汽车用品"},{"cate_id":"17","short_name":"宠物生活","name":"宠物生活"},{"cate_id":"18","short_name":"装修建材","name":"装修建材"},{"cate_id":"19","short_name":"医药保健","name":"医药保健"},{"cate_id":"20","short_name":"教育音像","name":"教育音像"},{"cate_id":"21","short_name":"农用物资","name":"农用物资"}],"two_cate_list":[{"id":"22","name":"休闲食品","short_name":"休闲食品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e01f99413.jpg","two_cate_id":"22"},{"id":"23","name":"粮油调味","short_name":"粮油调味","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2018-03-26/5ab8beca10b8a.jpg","two_cate_id":"23"},{"id":"24","name":"水果","short_name":"水果","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e05af08d8.jpg","two_cate_id":"24"},{"id":"25","name":"肉类蛋品","short_name":"肉类蛋品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e0752858b.jpg","two_cate_id":"25"},{"id":"26","name":"海鲜水产","short_name":"海鲜水产","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e090cdf83.jpg","two_cate_id":"26"},{"id":"27","name":"冷冻食品","short_name":"冷冻食品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e0aaacaa8.jpg","two_cate_id":"27"},{"id":"28","name":"蔬菜","short_name":"蔬菜","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e0c1a7691.jpg","two_cate_id":"28"},{"id":"29","name":"饮品甜品","short_name":"饮品甜品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e0e3482f0.jpg","two_cate_id":"29"},{"id":"30","name":"食品礼券","short_name":"食品礼券","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e1058f0fd.jpg","two_cate_id":"30"},{"id":"31","name":"洗发护发","short_name":"洗发护发","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e176f3265.jpg","two_cate_id":"31"},{"id":"32","name":"面部护肤","short_name":"面部护肤","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e197982fb.jpg","two_cate_id":"32"},{"id":"33","name":"口腔护理","short_name":"口腔护理","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e1b83a1fd.jpg","two_cate_id":"33"},{"id":"34","name":"身体护理","short_name":"身体护理","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e1d07e739.jpg","two_cate_id":"34"},{"id":"35","name":"女性护理","short_name":"女性护理","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e1ea7e591.jpg","two_cate_id":"35"},{"id":"36","name":"香水彩妆","short_name":"香水彩妆","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e2a5d1a63.jpg","two_cate_id":"36"},{"id":"37","name":"清洁用品","short_name":"清洁用品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e2c98d85b.jpg","two_cate_id":"37"},{"id":"38","name":"妈妈专区","short_name":"妈妈专区","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e2f36202e.jpg","two_cate_id":"38"},{"id":"39","name":"母婴奶粉","short_name":"母婴奶粉","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e30fd745e.jpg","two_cate_id":"39"},{"id":"40","name":"尿裤湿巾","short_name":"尿裤湿巾","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e3315d058.jpg","two_cate_id":"40"},{"id":"41","name":"喂养用品","short_name":"喂养用品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e349d0fe0.jpg","two_cate_id":"41"},{"id":"42","name":"营养辅食","short_name":"营养辅食","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e3631c689.jpg","two_cate_id":"42"},{"id":"43","name":"寝居服饰","short_name":"寝居服饰","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e390a5368.jpg","two_cate_id":"43"},{"id":"44","name":"洗护用品","short_name":"洗护用品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e3af53911.jpg","two_cate_id":"44"},{"id":"45","name":"童装童鞋","short_name":"童装童鞋","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e3ca55896.jpg","two_cate_id":"45"},{"id":"46","name":"童车童床","short_name":"童车童床","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e3e92a98a.jpg","two_cate_id":"46"},{"id":"47","name":"安全座椅","short_name":"安全座椅","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e407f23fc.jpg","two_cate_id":"47"},{"id":"48","name":"女装","short_name":"女装","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e4290d2db.jpg","two_cate_id":"48"},{"id":"49","name":"男装","short_name":"男装","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e4439a701.jpg","two_cate_id":"49"},{"id":"50","name":"内衣","short_name":"内衣","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e45c7520a.jpg","two_cate_id":"50"},{"id":"51","name":"服饰配件","short_name":"服饰配件","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e474aff71.jpg","two_cate_id":"51"},{"id":"52","name":"女鞋靴","short_name":"女鞋靴","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e49769f4a.jpg","two_cate_id":"52"},{"id":"53","name":"男鞋靴","short_name":"男鞋靴","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e4b19effb.jpg","two_cate_id":"53"},{"id":"54","name":"户外鞋服","short_name":"户外鞋服","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e4d5c28a2.jpg","two_cate_id":"54"},{"id":"55","name":"户外装备","short_name":"户外装备","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e4f2bd64e.jpg","two_cate_id":"55"},{"id":"56","name":"运动服饰","short_name":"运动服饰","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e512c25a0.jpg","two_cate_id":"56"},{"id":"57","name":"运动鞋包","short_name":"运动鞋包","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e5355387d.jpg","two_cate_id":"57"},{"id":"58","name":"健身训练","short_name":"健身训练","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e54e4ebb3.jpg","two_cate_id":"58"},{"id":"59","name":"体育用品","short_name":"体育用品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e569a39f4.jpg","two_cate_id":"59"},{"id":"60","name":"骑行运动","short_name":"骑行运动","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e58433078.jpg","two_cate_id":"60"},{"id":"61","name":"游泳用品","short_name":"游泳用品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e59dbf5a8.jpg","two_cate_id":"61"},{"id":"62","name":"垂钓用品","short_name":"垂钓用品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e5b4a1b2d.jpg","two_cate_id":"62"},{"id":"63","name":"中外名酒","short_name":"中外名酒","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e5d999f6a.jpg","two_cate_id":"63"},{"id":"64","name":"饮料冲调","short_name":"饮料冲调","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e5f69b3bd.jpg","two_cate_id":"64"},{"id":"65","name":"茗茶","short_name":"茗茶","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e60f090cd.jpg","two_cate_id":"65"},{"id":"66","name":"毛绒布艺","short_name":"毛绒布艺","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e6376cabd.jpg","two_cate_id":"66"},{"id":"67","name":"积木拼插","short_name":"积木拼插","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e661d05c8.jpg","two_cate_id":"67"},{"id":"68","name":"娃娃玩具","short_name":"娃娃玩具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e67aad13b.jpg","two_cate_id":"68"},{"id":"69","name":"益智玩具","short_name":"益智玩具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e6912023a.jpg","two_cate_id":"69"},{"id":"70","name":"DIY玩具","short_name":"DIY玩具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e6aabb5a5.jpg","two_cate_id":"70"},{"id":"71","name":"健身玩具","short_name":"健身玩具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e6c0832a7.jpg","two_cate_id":"71"},{"id":"72","name":"遥控/电动","short_name":"遥控/电动","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e6db3ba6e.jpg","two_cate_id":"72"},{"id":"73","name":"模型玩具","short_name":"模型玩具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e6f76bd1f.jpg","two_cate_id":"73"},{"id":"74","name":"创意减压","short_name":"创意减压","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e725a31d3.jpg","two_cate_id":"74"},{"id":"75","name":"动漫玩具","short_name":"动漫玩具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e7409a42b.jpg","two_cate_id":"75"},{"id":"76","name":"乐器","short_name":"乐器","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e86764f71.jpg","two_cate_id":"76"},{"id":"77","name":"潮流女包","short_name":"潮流女包","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e89281ef7.jpg","two_cate_id":"77"},{"id":"78","name":"精品男包","short_name":"精品男包","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e8ab4ecba.jpg","two_cate_id":"78"},{"id":"79","name":"功能箱包","short_name":"功能箱包","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e8c47ff9d.jpg","two_cate_id":"79"},{"id":"80","name":"礼品","short_name":"礼品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e8de1d3cc.jpg","two_cate_id":"80"},{"id":"81","name":"家纺","short_name":"家纺","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e908a1bc6.jpg","two_cate_id":"81"},{"id":"82","name":"生活日用","short_name":"生活日用","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e91ed1d4a.jpg","two_cate_id":"82"},{"id":"83","name":"收纳用品","short_name":"收纳用品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e93783012.jpg","two_cate_id":"83"},{"id":"84","name":"家装软饰","short_name":"家装软饰","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e94e90fff.jpg","two_cate_id":"84"},{"id":"85","name":"客厅家具","short_name":"客厅家具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e96606325.jpg","two_cate_id":"85"},{"id":"86","name":"卧室家具","short_name":"卧室家具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e98389933.jpg","two_cate_id":"86"},{"id":"87","name":"儿童家具","short_name":"儿童家具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e99d2a858.jpg","two_cate_id":"87"},{"id":"88","name":"餐厅家具","short_name":"餐厅家具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e9b65ef3c.jpg","two_cate_id":"88"},{"id":"89","name":"书房家具","short_name":"书房家具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e9cd40ef7.jpg","two_cate_id":"89"},{"id":"90","name":"储物家具","short_name":"储物家具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e9f49a861.jpg","two_cate_id":"90"},{"id":"91","name":"阳台/户外","short_name":"阳台/户外","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ea10798cd.jpg","two_cate_id":"91"},{"id":"92","name":"水具酒具","short_name":"水具酒具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ea663bc01.jpg","two_cate_id":"92"},{"id":"93","name":"餐具","short_name":"餐具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ea7ebdc6f.jpg","two_cate_id":"93"},{"id":"94","name":"刀剪菜板","short_name":"刀剪菜板","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ea95ee017.jpg","two_cate_id":"94"},{"id":"95","name":"烹饪锅具","short_name":"烹饪锅具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6eaafc81c4.jpg","two_cate_id":"95"},{"id":"96","name":"茶具/咖啡具","short_name":"茶具/咖啡具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6eac8bf0d1.jpg","two_cate_id":"96"},{"id":"97","name":"厨房配件","short_name":"厨房配件","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6eae38a79e.jpg","two_cate_id":"97"},{"id":"98","name":"酒店用品","short_name":"酒店用品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6eb07a7ef3.jpg","two_cate_id":"98"},{"id":"99","name":"大家电","short_name":"大家电","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6eb27a731c.jpg","two_cate_id":"99"},{"id":"100","name":"小家电","short_name":"小家电","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6eb4f28b6f.jpg","two_cate_id":"100"},{"id":"101","name":"厨卫大电","short_name":"厨卫大电","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6eb6beeb9f.jpg","two_cate_id":"101"},{"id":"102","name":"厨房小电","short_name":"厨房小电","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6eb8bad235.jpg","two_cate_id":"102"},{"id":"103","name":"个护健康","short_name":"个护健康","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6eba9c0228.jpg","two_cate_id":"103"},{"id":"104","name":"文具/耗材","short_name":"文具/耗材","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ebd51d0ec.jpg","two_cate_id":"104"},{"id":"105","name":"办公设备","short_name":"办公设备","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ece0aa733.jpg","two_cate_id":"105"},{"id":"106","name":"电脑整机","short_name":"电脑整机","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ed008db9d.jpg","two_cate_id":"106"},{"id":"107","name":"电脑配件","short_name":"电脑配件","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ed236692a.jpg","two_cate_id":"107"},{"id":"108","name":"外设产品","short_name":"外设产品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ed4af247a.jpg","two_cate_id":"108"},{"id":"109","name":"网络产品","short_name":"网络产品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ed679a3cf.jpg","two_cate_id":"109"},{"id":"110","name":"游戏设备","short_name":"游戏设备","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ed8af36da.jpg","two_cate_id":"110"},{"id":"111","name":"办公家具","short_name":"办公家具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6edaf4f540.jpg","two_cate_id":"111"},{"id":"112","name":"手机通讯","short_name":"手机通讯","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ede1991e3.jpg","two_cate_id":"112"},{"id":"113","name":"手机配件","short_name":"手机配件","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6edfb6c3ad.jpg","two_cate_id":"113"},{"id":"114","name":"智能设备","short_name":"智能设备","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ee1a5e1c6.jpg","two_cate_id":"114"},{"id":"115","name":"影音娱乐","short_name":"影音娱乐","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ee3c84bd0.jpg","two_cate_id":"115"},{"id":"116","name":"数码配件","short_name":"数码配件","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ee57e1f1e.jpg","two_cate_id":"116"},{"id":"117","name":"摄影摄像","short_name":"摄影摄像","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ee6eed047.jpg","two_cate_id":"117"},{"id":"118","name":"电子教育","short_name":"电子教育","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ee889c2e1.jpg","two_cate_id":"118"},{"id":"119","name":"运营商","short_name":"运营商","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6eea26eb8a.jpg","two_cate_id":"119"},{"id":"120","name":"钟表","short_name":"钟表","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6eed943f2b.jpg","two_cate_id":"120"},{"id":"121","name":"时尚饰品","short_name":"时尚饰品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6eef227b36.jpg","two_cate_id":"121"},{"id":"122","name":"木手串/把件","short_name":"木手串/把件","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ef0917e72.jpg","two_cate_id":"122"},{"id":"123","name":"银饰","short_name":"银饰","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ef2063a64.jpg","two_cate_id":"123"},{"id":"124","name":"K金饰品","short_name":"K金饰品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ef3908fb3.jpg","two_cate_id":"124"},{"id":"125","name":"珍珠","short_name":"珍珠","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ef503217a.jpg","two_cate_id":"125"},{"id":"126","name":"翡翠玉石","short_name":"翡翠玉石","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ef6f6cc1a.jpg","two_cate_id":"126"},{"id":"127","name":"水晶玛瑙","short_name":"水晶玛瑙","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ef86537d8.jpg","two_cate_id":"127"},{"id":"128","name":"彩宝","short_name":"彩宝","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6efa199619.jpg","two_cate_id":"128"},{"id":"129","name":"黄金","short_name":"黄金","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6efbd69cd3.jpg","two_cate_id":"129"},{"id":"130","name":"铂金","short_name":"铂金","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6efd42a4e6.jpg","two_cate_id":"130"},{"id":"131","name":"钻石","short_name":"钻石","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6efed642c5.jpg","two_cate_id":"131"},{"id":"132","name":"金银投资","short_name":"金银投资","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f009c5735.jpg","two_cate_id":"132"},{"id":"133","name":"汽车装饰","short_name":"汽车装饰","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f03187d36.jpg","two_cate_id":"133"},{"id":"134","name":"美容清洗","short_name":"美容清洗","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f0501a648.jpg","two_cate_id":"134"},{"id":"135","name":"车载电器","short_name":"车载电器","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f1e37a6cb.jpg","two_cate_id":"135"},{"id":"136","name":"维修保养","short_name":"维修保养","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f2078cf1e.jpg","two_cate_id":"136"},{"id":"137","name":"安全防护","short_name":"安全防护","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f22c5aaae.jpg","two_cate_id":"137"},{"id":"138","name":"赛事改装","short_name":"赛事改装","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f24786729.jpg","two_cate_id":"138"},{"id":"139","name":"宠物主粮","short_name":"宠物主粮","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f2bbb50a3.jpg","two_cate_id":"139"},{"id":"140","name":"宠物零食","short_name":"宠物零食","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f2ebbd8f9.jpg","two_cate_id":"140"},{"id":"141","name":"宠物玩具","short_name":"宠物玩具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f308b2f25.jpg","two_cate_id":"141"},{"id":"142","name":"宠物用品","short_name":"宠物用品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f3251b602.jpg","two_cate_id":"142"},{"id":"143","name":"洗护美容","short_name":"洗护美容","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f344097f1.jpg","two_cate_id":"143"},{"id":"144","name":"出行装备","short_name":"出行装备","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f36632dbd.jpg","two_cate_id":"144"},{"id":"145","name":"医疗保健","short_name":"医疗保健","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f447f0aae.jpg","two_cate_id":"145"},{"id":"146","name":"装修材料","short_name":"装修材料","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f47e9c9b2.jpg","two_cate_id":"146"},{"id":"147","name":"电工电料","short_name":"电工电料","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f494293e0.jpg","two_cate_id":"147"},{"id":"148","name":"灯饰照明","short_name":"灯饰照明","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f4a9cce2a.jpg","two_cate_id":"148"},{"id":"149","name":"五金工具","short_name":"五金工具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f4c517441.jpg","two_cate_id":"149"},{"id":"150","name":"厨房卫浴","short_name":"厨房卫浴","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f4e0ec484.jpg","two_cate_id":"150"},{"id":"151","name":"门窗","short_name":"门窗","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f4f647d06.jpg","two_cate_id":"151"},{"id":"152","name":"供热系统","short_name":"供热系统","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f5118a22d.jpg","two_cate_id":"152"},{"id":"153","name":"营养成分","short_name":"营养成分","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f5440bf43.jpg","two_cate_id":"153"},{"id":"154","name":"保健器械","short_name":"保健器械","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f56001d54.jpg","two_cate_id":"154"},{"id":"155","name":"传统滋补","short_name":"传统滋补","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f57691118.jpg","two_cate_id":"155"},{"id":"156","name":"护理护具","short_name":"护理护具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f58d42c58.jpg","two_cate_id":"156"},{"id":"157","name":"成人用品","short_name":"成人用品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f5ab9e6e0.jpg","two_cate_id":"157"},{"id":"158","name":"营养健康","short_name":"营养健康","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f5c49742b.jpg","two_cate_id":"158"},{"id":"159","name":"中西药品","short_name":"中西药品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f5e8cea7b.jpg","two_cate_id":"159"},{"id":"160","name":"图书光盘","short_name":"图书光盘","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f61078ad0.jpg","two_cate_id":"160"},{"id":"161","name":"音乐","short_name":"音乐","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f633cf5af.jpg","two_cate_id":"161"},{"id":"162","name":"影视","short_name":"影视","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f64936405.jpg","two_cate_id":"162"},{"id":"163","name":"职业教育","short_name":"职业教育","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f65e97ad0.jpg","two_cate_id":"163"},{"id":"164","name":"工具软件","short_name":"工具软件","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f67551644.jpg","two_cate_id":"164"},{"id":"165","name":"游戏软件","short_name":"游戏软件","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f68c051bb.jpg","two_cate_id":"165"},{"id":"166","name":"农药","short_name":"农药","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f6b3b8dc3.jpg","two_cate_id":"166"},{"id":"167","name":"肥料","short_name":"肥料","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f6c932a15.jpg","two_cate_id":"167"},{"id":"168","name":"饲料","short_name":"饲料","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f75248296.jpg","two_cate_id":"168"},{"id":"169","name":"种子/种苗","short_name":"种子/种苗","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f76d33ae7.jpg","two_cate_id":"169"},{"id":"170","name":"兽药","short_name":"兽药","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f7c9340b9.jpg","two_cate_id":"170"},{"id":"171","name":"盆栽/苗木","short_name":"盆栽/苗木","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f7e92c3af.jpg","two_cate_id":"171"},{"id":"172","name":"园林/农耕","short_name":"园林/农耕","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f8022268c.jpg","two_cate_id":"172"},{"id":"173","name":"兽用器具","short_name":"兽用器具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f81b94ea8.jpg","two_cate_id":"173"}],"ads":{"ads_id":"95","picture":"http://test.wujiemall.com/Uploads/Ads/2018-04-17/5ad5e1576344a.png","desc":"测试","href":"","position":"19","merchant_id":"4","goods_id":"0"},"list":[{"goods_id":"156","goods_name":"（饮品甜品其他-新测试）佰草汇五宝茶","country_id":"37","path":"/Uploads/Country/2018-02-06/5a7942dcee41c.png","goods_img":"http://test.wujiemall.com/Uploads/Goods/2018-02-24/5a90c1bd2d8ae.jpg","market_price":"35.00","shop_price":"29.80","integral":"2.00","ticket_buy_id":"1","discount":"30.00","yellow_discount":"15.00","blue_discount":"15.00","sell_num":"1232","country_logo":"http://test.wujiemall.com/Uploads/Country/2018-02-06/5a7942dcee41c.png","ticket_buy_discount":"30","all_goods_num":"8866"},{"goods_id":"589","goods_name":"（书柜/书架）书台书柜组合套装","country_id":"47","path":"/Uploads/Country/2017-11-29/5a1e84bd339ca.png","goods_img":"http://test.wujiemall.com/Uploads/Goods/2018-02-28/5a96447c0ec78.jpg","market_price":"1805.00","shop_price":"1799.00","integral":"74.00","ticket_buy_id":"0","discount":"20.00","yellow_discount":"10.00","blue_discount":"10.00","sell_num":"0","country_logo":"http://test.wujiemall.com/Uploads/Country/2017-11-29/5a1e84bd339ca.png","ticket_buy_discount":"20","all_goods_num":"1000"},{"goods_id":"202","goods_name":"（日韩表-新测试）街头欧美原宿风情侣手表","country_id":"47","path":"/Uploads/Country/2017-11-29/5a1e84bd339ca.png","goods_img":"http://test.wujiemall.com/Uploads/Goods/2018-02-24/5a90d96c8682d.jpg","market_price":"30.00","shop_price":"25.00","integral":"3.00","ticket_buy_id":"0","discount":"30.00","yellow_discount":"15.00","blue_discount":"15.00","sell_num":"1274","country_logo":"http://test.wujiemall.com/Uploads/Country/2017-11-29/5a1e84bd339ca.png","ticket_buy_discount":"30","all_goods_num":"763"},{"goods_id":"653","goods_name":"（再测试）和平挂面400g 清仓甩卖","country_id":"47","path":"/Uploads/Country/2017-11-29/5a1e84bd339ca.png","goods_img":"http://test.wujiemall.com/Uploads/Goods/2018-04-17/5ad58e9dd8197.jpg","market_price":"10.00","shop_price":"8.80","integral":"2.00","ticket_buy_id":"0","discount":"60.00","yellow_discount":"30.00","blue_discount":"30.00","sell_num":"33","country_logo":"http://test.wujiemall.com/Uploads/Country/2017-11-29/5a1e84bd339ca.png","ticket_buy_discount":"60","all_goods_num":"167"},{"goods_id":"177","goods_name":" （橄榄核/核桃-新测试）雕十八罗汉手串","country_id":"47","path":"/Uploads/Country/2017-11-29/5a1e84bd339ca.png","goods_img":"http://test.wujiemall.com/Uploads/Goods/2018-02-24/5a90d08cf15c0.jpg","market_price":"399.00","shop_price":"258.00","integral":"32.00","ticket_buy_id":"0","discount":"30.00","yellow_discount":"15.00","blue_discount":"15.00","sell_num":"1012","country_logo":"http://test.wujiemall.com/Uploads/Country/2017-11-29/5a1e84bd339ca.png","ticket_buy_discount":"30","all_goods_num":"69"},{"goods_id":"13","goods_name":"（再测试宝贝属性）女士羽绒服白鸭绒羽绒服","country_id":"47","path":"/Uploads/Country/2017-11-29/5a1e84bd339ca.png","goods_img":"http://test.wujiemall.com/Uploads/Goods/2018-02-23/5a8f7bada4f0c.jpg","market_price":"300.00","shop_price":"188.00","integral":"31.00","ticket_buy_id":"0","discount":"40.00","yellow_discount":"20.00","blue_discount":"20.00","sell_num":"53","country_logo":"http://test.wujiemall.com/Uploads/Country/2017-11-29/5a1e84bd339ca.png","ticket_buy_discount":"40","all_goods_num":"357"},{"goods_id":"22","goods_name":"（坚果炒货）百草林年货坚果大礼包1200克","country_id":"47","path":"/Uploads/Country/2017-11-29/5a1e84bd339ca.png","goods_img":"http://test.wujiemall.com/Uploads/Goods/2018-02-23/5a8f81d736344.jpg","market_price":"130.00","shop_price":"110.00","integral":"3.00","ticket_buy_id":"0","discount":"10.00","yellow_discount":"5.00","blue_discount":"5.00","sell_num":"10","country_logo":"http://test.wujiemall.com/Uploads/Country/2017-11-29/5a1e84bd339ca.png","ticket_buy_discount":"10","all_goods_num":"0"},{"goods_id":"646","goods_name":"（测试-面条）和平经典面条400g","country_id":"47","path":"/Uploads/Country/2017-11-29/5a1e84bd339ca.png","goods_img":"http://test.wujiemall.com/Uploads/Goods/2018-03-13/5aa77496e2711.jpg","market_price":"5.00","shop_price":"3.90","integral":"0.00","ticket_buy_id":"0","discount":"40.00","yellow_discount":"20.00","blue_discount":"20.00","sell_num":"34","country_logo":"http://test.wujiemall.com/Uploads/Country/2017-11-29/5a1e84bd339ca.png","ticket_buy_discount":"40","all_goods_num":"66"},{"goods_id":"5","goods_name":"（休闲零食）馋猫酥饼","country_id":"47","path":"/Uploads/Country/2017-11-29/5a1e84bd339ca.png","goods_img":"http://test.wujiemall.com/Uploads/Goods/2018-02-23/5a8f6178df211.jpg","market_price":"20.00","shop_price":"18.00","integral":"4.00","ticket_buy_id":"0","discount":"50.00","yellow_discount":"25.00","blue_discount":"25.00","sell_num":"100","country_logo":"http://test.wujiemall.com/Uploads/Country/2017-11-29/5a1e84bd339ca.png","ticket_buy_discount":"50","all_goods_num":"0"},{"goods_id":"667","goods_name":"34234","country_id":"47","path":"/Uploads/Country/2017-11-29/5a1e84bd339ca.png","goods_img":"http://test.wujiemall.com/Uploads/Goods/2018-07-10/5b44763bee098.jpg","market_price":"555.00","shop_price":"34.00","integral":"16.00","ticket_buy_id":"0","discount":"90.00","yellow_discount":"45.00","blue_discount":"45.00","sell_num":"0","country_logo":"http://test.wujiemall.com/Uploads/Country/2017-11-29/5a1e84bd339ca.png","ticket_buy_discount":"90","all_goods_num":"22"}]}
     * nums : 0
     */

    private int code;
    private String message;
    private DataBean data;
    private String nums;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public static class DataBean {
        /**
         * top_nav : [{"cate_id":0,"short_name":"全部","name":"全部"},{"cate_id":"1","short_name":"食品生鲜","name":"食品生鲜"},{"cate_id":"2","short_name":"个护化妆","name":"个护化妆"},{"cate_id":"3","short_name":"母婴用品","name":"母婴用品"},{"cate_id":"4","short_name":"服饰内衣","name":"服饰内衣"},{"cate_id":"5","short_name":"成人鞋靴","name":"成人鞋靴"},{"cate_id":"6","short_name":"运动休闲","name":"运动休闲"},{"cate_id":"7","short_name":"酒水饮料","name":"酒水饮料"},{"cate_id":"8","short_name":"玩具乐器","name":"玩具乐器"},{"cate_id":"9","short_name":"礼品箱包","name":"礼品箱包"},{"cate_id":"10","short_name":"家居家装","name":"家居家装"},{"cate_id":"11","short_name":"厨房用具","name":"厨房用具"},{"cate_id":"12","short_name":"家用电器","name":"家用电器"},{"cate_id":"13","short_name":"电脑办公","name":"电脑办公"},{"cate_id":"14","short_name":"手机数码","name":"手机数码"},{"cate_id":"15","short_name":"钟表珠宝","name":"钟表珠宝"},{"cate_id":"16","short_name":"汽车用品","name":"汽车用品"},{"cate_id":"17","short_name":"宠物生活","name":"宠物生活"},{"cate_id":"18","short_name":"装修建材","name":"装修建材"},{"cate_id":"19","short_name":"医药保健","name":"医药保健"},{"cate_id":"20","short_name":"教育音像","name":"教育音像"},{"cate_id":"21","short_name":"农用物资","name":"农用物资"}]
         * two_cate_list : [{"id":"22","name":"休闲食品","short_name":"休闲食品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e01f99413.jpg","two_cate_id":"22"},{"id":"23","name":"粮油调味","short_name":"粮油调味","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2018-03-26/5ab8beca10b8a.jpg","two_cate_id":"23"},{"id":"24","name":"水果","short_name":"水果","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e05af08d8.jpg","two_cate_id":"24"},{"id":"25","name":"肉类蛋品","short_name":"肉类蛋品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e0752858b.jpg","two_cate_id":"25"},{"id":"26","name":"海鲜水产","short_name":"海鲜水产","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e090cdf83.jpg","two_cate_id":"26"},{"id":"27","name":"冷冻食品","short_name":"冷冻食品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e0aaacaa8.jpg","two_cate_id":"27"},{"id":"28","name":"蔬菜","short_name":"蔬菜","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e0c1a7691.jpg","two_cate_id":"28"},{"id":"29","name":"饮品甜品","short_name":"饮品甜品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e0e3482f0.jpg","two_cate_id":"29"},{"id":"30","name":"食品礼券","short_name":"食品礼券","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e1058f0fd.jpg","two_cate_id":"30"},{"id":"31","name":"洗发护发","short_name":"洗发护发","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e176f3265.jpg","two_cate_id":"31"},{"id":"32","name":"面部护肤","short_name":"面部护肤","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e197982fb.jpg","two_cate_id":"32"},{"id":"33","name":"口腔护理","short_name":"口腔护理","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e1b83a1fd.jpg","two_cate_id":"33"},{"id":"34","name":"身体护理","short_name":"身体护理","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e1d07e739.jpg","two_cate_id":"34"},{"id":"35","name":"女性护理","short_name":"女性护理","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e1ea7e591.jpg","two_cate_id":"35"},{"id":"36","name":"香水彩妆","short_name":"香水彩妆","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e2a5d1a63.jpg","two_cate_id":"36"},{"id":"37","name":"清洁用品","short_name":"清洁用品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e2c98d85b.jpg","two_cate_id":"37"},{"id":"38","name":"妈妈专区","short_name":"妈妈专区","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e2f36202e.jpg","two_cate_id":"38"},{"id":"39","name":"母婴奶粉","short_name":"母婴奶粉","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e30fd745e.jpg","two_cate_id":"39"},{"id":"40","name":"尿裤湿巾","short_name":"尿裤湿巾","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e3315d058.jpg","two_cate_id":"40"},{"id":"41","name":"喂养用品","short_name":"喂养用品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e349d0fe0.jpg","two_cate_id":"41"},{"id":"42","name":"营养辅食","short_name":"营养辅食","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e3631c689.jpg","two_cate_id":"42"},{"id":"43","name":"寝居服饰","short_name":"寝居服饰","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e390a5368.jpg","two_cate_id":"43"},{"id":"44","name":"洗护用品","short_name":"洗护用品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e3af53911.jpg","two_cate_id":"44"},{"id":"45","name":"童装童鞋","short_name":"童装童鞋","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e3ca55896.jpg","two_cate_id":"45"},{"id":"46","name":"童车童床","short_name":"童车童床","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e3e92a98a.jpg","two_cate_id":"46"},{"id":"47","name":"安全座椅","short_name":"安全座椅","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e407f23fc.jpg","two_cate_id":"47"},{"id":"48","name":"女装","short_name":"女装","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e4290d2db.jpg","two_cate_id":"48"},{"id":"49","name":"男装","short_name":"男装","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e4439a701.jpg","two_cate_id":"49"},{"id":"50","name":"内衣","short_name":"内衣","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e45c7520a.jpg","two_cate_id":"50"},{"id":"51","name":"服饰配件","short_name":"服饰配件","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e474aff71.jpg","two_cate_id":"51"},{"id":"52","name":"女鞋靴","short_name":"女鞋靴","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e49769f4a.jpg","two_cate_id":"52"},{"id":"53","name":"男鞋靴","short_name":"男鞋靴","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e4b19effb.jpg","two_cate_id":"53"},{"id":"54","name":"户外鞋服","short_name":"户外鞋服","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e4d5c28a2.jpg","two_cate_id":"54"},{"id":"55","name":"户外装备","short_name":"户外装备","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e4f2bd64e.jpg","two_cate_id":"55"},{"id":"56","name":"运动服饰","short_name":"运动服饰","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e512c25a0.jpg","two_cate_id":"56"},{"id":"57","name":"运动鞋包","short_name":"运动鞋包","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e5355387d.jpg","two_cate_id":"57"},{"id":"58","name":"健身训练","short_name":"健身训练","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e54e4ebb3.jpg","two_cate_id":"58"},{"id":"59","name":"体育用品","short_name":"体育用品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e569a39f4.jpg","two_cate_id":"59"},{"id":"60","name":"骑行运动","short_name":"骑行运动","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e58433078.jpg","two_cate_id":"60"},{"id":"61","name":"游泳用品","short_name":"游泳用品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e59dbf5a8.jpg","two_cate_id":"61"},{"id":"62","name":"垂钓用品","short_name":"垂钓用品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e5b4a1b2d.jpg","two_cate_id":"62"},{"id":"63","name":"中外名酒","short_name":"中外名酒","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e5d999f6a.jpg","two_cate_id":"63"},{"id":"64","name":"饮料冲调","short_name":"饮料冲调","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e5f69b3bd.jpg","two_cate_id":"64"},{"id":"65","name":"茗茶","short_name":"茗茶","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e60f090cd.jpg","two_cate_id":"65"},{"id":"66","name":"毛绒布艺","short_name":"毛绒布艺","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e6376cabd.jpg","two_cate_id":"66"},{"id":"67","name":"积木拼插","short_name":"积木拼插","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e661d05c8.jpg","two_cate_id":"67"},{"id":"68","name":"娃娃玩具","short_name":"娃娃玩具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e67aad13b.jpg","two_cate_id":"68"},{"id":"69","name":"益智玩具","short_name":"益智玩具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e6912023a.jpg","two_cate_id":"69"},{"id":"70","name":"DIY玩具","short_name":"DIY玩具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e6aabb5a5.jpg","two_cate_id":"70"},{"id":"71","name":"健身玩具","short_name":"健身玩具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e6c0832a7.jpg","two_cate_id":"71"},{"id":"72","name":"遥控/电动","short_name":"遥控/电动","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e6db3ba6e.jpg","two_cate_id":"72"},{"id":"73","name":"模型玩具","short_name":"模型玩具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e6f76bd1f.jpg","two_cate_id":"73"},{"id":"74","name":"创意减压","short_name":"创意减压","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e725a31d3.jpg","two_cate_id":"74"},{"id":"75","name":"动漫玩具","short_name":"动漫玩具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e7409a42b.jpg","two_cate_id":"75"},{"id":"76","name":"乐器","short_name":"乐器","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e86764f71.jpg","two_cate_id":"76"},{"id":"77","name":"潮流女包","short_name":"潮流女包","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e89281ef7.jpg","two_cate_id":"77"},{"id":"78","name":"精品男包","short_name":"精品男包","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e8ab4ecba.jpg","two_cate_id":"78"},{"id":"79","name":"功能箱包","short_name":"功能箱包","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e8c47ff9d.jpg","two_cate_id":"79"},{"id":"80","name":"礼品","short_name":"礼品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e8de1d3cc.jpg","two_cate_id":"80"},{"id":"81","name":"家纺","short_name":"家纺","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e908a1bc6.jpg","two_cate_id":"81"},{"id":"82","name":"生活日用","short_name":"生活日用","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e91ed1d4a.jpg","two_cate_id":"82"},{"id":"83","name":"收纳用品","short_name":"收纳用品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e93783012.jpg","two_cate_id":"83"},{"id":"84","name":"家装软饰","short_name":"家装软饰","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e94e90fff.jpg","two_cate_id":"84"},{"id":"85","name":"客厅家具","short_name":"客厅家具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e96606325.jpg","two_cate_id":"85"},{"id":"86","name":"卧室家具","short_name":"卧室家具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e98389933.jpg","two_cate_id":"86"},{"id":"87","name":"儿童家具","short_name":"儿童家具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e99d2a858.jpg","two_cate_id":"87"},{"id":"88","name":"餐厅家具","short_name":"餐厅家具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e9b65ef3c.jpg","two_cate_id":"88"},{"id":"89","name":"书房家具","short_name":"书房家具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e9cd40ef7.jpg","two_cate_id":"89"},{"id":"90","name":"储物家具","short_name":"储物家具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e9f49a861.jpg","two_cate_id":"90"},{"id":"91","name":"阳台/户外","short_name":"阳台/户外","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ea10798cd.jpg","two_cate_id":"91"},{"id":"92","name":"水具酒具","short_name":"水具酒具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ea663bc01.jpg","two_cate_id":"92"},{"id":"93","name":"餐具","short_name":"餐具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ea7ebdc6f.jpg","two_cate_id":"93"},{"id":"94","name":"刀剪菜板","short_name":"刀剪菜板","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ea95ee017.jpg","two_cate_id":"94"},{"id":"95","name":"烹饪锅具","short_name":"烹饪锅具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6eaafc81c4.jpg","two_cate_id":"95"},{"id":"96","name":"茶具/咖啡具","short_name":"茶具/咖啡具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6eac8bf0d1.jpg","two_cate_id":"96"},{"id":"97","name":"厨房配件","short_name":"厨房配件","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6eae38a79e.jpg","two_cate_id":"97"},{"id":"98","name":"酒店用品","short_name":"酒店用品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6eb07a7ef3.jpg","two_cate_id":"98"},{"id":"99","name":"大家电","short_name":"大家电","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6eb27a731c.jpg","two_cate_id":"99"},{"id":"100","name":"小家电","short_name":"小家电","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6eb4f28b6f.jpg","two_cate_id":"100"},{"id":"101","name":"厨卫大电","short_name":"厨卫大电","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6eb6beeb9f.jpg","two_cate_id":"101"},{"id":"102","name":"厨房小电","short_name":"厨房小电","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6eb8bad235.jpg","two_cate_id":"102"},{"id":"103","name":"个护健康","short_name":"个护健康","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6eba9c0228.jpg","two_cate_id":"103"},{"id":"104","name":"文具/耗材","short_name":"文具/耗材","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ebd51d0ec.jpg","two_cate_id":"104"},{"id":"105","name":"办公设备","short_name":"办公设备","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ece0aa733.jpg","two_cate_id":"105"},{"id":"106","name":"电脑整机","short_name":"电脑整机","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ed008db9d.jpg","two_cate_id":"106"},{"id":"107","name":"电脑配件","short_name":"电脑配件","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ed236692a.jpg","two_cate_id":"107"},{"id":"108","name":"外设产品","short_name":"外设产品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ed4af247a.jpg","two_cate_id":"108"},{"id":"109","name":"网络产品","short_name":"网络产品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ed679a3cf.jpg","two_cate_id":"109"},{"id":"110","name":"游戏设备","short_name":"游戏设备","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ed8af36da.jpg","two_cate_id":"110"},{"id":"111","name":"办公家具","short_name":"办公家具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6edaf4f540.jpg","two_cate_id":"111"},{"id":"112","name":"手机通讯","short_name":"手机通讯","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ede1991e3.jpg","two_cate_id":"112"},{"id":"113","name":"手机配件","short_name":"手机配件","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6edfb6c3ad.jpg","two_cate_id":"113"},{"id":"114","name":"智能设备","short_name":"智能设备","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ee1a5e1c6.jpg","two_cate_id":"114"},{"id":"115","name":"影音娱乐","short_name":"影音娱乐","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ee3c84bd0.jpg","two_cate_id":"115"},{"id":"116","name":"数码配件","short_name":"数码配件","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ee57e1f1e.jpg","two_cate_id":"116"},{"id":"117","name":"摄影摄像","short_name":"摄影摄像","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ee6eed047.jpg","two_cate_id":"117"},{"id":"118","name":"电子教育","short_name":"电子教育","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ee889c2e1.jpg","two_cate_id":"118"},{"id":"119","name":"运营商","short_name":"运营商","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6eea26eb8a.jpg","two_cate_id":"119"},{"id":"120","name":"钟表","short_name":"钟表","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6eed943f2b.jpg","two_cate_id":"120"},{"id":"121","name":"时尚饰品","short_name":"时尚饰品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6eef227b36.jpg","two_cate_id":"121"},{"id":"122","name":"木手串/把件","short_name":"木手串/把件","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ef0917e72.jpg","two_cate_id":"122"},{"id":"123","name":"银饰","short_name":"银饰","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ef2063a64.jpg","two_cate_id":"123"},{"id":"124","name":"K金饰品","short_name":"K金饰品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ef3908fb3.jpg","two_cate_id":"124"},{"id":"125","name":"珍珠","short_name":"珍珠","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ef503217a.jpg","two_cate_id":"125"},{"id":"126","name":"翡翠玉石","short_name":"翡翠玉石","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ef6f6cc1a.jpg","two_cate_id":"126"},{"id":"127","name":"水晶玛瑙","short_name":"水晶玛瑙","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6ef86537d8.jpg","two_cate_id":"127"},{"id":"128","name":"彩宝","short_name":"彩宝","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6efa199619.jpg","two_cate_id":"128"},{"id":"129","name":"黄金","short_name":"黄金","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6efbd69cd3.jpg","two_cate_id":"129"},{"id":"130","name":"铂金","short_name":"铂金","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6efd42a4e6.jpg","two_cate_id":"130"},{"id":"131","name":"钻石","short_name":"钻石","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6efed642c5.jpg","two_cate_id":"131"},{"id":"132","name":"金银投资","short_name":"金银投资","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f009c5735.jpg","two_cate_id":"132"},{"id":"133","name":"汽车装饰","short_name":"汽车装饰","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f03187d36.jpg","two_cate_id":"133"},{"id":"134","name":"美容清洗","short_name":"美容清洗","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f0501a648.jpg","two_cate_id":"134"},{"id":"135","name":"车载电器","short_name":"车载电器","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f1e37a6cb.jpg","two_cate_id":"135"},{"id":"136","name":"维修保养","short_name":"维修保养","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f2078cf1e.jpg","two_cate_id":"136"},{"id":"137","name":"安全防护","short_name":"安全防护","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f22c5aaae.jpg","two_cate_id":"137"},{"id":"138","name":"赛事改装","short_name":"赛事改装","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f24786729.jpg","two_cate_id":"138"},{"id":"139","name":"宠物主粮","short_name":"宠物主粮","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f2bbb50a3.jpg","two_cate_id":"139"},{"id":"140","name":"宠物零食","short_name":"宠物零食","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f2ebbd8f9.jpg","two_cate_id":"140"},{"id":"141","name":"宠物玩具","short_name":"宠物玩具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f308b2f25.jpg","two_cate_id":"141"},{"id":"142","name":"宠物用品","short_name":"宠物用品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f3251b602.jpg","two_cate_id":"142"},{"id":"143","name":"洗护美容","short_name":"洗护美容","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f344097f1.jpg","two_cate_id":"143"},{"id":"144","name":"出行装备","short_name":"出行装备","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f36632dbd.jpg","two_cate_id":"144"},{"id":"145","name":"医疗保健","short_name":"医疗保健","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f447f0aae.jpg","two_cate_id":"145"},{"id":"146","name":"装修材料","short_name":"装修材料","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f47e9c9b2.jpg","two_cate_id":"146"},{"id":"147","name":"电工电料","short_name":"电工电料","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f494293e0.jpg","two_cate_id":"147"},{"id":"148","name":"灯饰照明","short_name":"灯饰照明","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f4a9cce2a.jpg","two_cate_id":"148"},{"id":"149","name":"五金工具","short_name":"五金工具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f4c517441.jpg","two_cate_id":"149"},{"id":"150","name":"厨房卫浴","short_name":"厨房卫浴","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f4e0ec484.jpg","two_cate_id":"150"},{"id":"151","name":"门窗","short_name":"门窗","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f4f647d06.jpg","two_cate_id":"151"},{"id":"152","name":"供热系统","short_name":"供热系统","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f5118a22d.jpg","two_cate_id":"152"},{"id":"153","name":"营养成分","short_name":"营养成分","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f5440bf43.jpg","two_cate_id":"153"},{"id":"154","name":"保健器械","short_name":"保健器械","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f56001d54.jpg","two_cate_id":"154"},{"id":"155","name":"传统滋补","short_name":"传统滋补","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f57691118.jpg","two_cate_id":"155"},{"id":"156","name":"护理护具","short_name":"护理护具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f58d42c58.jpg","two_cate_id":"156"},{"id":"157","name":"成人用品","short_name":"成人用品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f5ab9e6e0.jpg","two_cate_id":"157"},{"id":"158","name":"营养健康","short_name":"营养健康","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f5c49742b.jpg","two_cate_id":"158"},{"id":"159","name":"中西药品","short_name":"中西药品","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f5e8cea7b.jpg","two_cate_id":"159"},{"id":"160","name":"图书光盘","short_name":"图书光盘","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f61078ad0.jpg","two_cate_id":"160"},{"id":"161","name":"音乐","short_name":"音乐","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f633cf5af.jpg","two_cate_id":"161"},{"id":"162","name":"影视","short_name":"影视","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f64936405.jpg","two_cate_id":"162"},{"id":"163","name":"职业教育","short_name":"职业教育","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f65e97ad0.jpg","two_cate_id":"163"},{"id":"164","name":"工具软件","short_name":"工具软件","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f67551644.jpg","two_cate_id":"164"},{"id":"165","name":"游戏软件","short_name":"游戏软件","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f68c051bb.jpg","two_cate_id":"165"},{"id":"166","name":"农药","short_name":"农药","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f6b3b8dc3.jpg","two_cate_id":"166"},{"id":"167","name":"肥料","short_name":"肥料","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f6c932a15.jpg","two_cate_id":"167"},{"id":"168","name":"饲料","short_name":"饲料","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f75248296.jpg","two_cate_id":"168"},{"id":"169","name":"种子/种苗","short_name":"种子/种苗","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f76d33ae7.jpg","two_cate_id":"169"},{"id":"170","name":"兽药","short_name":"兽药","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f7c9340b9.jpg","two_cate_id":"170"},{"id":"171","name":"盆栽/苗木","short_name":"盆栽/苗木","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f7e92c3af.jpg","two_cate_id":"171"},{"id":"172","name":"园林/农耕","short_name":"园林/农耕","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f8022268c.jpg","two_cate_id":"172"},{"id":"173","name":"兽用器具","short_name":"兽用器具","cate_img":"http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6f81b94ea8.jpg","two_cate_id":"173"}]
         * ads : {"ads_id":"95","picture":"http://test.wujiemall.com/Uploads/Ads/2018-04-17/5ad5e1576344a.png","desc":"测试","href":"","position":"19","merchant_id":"4","goods_id":"0"}
         * list : [{"goods_id":"156","goods_name":"（饮品甜品其他-新测试）佰草汇五宝茶","country_id":"37","path":"/Uploads/Country/2018-02-06/5a7942dcee41c.png","goods_img":"http://test.wujiemall.com/Uploads/Goods/2018-02-24/5a90c1bd2d8ae.jpg","market_price":"35.00","shop_price":"29.80","integral":"2.00","ticket_buy_id":"1","discount":"30.00","yellow_discount":"15.00","blue_discount":"15.00","sell_num":"1232","country_logo":"http://test.wujiemall.com/Uploads/Country/2018-02-06/5a7942dcee41c.png","ticket_buy_discount":"30","all_goods_num":"8866"},{"goods_id":"589","goods_name":"（书柜/书架）书台书柜组合套装","country_id":"47","path":"/Uploads/Country/2017-11-29/5a1e84bd339ca.png","goods_img":"http://test.wujiemall.com/Uploads/Goods/2018-02-28/5a96447c0ec78.jpg","market_price":"1805.00","shop_price":"1799.00","integral":"74.00","ticket_buy_id":"0","discount":"20.00","yellow_discount":"10.00","blue_discount":"10.00","sell_num":"0","country_logo":"http://test.wujiemall.com/Uploads/Country/2017-11-29/5a1e84bd339ca.png","ticket_buy_discount":"20","all_goods_num":"1000"},{"goods_id":"202","goods_name":"（日韩表-新测试）街头欧美原宿风情侣手表","country_id":"47","path":"/Uploads/Country/2017-11-29/5a1e84bd339ca.png","goods_img":"http://test.wujiemall.com/Uploads/Goods/2018-02-24/5a90d96c8682d.jpg","market_price":"30.00","shop_price":"25.00","integral":"3.00","ticket_buy_id":"0","discount":"30.00","yellow_discount":"15.00","blue_discount":"15.00","sell_num":"1274","country_logo":"http://test.wujiemall.com/Uploads/Country/2017-11-29/5a1e84bd339ca.png","ticket_buy_discount":"30","all_goods_num":"763"},{"goods_id":"653","goods_name":"（再测试）和平挂面400g 清仓甩卖","country_id":"47","path":"/Uploads/Country/2017-11-29/5a1e84bd339ca.png","goods_img":"http://test.wujiemall.com/Uploads/Goods/2018-04-17/5ad58e9dd8197.jpg","market_price":"10.00","shop_price":"8.80","integral":"2.00","ticket_buy_id":"0","discount":"60.00","yellow_discount":"30.00","blue_discount":"30.00","sell_num":"33","country_logo":"http://test.wujiemall.com/Uploads/Country/2017-11-29/5a1e84bd339ca.png","ticket_buy_discount":"60","all_goods_num":"167"},{"goods_id":"177","goods_name":" （橄榄核/核桃-新测试）雕十八罗汉手串","country_id":"47","path":"/Uploads/Country/2017-11-29/5a1e84bd339ca.png","goods_img":"http://test.wujiemall.com/Uploads/Goods/2018-02-24/5a90d08cf15c0.jpg","market_price":"399.00","shop_price":"258.00","integral":"32.00","ticket_buy_id":"0","discount":"30.00","yellow_discount":"15.00","blue_discount":"15.00","sell_num":"1012","country_logo":"http://test.wujiemall.com/Uploads/Country/2017-11-29/5a1e84bd339ca.png","ticket_buy_discount":"30","all_goods_num":"69"},{"goods_id":"13","goods_name":"（再测试宝贝属性）女士羽绒服白鸭绒羽绒服","country_id":"47","path":"/Uploads/Country/2017-11-29/5a1e84bd339ca.png","goods_img":"http://test.wujiemall.com/Uploads/Goods/2018-02-23/5a8f7bada4f0c.jpg","market_price":"300.00","shop_price":"188.00","integral":"31.00","ticket_buy_id":"0","discount":"40.00","yellow_discount":"20.00","blue_discount":"20.00","sell_num":"53","country_logo":"http://test.wujiemall.com/Uploads/Country/2017-11-29/5a1e84bd339ca.png","ticket_buy_discount":"40","all_goods_num":"357"},{"goods_id":"22","goods_name":"（坚果炒货）百草林年货坚果大礼包1200克","country_id":"47","path":"/Uploads/Country/2017-11-29/5a1e84bd339ca.png","goods_img":"http://test.wujiemall.com/Uploads/Goods/2018-02-23/5a8f81d736344.jpg","market_price":"130.00","shop_price":"110.00","integral":"3.00","ticket_buy_id":"0","discount":"10.00","yellow_discount":"5.00","blue_discount":"5.00","sell_num":"10","country_logo":"http://test.wujiemall.com/Uploads/Country/2017-11-29/5a1e84bd339ca.png","ticket_buy_discount":"10","all_goods_num":"0"},{"goods_id":"646","goods_name":"（测试-面条）和平经典面条400g","country_id":"47","path":"/Uploads/Country/2017-11-29/5a1e84bd339ca.png","goods_img":"http://test.wujiemall.com/Uploads/Goods/2018-03-13/5aa77496e2711.jpg","market_price":"5.00","shop_price":"3.90","integral":"0.00","ticket_buy_id":"0","discount":"40.00","yellow_discount":"20.00","blue_discount":"20.00","sell_num":"34","country_logo":"http://test.wujiemall.com/Uploads/Country/2017-11-29/5a1e84bd339ca.png","ticket_buy_discount":"40","all_goods_num":"66"},{"goods_id":"5","goods_name":"（休闲零食）馋猫酥饼","country_id":"47","path":"/Uploads/Country/2017-11-29/5a1e84bd339ca.png","goods_img":"http://test.wujiemall.com/Uploads/Goods/2018-02-23/5a8f6178df211.jpg","market_price":"20.00","shop_price":"18.00","integral":"4.00","ticket_buy_id":"0","discount":"50.00","yellow_discount":"25.00","blue_discount":"25.00","sell_num":"100","country_logo":"http://test.wujiemall.com/Uploads/Country/2017-11-29/5a1e84bd339ca.png","ticket_buy_discount":"50","all_goods_num":"0"},{"goods_id":"667","goods_name":"34234","country_id":"47","path":"/Uploads/Country/2017-11-29/5a1e84bd339ca.png","goods_img":"http://test.wujiemall.com/Uploads/Goods/2018-07-10/5b44763bee098.jpg","market_price":"555.00","shop_price":"34.00","integral":"16.00","ticket_buy_id":"0","discount":"90.00","yellow_discount":"45.00","blue_discount":"45.00","sell_num":"0","country_logo":"http://test.wujiemall.com/Uploads/Country/2017-11-29/5a1e84bd339ca.png","ticket_buy_discount":"90","all_goods_num":"22"}]
         */

        private AdsBean ads;
        private List<TopNavBean> top_nav;
        private List<TwoCateListBean> two_cate_list;
        private List<ListBean> list;

        public AdsBean getAds() {
            return ads;
        }

        public void setAds(AdsBean ads) {
            this.ads = ads;
        }

        public List<TopNavBean> getTop_nav() {
            return top_nav;
        }

        public void setTop_nav(List<TopNavBean> top_nav) {
            this.top_nav = top_nav;
        }

        public List<TwoCateListBean> getTwo_cate_list() {
            return two_cate_list;
        }

        public void setTwo_cate_list(List<TwoCateListBean> two_cate_list) {
            this.two_cate_list = two_cate_list;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class AdsBean {
            /**
             * ads_id : 95
             * picture : http://test.wujiemall.com/Uploads/Ads/2018-04-17/5ad5e1576344a.png
             * desc : 测试
             * href :
             * position : 19
             * merchant_id : 4
             * goods_id : 0
             */

            private String ads_id;
            private String picture;
            private String desc;
            private String href;
            private String position;
            private String merchant_id;
            private String goods_id;

            public String getAds_id() {
                return ads_id;
            }

            public void setAds_id(String ads_id) {
                this.ads_id = ads_id;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getMerchant_id() {
                return merchant_id;
            }

            public void setMerchant_id(String merchant_id) {
                this.merchant_id = merchant_id;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }
        }

        public static class TopNavBean {
            /**
             * cate_id : 0
             * short_name : 全部
             * name : 全部
             */

            private int cate_id;
            private String short_name;
            private String name;

            public int getCate_id() {
                return cate_id;
            }

            public void setCate_id(int cate_id) {
                this.cate_id = cate_id;
            }

            public String getShort_name() {
                return short_name;
            }

            public void setShort_name(String short_name) {
                this.short_name = short_name;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class TwoCateListBean {
            /**
             * id : 22
             * name : 休闲食品
             * short_name : 休闲食品
             * cate_img : http://test.wujiemall.com/Uploads/GoodsCategory/2017-10-30/59f6e01f99413.jpg
             * two_cate_id : 22
             */

            private String id;
            private String name;
            private String short_name;
            private String cate_img;
            private String two_cate_id;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getShort_name() {
                return short_name;
            }

            public void setShort_name(String short_name) {
                this.short_name = short_name;
            }

            public String getCate_img() {
                return cate_img;
            }

            public void setCate_img(String cate_img) {
                this.cate_img = cate_img;
            }

            public String getTwo_cate_id() {
                return two_cate_id;
            }

            public void setTwo_cate_id(String two_cate_id) {
                this.two_cate_id = two_cate_id;
            }
        }

        public static class ListBean {
            /**
             * goods_id : 156
             * goods_name : （饮品甜品其他-新测试）佰草汇五宝茶
             * country_id : 37
             * path : /Uploads/Country/2018-02-06/5a7942dcee41c.png
             * goods_img : http://test.wujiemall.com/Uploads/Goods/2018-02-24/5a90c1bd2d8ae.jpg
             * market_price : 35.00
             * shop_price : 29.80
             * integral : 2.00
             * ticket_buy_id : 1
             * discount : 30.00
             * yellow_discount : 15.00
             * blue_discount : 15.00
             * sell_num : 1232
             * country_logo : http://test.wujiemall.com/Uploads/Country/2018-02-06/5a7942dcee41c.png
             * ticket_buy_discount : 30
             * all_goods_num : 8866
             */

            private String goods_id;
            private String goods_name;
            private String country_id;
            private String path;
            private String goods_img;
            private String market_price;
            private String shop_price;
            private String integral;
            private String ticket_buy_id;
            private String discount;
            private String yellow_discount;
            private String blue_discount;
            private String sell_num;
            private String country_logo;
            private String ticket_buy_discount;
            private String all_goods_num;

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getCountry_id() {
                return country_id;
            }

            public void setCountry_id(String country_id) {
                this.country_id = country_id;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getGoods_img() {
                return goods_img;
            }

            public void setGoods_img(String goods_img) {
                this.goods_img = goods_img;
            }

            public String getMarket_price() {
                return market_price;
            }

            public void setMarket_price(String market_price) {
                this.market_price = market_price;
            }

            public String getShop_price() {
                return shop_price;
            }

            public void setShop_price(String shop_price) {
                this.shop_price = shop_price;
            }

            public String getIntegral() {
                return integral;
            }

            public void setIntegral(String integral) {
                this.integral = integral;
            }

            public String getTicket_buy_id() {
                return ticket_buy_id;
            }

            public void setTicket_buy_id(String ticket_buy_id) {
                this.ticket_buy_id = ticket_buy_id;
            }

            public String getDiscount() {
                return discount;
            }

            public void setDiscount(String discount) {
                this.discount = discount;
            }

            public String getYellow_discount() {
                return yellow_discount;
            }

            public void setYellow_discount(String yellow_discount) {
                this.yellow_discount = yellow_discount;
            }

            public String getBlue_discount() {
                return blue_discount;
            }

            public void setBlue_discount(String blue_discount) {
                this.blue_discount = blue_discount;
            }

            public String getSell_num() {
                return sell_num;
            }

            public void setSell_num(String sell_num) {
                this.sell_num = sell_num;
            }

            public String getCountry_logo() {
                return country_logo;
            }

            public void setCountry_logo(String country_logo) {
                this.country_logo = country_logo;
            }

            public String getTicket_buy_discount() {
                return ticket_buy_discount;
            }

            public void setTicket_buy_discount(String ticket_buy_discount) {
                this.ticket_buy_discount = ticket_buy_discount;
            }

            public String getAll_goods_num() {
                return all_goods_num;
            }

            public void setAll_goods_num(String all_goods_num) {
                this.all_goods_num = all_goods_num;
            }
        }
    }
}
