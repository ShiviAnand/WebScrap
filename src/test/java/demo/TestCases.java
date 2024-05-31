package demo;

import java.io.*;
import java.text.ParseException;
import java.util.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.time.Duration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases {
    ChromeDriver driver;
    SoftAssert ssert = new SoftAssert();
    WrapperClass wrp;

    public TestCases() {
        System.out.println("Constructor: TestCases");
        WebDriverManager.chromedriver().timeout(30).setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.scrapethissite.com/pages/");
    }

    @AfterTest
    public void endTest() {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    @Test
    public void testCase01() throws ParseException {
        System.out.println("Start Test case: testCase01");

        wrp = new WrapperClass(driver);
        wrp.clickOnElement(driver.findElement(By.partialLinkText("Hockey Teams:")));

        List<HashMap<String, List<Object>>> booksAuthorsMapsList = new ArrayList<>();
        HashMap<String, List<Object>> phpBooksAuthorsMap = new HashMap<>();

        int p = 1;
        while (p < 5) {

            List<WebElement> teamName = driver
                    .findElements(By.xpath("//table[@class='table']//tr[@class='team']//td[1]"));
            List<WebElement> teamYear = driver
                    .findElements(By.xpath("//table[@class='table']//tr[@class='team']//td[2]"));
            List<WebElement> teamPer = driver
                    .findElements(By.xpath("//table[@class='table']//tr[@class='team']//td[6]"));

            for (int i = 0; i < teamName.size(); i++) {
                long epoch = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse("01/01/1970 01:00:00")
                        .getTime();

                String per = teamPer.get(i).getText();
                double a = Double.parseDouble(per);

                if (a * 100 < 40) {
                    phpBooksAuthorsMap.put(teamName.get(i).getText(),
                            Arrays.asList(epoch, Integer.valueOf(teamYear.get(i).getText()), Math.round(a * 100)));
                }

            }
            wrp.scrollIntoView(driver.findElement(By.xpath("//a[@aria-label='Next']")));
            p++;
            wrp.clickOnElement(driver.findElement(By.xpath("//a[normalize-space()='" + p + "']")));
        }

        booksAuthorsMapsList.add(phpBooksAuthorsMap);

        // covert list to json
        ObjectMapper mapper = new ObjectMapper();
        try {
            String teamDeatil = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(booksAuthorsMapsList);
            System.out.println(teamDeatil);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String userDir = System.getProperty("user.dir");
        // Writing JSON on a file
        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(userDir + "\\src\\test\\resources\\JSONFromMap.json"), booksAuthorsMapsList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("end Test case: testCase01");
    }

    @Test
    public void testcase02() throws ParseException {

        System.out.println("Start Test case: testCase01");
  

        wrp = new WrapperClass(driver);
        wrp.clickOnElement(driver.findElement(By.partialLinkText("Oscar Winning Films:")));

        List<HashMap<String, List<Object>>> winnerList = new ArrayList<>();
        HashMap<String, List<Object>> winnerdetail = new HashMap<>();
       

        List<WebElement> years = driver.findElements(By.xpath("//div[@class='col-md-12 text-center']//a"));
        for(int p= 0;p<years.size();p++){
        years.get(p).click();
        WebDriverWait wait =  new WebDriverWait(driver, Duration.ofSeconds(40));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//div[@id='loading']"))));
  
        List<Object> ls = new ArrayList<>();
        for(int i=1;i<=5;i++){
    for(int j=1;j<=4;j++){
    boolean flag = false;
    try{
        
        if(j==4){
     
        if(driver.findElement(By.xpath("//tbody/tr["+i+"]//td[4]/i")).isDisplayed()){
           flag = true;
        }
        System.out.println(flag);
        ls.add(flag);
    }else{
       System.out.println(driver.findElement(By.xpath("//tbody/tr["+i+"]//td["+j+"]")).getText()); 
       ls.add(driver.findElement(By.xpath("//tbody/tr["+i+"]//td["+j+"]")).getText());
    }
    }catch(Exception e){
        flag = false;
        System.out.println(flag);
        ls.add(flag);
    }}
  
}
winnerdetail.put( years.get(p).getText(), ls);
        }
        winnerList.add(winnerdetail)  ;   

        ObjectMapper mapper = new ObjectMapper();
        try {
            String teamDeatil = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(winnerList);
            System.out.println(teamDeatil);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String userDir = System.getProperty("user.dir");
        // Writing JSON on a file
        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(userDir + "\\src\\test\\resources\\JSONTC2.json"), winnerList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("end Test case: testCase02");

     

        
     
}

}
