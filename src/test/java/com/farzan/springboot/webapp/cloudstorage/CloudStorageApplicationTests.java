package com.farzan.springboot.webapp.cloudstorage;

import com.farzan.springboot.webapp.cloudstorage.PageObjects.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {


	@LocalServerPort
	private int port;

	private String baseURL;
	private String firstName = "Farzan";
	private String lastName = "Farzani";
	private String username = "hyperuser";
	private String password = "T#ur^t8oG3Tfe60";
	private String noteTitle = "new test";
	private String noteDescription = "note content is going to be something really fun!";
	private String newNoteTitle = "new old test";
	private String newNoteDescription = "note contents can change from time to time!";
	private String url = "https://start.spring.io/";
	private String credUsername = "springnewbie";
	private String credPassword = "w5M1ZwDbb24m$hp";
	private String newUrl = "https://classroom.udacity.com/";
	private String newCredUsername = "springguru";
	private String newCredPassword = "tJOT59s16%CW32i";



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

	@Order(5)
	@Test
	public void getRandomPage() {
		driver.get("http://localhost:" + this.port + "/blahblahblah");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/*
	 2) Write a Selenium test that signs up a new user, logs that user in, verifies that they can access the home page,
	 	then logs out and verifies that the home page is no longer accessible.
	*/
	@Order(6)
	@Test
	public void userSignUpInOutProcess(){

		// first: sign up
		driver.get(baseURL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstName,lastName , username, password);

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

	@Order(7)
	@Test
	public void createNoteAndCheckIt(){
		WebDriverWait wait = new WebDriverWait(driver, 50);
		// first: sign in
		driver.get(baseURL + "/login");
		SigninPage signinPage = new SigninPage(driver);
		signinPage.login(username, password);
		Assertions.assertEquals("Home", driver.getTitle());

		// second: add a note
		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);
		homePage.clickOnNoteTab();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("newNote")));
		homePage.clickNewNote();

		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title")));

		homePage.createNote(noteTitle, noteDescription);
		Assertions.assertEquals("Result", driver.getTitle());
		Assertions.assertNotNull(driver.findElement(By.className("alert-success")));
		// we're now on /notes but the result.html is rendered (according to logic)

		// third: check newly created note
		// By clicking on home link on result, we'll automatically be taken to home page
		driver.get(baseURL + "/home");
		HomePage homePage1 = new HomePage(driver);
		// no need to clickOnNoteTab() as js localstorage will keep the current tab
		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab")));
		homePage1.checkNote(noteTitle);
	}

	/*
	 4) Write a Selenium test that logs in an existing user with existing notes, clicks the edit note button on an
	 	existing note, changes the note data, saves the changes, and verifies that the changes appear in the note list.
	*/

	@Order(8)
	@Test
	public void editNoteAndCheckIt(){
		WebDriverWait wait = new WebDriverWait(driver, 50);
		// first: sign in
		driver.get(baseURL + "/login");
		SigninPage signinPage = new SigninPage(driver);
		signinPage.login(username, password);
		Assertions.assertEquals("Home", driver.getTitle());

		// second: update a note
		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);
		homePage.clickOnNoteTab();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("edit-note")));
		// controller is triggered by edit button but the logic for update is the same in service
		homePage.clickEditNote();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title")));
		homePage.clearNotenput();
		homePage.createNote(newNoteTitle, newNoteDescription);
		Assertions.assertEquals("Result", driver.getTitle());
		Assertions.assertNotNull(driver.findElement(By.className("alert-success")));

		// third: check newly updated note
		driver.get(baseURL + "/home");
		HomePage homePage1 = new HomePage(driver);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab")));
		homePage1.checkNote(newNoteTitle);
	}

	/*
	 5) Write a Selenium test that logs in an existing user with existing notes, clicks the delete note button on an
	 	existing note, and verifies that the note no longer appears in the note list.
	*/

	@Order(9)
	@Test
	public void deleteNoteAndCheckIt(){
		WebDriverWait wait = new WebDriverWait(driver, 50);
		// first: sign in
		driver.get(baseURL + "/login");
		SigninPage signinPage = new SigninPage(driver);
		signinPage.login(username, password);
		Assertions.assertEquals("Home", driver.getTitle());

		// second: delete a note
		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);
		homePage.clickOnNoteTab();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("delete-note")));
		homePage.clickDeleteNote();
		Assertions.assertEquals("Result", driver.getTitle());
		Assertions.assertNotNull(driver.findElement(By.className("alert-success")));

		// third: check newly updated note
		driver.get(baseURL + "/home");
		HomePage homePage1 = new HomePage(driver);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab")));
		homePage1.checkDeleteNote(newNoteTitle);
	}

	/*
	 6) Write a Selenium test that logs in an existing user, creates a credential and verifies that the credential
	 	details are visible in the credential list.
	*/

	@Order(10)
	@Test
	public void createCredentialsAndCheckThem(){
		WebDriverWait wait = new WebDriverWait(driver, 50);
		// first: sign in
		driver.get(baseURL + "/login");
		SigninPage signinPage = new SigninPage(driver);
		signinPage.login(username, password);
		Assertions.assertEquals("Home", driver.getTitle());

		// second: create credentials
		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);
		homePage.clickOnCredTab();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("newCred")));
		homePage.clickNewCredentials();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url")));
		homePage.saveCredentials(url, credUsername, credPassword);
		Assertions.assertEquals("Result", driver.getTitle());
		Assertions.assertNotNull(driver.findElement(By.className("alert-success")));

		// third: check newly created credentials
		driver.get(baseURL + "/home");
		HomePage homePage1 = new HomePage(driver);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab")));
		homePage1.checkCredentials(credUsername);
	}

	/*
	 7) Write a Selenium test that logs in an existing user with existing credentials, clicks the edit credential
	 	button on an existing credential, changes the credential data, saves the changes, and verifies that the changes
	 	appear in the credential list.
	*/
	@Order(11)
	@Test
	public void editCredentialsAndCheckThem(){
		WebDriverWait wait = new WebDriverWait(driver, 50);
		// first: sign in
		driver.get(baseURL + "/login");
		SigninPage signinPage = new SigninPage(driver);
		signinPage.login(username, password);
		Assertions.assertEquals("Home", driver.getTitle());

		// second: update a note
		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);
		homePage.clickOnCredTab();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("edit-creds")));
		homePage.clickEditCreds();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url")));
		homePage.clearCredsInput();
		homePage.saveCredentials(newUrl, newCredUsername, newCredPassword);
		Assertions.assertEquals("Result", driver.getTitle());
		Assertions.assertNotNull(driver.findElement(By.className("alert-success")));

		// third: check newly updated note
		driver.get(baseURL + "/home");
		HomePage homePage1 = new HomePage(driver);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab")));
		homePage1.checkCredentials(newCredUsername);
	}

	/*
	 8) Write a Selenium test that logs in an existing user with existing credentials, clicks the delete credential
	 	button on an existing credential, and verifies that the credential no longer appears in the credential list.
	*/

	@Order(12)
	@Test
	public void deleteCredentialsAndCheckThem(){
		WebDriverWait wait = new WebDriverWait(driver, 50);
		// first: sign in
		driver.get(baseURL + "/login");
		SigninPage signinPage = new SigninPage(driver);
		signinPage.login(username, password);
		Assertions.assertEquals("Home", driver.getTitle());

		// second: delete a note
		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);
		homePage.clickOnCredTab();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("delete-creds")));
		homePage.clickDeleteCredentials();
		Assertions.assertEquals("Result", driver.getTitle());
		Assertions.assertNotNull(driver.findElement(By.className("alert-success")));

		// third: check newly updated note
		driver.get(baseURL + "/home");
		HomePage homePage1 = new HomePage(driver);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab")));
		homePage1.checkDeleteCreds(newCredUsername);
	}

}
