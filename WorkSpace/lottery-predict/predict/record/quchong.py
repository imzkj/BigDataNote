# coding:UTF-8


f = open("record/record_2018-09-05.txt", 'a+')
f1 = open("record/record_2018-09-01.txt", 'a+')
last = ""
this_value = ""
for i in f:
    this_value = i.split("\t")[0]
    if last!="" and this_value==last:
        continue
    last = this_value
    f1.write(i)
f.close()
f1.close()