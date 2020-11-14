# coding:UTF-8
import re
import mysqlUtil


def parseData(data):
	allData = getAllStr(data)
	record = getRecord(allData)
	return record

# 从网站源码中获取历史开奖记录
def getAllStr(data):
	allStr = re.search("<span id=\"history_issue\">(.*?)</span>", data)
	return allStr.group(1)

# 分析开奖记录，将记录保存到本地字典中
def getRecord(allData):
	result = re.finditer("<td class=drawinfo2>(.*?)</td><td class=drawinfo2>(.*?)</td>", allData)
	recordDict = {}
	for i in result:
		issue = i.group(1)
		record = i.group(2)
		recordDict[issue] = record
	return recordDict


def getTerm(data):
	result = re.search("已开<span id=\"current_sale\">(.*?)</span>期", data)
	return result.group(1)


if __name__ == '__main__':
	data = '''<div class=dz0>
					<span class=s00>正在销售</span>
					<span class=sbd>第<span class=sbdn id="current_issue">180901155</span>期</span>
					<span class=nbox style='letter-spacing:1px;'>已开<span id="current_sale">154</span>期,剩<span id="current_left">134</span>期</span><br/>
					本期封盘时间<span class=nbox id="current_endtime">2018-09-01 12:54:30</span> 剩余 <span class=sbox id="count_down">00:00:00</span>
				</div>
			</td>
			<td class="box"><td class=l2><span id="history_issue"><table cellpadding=0 cellspacing=0 class=drawinfos><tr class=drawinfo><td width=180px class=drawinfo>期号</td><td width=262px class=drawinfo>开奖号码</td></tr><tr class=drawinfo1><td class=drawinfo2>180901154</td><td class=drawinfo2>7,5,9,1,8</td></tr><tr class=drawinfo1><td class=drawinfo2>180901153</td><td class=drawinfo2>2,3,2,5,4</td></tr><tr class=drawinfo1><td class=drawinfo2>180901152</td><td class=drawinfo2>3,4,9,8,5</td></tr><tr class=drawinfo1><td class=drawinfo2>180901151</td><td class=drawinfo2>7,1,3,9,1</td></tr><tr class=drawinfo1><td class=drawinfo2>180901150</td><td class=drawinfo2>7,9,0,5,6</td></tr></table></span>
			</td>'''
	recordDict = parseData(data)
	print recordDict
	for i in recordDict.keys():
		print i + "    " + recordDict[i]
	# mysqlUtil.writeRecordToDatabase(recordDict)
	print getTerm(data)
