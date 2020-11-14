# coding:UTF-8

import cookielib
import urllib
import urllib2


def login():
	filename = r'F:\lottery_cookie.txt'
	# show cookie and save to local
	cookie = cookielib.MozillaCookieJar()
	handler = urllib2.HTTPCookieProcessor(cookie)
	opener = urllib2.build_opener(handler)
	# 记得使用urllib模块进行参数的encode
	post_data = urllib.urlencode({
		'flag': 'login',
		'username': '听雨轩',
		'password': 'zkj123456',
		'Button1': ''
	})
	# 登陆地址
	url = 'http://pailook.cn/'
	# 开始进行模拟登陆
	opener.open(url, post_data)
	# 把cookie保存到本地文件
	saveCookie(cookie, filename)
	return opener


def saveCookie(cookie, filename):
	cookie.save(filename, ignore_discard=True, ignore_expires=True)


def getData(opener):
	# 利用cookie访问另一个网址 获取网页内容
	data_url = 'http://pailook.cn/play_qtssc.php'
	result = opener.open(data_url)
	return result.read()
