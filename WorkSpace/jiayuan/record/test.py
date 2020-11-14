#!/usr/bin/python
#-*- coding:utf-8 -*-
import json
from mysqlUtil import  *
from login import  *


def getData(js):
    # 利用cookie访问另一个网址 获取网页内容
    uid_hash_set = {}
    js = js.decode("unicode_escape").replace("##jiayser##","").replace("//","") #字符串里很牛X的一个函数replace()
    content = json.loads(js) #字典类型(这个字典是大字典,key只有isLogin、count、pageTotal、userInfo，其中userInfo是每个人的资料)
    userinfoArray = content['userInfo']
    for i in range(len(userinfoArray)):
        userinfo = userinfoArray[i]
        if 'realUid' in userinfo:
            uid = userinfo['realUid']
            if uid == None:
                uid = "null"
            else:
                if 'helloUrl' in userinfo:
                    helloUrl = userinfo['helloUrl']
                if helloUrl != None:
                    uid_hash_set[uid] = getUidHash(helloUrl)
        else:
            uid = "null"
        if 'age' in userinfo:
            age = userinfo['age']
            if age == None:
                age = "null"
        else:
            age = "null"
        if 'nickname' in userinfo:
            nickname = userinfo['nickname']
            if nickname == None:
                nickname = "null"
        else:
            nickname = "null"
        if 'marriage' in userinfo:
            marriage = userinfo['marriage']
            if marriage == None:
                marriage = "null"
        else:
            marriage = "null"
        if 'height' in userinfo:
            height = userinfo['height']
            if height == None:
                height = "null"
        else:
            height = "null"
        if 'education' in userinfo:
            education = userinfo['education']
            if education == None:
                education = "null"
        else:
            education = "null"
        if 'income' in userinfo:
            income = userinfo['income']
            if income == None:
                income = "null"
        else:
            income = "null"
        if 'work_location' in userinfo:
            work_location = userinfo['work_location']
            if work_location == None:
                work_location = "null"
        else:
            work_location = "null"
        if 'work_sublocation' in userinfo:
            work_sublocation = userinfo['work_sublocation']
            if work_sublocation == None:
                work_sublocation = "null"
        else:
            work_sublocation = "null"
        if 'shortnote' in userinfo:
            shortnote = userinfo['shortnote']
            if shortnote == None:
                shortnote = "null"
        else:
            shortnote = "null"
        if 'matchCondition' in userinfo:
            matchCondition = userinfo['matchCondition']
            if matchCondition == None:
                matchCondition = "null"
        else:
            matchCondition = "null"
        writeRecordToDatabase(uid,age,nickname,marriage,height,education,income,work_location,work_sublocation,shortnote,matchCondition)
    return uid_hash_set

if __name__ == '__main__':
    # js = '{"isLogin":true,"count":3483,"pageTotal":349,"userInfo":[{"uid":183327003,"realUid":184327003,"nickname":"一笑奈何.","sex":"女","sexValue":"f","randAttr":"formal","marriage":"未婚","height":"174","education":"本科","income":null,"work_location":"潮阳","work_sublocation":"潮阳","age":25,"image":"http:\/\/at2.jyimg.com\/9c\/b9\/a7099f903964e24dac4190c30511\/a7099f903_1_avatar_p.jpg","count":"3483","online":0,"randTag":"<span>174cm<\/span>","randListTag":"<span>174cm<\/span>","userIcon":"<i title=手机认证 class=tel><\/i>","helloUrl":"http:\/\/www.jiayuan.com\/msg\/hello.php?type=20&randomfrom=4&uhash=9ca7099f903964e24dac4190c30511b9","sendMsgUrl":"http:\/\/www.jiayuan.com\/msg\/send.php?uhash=9ca7099f903964e24dac4190c30511b9","shortnote":"我是一个孝顺,纯真,简单,体贴的人 。","matchCondition":"24-32岁,174-199cm,广东,潮阳"},{"uid":183364505,"realUid":184364505,"nickname":"唐唐","sex":"女","sexValue":"f","randAttr":"formal","marriage":"未婚","height":"170","education":"高中中专及以下","income":null,"work_location":"广州","work_sublocation":"广州","age":20,"image":"http:\/\/at1.jyimg.com\/00\/6b\/4a1ac2652c0232d3379fc2ab3302\/4a1ac2652_1_avatar_p.jpg","count":"3483","online":0,"randTag":"<span>170cm<\/span>","randListTag":"<span>170cm<\/span>","userIcon":"<i title=手机认证 class=tel><\/i>","helloUrl":"http:\/\/www.jiayuan.com\/msg\/hello.php?type=20&randomfrom=4&uhash=004a1ac2652c0232d3379fc2ab33026b","sendMsgUrl":"http:\/\/www.jiayuan.com\/msg\/send.php?uhash=004a1ac2652c0232d3379fc2ab33026b","shortnote":"爱好广泛的我，喜欢跳舞，是个不折不扣的美食家，我有些特长：特别能睡， 是不是很有趣呢？关于外貌，简单形容就是漂亮，我的工作属于IT业,金融业,销售，我现在的生活状态和心情，可以说是…","matchCondition":"19-27岁,170-195cm,广东,广州"},{"uid":183387498,"realUid":184387498,"nickname":"守护云开日","sex":"女","sexValue":"f","randAttr":"formal","marriage":"未婚","height":"169","education":"本科","income":null,"work_location":"广州","work_sublocation":"广州","age":25,"image":"http:\/\/at1.jyimg.com\/88\/c3\/565783d634cdbffda837ca228291\/565783d63_1_avatar_p.jpg","count":"3483","online":0,"randTag":"<span>169cm<\/span>","randListTag":"<span>169cm<\/span>","userIcon":"<i title=手机认证 class=tel><\/i><i title=钻石会员 class=zshy><\/i>","helloUrl":"http:\/\/www.jiayuan.com\/msg\/hello.php?type=20&randomfrom=4&uhash=88565783d634cdbffda837ca228291c3","sendMsgUrl":"http:\/\/www.jiayuan.com\/msg\/send.php?uhash=88565783d634cdbffda837ca228291c3","shortnote":"我是一个文静,善良,开朗的人 。","matchCondition":"24-32岁,169-194cm"},{"uid":183382117,"realUid":184382117,"nickname":"斯琴","sex":"女","sexValue":"f","randAttr":"formal","marriage":"未婚","height":"168","education":"本科","income":null,"work_location":"广州","work_sublocation":"广州","age":24,"image":"http:\/\/at1.jyimg.com\/c3\/46\/edc52cf9db2dd07240c7d11575ef\/edc52cf9d_1_avatar_p.jpg","count":"3483","online":0,"randTag":"<span>168cm<\/span>","randListTag":"<span>168cm<\/span>","userIcon":"<i title=手机认证 class=tel><\/i>","helloUrl":"http:\/\/www.jiayuan.com\/msg\/hello.php?type=20&randomfrom=4&uhash=c3edc52cf9db2dd07240c7d11575ef46","sendMsgUrl":"http:\/\/www.jiayuan.com\/msg\/send.php?uhash=c3edc52cf9db2dd07240c7d11575ef46","shortnote":"爱好广泛的我，喜欢逛街,淘宝,自拍，我的工作属于教育行业。","matchCondition":"23-31岁,168-193cm"},{"uid":183410731,"realUid":184410731,"nickname":"古灵精怪","sex":"女","sexValue":"f","randAttr":"formal","marriage":"未婚","height":"166","education":"本科","income":null,"work_location":"深圳","work_sublocation":"深圳","age":24,"image":"http:\/\/at4.jyimg.com\/7f\/6b\/df89d78d751d31c42fb85a2a88ec\/df89d78d7_1_avatar_p.jpg","count":"3483","online":0,"randTag":"<span>166cm<\/span>","randListTag":"<span>166cm<\/span>","userIcon":"<i title=手机认证 class=tel><\/i>","helloUrl":"http:\/\/www.jiayuan.com\/msg\/hello.php?type=20&randomfrom=4&uhash=7fdf89d78d751d31c42fb85a2a88ec6b","sendMsgUrl":"http:\/\/www.jiayuan.com\/msg\/send.php?uhash=7fdf89d78d751d31c42fb85a2a88ec6b","shortnote":"我期待这样的你：这一生任何时候你都一直紧紧的牵住我的手让我相信天长地久。当我白发苍苍躺在你怀里 ，将合上双眸的时候，你愿意和我一起握住这一生爱的玫瑰，陪我从人间走到天堂。     …","matchCondition":"24-32岁,166-191cm"},{"uid":183326897,"realUid":184326897,"nickname":"却卜想放开","sex":"女","sexValue":"f","randAttr":"formal","marriage":"未婚","height":"166","education":"本科","income":null,"work_location":"汕头","work_sublocation":"汕头","age":24,"image":"http:\/\/at4.jyimg.com\/31\/eb\/d0028f047ef2ca802f92cc26ab35\/d0028f047_1_avatar_p.jpg","count":"3483","online":0,"randTag":"<span>166cm<\/span>","randListTag":"<span>166cm<\/span>","userIcon":"<i title=手机认证 class=tel><\/i>","helloUrl":"http:\/\/www.jiayuan.com\/msg\/hello.php?type=20&randomfrom=4&uhash=31d0028f047ef2ca802f92cc26ab35eb","sendMsgUrl":"http:\/\/www.jiayuan.com\/msg\/send.php?uhash=31d0028f047ef2ca802f92cc26ab35eb","shortnote":"我是一个可爱的人 ，爱好广泛的我，喜欢养狗,摄影。","matchCondition":"23-31岁,166-191cm,广东,汕头"},{"uid":183361979,"realUid":184361979,"nickname":"野性的高跟鞋","sex":"女","sexValue":"f","randAttr":"formal","marriage":"未婚","height":"170","education":"高中中专及以下","income":null,"work_location":"广州","work_sublocation":"广州","age":19,"image":"http:\/\/at3.jyimg.com\/af\/17\/ac06e2b452909d05e4721cd3c2d3\/ac06e2b45_1_avatar_p.jpg","count":"3483","online":0,"randTag":"<span>170cm<\/span>","randListTag":"<span>170cm<\/span>","userIcon":"<i title=手机认证 class=tel><\/i>","helloUrl":"http:\/\/www.jiayuan.com\/msg\/hello.php?type=20&randomfrom=4&uhash=afac06e2b452909d05e4721cd3c2d317","sendMsgUrl":"http:\/\/www.jiayuan.com\/msg\/send.php?uhash=afac06e2b452909d05e4721cd3c2d317","shortnote":"我是一个诚信的人 ，爱好广泛的我，喜欢淘宝,弹钢琴,电影，是个不折不扣的学霸，我的工作属于销售，我现在的生活状态和心情，可以说是“有点寂寞,非诚勿扰”。...","matchCondition":"18-26岁,170-195cm,广东,广州"},{"uid":183340999,"realUid":184340999,"nickname":"林倩怡","sex":"女","sexValue":"f","randAttr":"formal","marriage":"未婚","height":"170","education":"本科","income":null,"work_location":"深圳","work_sublocation":"深圳","age":23,"image":"http:\/\/at2.jyimg.com\/9c\/07\/46a0df2999a133e735c2c2b9ba13\/46a0df299_2_avatar_p.jpg","count":"3483","online":1,"randTag":"<span>170cm<\/span>","randListTag":"<span>170cm<\/span>","userIcon":"<i title=在线 class=online><\/i><i title=手机认证 class=tel><\/i>","helloUrl":"http:\/\/www.jiayuan.com\/msg\/hello.php?type=20&randomfrom=4&uhash=9c46a0df2999a133e735c2c2b9ba1307","sendMsgUrl":"http:\/\/www.jiayuan.com\/msg\/send.php?uhash=9c46a0df2999a133e735c2c2b9ba1307","shortnote":"我是一个文静,细心,执着的人 。我的微信是 w15101617886   诚心交友非诚勿扰，将心比心，你会发现遇上我是你人生最大的乐趣，有趣的灵魂伴侣...","matchCondition":"22-30岁,170-195cm,广东,广州"},{"uid":183377234,"realUid":184377234,"nickname":"星辰","sex":"女","sexValue":"f","randAttr":"formal","marriage":"未婚","height":"164","education":"大专","income":null,"work_location":"广州","work_sublocation":"广州","age":20,"image":"http:\/\/at3.jyimg.com\/e5\/2a\/e5c52c58769909b852bc51557838\/e5c52c587_1_avatar_p.jpg","count":"3483","online":0,"randTag":"<span>164cm<\/span>","randListTag":"<span>164cm<\/span>","userIcon":"<i title=手机认证 class=tel><\/i>","helloUrl":"http:\/\/www.jiayuan.com\/msg\/hello.php?type=20&randomfrom=4&uhash=e5e5c52c58769909b852bc515578382a","sendMsgUrl":"http:\/\/www.jiayuan.com\/msg\/send.php?uhash=e5e5c52c58769909b852bc515578382a","shortnote":"我在等一个人，一个可以把我的寂寞画上休止符的人；一个可以陪我听遍所有悲伤的情歌却不会让我想哭的人；一个我可以在他身上找出一百个缺点却还是执意要爱的人；一个会对我说：我们有坑一起跳，…","matchCondition":"19-27岁,164-189cm,广东,广州"}],"second_search":0,"express_search":[{"uid":"169727383","realUid":170727383,"sex":"f","nickname":"上官亦月","helloUrl":"http:\/\/www.jiayuan.com\/msg\/hello.php?type=20&randomfrom=4&uhash=b9abdb219f5fddc9008d20b5f7360213","age":23,"image":"http:\/\/at4.jyimg.com\/b9\/13\/abdb219f5fddc9008d20b5f73602\/abdb219f5_1_avatar_p.jpg","work_location":"广东","ex_mark":"国有企业 ","fxly":"cp-eexpress"},{"uid":"155379788","realUid":156379788,"sex":"f","nickname":"没有来路的葵花","helloUrl":"http:\/\/www.jiayuan.com\/msg\/hello.php?type=20&randomfrom=4&uhash=2b295eeac39e529c5a424d6b550c66a0","age":24,"image":"http:\/\/at3.jyimg.com\/2b\/a0\/295eeac39e529c5a424d6b550c66\/295eeac39_1_avatar_p.jpg","work_location":"广东","ex_mark":"160cm","fxly":"cp-eexpress"},{"uid":"162552272","realUid":163552272,"sex":"f","nickname":"匀称的鳗鲡在棉花堡","helloUrl":"http:\/\/www.jiayuan.com\/msg\/hello.php?type=20&randomfrom=4&uhash=eb6b82c32505a57ebc3dcaece5337819","age":23,"image":"http:\/\/at3.jyimg.com\/eb\/19\/6b82c32505a57ebc3dcaece53378\/6b82c3250_3_avatar_p.jpg","work_location":"广东","ex_mark":"160cm","fxly":"cp-eexpress"},{"uid":"43772720","realUid":44772720,"sex":"f","nickname":"溟月","helloUrl":"http:\/\/www.jiayuan.com\/msg\/hello.php?type=20&randomfrom=4&uhash=566d3694d83af19c45391710349ef16b","age":25,"image":"http:\/\/at2.jyimg.com\/56\/6b\/6d3694d83af19c45391710349ef1\/6d3694d83_avatar_p.jpg","work_location":"广东","ex_mark":"155cm","fxly":"cp-eexpress"},{"uid":"171873346","realUid":172873346,"sex":"f","nickname":"白衣天使","helloUrl":"http:\/\/www.jiayuan.com\/msg\/hello.php?type=20&randomfrom=4&uhash=beafbfa82db5674da901830b3e6faa6d","age":23,"image":"http:\/\/at4.jyimg.com\/be\/6d\/afbfa82db5674da901830b3e6faa\/afbfa82db_4_avatar_p.jpg","work_location":"广东","ex_mark":"事业单位 ","fxly":"cp-eexpress"},{"uid":"148737858","realUid":149737858,"sex":"f","nickname":"ymt","helloUrl":"http:\/\/www.jiayuan.com\/msg\/hello.php?type=20&randomfrom=4&uhash=fb40eca85522962432c29eb532d9fb02","age":25,"image":"http:\/\/at4.jyimg.com\/fb\/02\/40eca85522962432c29eb532d9fb\/40eca8552_1_avatar_p.jpg","work_location":"广东","ex_mark":"161cm","fxly":"cp-eexpress"},{"uid":"137975322","realUid":138975322,"sex":"f","nickname":"Candy","helloUrl":"http:\/\/www.jiayuan.com\/msg\/hello.php?type=20&randomfrom=4&uhash=a9d06eb80c983b28546679134edac276","age":24,"image":"http:\/\/at3.jyimg.com\/a9\/76\/d06eb80c983b28546679134edac2\/d06eb80c9_3_avatar_p.jpg","work_location":"广东","ex_mark":"163cm","fxly":"cp-eexpress"},{"uid":"151498745","realUid":152498745,"sex":"f","nickname":"小妮子","helloUrl":"http:\/\/www.jiayuan.com\/msg\/hello.php?type=20&randomfrom=4&uhash=bc83eaef704e63c7f8578d926be10781","age":24,"image":"http:\/\/at4.jyimg.com\/bc\/81\/83eaef704e63c7f8578d926be107\/83eaef704_2_avatar_p.jpg","work_location":"广东","ex_mark":"160cm","fxly":"cp-eexpress"},{"uid":"183417687","realUid":184417687,"sex":"f","nickname":"糖糖","helloUrl":"http:\/\/www.jiayuan.com\/msg\/hello.php?type=20&randomfrom=4&uhash=9326025498f674fdd8a80bf1db714f46","age":24,"image":"http:\/\/at2.jyimg.com\/93\/46\/26025498f674fdd8a80bf1db714f\/26025498f_1_avatar_p.jpg","work_location":"广东","ex_mark":"本科","fxly":"cp-eexpress"},{"uid":"169768955","realUid":170768955,"sex":"f","nickname":"Eva Yeng","helloUrl":"http:\/\/www.jiayuan.com\/msg\/hello.php?type=20&randomfrom=4&uhash=caf058b15f019d60620fe82873e1fa04","age":22,"image":"http:\/\/at1.jyimg.com\/ca\/04\/f058b15f019d60620fe82873e1fa\/f058b15f0_1_avatar_p.jpg","work_location":"广东","ex_mark":"154cm","fxly":"cp-eexpress"}],"condi":{"work_sublocation":[],"work_location":["44"],"age":{"min":"18","max":"25"},"height":{"min":"164","max":"175"},"avatar":"1"}}'

    uid_hash_set = {}
    user_name = {}

    # uid_hash_set["176184699"] = "aa0fd3a18e8d3bd8ef4935c0331e1e4c"
    # user_name["176184699"] = "Jane"

    # uid_hash_set["169769905"] = "3bf42ce6f10196123c5c233da321663b"
    # user_name["169769905"] = "清纯女孩"

    # uid_hash_set["169324990"] = "66dd2fd837edc6a601fcb4176ceb126e"
    # user_name["169324990"] = "薇薇一笑很倾城"

    # uid_hash_set["180939792"] = "cf850b62dc4a30c9c7bf5df2afda48ee"
    # user_name["180939792"] = "面纱"

    # uid_hash_set["190053803"] = "8ccc58e114cb1fba48f04b68afda4b41"
    # user_name["190053803"] = "淇淇宝贝"

    # uid_hash_set["189326482"] = "6ab5f972ccae480366482555bfac35ac"
    # user_name["189326482"] = "小巧的飞雪在科威特"

    # uid_hash_set["189582005"] = "2caf7b2eb39e9f23f117c0123391d381"
    # user_name["189582005"] = "在图书馆纠结的梦旋"

    # uid_hash_set["189782792"] = "3daed6a86f2ee890b070fb7a471e9b3f"
    # user_name["189782792"] = "卢秀艳"

    # uid_hash_set["168967241"] = "43739f557efd4b20c92058efd48f29f6"
    # user_name["168967241"] = "菲菲"

    # uid_hash_set["154734405"] = "d75e79b6226e7887b3e0b6821c7cd095"
    # user_name["154734405"] = "VK"

    # uid_hash_set["185542878"] = "a9b8fe50dc23179f9404dcacc9e9aad1"
    # user_name["185542878"] = "努力向前"

    # uid_hash_set["181141909"] = "e0b52b4b483d7f38cdf06e8050af2b58"
    # user_name["181141909"] = "訫兒"

    # uid_hash_set["184475604"] = "f6b2bae8f2911c6c0b68f927cfbb64d8"
    # user_name["184475604"] = "小珍珠"

    # uid_hash_set["178638667"] = "19e4f2226471dcd01111a6c10898f5c6"
    # user_name["178638667"] = "陈佳瑞"

    # uid_hash_set["95803399"] = "25c1ab65dc69b4e27c9ca0fc0f931c1b"
    # user_name["95803399"] = "静儿"

    # uid_hash_set["189859526"] = "d020150efcfd50f5e547e239bd1df63b"
    # user_name["189859526"] = "Jade"

    # uid_hash_set["142955779"] = "d4fe8e1998f986887206594b9bfc93ac"
    # user_name["142955779"] = "东方天旎"

    # uid_hash_set["184446866"] = "41e0884d0be033cb86f66e2bfe9de207"
    # user_name["184446866"] = "女孩"

    # uid_hash_set = getData(js)
    opener = login()
    content = '认真看完了你的资料，很高兴遇见你！我爱好比较广泛，喜欢琴棋书画、古典音乐、运动和摄影等。本人性格坚强乐观，对待感情比较谨慎，' \
              '不会轻易开始，也不会轻易放弃，至今母胎单身。我相信一见钟情，但也坚持日久生情，憧憬着从一而终的爱情，希望你是我一直在等的那个人！' \
              '能给我一个认识你的机会吗？从朋友开始了解的那种！我等你，在你不知道的世界里！盼复！'
    sendMsg(opener, content, user_name,uid_hash_set)