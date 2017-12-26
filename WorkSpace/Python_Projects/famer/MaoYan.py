#!/usr/bin/python
# -*- coding:utf-8 -*-


from selenium import webdriver
from PIL import Image

driver = webdriver.Firefox() # 创建webdriver对象

url = "http://piaofang.maoyan.com" # 定义目标url
driver.get(url) # 打开目标页面

# 获取当前电影名称列表
movie_names = [driver.find_element_by_xpath(".//*[@id='ticket_tbody']/ul[{}]/li[1]/b".format(i)).text for i in range(1,24)]

# 获取实时票房列表
current_piaofang = [driver.find_element_by_xpath(".//*[@id='ticket_tbody']/ul[{}]/li[2]/b/i".format(i)).text for i in range(1,24)]
# 定义截图函数
def snap_shot(url, image_path, scroll_top=90):

    # 打开页面，窗口最大化
    driver.get(url)
    driver.maximize_window()

    # 调用JS脚本滚动页面
    scroll_js = "var q=document.documentElement.scrollTop={}".format(scroll_top)
    driver.execute_script(scroll_js)

    # 截图存储
    driver.save_screenshot(image_path)


# 定义抠图函数
def crop_image(image_path, pattern_xpath, crop_path, scroll_top=90):

    # 获取页面元素及其位置、尺寸
    element = driver.find_element_by_xpath(pattern_xpath)
    location = element.location
    size = element.size

    # 计算抠取区域的绝对坐标
    left = location['x']
    top = location['y'] - scroll_top
    right = location['x'] + size['width']
    bottom = location['y'] + size['height'] - scroll_top

    # 打开图片，抠取相应区域并存储
    im = Image.open(image_path)
    im = im.crop((left, top, right, bottom))

    im.save(crop_path)
# 获取实时票房数据的有效数字长度列表
curpf_lenths = [len(current_piaofang[i-1]) for i in range(1,24)]


# 定义切图函数
def single_digit(index=1):
    lenth = curpf_lenths[index-1]
    name = movie_names[index-1]

    im = Image.open("snap_shot/crop/current_piaofang_{}.png".format(name))

    # 转换为灰度图像
    im = im.convert('L')

    # 切分整数部分
    for j in range(lenth-3):
        locals()['digit_'+ str(j)] = im.crop((0+j*6, 0, 6+j*6, 12))

    # 切分小数部分
    for j in range(lenth-3, lenth-1):
        locals()['digit_'+ str(j)] = im.crop((j*6+4.8, 0, 6+j*6+4.8, 12))

    # 对每部电影，按位存储图片
    for k in range(0, lenth-2):
        locals()['digit_'+ str(k)].save("snap_shot/train/digit_{0}_{1}_{2}.png".format(k, name, now_sign))


# 启动切图函数
for i in range(1,24):
    single_digit(i)

# 二值化函数
def binary_image(im):
    threshold = 200 # 阈值设为200
    table = []

    for i in range(256):
        if i < threshold:
            table.append(0)
        else:
            table.append(1)
    out = im.point(table,'1')

    return out

# 矢量化函数
def buildvector(im):
    v = []

    for i in im.getdata():
        v.append(i)

    return v
#vdata = np.matrix(data,dtype=‘float‘)/255.0
    #new_data = np.reshape(data,(width,height))


