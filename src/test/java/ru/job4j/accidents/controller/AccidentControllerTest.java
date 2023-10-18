package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class AccidentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccidentService accidentService;

    @Test
    @WithMockUser
    public void shouldReturnCreatingPage() throws Exception {
        this.mockMvc.perform(get("/accident/createAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accident/createAccident"));
    }

    @Test
    @WithMockUser
    public void whenRequestPageWithWrongIdShouldReturnErrorPage() throws Exception {
        when(accidentService.findById(0)).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/accident/0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("error/404"));
    }

    @Test
    @WithMockUser
    public void whenRequestPageWithCorrectIdShouldReturnErrorPage() throws Exception {
        Accident accident = new Accident(1, "name", "text", "address",
                new AccidentType(1, "accidentName"), Set.of(new Rule(1, "ruleName")));
        when(accidentService.findById(1)).thenReturn(Optional.of(accident));
        this.mockMvc.perform(get("/accident/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accident/editAccident"));
    }

    @Test
    @WithMockUser
    public void whenDeleteAccidentWithWrongIdShouldReturnErrorPage() throws Exception {
        when(accidentService.delete(0)).thenReturn(false);
        this.mockMvc.perform(get("/accident/delete/0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("error/404"));
    }

    @Test
    @WithMockUser
    public void whenDeleteAccidentWithCorrectIdShouldReturnErrorPage() throws Exception {
        when(accidentService.delete(1)).thenReturn(true);
        this.mockMvc.perform(get("/accident/delete/1"))
                .andDo(print())
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/index"));
    }

}