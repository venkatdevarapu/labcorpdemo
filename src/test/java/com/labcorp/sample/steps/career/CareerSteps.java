package com.labcorp.sample.steps.career;

import com.labcorp.sample.pages.CareerPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CareerSteps {

    @Autowired
    CareerPage careerPage;

    @When("I search for Careers in the career page")
    public void enterSearch()
    {
        careerPage.enterSearchCriteria();
    }

    @Then("the search results matching the search criteria must appear")
    public void validateSearch()
    {
        careerPage.verifyResultsDisplayed();
    }

    @When("I click on the matched Career")
    public void clickMatchedJob()
    {
        careerPage.clickSearchedJob();
    }

    @Then("the results page must match with the search criteria")
    public void validateJob()
    {
        careerPage.assertJobDetails();
    }

    @When("I click on the Apply Now button")
    public void clickApply()
    {
        careerPage.clickApplyButton();
    }

    @Then("the results must match with the previous page")
    public void verifyApplyResults()
    {
        careerPage.validateApplyResults();
    }
}
