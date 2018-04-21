# weiboSpider
利用 selenium 模拟微博登录爬虫
需要的jar 包  selenium-server-standalone-2.42.2 
需要的模拟浏览器driver chromedriver PS:mac和window版不同 根据系统和装的Chrome版本下载对应的可执行文件 链接:http://chromedriver.storage.googleapis.com/index.html
通过模拟浏览器的点击行为对微博页面的内容进行动态抓取,通过css 进行元素定位,进行定向抓取对应的内容,此爬虫因为抓取的是微博网页版,所以为了保证抓取数据的完整性,设置的等待时间比较久

