#!/usr/bin/python
#-*- coding:utf-8 -*-

#爬取世纪佳缘
#这个网站是真的烦，刚开始的时候用scrapy框架写,但是因为刚接触框架,碰到js渲染的页面之后就没办法了,所以就采用一般的爬虫了
#js渲染过的数据，可能在网页源码里面没有数据，需要js异步请求提取数据，然后展示，所以爬取这类的数据，只需要找到js发送请求的url就行了
#js发送的请求可能是post(比如这个例子)或者是get(比如豆瓣电影剧情的排行榜),所以要看好是什么请求

import sys
import json
import urllib
import urllib2


reload(sys)
sys.setdefaultencoding("utf-8") #设置默认的编码格式,把Unicode格式转换成汉字写到文件里面的时候需要用到,要配合decode("unicode_escape")使用

def parse_page(self):
	url = "http://search.jiayuan.com/v2/search_v2.php?" #初始url的前半段,加上后半段发送的是post请求
	headers = {"User-Agent":"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36"}
	formdata = {
		"sex":"f",
		"key":"",
		"stc":"1:11,2:20.25,3:160.170,23:1",
		"sn":"default",
		"sv":"1",
		"p":self.page, #每次数据js异步提取数据的时候只有p变化了,p对应于当前页面是第几页
		"f":"select",
		"listStyle":"bigPhoto",
		"pri_uid":"170633820",
		"jsversion":"v5"
	}
	data = urllib.urlencode(formdata)
	request = urllib2.Request( url ,data = data , headers = headers)
	response = urllib2.urlopen(request)
	#        print response.info().get('Content-Encoding') #这会打印出服务器返回数据的压缩方式,如果未有压缩则返回None
	js = response.read() #返回的response.read()是json格式的,但是首和尾多了一段字符串"##jiayse##"和"##jiayse##",所以下面就处理一下

	#print type(js) #字符串类型
	js = js.replace("##jiayser##","").replace("//","") #字符串里很牛X的一个函数replace()
	print js
	content = json.loads(js) #字典类型(这个字典是大字典,key只有isLogin、count、pageTotal、userInfo，其中userInfo是每个人的资料)

	self.parse_person(content['userInfo']) #调用parse_person函数处理大字典的ueerInfo(还是字典类型的),即每个人的资料
#            print type(content['userInfo'])#字典类型

def parse_person(self,userinfo):
	for i in range(len(userinfo)):

		form = {"昵称":userinfo[i]['nickname'],"年龄":userinfo[i]['age'],"内心独白":userinfo[i]['shortnote']} #把要爬取的数据写成一个字典

		#print form
		#print type(form)

		#不能把dict和list类型直接写入文件, 把字典写入json文件的时候要把字典转换成json字符串才行(json.dumps(form))
		#decode("unicode_escape")表示把unicode格式转换成汉字,但用一个这竟然还不行!还要加上上面的import sys... , 真麻烦
		form = json.dumps(form).decode("unicode_escape")

		self.filename.write(form+"\n") #写入文件

	if self.page <10: #这里随便写self.page,只要不超过页面加载的范围就行
		self.page+=1
		self.parse_page() #自加一之后在调用parse_page()函数进行换页,然后接着爬

if __name__ == "__main__":
	parse_page() #调用parse_page()函数开始爬取