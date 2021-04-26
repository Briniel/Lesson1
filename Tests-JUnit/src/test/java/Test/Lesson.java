package Test;

import Config.SettingConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Lesson {

    private static Logger logger = LogManager.getLogger(Lesson.class);
    protected static WebDriver driver;
    private SettingConfig cfg = ConfigFactory.create(SettingConfig.class);
    private WebDriverWait wait;

    @Before
    public void startDriver() {
        WebDriverManager.chromedriver().setup();
        logger.info("Поднятие драйвера");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
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
    public void addressTest() {
        logger.info("Запуск браузера");
        driver.get(cfg.url());
        logger.info("Браузер запущен открыт сайт Otus");
        click(By.xpath("//*[@title='Контакты']"));
        String adressCheck = driver.findElement(By.xpath("//*[contains(text(), 'Адрес')]/../div[2]")).getText();
        String adress = "125167, г. Москва, Нарышкинская аллея., д. 5, стр. 2, тел. +7 499 938-92-02";
        Assert.assertEquals("Ошибкка при сравнении: " + adress + " и " + adressCheck, adress, adressCheck);
    }

    @Test
    public void tele2Test(){
        driver.get("https://msk.tele2.ru/shop/number");
        logger.info("Открыт сайт Tele2");
        String tittle = driver.getTitle();
        String tittle2 = "Красивые номера - купить красивый федеральный номер телефона Tele2 Москва и Московская область, продажа красивых мобильных номеров";
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(tittle).isEqualTo(tittle2);
        driver.findElement(By.id("searchNumber")).sendKeys("97");
        wait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(By.cssSelector(".phone-number"))));
        assertions.assertThat(wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".phone-number"))).isDisplayed()).isEqualTo(true);
        assertions.assertAll();
    }

    @Test
    public void testFAQ(){
        driver.get(cfg.url());
        logger.info("Браузер запущен открыт сайт Otus");
        click(By.xpath("//*[@title='FAQ']"));
        click(By.xpath("//*[contains(text(), 'Где посмотреть программу интересующего курса?')]"));
        String textGet = driver.findElement(By.xpath("//*[contains(text(), 'Где посмотреть программу интересующего курса?')]/../div[2]")).getText();
        String text = "Программу курса в сжатом виде можно увидеть на странице курса после блока с преподавателями. Подробную программу курса можно скачать кликнув на “Скачать подробную программу курса”";
        Assert.assertEquals("ОШИБКА! Текст " + text + " не совпадает с фактическим текстом " + textGet, text, textGet);
    }

    @Test
    public void testSub() {
        driver.get(cfg.url());
        driver.manage().addCookie(new Cookie("sessionid", "zym2gxvy8ll6s82cy4cwurt8oex0qgz1"));
        driver.navigate().refresh();
        logger.info("Браузер запущен открыт сайт Otus");
        setText(By.xpath("//*[@type='email']"), "test1231@test.ru");
        click(By.xpath("//button[contains(text(), 'Подписаться')]"));
        String textGet = driver.findElement(By.xpath("//*[contains(text(), 'Подпишитесь на наши новости')]/../p[2]")).getText();
        String text = "Вы успешно подписались";
        Assert.assertEquals("ОШИБКА! Текст " + text + " не совпадает с фактическим текстом " + textGet, text, textGet);
    }

    private void click(By element){
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    private void setText(By element, String text){
        WebElement textFild = wait.until(ExpectedConditions.elementToBeClickable(element));
        textFild.clear();
        textFild.sendKeys(text);
    }
}
