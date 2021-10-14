package ui.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class SoundCloud{

    @Test
    public void soundCloudTest() throws InterruptedException, IOException, UnsupportedFlavorException {
        WebDriverManager.chromedriver().browserVersion("93.0.4577.82").setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("enable-automation");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-browser-side-navigation");
        options.addArguments("--disable-gpu");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://soundcloud.com/");
        
        Thread.sleep(5000);

        driver.findElement(By.xpath("/html/body/div[3]/div[3]/div/div[1]/div/div[2]/div/button[2]")).click();
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div/div[2]/div/div[1]/span/span/form/input")).sendKeys("Ariana Grande");
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div/div[1]/div/div[1]/div")).click();
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div/div[2]/div/div[1]/span/span/form/button")).click();
        Thread.sleep(7000);
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div/div[3]/div/div/div/ul/li[2]/div/div/div/div[2]/div[4]/div[1]/div/div/button[4]")).click();
        driver.findElement(By.xpath("/html/body/div[7]/div/div/button[1]")).click();

        driver.get("https://sclouddownloader.net/");

        String myText = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);

        driver.findElement(By.xpath("/html/body/div[2]/div/center/form/div/input")).sendKeys(myText);

        driver.findElement(By.xpath("/html/body/div[2]/div/center/form/div/div/input")).click();
        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[1]/center/a[1]")).click();
    }
}
