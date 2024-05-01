package utility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrowserDriver {
    public static WebDriver driver;

    BrowserDriver(){
        this.driver = driver;
        System.setProperty("webdriver.chrom.driver",System.getProperty("user.dir")+"/src/test/resources/drivers/chromedriver.exe");
        this.driver = new ChromeDriver();
    }

    public void close(){
        this.driver.close();
    }
}
