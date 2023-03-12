package bigth.myserver.web;

import bigth.myserver.service.StringCalculationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@Transactional
class StringCalculationControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    StringCalculationService stringCalculationService;

    @Test
    @WithAnonymousUser
    void stringCalculator() throws Exception {
        mockMvc.perform(get("/strings/calculators/length"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void calculate() throws Exception {
        // Given
        var str = "calculateString";
        var charsLength = 5;
        when(stringCalculationService.calculateCharsLength(any())).thenReturn(charsLength);
        var bytesLength = 10;
        when(stringCalculationService.calculateBytesLength(any())).thenReturn(bytesLength);
        // When
        mockMvc.perform(post("/strings/calculators/length")
                        .queryParam("str", str)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("str", str))
                .andExpect(MockMvcResultMatchers.model().attribute("charsLength", charsLength))
                .andExpect(MockMvcResultMatchers.model().attribute("bytesLength", bytesLength));
    }
}