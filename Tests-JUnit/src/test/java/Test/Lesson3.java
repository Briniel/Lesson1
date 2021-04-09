package Test;

import Proper.SettingConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Lesson3 {

    private Logger logger = LogManager.getLogger(Lesson3.class);
    protected static WebDriver driver;
    private SettingConfig cfg = ConfigFactory.create(SettingConfig.class);

    @Test
    public void logExamlpe() {
        logger.info("Start Test");
        logger.error("It's error");
        logger.fatal("It's fatal");
        logger.debug("It's debug");
    }

    @Before
    public void startDriver() {
        WebDriverManager.chromedriver().setup();
        logger.info("Поднятие драйвера");
        driver = new ChromeDriver();
        logger.info("Драйвер поднят");
        logger.info("Запуск браузера");
    }

    @After
    public void stop() {
        logger.info("Выключение драйвера");
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void webDriverTest() {
        logger.info("Запуск браузера");
        driver.get("https://" + cfg.url() + "/");
        String url = driver.getTitle();
        Assert.assertTrue("ОШИБКА! Ожидалось " + cfg.url() + " открылся сайт"  + url,
                url.contains(cfg.url()));
        logger.info("Браузер запущен");
    }

}
