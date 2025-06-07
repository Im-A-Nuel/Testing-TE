import { Builder, By, until } from "selenium-webdriver";
import chrome from 'selenium-webdriver/chrome.js';
import { Given, When, Then, setDefaultTimeout, After } from "@cucumber/cucumber";
import { expect } from "chai";

setDefaultTimeout(30000);

let driver;

Given('the user is on the login page', async function () {
  let options = new chrome.Options();
  options.addArguments('--incognito');

  driver = new Builder().forBrowser('chrome').setChromeOptions(options).build();
  await driver.get('https://www.saucedemo.com/');
});

When('the user enters an invalid username and password', async function () {
  await driver.findElement(By.id('user-name')).sendKeys('invalid_user');
  await driver.findElement(By.id('password')).sendKeys('wrong_password');
});

When('the user enters a valid username and password', async function () {
  await driver.findElement(By.id('user-name')).sendKeys('standard_user');
  await driver.findElement(By.id('password')).sendKeys('secret_sauce');
});

When('the user clicks the login button', async function () {
  await driver.findElement(By.id('login-button')).click();
});

Then('the user should see a failed message', async function () {
  const err = await driver.wait(until.elementLocated(By.css('[data-test="error"]')), 5000).getText();
  expect(err.toLowerCase()).to.include('do not match');
});

Then('the user should see the home page', async function () {
  const title = await driver.wait(until.elementLocated(By.css('[data-test="title"]')), 5000).getText();
  expect(title).to.equal('Products');
});

Given('the user is on the item page', async function () {
  await driver.get('https://www.saucedemo.com/');
  await driver.findElement(By.id('user-name')).sendKeys('standard_user');
  await driver.findElement(By.id('password')).sendKeys('secret_sauce');
  await driver.findElement(By.id('login-button')).click();

  await driver.wait(until.elementLocated(By.css('[data-test="inventory-list"]')), 5000);
});

When('the user add item to the cart', async function () {
  await driver.findElement(By.css('button[data-test="add-to-cart-sauce-labs-backpack"]')).click();
});

When('the user in the item list', async function () {
  await driver.wait(until.elementLocated(By.css('[data-test="inventory-list"]')), 5000);
});

Then('item should be seen in the item page', async function () {
  const cartBadge = await driver.findElement(By.className('shopping_cart_badge')).getText();
  expect(cartBadge).to.equal('1');
});

When('the user remove item to the cart', async function () {
  await driver.findElement(By.css('button[data-test="remove-sauce-labs-backpack"]')).click();
});

Then("item shouldn't be seen in the item page", async function () {
  const badgeExists = await driver.findElements(By.className('shopping_cart_badge'));
  expect(badgeExists.length).to.equal(0);
});

Given('the user is on the cart page', async function () {

  await driver.get('https://www.saucedemo.com/');
  await driver.findElement(By.id('user-name')).sendKeys('standard_user');
  await driver.findElement(By.id('password')).sendKeys('secret_sauce');
  await driver.findElement(By.id('login-button')).click();


  await driver.findElement(By.css('a[data-test="shopping-cart-link"]')).click();
  await driver.wait(until.elementLocated(By.css('[data-test="title"]')), 5000);
  const title = await driver.findElement(By.css('[data-test="title"]')).getText();
  expect(title).to.equal('Your Cart');
});


When('the user tries to checkout', async function () {
  await driver.findElement(By.css('button[data-test="checkout"]')).click();
});


Then('the user should see a message "Cart is empty"', async function () {
  const cartItems = await driver.findElements(By.className('cart_item'));
  expect(cartItems.length).to.equal(0); 
});


When('the user clicks the logout button', async function () {
  await driver.findElement(By.id('react-burger-menu-btn')).click();
  const logoutBtn = await driver.wait(until.elementLocated(By.id('logout_sidebar_link')), 5000);
  await driver.wait(until.elementIsVisible(logoutBtn), 3000);
  await logoutBtn.click();
});

Then('the user should be redirected to the login page', async function () {
  const loginBtn = await driver.wait(until.elementLocated(By.id('login-button')), 7000);
  expect(loginBtn).to.exist;
});


After(async function () {
  if (driver) {
    await driver.quit();
  }
});
