package Steps;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import java.io.File;
import java.io.IOException;
import java.sql.Driver;
import java.text.ParseException;
import java.text.SimpleDateFormat;
    import java.util.HashMap;
    import java.util.Map;
    import java.util.Date;


public class Loginstep {

    //Global Variable
    String tanggal_;
    Date tanggal = new Date();
    WebDriver driver;
    boolean ocrketemu = false;
    String title_pembanding = "";
    String isiNomorPonsel, isiPin;

    public void setOcrketemu() throws Throwable {
        while (!ocrketemu) {
            this.sayaMengisiNomorPonsel(isiNomorPonsel);
            this.sayaMengisiPin(isiPin);
            this.sayaMengisiCaptcha();
            this.sayaKlikLogin();
            this.berhasilLoginBeradaPadaHalamanUtamaBelanjaqu();
       }
    }

    public Loginstep() throws ParseException {

    }

    @Given("^Saya berada pada halaman belanjaqu$")
    public void sayaBeradaPadaHalamanBelanjaqu() {

        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        System.setProperty("webdriver.chrome.driver", "D:\\WORK\\JAVA\\Tesseract-automation-captcha-java-master\\chromedriver.exe");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://belanjaqu.co.id/");

        String title = driver.getTitle();
        System.out.println(title);
        Assert.assertEquals("Komunitas Belanja Indonesia | Belanjaqu", title);
    }

    @When("^Saya klik side bar$")
    public void sayaKlikSideBar() {
        WebElement button = driver.findElement(By.xpath("/html/body/app-root/div/div/belanjaqu-home/div[1]/belanjaqu-header/header/div/div/div[1]/button"));
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].click();", button);
    }

    @And("^Saya klik tombol login$")
    public void sayaKlikTombolLogin() {
        WebElement button = driver.findElement(By.xpath("/html/body/app-root/div/div/belanjaqu-home/div[1]/belanjaqu-menu/div[2]/div[3]/div/ul/li[1]/a"));
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].click();", button);
    }

    @Then("^Sistem menampilkan halaman login$")
    public void sistemMenampilkanHalamanLogin() {
        String title = driver.getTitle();
        title_pembanding = title;
        System.out.println(title);
        Assert.assertEquals("Login Email - Belanjaqu", title);
    }

    @Then("^Saya klik login dengan paytren$")
    public void sayaKlikLoginDenganPaytren() throws InterruptedException {
        Thread.sleep(1000);
        WebElement button = driver.findElement(By.xpath("/html/body/app-root/div/div/belanjaqu-login-email/div/div/form/div[2]/div[6]/div/div[1]/a"));
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].click();", button);
    }

    @And("^Saya mengisi nomor ponsel \"([^\"]*)\"$")
    public void sayaMengisiNomorPonsel(String nomorponsel) throws Throwable {
            isiNomorPonsel = nomorponsel;
//        try {
        driver.findElement(By.xpath("/html/body/app-root/div/div/belanjaqu-login-paytren/div[1]/div/form/div[2]/div[2]/input")).sendKeys(nomorponsel);
//        }
//            catch (PendingException exception) {
//            System.out.println(exception.getMessage());
//            System.out.println(exception.getCause());
//            System.out.println(exception.getStackTrace());
//            exception.printStackTrace();
//        }
    }

    @And("^Saya mengisi pin \"([^\"]*)\"$")
    public void sayaMengisiPin(String pin) throws PendingException {
        isiPin = pin;
        driver.findElement(By.xpath("/html/body/app-root/div/div/belanjaqu-login-paytren/div[1]/div/form/div[2]/div[3]/div/input")).sendKeys(pin);
    }

    @And("^Saya mengisi captcha$")
    public void sayaMengisiCaptcha() throws Throwable {
        SimpleDateFormat tgl = new SimpleDateFormat("ddMMHHmmss");
        tanggal_ = tgl.format(tanggal);

        Thread.sleep(3000);
        File src = driver.findElement(By.xpath("/html/body/app-root/div/div/belanjaqu-login-paytren/div[1]/div/form/div[2]/div[4]/div[1]/img")).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + ("/screenshoot/captcha") + tanggal_ + ".png";
        System.out.println(tanggal_);

        FileHandler.copy(src, new File(path));
        ITesseract image = new Tesseract();
        image.setTessVariable("300", "300");
        image.setDatapath("D:\\WORK\\JAVA\\Tesseract-automation-captcha-java-master\\tessdata");
        image.setLanguage("eng");

        Thread.sleep(2000);
        String imageText = image.doOCR(new File(path));
        String fiximageText = imageText.replaceAll("[^a-z0-g]", "");
        System.out.println("captcha OCR = " + fiximageText);

        if (fiximageText.length()==6){
            driver.findElement(By.xpath("/html/body/app-root/div/div/belanjaqu-login-paytren/div[1]/div/form/div[2]/div[4]/div[2]/input")).sendKeys(fiximageText);
        } else {
            driver.navigate().refresh();
            setOcrketemu();
        }

    }
        @Then("^Saya Klik Login$")
        public void sayaKlikLogin () throws InterruptedException {
        WebElement button = driver.findElement(By.xpath("//button[contains(text(),'LOGIN')]"));
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        Thread.sleep(1000);
        js.executeScript("arguments[0].click();", button);

    }
        @Then("^Berhasil login berada pada halaman utama belanjaqu$")
        public void berhasilLoginBeradaPadaHalamanUtamaBelanjaqu () throws Throwable {
        Thread.sleep(5000);
        //halo
            //testaksjdakl
        System.out.println(driver.getTitle());

        if (driver.getTitle().equals("Akun Dashboard - Belanjaqu")) {
            Assert.assertEquals("Akun Dashboard - Belanjaqu", driver.getTitle());
            System.out.println("etdah");
                ocrketemu = true;
            System.out.println(ocrketemu);
            Thread.sleep(10000);
            driver.close();
        } else {
            driver.navigate().refresh();
            setOcrketemu();
        }



    }


}
