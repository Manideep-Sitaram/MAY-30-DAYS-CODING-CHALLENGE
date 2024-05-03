import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumADAAccessibilityTesting {

    public static void main(String[] args) {

        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");

        // Create a new WebDriver instance
        WebDriver driver = new ChromeDriver();

        // Navigate to the web page under test
        driver.get("https://www.ada.gov/resources/web-guidance/");

        // Create a WebDriverWait instance to wait for elements to load
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Get the element corresponding to the first manual ADA test case
        WebElement motorImpairmentElement = driver.findElement(By.xpath("//a[@href='#crt-page--sidenav']"));

        // Check if the element is focusable and can be activated using the keyboard
        boolean isFocusable = motorImpairmentElement.isEnabled() && motorImpairmentElement.isDisplayed();
        boolean canBeActivated = motorImpairmentElement.getAttribute("tabindex") != null;

        // Assert that the element is focusable and can be activated using the keyboard
        org.junit.Assert.assertTrue("Motor Impairment element should be focusable and activatable using the keyboard", isFocusable && canBeActivated);

        // Get the element corresponding to the second manual ADA test case
        WebElement clearInstructionsElement = driver.findElement(By.xpath("//a[@href='#res']"));

        // Check if the element is present on the page
        boolean isPresent = clearInstructionsElement.isDisplayed();

        // Assert that the element is present on the page
        org.junit.Assert.assertTrue("Clear instructions element should be present on the page", isPresent);

        // Navigate to the link for the clear instructions element
        clearInstructionsElement.click();

        // Wait for the page to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Resources']")));

        // Check if the page contains the expected resources
        boolean containsResources = driver.findElements(By.xpath("//ul[@class='no-bullets']/li")).size() > 0;

        // Assert that the page contains the expected resources
        org.junit.Assert.assertTrue("Resources page should contain a list of resources", containsResources);

        // Navigate back to the main page
        driver.navigate().back();

        // Check if the page title contains the expected text
        String expectedTitle = "Guidance on Web Accessibility and the ADA";
        String actualTitle = driver.getTitle();

        // Assert that the page title contains the expected text
        org.junit.Assert.assertEquals("Page title should contain the expected text", expectedTitle, actualTitle);

        // Close the WebDriver instance
        driver.quit();
    }
}