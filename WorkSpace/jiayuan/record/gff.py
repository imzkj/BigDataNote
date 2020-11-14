import urllib2

url = 'http://search.jiayuan.com/v2/search_v2.php?key=&sex=f&stc=1:44,2:18.25,3:164.175,23:1&sn=default&sv=1' \
      '&p=' + str(1) + '&pt=138&ft=off&f=select&mt=u'

req = urllib2.Request(url)
res_data = urllib2.urlopen(req)
res = res_data.read()
print res