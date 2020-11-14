# coding:UTF-8

last_record = set()
this_record = set()

def writeRecordToFile(recordDict):
    try:
        global last_record
        global this_record
        recordDict = sorted(recordDict.items(), key=lambda d: d[0])
        for issue in recordDict:
            issue_value = issue[0]
            if issue_value in last_record:
                continue
            this_record.add(issue_value)
            record = issue[1]
            term = int(issue_value[-3:])
            ymd = "20" + issue_value[0:2] + "-" + issue_value[2:4] + "-" + issue_value[4:6]
            f = open('record/record_' + ymd + '.txt', 'a')  # 若是'wb'就表示写二进制文件
            list = record.split(",", -1)
            wan = list[0]
            qian = list[1]
            bai = list[2]
            shi = list[3]
            ge = list[4]
            content = "\"" + str(issue_value) + "\"\t\"" + str(ymd) + "\"\t\"" + str(term) + "\"\t\"" + str(record) + \
                      "\"\t\"" + str(wan) + "\"\t\"" + str(qian) + "\"\t\"" + str(bai) + "\"\t\"" + str(
                shi) + "\"\t\"" + \
                      str(ge) + "\"\n"
            f.write(content)
            f.close()
        last_record = this_record.copy()
        this_record.clear()
    except BaseException as e:
        print "写文件操作失败！"
        print e


if __name__ == '__main__':
    recordDict = {'180901154': '7,5,9,1,8', '180901152': '3,4,9,8,5', '180901153': '2,3,2,5,4',
                  '180901150': '7,9,0,5,6', '180901151': '7,1,3,9,1'}
    ymd = '2018-09-01'
    writeRecordToFile(recordDict)
