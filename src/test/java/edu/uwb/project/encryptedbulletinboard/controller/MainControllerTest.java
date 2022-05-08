package edu.uwb.project.encryptedbulletinboard.controller;

import edu.uwb.project.encryptedbulletinboard.service.BoardService;
import edu.uwb.project.encryptedbulletinboard.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MainController.class)
class MainControllerTest {

    @MockBean
    private BoardService board;

    @MockBean
    private UserService user;


    @Autowired
    private MockMvc mvc;


    @Test
    void getIndexPage() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/");
        mvc.perform(request).andExpect(status().isOk()).andDo(print());

    }

    @Test
    void login() {
    }

    @Test
    void getRegisterPage() {
    }

    @Test
    void register() {
    }

    @Test
    void getSingOutPage() {
    }

    @Test
    void getCreateBoardPage() {
    }

    @Test
    void createNewBoard() {
    }

    @Test
    void getJoinBoardPage() {
    }

    @Test
    void joinBoard() {
    }

    @Test
    void getExitBoardPage() {
    }

    @Test
    void exitBoard() {
    }

    @Test
    void viewBoardPage() {
    }
}