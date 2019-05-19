package com.sct.springcloud.control;

import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.sct.springcloud.dto.SctUserInfoDTO;
import com.sct.springcloud.service.SctUserInfoService;
import com.sct.springcloud.util.JsonUtil;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SctUserInfoControl.class})
@AutoConfigureMockMvc
@WebAppConfiguration
public class SctUserInfoControlTest {
	
	private Logger log = Logger.getLogger(getClass());
	private MockMvc mvc;
	private MockHttpSession session;
	@Autowired
	private WebApplicationContext context;
	@MockBean
	private SctUserInfoService sctUserInfoService;
	
	@Before
	public void setupMockMvc() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
		session = new MockHttpSession();
	}

	@SuppressWarnings("unchecked")
	@Test(timeout=7000)
	public void testList() {
		ResultActions resultActions;
		try {
			resultActions = mvc.perform(MockMvcRequestBuilders.get("/project/one/users/list/v1")
//					.param("", "")
					.accept(MediaType.APPLICATION_JSON_UTF8)
					.session(session));
			resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
			MvcResult mvcResult = resultActions.andReturn();
			List<SctUserInfoDTO> sctUserInfoDTOs = (List<SctUserInfoDTO>) mvcResult.getModelAndView().getModel().get("List<SctUserInfoDTO>");
			log.info(JsonUtil.mapToJson(sctUserInfoDTOs));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void testQueryById() {
		fail("Not yet implemented");
	}
	
	@Test(timeout=7000)
	public void testQueryUserByParam() {
		String param = "{}";
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/project/one/users/byParam/v1").content(param)
								.contentType(MediaType.APPLICATION_JSON_UTF8)
								.session(session);
		try {
			MvcResult mvcResult = mvc.perform(requestBuilder)
					.andExpect(MockMvcResultMatchers.status().isOk())
//					.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("1"))
					.andDo(MockMvcResultHandlers.print())
					.andReturn();
			String resultstr = mvcResult.getResponse().getContentAsString();
			Map<String, Object> returnMap = JsonUtil.jsonToMap(resultstr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testSendMail() {
		fail("Not yet implemented");
	}

}
