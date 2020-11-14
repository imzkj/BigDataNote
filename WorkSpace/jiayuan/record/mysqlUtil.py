# coding:UTF-8
import sqlite3

def writeRecordToDatabase(uid,age,nickname,marriage,height,education,income,work_location,work_sublocation,shortnote,matchCondition):
    conn = sqlite3.connect('D:\jiayuan.db')
    try:
        c = conn.cursor()
        sql = "REPLACE INTO info(uid,age,nickname,marriage,height,education,income,work_location,work_sublocation,shortnote,matchCondition) " \
              "VALUES("+str(uid) + "," + str(age) + ",'" + nickname + "','" + marriage + "'," + str(height) + ",'" + education \
              + "','" + income + "','" + work_location + "','" + work_sublocation + "','" + shortnote + "','" + matchCondition + "')"
        c.execute(sql);
        conn.commit()
        conn.close()
    except BaseException as e:
        print "数据库操作失败！"
        print e
    finally:
        # 关闭数据库连接
        conn.close()

def writeUid(uid, uidhash):
    conn = sqlite3.connect('D:\jiayuan.db')
    try:
        c = conn.cursor()
        sql = "REPLACE INTO send_situation(uid,uid_hash,send_times,send_date) VALUES(" + str(uid) + ",'" + str(uidhash) +"',1, strftime('%Y-%m-%d %H:%M:%f','now','localtime'))"
        c.execute(sql)
        conn.commit()
        conn.close()
    except BaseException as e:
        print "数据库操作失败！"
        print e
    finally:
        # 关闭数据库连接
        conn.close()

def updateHash(uid, uidhash):
    conn = sqlite3.connect('D:\jiayuan.db')
    try:
        c = conn.cursor()
        sql = "UPDATE send_situation SET uid_hash='" + str(uidhash) + "' WHERE uid='" + str(uid) + "'"
        c.execute(sql)
        conn.commit()
        conn.close()
    except BaseException as e:
        print "数据库更新操作失败！"
        print e
    finally:
        # 关闭数据库连接
        conn.close()

def is_in(uid):
    try:
        conn = sqlite3.connect('D:\jiayuan.db')
        c = conn.cursor()
        cursor = c.execute("SELECT uid from send_situation where uid ='"+str(uid)+"'")
        for row in cursor:
             if row[0]:
                 return True
        return False
    except BaseException as e:
        print "数据库查詢失败！"
        print e
    finally:
        # 关闭数据库连接
        conn.close()

if __name__ == '__main__':
    writeRecordToDatabase("179283788", "24","藤藤菜","未婚","171","本科","null","广州","广州","我是一个温柔的人 ，爱好广泛的我，喜欢跳舞,自拍。",
                          "24-32岁,171-196cm,广东,广州")
    writeUid(179283788)
    if is_in(1792837881):
        print "存在"
