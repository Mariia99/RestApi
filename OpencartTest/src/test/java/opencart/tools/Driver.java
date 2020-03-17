package opencart.tools;

import opencart.data.ConstantVariables;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;

public class Driver {

    private static WebDriver driver = null;

    private Driver() {
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            if (ConstantVariables.BROWSER_NAME.equalsIgnoreCase("chrome")) {
                System.setProperty("webdriver.chrome.driver", "./target/drivers/chromedriver.exe");
                ChromeOptions options = new ChromeOptions();
                options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS,true);
                driver = new ChromeDriver(options);
            } else if (ConstantVariables.BROWSER_NAME.equalsIgnoreCase("firefox")) {
                System.setProperty("webdriver.gecko.driver", "./target/drivers/geckodriver.exe");
                FirefoxOptions options = new FirefoxOptions();
                options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS,true);
                driver = new FirefoxDriver(options);

            }
        }
        return driver;
    }
}
