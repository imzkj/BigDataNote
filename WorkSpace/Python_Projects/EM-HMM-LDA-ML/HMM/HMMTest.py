# coding=UTF-8
'''
Created on 2017年4月5日

@author: Administrator
'''

def load_model(f_name):
    ifp = file(f_name, 'rb')
    return eval(ifp.read())  #eval参数是一个字符串, 可以把这个字符串当成表达式来求值,


prob_start = load_model("trainHMM\prob_start.py")
prob_trans = load_model("trainHMM\prob_trans.py")
prob_emit = load_model("trainHMM\prob_emit.py")


def viterbi(obs, states, start_p, trans_p, emit_p):  #维特比算法（一种递归算法）
    V = [{}]
    path = {}
    for y in states:   #初始值
        V[0][y] = start_p[y] * emit_p[y].get(obs[0],0)   #在位置0，以y状态为末尾的状态序列的最大概率 
        path[y] = [y]
        print "V[0][y]: ",V[0][y]
        print "path[y]: ",path[y]
    for t in range(1,len(obs)):#华字进来
        V.append({})
        newpath = {}
        for y in states:      #从y0 -> y状态的递归 B  初始概率V[t-1][y0]*转换概率*这个词作为y的概率
            (prob, state) = max([(V[t-1][y0] * trans_p[y0].get(y,0) * emit_p[y].get(obs[t],0) ,y0) for y0 in states if V[t-1][y0]>0])
            V[t][y] =prob
            newpath[y] = path[state] + [y]
        path = newpath  #记录状态序列
        print "V[t][y]: ",V[t]
        print "path: ",path
    (prob, state) = max([(V[len(obs) - 1][y], y) for y in states])  #在最后一个位置，以y状态为末尾的状态序列的最大概率
    print "prob: ",prob
    print "state: ",state
    return (prob, path[state])  #返回概率和状态序列


def cut(sentence):
    prob, pos_list =  viterbi(sentence,('B','M','E','S'), prob_start, prob_trans, prob_emit)
    return (prob,pos_list)


if __name__ == "__main__":
    test_str = u"中华人民共和国成立了"
    prob,pos_list = cut(test_str)
    
    print test_str
    print prob
    print pos_list