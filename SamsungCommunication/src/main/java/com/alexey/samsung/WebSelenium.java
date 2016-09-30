package com.alexey.samsung;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by aokly on 25.09.2016.
 */
public class WebSelenium implements AutoCloseable {

    static WebDriver driver;

    public void fillAddUserPage(String login, String password, String name, String mail) throws InterruptedException {
        //Thread.sleep(1000);

        driver.get("http://mdl.sch239.net/user/editadvanced.php?id=-1");
        WebElement loginW = driver.findElement(By.name("username"));
        loginW.sendKeys(login.toLowerCase());
        WebElement passwordW = driver.findElement(By.name("newpassword"));
        passwordW.sendKeys(password);
        String s[] = name.split(" ");
        WebElement nameW = driver.findElement(By.name("firstname"));
        nameW.sendKeys(s[0]);
        WebElement lastNameW = driver.findElement(By.name("lastname"));
        lastNameW.sendKeys(s[1]);
        WebElement mailW = driver.findElement(By.name("email"));
        mailW.sendKeys(mail);
        WebElement btn = driver.findElement(By.name("submitbutton"));
        btn.click();
        Thread.sleep(4000);
    }

    public void fillAddUserPage(ArrayList<String> lst) throws InterruptedException {
        fillAddUserPage(lst.get(0), lst.get(1), lst.get(2), lst.get(3));
    }

    public void loginToMdl() {
        driver.get("http://mdl.sch239.net/");
        WebElement login = driver.findElement(By.name("username"));
        login.sendKeys("kluninao");
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("kluninao");
        WebElement btn = driver.findElement(By.id("loginbtn"));
        btn.click();
    }

    WebSelenium() {
        System.setProperty("webdriver.chrome.driver", "C://Program Files (x86)//chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Override
    public void close() throws Exception {
        driver.quit();
    }

    public ArrayList<WebElement> getAList(List<WebElement> l){

        ArrayList<WebElement> al = new ArrayList<>();
        for (WebElement w:l){
            al.add(w);
           // System.out.println(w);
        }
        return  al;
    }
    public void getAttempts(String attemptName) throws InterruptedException {
        driver.get("http://mdl.sch239.net/course/view.php?id=44");
        Thread.sleep(1000);
        WebElement wTest = driver.findElement(By.xpath("//*[text()='" + attemptName + "']"));

        wTest.click();
        WebElement wAppempt = driver.findElement(By.xpath("//*[@id='region-main']/div/div[2]/a"));
        wAppempt.click();
        //Thread.sleep(2000);

        WebElement elem = driver.findElement(By.id("attempts"));
        List<WebElement> trs = elem.findElements(By.tagName("tr"));
        trs = trs.subList(0,trs.size()-2);
        for (WebElement tr : trs) {
            List<WebElement> tdList =  tr.findElements(By.tagName("td"));
            if (tdList.size()!=0) {
                ArrayList<WebElement> tds = new ArrayList<WebElement>(tdList);
                if (tds.size()>1) {
                    ArrayList<WebElement> hrLst = new ArrayList<WebElement>(tds.get(1).findElements(By.tagName("a")));
                    if (hrLst.size() == 2) {
                        String attemptHref = hrLst.get(1).getAttribute("href");
                        String name = hrLst.get(0).getText();
                        System.out.println(attemptHref + " " + name);

                        String mail = tds.get(2).getText();
                        String state = tds.get(3).getText();
                        String starts = tds.get(4).getText();
                        String ends = tds.get(5).getText();
                        String tm = tds.get(6).getText();
                        String evaluation = tds.get(7).getText();

                        ArrayList<WebElement> attemptList = getAList(tds.subList(8, tds.size()));
                        for (WebElement w : attemptList) {
                            System.out.print(" "+w.getText());
                        }
                        System.out.println();
                    }
                }
            }
/*
            for (WebElement td : tds) {
                i++;
                switch (i){
                    case 1:

                    case 2:
                        ArrayList<WebElement> hrLst = (td.findElements(By.tagName("a"));
                        attemptHref = hr.getAttribute("href");
                        String s [] = td.getText().split("\n");
                        name = s[0];
                        System.out.println(attemptHref+" "+name);
                        break;
                    case 3:
                        mail = td.getText();
                        break;
                    case 4:
                        state = td.getText();
                        break;
                    case 5:
                        starts = td.getText();
                        break;
                    case 6:
                        ends = td.getText();
                        break;
                    case 7:
                        tm = td.getText();
                        break;
                    case 8:
                        evaluation = td.getText();
                        break;
                    default:
                        String s1 = td.getText();
                        s1 = (s1=="-"?"0":s1);
                        attemptList.add(s1);
                }
            }
            for (String at:attemptList){
                System.out.print(at);
            }
            System.out.println();*/
        }

    }
}
