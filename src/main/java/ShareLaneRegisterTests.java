import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ShareLaneRegisterTests {

    public static final String URL1="https://www.sharelane.com/cgi-bin/register.py";
    public static final String URL2="https://www.sharelane.com/cgi-bin/register.py?page=1&zip_code=12345";

    @Test
    public void goToLogin() {
        open("https://www.sharelane.com");
        $(By.xpath("//*[text()='ENTER']")).click();
        String result =$(By.cssSelector("[value=Login]")).getAttribute("value");
        Assert.assertEquals (result,"Login");
    }

    @Test
    public void ZipCodeShouldBe5digits1() {
        open(URL1);
        //String URL="https://www.sharelane.com/cgi-bin/register.py?page=1&zip_code=12345"
        $(By.name("zip_code")).sendKeys("12345");
        $(By.cssSelector("[value=Continue]")).click();
        String result =$(By.cssSelector("[value=Register]")).getAttribute("value");
        Assert.assertEquals (result,"Register");
    }

    @Test
    public void ZipCodeShouldBe5digits2() {
        open(URL1);
        //String URL="https://www.sharelane.com/cgi-bin/register.py?page=1&zip_code=12345"
        $(By.name("zip_code")).sendKeys("1234");
        $(By.cssSelector("[value=Continue]")).click();
        String result =$(By.cssSelector(".error_message")).getText();
        Assert.assertEquals(result, "Oops, error on page. ZIP code should have 5 digits");
    }

    @Test
    public void RegistrationByValidData1() {
        open("https://www.sharelane.com/cgi-bin/register.py?page=1&zip_code=12345");
        //String URL="https://www.sharelane.com/cgi-bin/register.py?page=1&zip_code=12345"
        $(By.name("first_name")).sendKeys("Lena");
        $(By.name("email")).sendKeys("a@a.ru");
        $(By.name("password1")).sendKeys("1234");
        $(By.name("password2")).sendKeys("1234");
        $(By.cssSelector("[value=Register]")).click();
        String result =$(By.cssSelector(".confirmation_message")).getText();
        Assert.assertEquals (result,"Account is created!");

    }

    @Test
    public void RegistrationByInValidPassword() {
        open(URL2);
        $(By.name("first_name")).sendKeys("Lena");
        $(By.name("email")).sendKeys("a@a.ru");
        $(By.name("password1")).sendKeys("123");
        $(By.name("password2")).sendKeys("123");
        $(By.cssSelector("[value=Register]")).click();
        String result =$(By.cssSelector(".error_message")).getText();
        Assert.assertEquals (result,"Oops, error on page. Some of your fields have invalid data or email was previously used");

    }

    @Test
    public void PasswordsShouldBeMatch() {
        open(URL2);
        $(By.name("first_name")).sendKeys("Lena");
        $(By.name("email")).sendKeys("a@a.ru");
        $(By.name("password1")).sendKeys("1234");
        $(By.name("password2")).sendKeys("12345");
        $(By.cssSelector("[value=Register]")).click();
        String result =$(By.cssSelector(".error_message")).getText();
        Assert.assertEquals (result,"Oops, error on page. Some of your fields have invalid data or email was previously used");
    }

}
