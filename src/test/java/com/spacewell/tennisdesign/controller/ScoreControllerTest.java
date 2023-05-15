package com.spacewell.tennisdesign.controller;


import com.spacewell.tennisdesign.exceptions.ExceptionAdvice;
import com.spacewell.tennisdesign.service.ScoringService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ScoreControllerTest {
    private MockMvc controllerMockMvc;

    @Mock
    private ScoringService scoringService;
    @InjectMocks
    private ScoreController controller;

    @Before
    public void setup() {
        controllerMockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new ExceptionAdvice())
                .setMessageConverters(
                        new MappingJackson2HttpMessageConverter(),
                        new Jaxb2RootElementHttpMessageConverter())
                .build();
    }

    @Test
    public void serviceActionName_mappingsWork() throws Exception {
        when(scoringService.getScore()).thenReturn("30:15");
        MvcResult result = controllerMockMvc
                .perform(
                        MockMvcRequestBuilders.get("/score")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        assertEquals("\"30:15\"", result.getResponse().getContentAsString());
    }
}