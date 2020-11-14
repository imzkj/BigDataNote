# coding:UTF-8
import random

from login import *
from parse import *
from mysqlUtil import *
import time


def getYmd():
	return time.strftime('%Y-%m-%d', time.localtime(time.time()))


if __name__ == '__main__':
	count = 1
	# 登录网站
	opener = login()
	while True:
		try:
			# 获取网站源码
			data = getData(opener)
			# 分析网站源码，得到开奖记录
			recordDict = parseData(data)
			# 获取当天时间
			ymd = getYmd()
			# 将获取到的记录写进数据库
			writeRecordToDatabase(recordDict, ymd)
			print "成功写入: " + str(count) + " 批！"
			count += 1
			# 20分钟获取一次记录
			time.sleep(random.randint(1200, 1350))
		except BaseException as e:
			opener = login()
			print "发生异常，重新初始化………"
			print e.message
			time.sleep(60)
