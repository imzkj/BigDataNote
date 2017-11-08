#coding:utf-8
#双下划线，目的避免子类对父类同名属性的冲突
class Student(object):
    def __init__(self,name,age):
        self.name=name
        self.age=age
        self.__address='Shanghai'

joke=Student('Joke',28)
# print(joke.__address
print(joke._Student__address)
#类内的双下划线# 的内容，类外调用使用被改为属性前面加上单下划线和类名



r = [0 for b in range(2) if not b]
print r