import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class CardOrderTest {
    private WebDriver driver;
//    private ChromeOptions options;

    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "./driver/windows/chromedriver.exe");
    }

    @BeforeEach
    void setUp() {
//        options = new ChromeOptions();
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--no-sandbox");
//        options.addArguments("--headless");
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void testWithTrueData() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Путин Вова");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+71234567890");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();
        String actualMessage = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        String expectedMessage = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.".strip();
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }
    @Test
    void testAllEmptyFields() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();
        List<WebElement> textFields = driver.findElements(By.cssSelector("[class='input__sub']"));
        String actualMessage = textFields.get(0).getText().strip();
        String expectedMessage = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void testNameEmptyField() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+71234567890");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();
        List<WebElement> textFields = driver.findElements(By.cssSelector("[class='input__sub']"));
        String actualMessage = textFields.get(0).getText().strip();
        String expectedMessage = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void testWithLatinName() {

        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Putin Vova");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+71234567890");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();
        List<WebElement> textFields = driver.findElements(By.cssSelector("[class='input__sub']"));
        String actualMessage = textFields.get(0).getText().strip();
        String expectedMessage = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());

    }

    @Test
    void testPhoneWithoutPlus() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Путин Вова");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("71234567890");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();
        List<WebElement> textFields = driver.findElements(By.cssSelector("[class='input__sub']"));
        String actualMessage = textFields.get(1).getText().strip();
        String expectedMessage = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void testPhoneWith10Symbols() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Путин Вова");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+7123456789");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();
        List<WebElement> textFields = driver.findElements(By.cssSelector("[class='input__sub']"));
        String actualMessage = textFields.get(1).getText().strip();
        String expectedMessage = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void testPhoneWith12Symbols() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Путин Вова");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+712345678901");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();
        List<WebElement> textFields = driver.findElements(By.cssSelector("[class='input__sub']"));
        String actualMessage = textFields.get(1).getText().strip();
        String expectedMessage = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }
    @Test
    void testPhoneWithInvalidSymbols() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Путин Вова");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+7 (123) 456-78-90");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();
        List<WebElement> textFields = driver.findElements(By.cssSelector("[class='input__sub']"));
        String actualMessage = textFields.get(1).getText().strip();
        String expectedMessage = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void testPhoneEmptyField() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Путин Вова");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();
        List<WebElement> textFields = driver.findElements(By.cssSelector("[class='input__sub']"));
        String actualMessage = textFields.get(1).getText().strip();
        String expectedMessage = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }
    @Test
    void testWithoutConcent() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Путин Вова");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+712345678901");
        driver.findElement(By.cssSelector("[type='button']")).click();
        String actualMessage = driver.findElement(By.cssSelector("[class='checkbox__text']")).getText();
        String expectedMessage = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }
}