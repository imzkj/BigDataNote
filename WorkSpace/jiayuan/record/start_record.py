# coding:UTF-8
import random

from login import *
import time


def getYmd():
	return time.strftime('%Y-%m-%d', time.localtime(time.time()))


if __name__ == '__main__':
	# 登录网站
	opener = login()
	page = 1
	count = 0

	content = '认真看完了你的资料，很高兴遇见你！我爱好比较广泛，喜欢琴棋书画、古典音乐、运动和摄影等。本人性格坚强乐观，对待感情比较谨慎，' \
			  '至今母胎单身。我相信一见钟情，但也坚持日久生情，憧憬着从一而终的爱情，希望你是我一直在等的那个人！能给我一个认识你的机会吗？' \
			  '从朋友开始了解的那种！盼复！我等你，在你不知道的世界里！'
	while True:
		try:
			# 获取用户条件参数
			data_url = 'http://search.jiayuan.com/v2/search_v2.php?key=&sex=f&stc=2:18.25,3:164.175,23:1&sn=default&sv=1' \
			   '&p=' + str(page) + '&pt=138&ft=off&f=select&mt=u'
			# 获取网站源码
			# user_name, uid_hash_set = getData(opener, data_url)
			user_name, uid_hash_set = getData(None, data_url)
			print "获取第: " + str(page) + "内容成功"
			ok = sendMsg(opener, content, user_name, uid_hash_set)
			print "发送第: " + str(page) + "内容成功，结果: " + str(ok)
			count = count + ok
			print "无发送成功次数: " + str(count)
			# if count >= 50:
			# 	break
			page = page + 1
			# time.sleep(8)
		except BaseException as e:
			opener = login()
			print "发生异常，重新初始化………"
			print e.message
			time.sleep(30)