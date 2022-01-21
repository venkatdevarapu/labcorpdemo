package com.labcorp.sample.common;

import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class BasePage extends PageObject {

    public void closeDriver(Exception e)
    {
        e.printStackTrace();
        getDriver().quit();
    }

    public void waitTime(By element)
    {
        for(int i=0;i<1000;i++)
        {
            try
            {
                if(getDriver().findElement(element).isEnabled())
                {
                    break;
                }
            }
            catch (Exception e){}
        }
    }

    public void waitForElement(By element)
    {
        FluentWait fluentWait = new FluentWait(getDriver());
        fluentWait.withTimeout(100000, TimeUnit.MILLISECONDS);
        fluentWait.pollingEvery(250, TimeUnit.MILLISECONDS);
        fluentWait.ignoring(NoSuchElementException.class);
        fluentWait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    public boolean waitForNewTab(String windowTitle) {
        boolean windowFound = false;
        FluentWait fluentWait = new FluentWait(getDriver());
        fluentWait.withTimeout(5000, TimeUnit.MILLISECONDS);
        fluentWait.pollingEvery(250, TimeUnit.MILLISECONDS);
        Set<String> handle = getDriver().getWindowHandles();
        if(handle.size() > 1)
        {
            for(String windowHandle: handle)
            {
                if (getDriver().switchTo().window(windowHandle).getTitle().equals(windowTitle)) {
                    windowFound = true;
                    break;
                }
            }
        }
        return windowFound;
    }

    public void waitForJsToLoad()
    {
        WebDriverWait webDriverWait = new WebDriverWait(getDriver(),10);
        webDriverWait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }


}
