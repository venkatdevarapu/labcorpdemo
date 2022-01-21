package com.labcorp.sample.pages;

import com.labcorp.sample.common.BasePage;
import com.labcorp.sample.common.Constants;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HomePage extends BasePage {

    private final By careerLink = By.xpath("//nav[@class='column labcorp']/ul/li[3]/a[contains(text(),'Careers')]");
    private final By cookieClose = By.cssSelector("div#onetrust-close-btn-container>a");

    @Value("${labcorp.url}")
    public String labCorpUrl;

    public void launchUrl()
    {
        try
        {
            getDriver().navigate().to(labCorpUrl);
            waitForElement(careerLink);
            getDriver().findElement(cookieClose).click();
            getDriver().manage().window().maximize();
        }catch (Exception e)
        {
            closeDriver(e);
        }
    }

    public void verifyCareerLink()
    {
        try
        {
            Assert.assertTrue(getDriver().findElement(careerLink).isDisplayed());
        }
        catch (Exception e){
            closeDriver(e);
        }
    }

    public void clickCareerLink()
    {
        try
        {
            getDriver().findElement(careerLink).click();
        }catch (Exception e){
            closeDriver(e);
        }
    }

    public void validateCareerPageOpened()
    {
        Assert.assertTrue("Career Tab got opened",waitForNewTab(Constants.careerTitle));
    }


}
