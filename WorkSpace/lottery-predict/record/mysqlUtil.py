# coding:UTF-8
import MySQLdb


def writeRecordToDatabase(recordDict, ymd):
	try:
		# 打开数据库连接
		db = MySQLdb.connect("192.168.58.28", "root", "123456", "lottery", charset='utf8')
		# 使用cursor()方法获取操作游标
		cursor = db.cursor()
		replaceSql = "REPLACE INTO `lottery_record`(`issue`,`ymd`,`term`,`record`,`wan`," \
					 "`qian`,`bai`,`shi`,`ge`) VALUES(%s, %s, %s, %s, %s, %s, %s, %s, %s)"
		for issue in recordDict.keys():
			# issue为开奖的总期数，record为该期开奖记录
			record = recordDict[issue]
			# 在当天的期数
			term = int(issue[-3:])
			list = record.split(",", -1)
			# 万位记录
			wan = list[0]
			# 千位记录
			qian = list[1]
			# 百位记录
			bai = list[2]
			# 十位记录
			shi = list[3]
			# 个位记录
			ge = list[4]
			# 使用execute方法执行SQL语句，写进数据库
			cursor.execute(replaceSql, (issue, ymd, term, record, wan, qian, bai, shi, ge))
	except BaseException as e:
		print "数据库操作失败！"
		print e
	finally:
		# 关闭数据库连接
		db.close()


if __name__ == '__main__':
	recordDict = {'180901154': '7,5,9,1,8', '180901152': '3,4,9,8,5', '180901153': '2,3,2,5,4',
				  '180901150': '7,9,0,5,6', '180901151': '7,1,3,9,1'}
	ymd = '2018-09-01'
	writeRecordToDatabase(recordDict, ymd)
