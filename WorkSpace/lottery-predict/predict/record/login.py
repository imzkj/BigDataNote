# coding:UTF-8

import cookielib
import urllib
import urllib2


def login(count):
    filename = r'F:\lottery_cookie.txt'
    # show cookie and save to local
    cookie = cookielib.MozillaCookieJar()
    handler = urllib2.HTTPCookieProcessor(cookie)
    opener = urllib2.build_opener(handler)
    # 记得使用urllib模块进行参数的encode
    post_data = urllib.urlencode({
        'flag': 'login',
        'username': '抓阄来了',
        'password': 'zds951357',
        'Button1': ''
    })
    post_data_2 = urllib.urlencode({
        'flag': 'login',
        'username': '你出石头我出布',
        'password': 'qws740258',
        'Button1': ''
    })
    post_data_3 = urllib.urlencode({
        'flag': 'login',
        'username': '发财必须的',
        'password': 'rtu965489',
        'Button1': ''
    })
    if count == 2:
        post_data = post_data_2
        print "登陆账号: 你出石头我出布"
    elif count == 3:
        post_data = post_data_3
        print "登陆账号: 发财必须的"
    else:
        print "登陆账号: 抓阄来了"

    # 登陆地址
    url = 'http://shcp123.com/'
    # 开始进行模拟登陆
    opener.open(url, post_data)
    # 把cookie保存到本地文件
    # saveCookie(cookie, filename)
    return opener


def saveCookie(cookie, filename):
    cookie.save(filename, ignore_discard=True, ignore_expires=True)


def getData(opener):
    # 利用cookie访问另一个网址 获取网页内容
    data_url = 'http://shcp123.com/play_qtssc.php'
    result = opener.open(data_url)
    return result.read()
