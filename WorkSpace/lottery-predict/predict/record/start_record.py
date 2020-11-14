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
    current_count = random.randint(1, 3)
    # 登录网站
    opener = login(current_count)
    while True:
        try:
            # 获取网站源码
            data = getData(opener)
            # 分析网站源码，得到记录
            recordDict = parseData(data)
            # # 获取当天时间
            # ymd = getYmd()
            # 将获取到的记录写进数据库
            writeRecordToFile(recordDict)
            print "成功写入: " + str(count) + " 批！"
            count += 1
            # 20分钟获取一次记录
            time.sleep(random.randint(1200, 1250))
            if count % 5 == 0:
                count_value = random.randint(1, 3)
                if current_count != count_value:
                    current_count = count_value
                    opener = login(current_count)
                    print "账号登陆切换成: " + str(current_count) + " 成功!"
                else:
                    print "当前随机登陆账号: " + str(count_value) + " 与上次登陆账号: " + str(current_count) + " 相同，无需切换!"
        except BaseException as e:
            print "发生异常，重新初始化………"
            opener = login(current_count)
            print e.message
            time.sleep(60)
