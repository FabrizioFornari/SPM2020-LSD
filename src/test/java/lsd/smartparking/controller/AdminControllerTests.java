package lsd.smartparking.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import lsd.smartparking.model.Municipality;

@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc
@ContextConfiguration
public class AdminControllerTests {
 
    @Autowired
	private MockMvc mockMvc;
 
    @Test
	void shouldLogin() throws Exception {
    	this.mockMvc.perform( get("/api/admin/login")
			.with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin"))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			//.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("You are logged in"));
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void shouldReturnMunicipalities() throws Exception {
		MvcResult result = this.mockMvc.perform( get("/api/admin/view/municipalities") )
			.andDo(print())
			.andExpect(status().isOk())
			.andReturn();

		String json = result.getResponse().getContentAsString();
		Type type = new TypeToken<ArrayList<Municipality>>(){}.getType();
		ArrayList<Municipality> allMunicipalities = new Gson().fromJson(json, type);
		System.out.println(allMunicipalities);
	}

}