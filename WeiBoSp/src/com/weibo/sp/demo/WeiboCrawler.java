package com.weibo.sp.demo;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.interactions.Actions;

public class WeiboCrawler   {

   
	///Users/jiakang/eclipse-workspace/weibosp/WeiBoSp/driver
    public static void main(String[] args) throws Exception {
       testSelenium();
       
       
    }
    
    public static void testSelenium() throws IOException, InterruptedException {
    	long waitLoadBaseTime = 10000;
    	int waitLoadRandomTime = 5000;
    	Random random = new Random(System.currentTimeMillis());
        System.getProperties().setProperty("webdriver.chrome.driver", "/Users/jiakang/eclipse-workspace/weibosp/WeiBoSp/driver/chromedriver");
        @SuppressWarnings("deprecation")
        ChromeDriverService service = new ChromeDriverService.Builder()
        .usingDriverExecutable(
        new File(
        "/Users/jiakang/eclipse-workspace/weibosp/WeiBoSp/driver/chromedriver"))
        .usingAnyFreePort().build();
        service.start();
        WebDriver driver = new ChromeDriver();
      String baseUrl = "https://weibo.com";
//        String baseUrl = "https://passport.weibo.cn/signin/login?entry=mweibo&res=wel&wm=3349&r=http%3A%2F%2Fm.weibo.cn%2F";
      //打开浏览器
      driver.get(baseUrl);
      //因为网站不一定可以马上打开，让进程停一下，否则页面的元素会找不到。
      Thread.sleep(waitLoadBaseTime+random.nextInt(waitLoadRandomTime));
      //打开后的页面
//       driver.get("https://weibo.com/u/2166965937?refer_flag=1005055010_&is_all=1");
    //找到名为"loginName"的元素，填写帐号
//      driver.findElement(By.id("loginName")).clear();
//      driver.findElement(By.id("loginName")).sendKeys("18234116030");

      //找到名为"loginPassword"的元素，填写密码
//      driver.findElement(By.id("loginPassword")).clear();
//      driver.findElement(By.id("loginPassword")).sendKeys("KANG316316jia");

      //找到登录按钮，点击
//      driver.findElement(By.id("loginAction")).click();

      //然后这个页面就会进入到登录后的界面了
      driver.findElement(By.cssSelector("input[name=username]")).sendKeys("你的账号");
      driver.findElement(By.cssSelector("input[name=password]")).sendKeys("你的密码");
      driver.findElement(By.cssSelector("a[action-type=btn_submit]")).click();
      //因为网站不一定可以马上打开，让进程停一下，否则页面还没有加载出来就进行下一步了。
      Thread.sleep(1000);
      driver.get("https://weibo.com/5896401674/Gap2v3T57?filter=all&root_comment_id=0&type=comment");
//      //等待页面动态加载完毕
        Thread.sleep(waitLoadBaseTime+random.nextInt(waitLoadRandomTime));
        
//      选择每条微博的整体子模块
//      List<WebElement> telements = driver.findElements(By.cssSelector("div[action-type=feed_list_item]"));
//      for(int i = 0;i<telements.size();i++) {
//    	  //展开评论
//    	    telements.get(i).findElement(By.cssSelector("a[action-type=fl_comment]")).click();;
//          Thread.sleep(1000);
//      }
//      Thread.sleep(waitLoadBaseTime+random.nextInt(waitLoadRandomTime));

      List<WebElement> elements = driver.findElements(By.cssSelector("div[action-type=feed_list_item]"));
      
      //选择每条微博的文本内容模块 //,
//      List<WebElement> elements2 = driver.findElements(By.cssSelector("div[node-type=feed_list_reason],div[node-type=feed_list_content]"));
//      System.out.println(elements.get(0));

      for (int i = 0; i <elements.size(); i++) {
    	     
    	  WebElement element =  elements.get(i);
    	  WebElement elements2 = element.findElement(By.cssSelector("div[node-type=feed_list_content]"));
 		 String text = elements2.getText();
 		  if(text.contains("转发微博")) {
 			 continue;
 		  }  
    //feed_list_repeat
        List<WebElement> subelements = element.findElements(By.cssSelector("div[node-type=root_comment]")); 
        if(subelements.size() == 0) {
          	continue; 
        }
 	    System.out.println("内容:"+text);
    	   for(int s = 0;s<subelements.size();s++) {
    		   WebElement subelement = subelements.get(s);  		
    		   WebElement face = subelement.findElement(By.cssSelector("div.WB_face.W_fl"));
//	    	      System.out.println("face"+face.findElement(By.cssSelector("a")).getAttribute("href"));
    		   Actions action = new Actions(driver);
    		   action.moveToElement(face).perform();
//	    		   action.moveToElement(face).clickAndHold();
//    	        Thread.sleep(waitLoadBaseTime+random.nextInt(waitLoadRandomTime));
	    	        Thread.sleep(5000);
	    		   List<WebElement> contents = driver.findElements(By.cssSelector("div[class=content]"));
	    		   for(WebElement c:contents) {
	    			   if(c.getText().isEmpty()) {
	    				   continue;
	    			   }
	    			   String ss = c.getText();
	    			   String[] tmpss = ss.split("\n");
	    			   String cstr = "个人信息:";
	    			   for(int snum = 0;snum<tmpss.length;snum++) {
	    				   if(snum == 0||snum == 1) {
	    					   cstr = cstr +tmpss[snum]+"\n";
	    				   }else if(snum == 2) {
	    					   cstr = cstr+"地区:"+tmpss[snum];
	    				   }else if(tmpss[snum].contains("共同关注")){
	    					   cstr = "\n"+cstr +tmpss[snum];	    					   
	    				   }
	    			   }
	    	    	      System.out.println(cstr);
	    		   } 


    		   List<WebElement> sube =  subelement.findElements(By.cssSelector("div[class=WB_text]"));   
    		   WebElement sub = sube.get(0);
    		  action.moveToElement(sub).perform();
    		  action.release(); 
    		  Thread.sleep(1000);
    		  String str = sub.getText();
    		  String[] strs = str.split("：");
    		  str = strs[strs.length-1];
    	      System.out.println("用户昵称:"+sub.findElement(By.cssSelector("a")).getText()+"\n"+ sub.findElement(By.cssSelector("a")).getAttribute("usercard")+"\n"+"评论:"+str);
    	      System.out.println("+++++++++++");

    	      } 
      System.out.println("第"+i+"条-----------------------");
      }    
       driver.quit();
      // 关闭 ChromeDriver 接口
      service.stop();
        
    }

	private static Object ActionChains(WebDriver driver) {
		// TODO Auto-generated method stub
		return null;
	}
	

}

