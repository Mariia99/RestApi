package com.opencart.pages.search;

import com.opencart.pages.AbstractPageWithHeader;
import com.opencart.pages.AlertComponent;
import com.opencart.pages.ProductContainersComponent;
import com.opencart.pages.cart.CartPage;
import com.opencart.pages.comparison.ComparisonPage;
import com.opencart.pages.product.ProductPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

public class SearchPage extends AbstractPageWithHeader {

    //Errors
    public final String PRODUCT_NOT_FOUND = "PRODUCT NOT FOUND";
    //Selectors
    private final String PRODUCT_COMPONENT_LOCATOR = "//div[@class='product-thumb']";// xpath
    private final String SUCCESS_SEARCH_PAGE_URL = "search&search";// url
    private final String ALERT_LOCATOR = "//div[@class='alert alert-success alert-dismissible']";// xpath
    private final String SEARCH_CRITERIA_ELEMENT_LOCATOR = "//div[@id='content']";// xpath
    private final String PRODUCT_DISPLAY_ELEMENT_LOCATOR = "//div[@id='content']/p/following-sibling::div[1]"; //xpath

    //WebElements
    @FindBy(how = How.XPATH, xpath = "//p[contains(text(),'no product')]")
    private WebElement emptyResultMessage;

    //Components
    private List<ProductContainersComponent> productContainersComponents;

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    ///region ATOMIC_OPERATIONS

    //emptyResultMessage
    public String  getemptyResultMessageText(){
        return emptyResultMessage.getText();
    }

    //productContainersComponents
    public List<ProductContainersComponent> getProductContainersComponents() {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 3);
        productContainersComponents = new ArrayList<>();
        for (WebElement current : wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(PRODUCT_COMPONENT_LOCATOR)))) {
            productContainersComponents.add(new ProductContainersComponent(current));
        }
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        /*productContainersComponents = new ArrayList<>();
        for (WebElement current : driver.findElements(By.xpath(PRODUCT_COMPONENT_LOCATOR)))
            productContainersComponents.add(new ProductContainersComponent(current)); //Valera*/

        return productContainersComponents;
    }

    public ProductDisplayCriteriaComponent getProductDisplayCriteriaComponent() {
        return new ProductDisplayCriteriaComponent(driver.findElement(By.xpath(PRODUCT_DISPLAY_ELEMENT_LOCATOR)));
    }

    public SearchCriteriaComponent getSearchCriteriaComponent() {
        return new SearchCriteriaComponent(driver.findElement(By.xpath(SEARCH_CRITERIA_ELEMENT_LOCATOR)));
    }

    //alertComponent
    public AlertComponent getAlertComponentWithWait() {
        try {
            Thread.sleep(3000); //Only for presentation, bug alert
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
//        WebDriverWait wait = new WebDriverWait(driver, 5);
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ALERT_LOCATOR)));
//        searchPageAlertComponent = new SearchPageAlertComponent(wait.until(ExpectedConditions.presenceOfElementLocated((By.cssSelector(ALERT_LOCATOR)))));
//        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        //return searchPageAlertComponent;
        return new AlertComponent(driver.findElement(By.xpath(ALERT_LOCATOR)));
    }

    public AlertComponent getAlertComponentWithoutWait() {
        return new AlertComponent(driver.findElement(By.xpath(ALERT_LOCATOR)));
    }

    //productContainerComponents
    public Integer getProductContainerComponentsSize() {
        return productContainersComponents.size();
    }

    ///endregion

    ///region FUNCTIONAL

    /**
     * Find product in productContainersComponent by name.
     *
     * @param productName Product name.
     * @return The Object of ProductContainersComponent.
     */
    public ProductContainersComponent getProductComponentByName(String productName) {
        ProductContainersComponent result = null;
        for (ProductContainersComponent current : productContainersComponents) {
            if (current.getNameText().equalsIgnoreCase(productName)) {
                result = current;
                break;
            }
        }
        return result;
    }

    public ArrayList<String> getProductComponentNamesList() {
        ArrayList<String> ProductComponentNamesList = new ArrayList<>();
        for (ProductContainersComponent current : productContainersComponents) {
            ProductComponentNamesList.add(current.getNameText());
        }
        return ProductComponentNamesList;
    }

    //CompareButton

    /**
     * Add product to cart by button.
     *
     * @param productName Product name.
     */
    public void clickOnProductComponentAddToCartButtonByName(String productName) {
        getProductComponentByName(productName).clickAddToCartButton();
    }

    /**
     * Add product to comparison by button
     *
     * @param productName Product name.
     * @return Returning new Comparison Page.
     */
    public SearchPage clickProductComponentCompareButtonByName(String productName) {
        getProductComponentByName(productName).clickCompareButton();
        return new SearchPage(driver);
    }

    /**
     * This method adding two same product to comparison
     */
    public SearchPage AddTwoSameProductToComparison(String productName) {
        clickProductComponentCompareButtonByName(productName);
        clickProductComponentCompareButtonByName(productName);
        return this;
    }

    ///endregion

    ///region LOGIC

    /**
     * This method clicks on product comparison link from alert message
     *
     * @return Returns new Comparison page
     */
    public ComparisonPage clickProductComparisonLink() {
        getAlertComponentWithWait().clickOnCompareLink();
        return new ComparisonPage(driver);
    }

    /**
     * Return SearchPage or ProductPage depending on options of product.
     *
     * @param productName Product name.
     * @return SearchPage or ProductPage.
     */
    public AbstractPageWithHeader afterClickOnProductComponentAddToCartButtonByName(String productName) {
        clickOnProductComponentAddToCartButtonByName(productName);
        //TODO
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (driver.getCurrentUrl().contains(SUCCESS_SEARCH_PAGE_URL)) return returnSearchPage();
        else return returnProductPage();
    }

    //returnSearchPage
    private SearchPage returnSearchPage() {
        return new SearchPage(driver);
    }

    //returnProductPage
    private ProductPage returnProductPage() {
        return new ProductPage(driver);
    }

    public void toLowerCaseProductList(List<String> list) {
        ListIterator<String> iterator = list.listIterator();
        while (iterator.hasNext()) {
            iterator.set(iterator.next().toLowerCase());
        }
    }

    //alert after add to cart
    public CartPage goToShoppingCartFromAlert() {
        getAlertComponentWithoutWait().clickOnCartLink();
        return new CartPage(driver);
    }

    public ProductPage goToProductPageFromAlert() {
        getAlertComponentWithoutWait().clickOnProductLink();
        return new ProductPage(driver);
    }

    //add to cart by button
    public void clickProductComponentAddToCartButtonByName(String productName) {
        getProductComponentByName(productName).clickAddToCartButton();
    }

    //add to Wish List by button
    public void clickProductComponentAddToWishList(String productName) {
        getProductComponentByName(productName).clickAddToWishListButton();
        //InitializeAlert();
    }

    ///endregion
}