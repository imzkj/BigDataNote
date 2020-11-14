# coding:UTF-8
import MySQLdb
from decimal import Decimal
from record.start_record import parseData


class analyse(object):
    countDict = {}
    for i in range(10):
        countDict[i] = 0
        for j in range(10):
            map = (i, j)
            countDict[map] = 0

    @staticmethod
    def get_data(wei):
        try:
            # 打开数据库连接
            db = MySQLdb.connect("192.168.58.28", "root", "123456", "lottery", charset='utf8')
            # 使用cursor()方法获取操作游标
            cursor = db.cursor()
            sql = "SELECT issue," + wei + " FROM lottery_record ORDER BY issue ASC"
            # 使用execute方法执行SQL语句，写进数据库
            cursor.execute(sql)
            # 获取所有记录列表
            results = cursor.fetchall()
        except BaseException as e:
            print "数据库操作失败！"
            print e
        finally:
            # 关闭数据库连接
            db.close()
        return results

    @staticmethod
    def save_rate(result_num, wei):
        try:
            # 打开数据库连接
            db = MySQLdb.connect("192.168.58.28", "root", "123456", "lottery", charset='utf8')
            # 使用cursor()方法获取操作游标
            cursor = db.cursor()
            replaceSql = "REPLACE INTO result(`wei`,`key`,`value`,`probability`) VALUES('" + wei + "', %s, %s, %s)"
            for map_value in result_num.keys():
                value = result_num[map_value]
                if isinstance(map_value, int) or value == 0:
                    continue
                all_num = result_num[map_value[0]]
                rate = round(Decimal(value) / Decimal(all_num), 8)
                # 使用execute方法执行SQL语句，写进数据库
                cursor.execute(replaceSql, (map_value[0], map_value[1], rate))
        except BaseException as e:
            print "数据库操作失败！"
            print e
        finally:
            # 关闭数据库连接
            db.close()

    @staticmethod
    def analyse_data(database_wei):
        last_issue = -1
        last_wei = -1
        results = analyse.get_data(database_wei)
        for row in results:
            issue = int(row[0])
            wei = int(row[1])
            if last_issue == -1 or issue - last_issue != 1:
                last_issue = issue
                last_wei = wei
                continue
            map = (last_wei, wei)
            num = analyse.countDict[map]
            analyse.countDict[map] = num + 1
            num1 = analyse.countDict[last_wei]
            analyse.countDict[last_wei] = num1 + 1
            last_issue = issue
            last_wei = wei
        analyse.save_rate(analyse.countDict, database_wei)
        return analyse.countDict


if __name__ == '__main__':
    wei_shu = ("wan", "qian", "bai", "shi", "ge")
    for i in wei_shu:
        result_num = analyse.analyse_data(i)
    # for i in result_num.keys():
    # 	value = result_num[i]
    # 	if value > 0:
    # 		print str(i) + "  |  " + str(value)
