
from login import *
if __name__ == '__main__':
	opener = login()
	post_data = urllib.urlencode({
		'flag': 'login',
		'username': '听雨轩',
		'password': 'zkj123456',
		'Button1': ''
	})
	# 登陆地址
	url = 'http://pailook.cn/'
	# 开始进行模拟登陆
	opener.open(url, post_data)