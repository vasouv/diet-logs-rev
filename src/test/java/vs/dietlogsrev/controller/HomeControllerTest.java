package vs.dietlogsrev.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = HomeController.class)
public class HomeControllerTest {

  @Autowired MockMvc mvc;

  @Test
  public void home() throws Exception {
    mvc.perform(get("/")).andExpect(status().isOk()).andExpect(content().string("Diet Logs Revisited"));
  }
}
