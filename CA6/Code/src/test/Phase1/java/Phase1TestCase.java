package com.example.tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class UntitledTestCase {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testUntitledTestCase() throws Exception {
    driver.get("http://localhost:8080/swagger-ui/index.html#/");
    driver.findElement(By.xpath("//div[@id='operations-authentication-controller-signup']/div/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-authentication-controller-signup']/div[2]/div/div/div/div[2]/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-authentication-controller-signup']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-authentication-controller-signup']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-authentication-controller-signup']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [doubleClick | xpath=//div[@id='operations-authentication-controller-signup']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea | ]]
    driver.findElement(By.xpath("//div[@id='operations-authentication-controller-signup']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-authentication-controller-signup']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [doubleClick | xpath=//div[@id='operations-authentication-controller-signup']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea | ]]
    driver.findElement(By.xpath("//div[@id='operations-authentication-controller-signup']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-authentication-controller-signup']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).clear();
    driver.findElement(By.xpath("//div[@id='operations-authentication-controller-signup']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).sendKeys("{\n    \"username\": \"atask\",\n    \"password\": \"123\",\n    \"email\": \"atask@gmail.com\",\n    \"birthDate\": \"2000-01-01\",\n    \"address\": \"Tehran, Iran\"\n}");
    driver.findElement(By.xpath("//div[@id='operations-authentication-controller-signup']/div[2]/div/div[2]/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-authentication-controller-signup']/div/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-commodities-controller-getCommodities']/div/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-commodities-controller-getCommodities']/div[2]/div/div/div/div[2]/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-commodities-controller-getCommodities']/div[2]/div/div[2]/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-commodities-controller-getCommodities']/div/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addCredit']/div/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-authentication-controller-signup']/div/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-authentication-controller-signup']/div/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addCredit']/div/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-getUser']/div/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-getUser']/div[2]/div/div/div/div[2]/button")).click();
    driver.findElement(By.xpath("//input[@value='']")).click();
    driver.findElement(By.xpath("//input[@value='']")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [doubleClick | xpath=//input[@value=''] | ]]
    driver.findElement(By.xpath("//input[@value='1']")).clear();
    driver.findElement(By.xpath("//input[@value='1']")).sendKeys("1");
    driver.findElement(By.xpath("//div[@id='operations-user-controller-getUser']/div[2]/div/div[2]/button")).click();
    driver.findElement(By.xpath("//input[@value='1']")).click();
    driver.findElement(By.xpath("//input[@value='1']")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [doubleClick | xpath=//input[@value='1'] | ]]
    driver.findElement(By.xpath("//input[@value='0']")).clear();
    driver.findElement(By.xpath("//input[@value='0']")).sendKeys("0");
    driver.findElement(By.xpath("//div[@id='operations-user-controller-getUser']/div[2]/div/div[2]/button")).click();
    driver.findElement(By.xpath("//input[@value='0']")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [doubleClick | xpath=//input[@value='0'] | ]]
    driver.findElement(By.xpath("//input[@value='atask']")).clear();
    driver.findElement(By.xpath("//input[@value='atask']")).sendKeys("atask");
    driver.findElement(By.xpath("//div[@id='operations-user-controller-getUser']/div[2]/div/div[2]/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-getUser']/div/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addCredit']/div/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div/div[2]/button")).click();
    driver.findElement(By.xpath("//input[@value='']")).click();
    driver.findElement(By.xpath("//input[@value='atask']")).click();
    driver.findElement(By.xpath("//input[@value='atask']")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [doubleClick | xpath=//input[@value='atask'] | ]]
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [doubleClick | xpath=//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea | ]]
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [doubleClick | xpath=//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea | ]]
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).clear();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).sendKeys("{\n  \"username\": \"atask\",\n  \"additionalProp2\": \"string\",\n  \"additionalProp3\": \"string\"\n}");
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [doubleClick | xpath=//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea | ]]
    driver.findElement(By.xpath("//input[@value='']")).click();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [doubleClick | xpath=//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea | ]]
    driver.findElement(By.xpath("//input[@value='']")).click();
    driver.findElement(By.xpath("//input[@value='atask']")).clear();
    driver.findElement(By.xpath("//input[@value='atask']")).sendKeys("atask");
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [doubleClick | xpath=//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea | ]]
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [doubleClick | xpath=//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea | ]]
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).clear();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).sendKeys("{\n  \"credit\": \"100000\"\n}");
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addCredit']/div[2]/div/div[2]/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addCredit']/div/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-getUser']/div/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-getUser']/div[2]/div/div[2]/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-getUser']/div/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-addToBuyList']/div/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-addToBuyList']/div[2]/div/div/div[3]/div[2]")).click();
    driver.findElement(By.xpath("//div[@id='+WvpTK8=']/div/pre")).click();
    driver.findElement(By.xpath("//div[@id='+WvpTK8=']/div/pre/code/span[3]")).click();
    driver.findElement(By.xpath("//div[@id='+WvpTK8=']/div/pre/code/span[3]")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [doubleClick | xpath=//div[@id='+WvpTK8=']/div/pre/code/span[3] | ]]
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-addToBuyList']/div[2]/div/div/div/div[2]/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-addToBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-addToBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-addToBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [doubleClick | xpath=//div[@id='operations-buy-list-controller-addToBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea | ]]
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-addToBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-addToBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [doubleClick | xpath=//div[@id='operations-buy-list-controller-addToBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea | ]]
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-addToBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-addToBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [doubleClick | xpath=//div[@id='operations-buy-list-controller-addToBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea | ]]
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-addToBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-addToBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [doubleClick | xpath=//div[@id='operations-buy-list-controller-addToBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea | ]]
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-addToBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-addToBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).clear();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-addToBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).sendKeys("{\n  \"username\": \"atask\",\n  \"id\": \"1\"\n}");
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-addToBuyList']/div[2]/div/div[2]/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-addToBuyList']/div/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-purchaseBuyList']/div/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-purchaseBuyList']/div[2]/div/div/div/div[2]/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-purchaseBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-purchaseBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-purchaseBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [doubleClick | xpath=//div[@id='operations-buy-list-controller-purchaseBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea | ]]
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-purchaseBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-purchaseBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [doubleClick | xpath=//div[@id='operations-buy-list-controller-purchaseBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea | ]]
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-purchaseBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-purchaseBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-purchaseBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-purchaseBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).clear();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-purchaseBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).sendKeys("{\n  \"username\": \"atask\"\n}");
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-purchaseBuyList']/div[2]/div/div[2]/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-purchaseBuyList']/div/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-getBuyList']/div/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-getBuyList']/div[2]/div/div/div/div[2]/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-getBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-getBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).clear();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-getBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).sendKeys("{\n\n}");
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-getBuyList']/div[2]/div/div[2]/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-getBuyList']/div/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-getPurchasedList']/div/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-getPurchasedList']/div[2]/div/div/div/div[2]/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-getPurchasedList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-getPurchasedList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).clear();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-getPurchasedList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).sendKeys("{\n}");
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-getPurchasedList']/div[2]/div/div[2]/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-getPurchasedList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-getPurchasedList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).clear();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-getPurchasedList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).sendKeys("{\n\n}");
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-getPurchasedList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-getPurchasedList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).clear();
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-getPurchasedList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")).sendKeys("{\n  \"username\": \"atask\"\n}");
    driver.findElement(By.xpath("//div[@id='operations-buy-list-controller-getPurchasedList']/div[2]/div/div[2]/button")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
