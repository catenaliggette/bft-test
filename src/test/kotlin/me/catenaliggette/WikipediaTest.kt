package me.catenaliggette

import io.github.bonigarcia.wdm.WebDriverManager
import io.qameta.allure.Step
import org.junit.jupiter.api.*
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import kotlin.test.assertEquals


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation::class)
class WikipediaTest {
    private lateinit var driver: WebDriver

    @BeforeAll
    fun before() {
        WebDriverManager.firefoxdriver().setup()
        driver = FirefoxDriver()
        driver.manage().window().maximize()
    }

    @AfterAll
    fun after() {
        driver.quit()
    }

    @Test
    @Order(1)
    fun `test wikipedia log in`() {
        val pageUrl = "https://en.wikipedia.org/wiki/Main_Page"
        openPage(pageUrl)
        click("pt-login-2")
        Thread.sleep(1000)
        sendKeys("Eythg45", "wpName1")
        sendKeys("eh8FaaXV9Yv9gy7", "wpPassword1")
        Thread.sleep(2000)
        click("wpLoginAttempt")
        Thread.sleep(2000)
        checkText("Eythg45", "pt-userpage-2")
        checkURL(pageUrl)
    }

    @Step("open {url}")
    fun openPage(url: String) {
        driver.get(url)
    }


    @Step("insert text {text} into field {elementID}")
    fun sendKeys(text: String, elementID: String) {
        val element = driver.findElement(By.id(elementID))
        element.sendKeys(text)
    }

    @Step("Click element")
    fun click(elementID: String) {
        val element = driver.findElement(By.id(elementID))
        element.click()
    }

    @Step("Check url {url}")
    fun checkURL(url: String) {
        assertEquals(url, driver.currentUrl)
    }

    @Step("Check url {url} after log out")
    fun checkLoggedOutUrl(url: String) {
        println(driver.currentUrl.substringBefore("&"))
        assertEquals(url, driver.currentUrl.substringBefore("&"))
    }


    @Step("Check text in username/ log in field")
    fun checkText(username: String, elementID: String) {
        val actualUser = driver.findElement(By.id(elementID)).text
        println(actualUser)
        assertEquals(username, actualUser)
    }
}