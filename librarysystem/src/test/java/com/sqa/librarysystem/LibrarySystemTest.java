package com.sqa.librarysystem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;

public class LibrarySystemTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        // Set the path to your WebDriver executable
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testUserLogin() {
        // Navigate to the login page
        driver.get("http://localhost:3000/login"); // Change port if necessary

        // Simulate user input for login
        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.sendKeys("librarianUsername"); // Replace with actual username

        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys("librarianPassword"); // Replace with actual password

        // Submit the form
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']")); // Adjust selector if needed
        loginButton.click();

        // Validate the login was successful
        String expectedTitle = "Library Dashboard"; // Adjust to your app's title
        assertEquals(expectedTitle, driver.getTitle());
    }

    @Test
    public void testAddBook() {
        // Log in first
        testUserLogin();

        // Navigate to the add book page
        driver.get("http://localhost:3000/addBook");

        // Simulate input for book details
        driver.findElement(By.name("title")).sendKeys("New Book Title");
        driver.findElement(By.name("author")).sendKeys("Author Name");
        driver.findElement(By.name("isbn")).sendKeys("123456789");

        // Submit the form
        driver.findElement(By.cssSelector("button[type='submit']")).click(); // Adjust selector if needed

        // Validate the book was added successfully
        String successMessage = driver.findElement(By.id("successMessage")).getText();
        assertEquals("Book added successfully!", successMessage); // Adjust message if necessary
    }

    @After
    public void tearDown() {
        // Close the browser
        driver.quit();
    }
}
