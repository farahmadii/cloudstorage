package com.farzan.springboot.webapp.cloudstorage;

import com.farzan.springboot.webapp.cloudstorage.PageObjects.CredentialsTabPage;
import com.farzan.springboot.webapp.cloudstorage.PageObjects.HomePage;
import com.farzan.springboot.webapp.cloudstorage.PageObjects.SigninPage;
import com.farzan.springboot.webapp.cloudstorage.PageObjects.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {


	@LocalServerPort
	private int port;

	private String baseURL;

	// driver should be static cuz it is initiated in @BeforeAll method, when no object is yet instantiated.
	private static WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		baseURL = "http://localhost:" + port;
		// driver.get(baseURL + "/home");

	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	/*
	 1) Write a Selenium test that verifies that the home page is not accessible without logging in.
	 	In other words, only signup and login pages are accessible by unauthorized users.
	*/

	@Order(1)
	@Test
	public void getLoginPage() {
		driver.get(baseURL + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Order(2)
	@Test
	public void getSignupPage(){
		driver.get(baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Order(3)
	@Test
	public void getHomePageUnauthorized(){
		driver.get(baseURL + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Order(4)
	@Test
	public void getResultPageUnauthorized(){
		driver.get(baseURL + "/result");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/*
	 2) Write a Selenium test that signs up a new user, logs that user in, verifies that they can access the home page,
	 	then logs out and verifies that the home page is no longer accessible.
	*/
	@Order(5)
	@Test
	public void userSignUpInOutProcess(){

		// first: sign up
		String username = "hyperuser";
		String password = "T#ur^t8oG3Tfe60";
		driver.get(baseURL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Farzan", "Farzani", username, password);

		// then: sign in
		driver.get(baseURL + "/login");
		SigninPage signinPage = new SigninPage(driver);
		signinPage.login(username, password);
		Assertions.assertEquals("Home", driver.getTitle());

		// finally sign out
		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);
		homePage.signout();
		// verify if home page is still accessible after logging out
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/*
	 3) Write a Selenium test that logs in an existing user, creates a note and verifies that the note details are
	 	visible in the note list.
	*/

	@Order(6)
	@Test
	public void createNoteAndCheckIt(){
		String username = "hyperuser";
		String password = "T#ur^t8oG3Tfe60";
		// first: sign in
		driver.get(baseURL + "/login");
		SigninPage signinPage = new SigninPage(driver);
		signinPage.login(username, password);
		Assertions.assertEquals("Home", driver.getTitle());

		// second: add a note
		 WebDriverWait wait = new WebDriverWait(driver, 100);
		 wait.withTimeout(Duration.ofSeconds(50));
		String noteTitle = "new test";
		String noteDescription = "note content is going to be something really fun!";
		driver.get(baseURL + "home");
		HomePage homePage = new HomePage(driver);
		homePage.createNoteAndSeeIt(noteTitle, noteDescription);

	}

	/*
	 4) Write a Selenium test that logs in an existing user with existing notes, clicks the edit note button on an
	 	existing note, changes the note data, saves the changes, and verifies that the changes appear in the note list.
	*/

	/*
	 5) Write a Selenium test that logs in an existing user with existing notes, clicks the delete note button on an
	 	existing note, and verifies that the note no longer appears in the note list.
	*/

	/*
	 6) Write a Selenium test that logs in an existing user, creates a credential and verifies that the credential
	 	details are visible in the credential list.
	*/

	/*
	 7) Write a Selenium test that logs in an existing user with existing credentials, clicks the edit credential
	 	button on an existing credential, changes the credential data, saves the changes, and verifies that the changes
	 	appear in the credential list.
	*/

	/*
	 8) Write a Selenium test that logs in an existing user with existing credentials, clicks the delete credential
	 	button on an existing credential, and verifies that the credential no longer appears in the credential list.
	*/

}
