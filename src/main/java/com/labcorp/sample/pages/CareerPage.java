package com.labcorp.sample.pages;

import com.labcorp.sample.common.BasePage;
import net.serenitybdd.core.Serenity;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CareerPage extends BasePage {

    public static By acceptButton = By.id("gdpr-button");
    public static By searchInput = By.xpath("//input[contains(@id,'search-keyword')]");
    public static By searchCity = By.className("search-location");
    public static By searchButton = By.xpath("//button[contains(@id,'search-submit')]");
    public static By searchResultsCount = By.xpath("//section[@id='search-results-list']/ul/li");
    public static By searchResultsFound = By.xpath("//section[@id='search-results']/h1[contains(text(),'results found for ')]");
    public static By searchResult(int row) {
        return By.xpath("//section[@id='search-results-list']/ul/li["+row+"]/a");
    }
    public static By searchResultText(int row) {
        return By.xpath("//section[@id='search-results-list']/ul/li["+row+"]/a/h2");
    }
    public static By searchResultLoc(int row) {
        return By.xpath("//section[@id='search-results-list']/ul/li["+row+"]/a/span[@class='job-location']");
    }
    public static By ajaxStateOption(String state)
    {
        return By.xpath("//ul[contains(@id,'search-location')]/li/a[contains(text(),'"+state+"')]");
    }
    public static By ajaxjobOption(String jobDesc)
    {
        return By.xpath("//*[contains(text(),'"+jobDesc+"')]");
    }
    public static By nextButton = By.xpath("//div[@class='pagination-paging']/a[@class='next']");
    public static By applyButton = By.xpath("//a[@class='button job-apply top']");
    public static By jobHeading = By.className("job-description__heading");
    public static By jobLocationText = By.xpath("//span[@class='job-location job-info']");
    public static By jobIdText = By.xpath("//span[@class='job-id job-info']");
    public Map<String, String> textToCompare = new HashMap<>();
    public String firstText = "firsttext";
    public String secondText = "secondtext";
    public static By firstTextToCompare = By.xpath("//div[@class='ats-description']/p/span");
    public static By secondTextToCompare = By.xpath("//div[@class='ats-description']/ul/li[1]");
    public static By returnToSearch = By.xpath("//span[text()='Return to Job Search']");
    public static By iframe = By.id("ae-userStateStore");
    public static By createAccountClose = By.xpath("//button[@class='close closebutton ae-button']");
    public static By fadePopUp = By.xpath("    //div[@class='popover bottom si-popover fade in']");
    public static By jobTitleDesc = By.xpath("//span[@class='jobTitle job-detail-title']");
    public static By jobNumber = By.xpath("//span[@class='jobnum']");
    public static By searchText(String text){
        return By.xpath("//span[contains(text(),'"+text+"')]");
    }


    /**
     * Enters the search criteria, job description, city and ID are passed from the run configs
     */
    public void enterSearchCriteria()
    {
        try
        {
            getDriver().findElement(acceptButton).click();
            waitForElement(searchButton);
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", getDriver().findElement(searchInput));
            getDriver().findElement(searchInput).sendKeys(System.getProperty("jobDescription"));
            Thread.sleep(2000);
            getDriver().findElement(ajaxjobOption(System.getProperty("jobDescription"))).click();
            waitForElement(ajaxjobOption(System.getProperty("jobDescription")));
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", getDriver().findElement(searchCity));
            getDriver().findElement(searchCity).clear();
            getDriver().findElement(searchCity).sendKeys(System.getProperty("jobCity"));
            Thread.sleep(3000);
            waitForElement(ajaxStateOption(System.getProperty("jobCity")));
            getDriver().findElement(ajaxStateOption(System.getProperty("jobCity"))).click();
            getDriver().findElement(searchButton).click();
            waitForElement(searchResultsFound);
        }
        catch (Exception e){
            closeDriver(e);
        }
    }

    /**
     * Verifies if the search results are displayed
     */
    public void verifyResultsDisplayed()
    {
        Assert.assertTrue(getDriver().findElement(searchResultsFound).isDisplayed());
    }

    /**
     * Searches for the job in the search results and clicks on the matched record
     */
    public void clickSearchedJob()
    {
        boolean resultMatched = false;
        try
        {
            String searchResultsText = getDriver().findElement(searchResultsFound).getText();
            int searchResults = Integer.parseInt(searchResultsText.split(" results found for")[0]);
            int result = 1;
            for(int row = 1; row<=searchResults; row++)
            {
                if((StringUtils.equalsIgnoreCase(System.getProperty("jobDescription"),
                        getDriver().findElement(searchResultText(1)).getText()))
                && (StringUtils.contains(getDriver().findElement(searchResultLoc(1)).getText(),
                        System.getProperty("jobCity"))))
                {
                    ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);",getDriver().findElement(searchResult(row)));
                    Serenity.takeScreenshot();
                    getDriver().findElement(searchResult(row)).click();
                    resultMatched = true;
                    break;
                }
                getResult(searchResults, result, row);

            }
        }catch (Exception e){
            closeDriver(e);
        }
        if(resultMatched==false)
        {
            Assert.fail("The job matching the search criteria is not displayed");
        }
    }

    /**
     * Checks if the row size is not greater than search results, and result is a variable which verifies
     * for the number of records on the page and resets it once it reaches 15
     * @param searchResults
     * @param result
     * @param row
     */
    private void getResult(int searchResults, int result, int row) {
        if((row !=(searchResults -15)) && result ==15)
        {
            getDriver().findElement(nextButton).click();
            result = 1;
        }
        else
        {
            result = result +1;
        }
    }

    /**
     * Validates job search details
     */
    public void assertJobDetails()
    {
        Assert.assertTrue("Validating Job Description",StringUtils.equalsIgnoreCase(System.getProperty("jobDescription"),
                getDriver().findElement(jobHeading).getText()));
        Assert.assertTrue("Validating Job City",StringUtils.contains(getDriver().findElement(jobLocationText).getText(),
                System.getProperty("jobCity")));
        Assert.assertTrue("Validating Job Id",StringUtils.equalsIgnoreCase(System.getProperty("jobId"),
                getDriver().findElement(jobIdText).getText().replace("Job ID ","")));
        textToCompare.put(firstText,getDriver().findElement(firstTextToCompare).getText());
        textToCompare.put(secondText,getDriver().findElement(secondTextToCompare).getText());
    }

    /**
     * Click on the Apply Button
     */
    public void clickApplyButton()
    {
        try
        {
            getDriver().findElement(applyButton).click();
            waitTime(fadePopUp);
            Actions actions = new Actions(getDriver());
            waitTime(createAccountClose);
            actions.moveToElement(getDriver().findElement(createAccountClose)).click().build().perform();
        }
        catch (Exception e){closeDriver(e);}
    }

    public void validateApplyResults()
    {
        try
        {
            Assert.assertTrue("Validating Job Description",StringUtils.equalsIgnoreCase(System.getProperty("jobDescription"),
                    getDriver().findElement(jobTitleDesc).getText()));
            Assert.assertTrue("Validating Job Id",StringUtils.equalsIgnoreCase(System.getProperty("jobId"),
                    getDriver().findElement(jobNumber).getText().trim().replace("#","")));
            Assert.assertTrue("Validating matching text",getDriver().findElement(searchText(textToCompare.get(firstText))).isDisplayed());
            Assert.assertTrue("Validating matching text",getDriver().findElement(searchText(textToCompare.get(secondText))).isDisplayed());

        }
        catch (Exception e){
            closeDriver(e);
        }
    }


}
