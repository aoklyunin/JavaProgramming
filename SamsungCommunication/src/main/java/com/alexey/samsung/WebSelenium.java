package com.alexey.samsung;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Created by aokly on 25.09.2016.
 */
public class WebSelenium {

    static WebDriver driver;
    public static void fillPage(String login,String password, String name, String mail) throws InterruptedException {
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

    public static void fillPage(ArrayList<String> lst) throws InterruptedException {
        fillPage(lst.get(0), lst.get(1),lst.get(2),lst.get(3));
    }
    public static void test() throws InterruptedException {
        System.out.println("works");
        System.setProperty("webdriver.chrome.driver", "C://Program Files (x86)//chromedriver.exe");
        driver = new ChromeDriver();
        System.out.println("asd");
        driver.get("http://mdl.sch239.net/");

        WebElement login = driver.findElement(By.name("username"));
        login.sendKeys("kluninao");
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("kluninao");
        WebElement btn = driver.findElement(By.id("loginbtn"));
        btn.click();

        Thread.sleep(1000);

        DBHelper dbHelper = new DBHelper();
        try {
            dbHelper.connect();
            ArrayList<ArrayList<String>>  lst = dbHelper.getValsRegister();
            for (ArrayList<String> ls:lst){
                //fillPage(ls);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //fillPage("asf1","asfasf2","asfas fa2","asasfasf@asg.rt");

        driver.quit();
    }

}
