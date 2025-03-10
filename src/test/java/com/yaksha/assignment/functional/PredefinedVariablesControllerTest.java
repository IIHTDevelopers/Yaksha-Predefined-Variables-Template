package com.yaksha.assignment.functional;

import static com.yaksha.assignment.utils.TestUtils.businessTestFile;
import static com.yaksha.assignment.utils.TestUtils.currentTest;
import static com.yaksha.assignment.utils.TestUtils.testReport;
import static com.yaksha.assignment.utils.TestUtils.yakshaAssert;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import com.yaksha.assignment.controller.PredefinedVariablesController;
import com.yaksha.assignment.utils.CustomParser;

public class PredefinedVariablesControllerTest {

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testShowSessionInfoPageAsIndex() throws Exception {
		// Mock HttpServletRequest and HttpSession using Mockito
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpSession session = mock(HttpSession.class);
		Model model = mock(Model.class);

		// Setup mock behavior
		when(request.getRequestURI()).thenReturn("/sessionInfo");
		when(request.getMethod()).thenReturn("GET");
		when(session.getId()).thenReturn("12345");
		when(session.getCreationTime()).thenReturn(System.currentTimeMillis());

		// Create an instance of the controller
		PredefinedVariablesController controller = new PredefinedVariablesController();

		// Call the method with mocked request, session, and model
		String viewName = controller.showSessionInfo(request, session, model);

		// Verify the view name is "sessionInfo"
		boolean isSessionInfoViewReturned = "sessionInfo".equals(viewName);

		// Verify the model contains the required attributes
		verify(model).addAttribute("requestInfo",
				"Request URI: /sessionInfo<br>Request Method: GET<br>Request Parameter (name): null");
		verify(model).addAttribute("sessionInfo",
				"Session ID: 12345<br>Session Creation Time: " + session.getCreationTime());
		verify(model).addAttribute("appInfo", "Application Info: Java Version - " + System.getProperty("java.version"));

		// Auto-grading with yakshaAssert
		yakshaAssert(currentTest(), isSessionInfoViewReturned, businessTestFile);
	}

	@Test
	public void testJspTagsAndHtmlTagClosureInIndexJsp() throws Exception {
		String filePath = "src/main/webapp/index.jsp";

		// Check for form submission and input elements in index.jsp
		boolean hasFormTag = CustomParser.checkJspTagPresence(filePath, "<form");
		boolean hasInputTags = CustomParser.checkJspTagPresence(filePath, "<input");

		// Run auto-grading using yakshaAssert
		yakshaAssert(currentTest(), hasFormTag && hasInputTags, businessTestFile);
	}

	@Test
	public void testJspTagsAndHtmlTagClosureInSessionInfoJsp() throws Exception {
		String filePath = "src/main/webapp/WEB-INF/views/sessionInfo.jsp";

		// Ensure that the sessionInfo.jsp page includes predefined variables and
		// properly closes HTML tags
		boolean hasRequestInfoTag = CustomParser.checkJspTagPresence(filePath, "${requestInfo}");
		boolean hasSessionInfoTag = CustomParser.checkJspTagPresence(filePath, "${sessionInfo}");
		boolean hasAppInfoTag = CustomParser.checkJspTagPresence(filePath, "${appInfo}");

		// Run auto-grading using yakshaAssert
		yakshaAssert(currentTest(),
				hasRequestInfoTag && hasSessionInfoTag && hasAppInfoTag,
				businessTestFile);
	}

	@Test
	public void testPredefinedVariablesControllerWithCorrectModel() throws Exception {
		// Create mock Model
		Model model = mock(Model.class);

		// Mock HttpServletRequest and HttpSession
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpSession session = mock(HttpSession.class);

		// Setup mock behavior
		when(request.getRequestURI()).thenReturn("/sessionInfo");
		when(request.getMethod()).thenReturn("GET");
		when(session.getId()).thenReturn("12345");
		when(session.getCreationTime()).thenReturn(System.currentTimeMillis());

		// Create the controller instance
		PredefinedVariablesController controller = new PredefinedVariablesController();

		// Call the method with mocked request, session, and model
		String viewName = controller.showSessionInfo(request, session, model);

		// Check if the correct view is returned
		boolean isSessionInfoViewReturned = "sessionInfo".equals(viewName);

		// Verify model contains required attributes
		verify(model).addAttribute("requestInfo",
				"Request URI: /sessionInfo<br>Request Method: GET<br>Request Parameter (name): null");
		verify(model).addAttribute("sessionInfo",
				"Session ID: 12345<br>Session Creation Time: " + session.getCreationTime());
		verify(model).addAttribute("appInfo", "Application Info: Java Version - " + System.getProperty("java.version"));

		// Auto-grading with yakshaAssert
		yakshaAssert(currentTest(), isSessionInfoViewReturned, businessTestFile);
	}
}
