package com.labcorp.sample.steps.home;

import com.labcorp.sample.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HomeSteps {

    @Autowired
    HomePage homePage;

    @Given("I navigate to labcorp website")
    public void launchSite()
    {
        homePage.launchUrl();
    }

    @Then("the lapcorp site must appear with career link")
    public void careerLinkDisplay()
    {
        homePage.verifyCareerLink();
    }

    @When("I click on the Careers tab")
    public void clickCareer()
    {
        homePage.clickCareerLink();
    }

    @Then("the Careers page must open in a new tab")
    public void validateCareerPage()
    {
        homePage.validateCareerPageOpened();
    }


}
