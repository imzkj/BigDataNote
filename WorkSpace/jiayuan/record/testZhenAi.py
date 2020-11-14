#!/usr/bin/python
#-*- coding:utf-8 -*-
import json
from mysqlUtil import  *
from login import  *

#
# import cookielib
# import urllib2
#
# #设置保存cookie的文件，同级目录下的cookie.txt
# filename = 'cookie.txt'
# #声明一个MozillaCookieJar对象实例来保存cookie，之后写入文件
# cookie = cookielib.MozillaCookieJar(filename)
# #利用urllib2库的HTTPCookieProcessor对象来创建cookie处理器
# handler = urllib2.HTTPCookieProcessor(cookie)
# #通过handler来构建opener
# opener = urllib2.build_opener(handler)
# #创建一个请求，原理同urllib2的urlopen
# response = opener.open("http://www.baidu.com")
# #保存cookie到文件
# cookie.save(ignore_discard=True, ignore_expires=True)

#
# import cookielib
# import urllib2
#
# #创建MozillaCookieJar实例对象
# cookie = cookielib.MozillaCookieJar()
# #从文件中读取cookie内容到变量
# cookie.
# cookie.load('cookie.txt', ignore_discard=True, ignore_expires=True)
# #创建请求的request
# req = urllib2.Request("http://www.baidu.com")
# #利用urllib2的build_opener方法创建一个opener
# opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(cookie))
# response = opener.open(req)
# print response.read()
#
#
#
#
# data_url = "https://baijiahao.baidu.com/s?id=1667795294359964289&wfr=spider&for=pc"
# req = urllib2.Request(data_url)
# result = urllib2.urlopen(req)
# js = result.read()
# # print js
# js = js.decode("unicode_escape").replace("##jiayser##","").replace("//","") #字符串里很牛X的一个函数replace()
#
# print js
#
# sid=cf73908c-6123-4698-9f13-7c003d500eaf; __channelId=901045%2C0; _pc_myzhenai_showdialog_=1; _pc_myzhenai_memberid_=%22%2C112810048%22; Hm_lvt_2c8ad67df9e787ad29dbd54ee608f5d2=1590539961; Hm_lpvt_2c8ad67df9e787ad29dbd54ee608f5d2=1590540874; login_health=aee30e759ac8dee9fdd5b716ecdcf0e6c8d184d3fcefc2cf039ca75017a44ec5721a5e8a06cdbfe89d304dd1732a10b56206e929e1892033c2ff6c6f69450034; _pc_login_validate_isUnconnectByAdmin=; token=112810048.1590541658037.525d0e46db022b2e4fe5e7c6f04afce9; refreshToken=112810048.1590628058037.4d570e6c8d2645b61e0b784438a42887; _pc_login_validate_sec=59



import httplib
import urlparse

def request(url, cookie):
    ret = urlparse.urlparse(url)    # Parse input URL
    if ret.scheme == 'http':
        conn = httplib.HTTPConnection(ret.netloc)
    elif ret.scheme == 'https':
        conn = httplib.HTTPSConnection(ret.netloc)

    url = ret.path
    if ret.query: url += '?' + ret.query
    if ret.fragment: url += '#' + ret.fragment
    if not url: url = '/'

    conn.request(method='GET', url=url , headers={'Cookie': cookie})
    return conn.getresponse()

if __name__ == '__main__':
    cookie_str = 'sid=cf73908c-6123-4698-9f13-7c003d500eaf; __channelId=901045%2C0; _pc_myzhenai_showdialog_=1; _pc_myzhenai_memberid_=%22%2C112810048%22; Hm_lvt_2c8ad67df9e787ad29dbd54ee608f5d2=1590539961; Hm_lpvt_2c8ad67df9e787ad29dbd54ee608f5d2=1590540874; login_health=aee30e759ac8dee9fdd5b716ecdcf0e6c8d184d3fcefc2cf039ca75017a44ec5721a5e8a06cdbfe89d304dd1732a10b56206e929e1892033c2ff6c6f69450034; _pc_login_validate_isUnconnectByAdmin=; token=112810048.1590541658037.525d0e46db022b2e4fe5e7c6f04afce9; refreshToken=112810048.1590628058037.4d570e6c8d2645b61e0b784438a42887; _pc_login_validate_sec=59'
    url = 'https://www.zhenai.com/n/search#sex=1&workCity=10101204&ageBegin=18&ageEnd=29&heightBegin=-1&heightEnd=-1&body=-1&multiEducation=-1&salaryBegin=-1&salaryEnd=-1&'
    html_doc = request(url, cookie_str).read()
    print html_doc
    import re
    print 'With Auth:', re.search('<title>(.*?)</title>', html_doc, re.IGNORECASE).group(1)

    html_doc = request(url).read()
    print 'Without Auth:', re.search('<title>(.*?)</title>', html_doc, re.IGNORECASE).group(1)