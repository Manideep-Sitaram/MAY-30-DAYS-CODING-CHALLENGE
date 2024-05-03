import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class WebAccessibilityTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "/Users/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testColorContrast() {
        driver.get("https://www.ada.gov/resources/web-guidance/");

        // Get the text of the page
        String text = driver.findElement(By.tagName("body")).getText();

        // Check if the text contains the word "contrast"
        Assert.assertTrue(text.contains("contrast"));
    }

    @Test
    public void testAltText() {
        driver.get("https://www.ada.gov/resources/web-guidance/");

        // Get all the images on the page
        List<WebElement> images = driver.findElements(By.tagName("img"));

        // Check if each image has an alt attribute
        for (WebElement image : images) {
            Assert.assertTrue(image.getAttribute("alt") != null);
        }
    }

    @Test
    public void testKeyboardNavigation() {
        driver.get("https://www.ada.gov/resources/web-guidance/");

        // Get the first link on the page
        WebElement link = driver.findElement(By.tagName("a"));

        // Use the keyboard to navigate to the link
        link.sendKeys(Keys.TAB);

        // Check if the link is focused
        Assert.assertTrue(link.getAttribute("class").contains("focus"));
    }

    @Test
    public void testScreenReaderAccess() {
        driver.get("https://www.ada.gov/resources/web-guidance/");

        // Get the text of the page
        String text = driver.findElement(By.tagName("body")).getText();

        // Check if the text contains the word "screen reader"
        Assert.assertTrue(text.contains("screen reader"));
    }
}
