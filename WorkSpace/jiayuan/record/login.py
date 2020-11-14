# coding:UTF-8

import cookielib
import time
import urllib
import urllib2
import re
import json
from mysqlUtil import *
import sys
reload(sys)
sys.setdefaultencoding('utf8')

def login():
    filename = r'F:\lottery_cookie.txt'
    # show cookie and save to local
    cookie = cookielib.MozillaCookieJar()
    handler = urllib2.HTTPCookieProcessor(cookie)
    opener = urllib2.build_opener(handler)
    # 记得使用urllib模块进行参数的encode
    post_data = urllib.urlencode({
        'name': '15338759556',
        'password': 'zkj654321',
        'remem_pass': 'on'
        # ,
        # '_s_x_id': '15f88900f0ef17ecfdc2db513d370633',
        # 'ljg_login': '1',
        # 'm_p_l': '1',
        # 'channel': '0',
        # 'position': '0'
    })
    # 登陆地址
    url = 'http://passport.jiayuan.com/dologin.php?pre_url=http://www.jiayuan.com/usercp/'
    # 开始进行模拟登陆
    opener.open(url, post_data)
    # 把cookie保存到本地文件
    saveCookie(cookie, filename)
    return opener


def saveCookie(cookie, filename):
    cookie.save(filename, ignore_discard=True, ignore_expires=True)


def getData(opener, data_url):
    # 利用cookie访问另一个网址 获取网页内容
    uid_hash_set = {}
    user_name = {}
    if opener:
        result = opener.open(data_url)
    else:
        print "不登录获取"
        req = urllib2.Request(data_url)
        result = urllib2.urlopen(req)
    js = result.read()
    js = js.decode("unicode_escape").replace("##jiayser##","").replace("//","") #字符串里很牛X的一个函数replace()
    try:
        content = json.loads(js) #字典类型(这个字典是大字典,key只有isLogin、count、pageTotal、userInfo，其中userInfo是每个人的资料)
    except BaseException as e:
        print "解析json失败！"
        print js
        return user_name, uid_hash_set
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
                nickname = "Hi"
            user_name[uid] = nickname
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
    return user_name, uid_hash_set


def getUidHash(helloUrl):
    allStr = re.search("&uhash=(.*)", helloUrl)
    return allStr.group(1)

def sendMsg(opener, content, user_name, uid_hash_set):
    send_url='http://www.jiayuan.com/msg/dosend.php'
    flag = 1
    for uid in uid_hash_set:
        try:
            # if is_in(uid):
            #     print str(uid) + "已发送过"
            #     updateHash(uid, uid_hash_set[uid])
                # continue
            self_content = "你好（" + user_name[uid] + "，原谅我暂且这么称呼你吧，如之前有系统发送信息请忽略，不是本人意思造成打扰深感抱歉），" \
                           + content
            self_content = content
            post_data = urllib.urlencode({
                'contents': self_content,
                'ok_xinzhi_id': '',
                'fxly': 'tj-xsp-jrsp-profile',
                'tj_wz': 'none',
                'reply_msgid': '0',
                'reply_send_time': '0',
                'to_hash': uid_hash_set[uid],
                'now_draft_id': '0',
                'need_fxtyp_tanchu': '0',
                'self_pay': '0',
                'fxbc': '0',
                'cai_xin': '0',
                'liwu_nofree': '0',
                'liwu_nofree_id': '565',
                'liwu_free': '1',
                'liwu_free_id': '559',
                'uqjm_code': '23e00885a7e63864c331a2e2205b9b99',
                'moban_type': '9',
                'zhuanti': '0',
                'cache_key': 'profile_' + str(uid) + '_178456318_tj-xsp-jrsp-profile'
            })
            opener.open(send_url, post_data)
            writeUid(uid, uid_hash_set[uid])
            flag = 0
            # time.sleep(1)
        except BaseException as e:
            print str(uid) + "发生信息异常………"
            print e.message
    return flag



if __name__ == '__main__':
    print getUidHash("181086760")