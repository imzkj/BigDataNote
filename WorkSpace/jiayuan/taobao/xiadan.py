# -*- coding: utf-8 -*-
from selenium import webdriver
import time

# UserData = 'C:\Users\\60186\AppData\Local\Google\Chrome\User Data\Default'
UserData = 'null'
allOrder = 'https://cart.taobao.com/cart.htm?spm=a21bo.2017.1997525049.1.5af911d9sXWGJd&from=mini&ad_id=&am_id=&cm_id=&pm_id=1501036000a02c5c3739'   # 所有订单网页链接
loginName = '15338759556'
loginPassword = 'am663556'   #  淘宝登录密码
payPassword = '1'   # 淘宝支付密码

if __name__ == '__main__':
    option = webdriver.ChromeOptions()
    option.add_argument(UserData)
    driver = webdriver.Chrome(chrome_options=option)
    driver.get(allOrder)
    nameInput = driver.find_element_by_name('TPL_username')
    nameInput.send_keys(loginName)
    pswInput = driver.find_element_by_name('TPL_password')
    pswInput.send_keys(loginPassword)
    loginBtn = driver.find_element_by_id('J_SubmitStatic')
    loginBtn.click()
    time.sleep(10)
    select = driver.find_element_by_xpath('//*[@id="tp-bought-root"]/div[3]/div[1]/label')
    select.click()
    time.sleep(0.2)
    btn = driver.find_element_by_xpath('//*[@id="tp-bought-root"]/div[3]/div[1]/div/button[1]')
    btn.click()
    driver.switch_to_window(driver.window_handles[1])
    time.sleep(0.5)
    pswInput = driver.find_element_by_id('payPassword_rsainput')
    pswInput.send_keys(payPassword)
    J_authSubmit = driver.find_element_by_id('J_authSubmit')
    while (not_executed):
        dt = list(time.localtime())
        hour = dt[3]
        minute = dt[4]
        if hour == 0 and minute == 0:
            not_executed = 0
    J_authSubmit.click()