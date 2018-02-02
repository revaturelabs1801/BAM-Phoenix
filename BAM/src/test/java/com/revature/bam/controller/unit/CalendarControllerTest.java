package com.revature.bam.controller.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.revature.bam.bean.Subtopic;
import com.revature.bam.controller.CalendarController;
import com.revature.bam.service.SubtopicService;


public class CalendarControllerTest {

	@Mock
	SubtopicService subtopicService;
	
	@InjectMocks
	CalendarController calendarController;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
		this.setMockMvc(MockMvcBuilders.standaloneSetup(CalendarController.class).build());
	}
	
	/**
	 * Testing of the Calendar Controller - getSubtopicsByBatchPagination()
	 * Checks to see if method returns an empty list and it's not null
	 * 
	 * @author Michael Garza
	 */
	@Test
	public void getSubtopicsByBatchPaginationTest(){
		MockHttpServletRequest req = new MockHttpServletRequest();
		req.addParameter("batchId", "5");
		req.addParameter("pageNumber", "0");
		req.addParameter("pageSize", "20");
		
		List<Subtopic> returnResults = (List<Subtopic>) calendarController.getSubtopicsByBatchPagination(req);
		assertNotNull(returnResults);
		assertEquals(returnResults, new ArrayList<Subtopic>());
	}
	
	/**
	 * Testing of the Calendar Controller - getNumberOfSubTopicsByBatch()
	 * Checks to see if method returns an empty list and it's not null
	 * 
	 * @author Michael Garza
	 */
	@Test
	public void getNumberOfSubTopicsByBatchTest(){
		MockHttpServletRequest req = new MockHttpServletRequest();
		req.addParameter("batchId", "5");
		
		Long returnValue = (Long) calendarController.getNumberOfSubTopicsByBatch(req).getBody();
		
		assertEquals(returnValue.longValue(), 0);
		assertNotNull(returnValue.longValue());
	}

	public MockMvc getMockMvc() {
		return mockMvc;
	}

	public void setMockMvc(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}
}
