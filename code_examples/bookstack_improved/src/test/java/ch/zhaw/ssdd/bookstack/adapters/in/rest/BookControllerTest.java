package ch.zhaw.ssdd.bookstack.adapters.in.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import ch.zhaw.ssdd.bookstack.adapters.out.persistance.BookRepository;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("inmemorytest")
@Transactional
class BookControllerTest {

        @Autowired
        MockMvc mockMvc;

        @Autowired
        BookRepository bookRepository;

        @Test
        void registerAndLoadBook() throws Exception {
                mockMvc.perform(post("/api/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                    {
                                                      "title": "Effective Java",
                                                      "isbn": "9780134685991"
                                                    }
                                                """))
                                .andExpect(status().isOk());

                mockMvc.perform(get("/api/books"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(1));

                mockMvc.perform(get("/api/books/9780134685991"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.title")
                                                .value("Effective Java"));
        }

        @Test
        void testExceptionHandling() throws Exception {
                mockMvc.perform(post("/api/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                    {
                                                      "title": "",
                                                      "isbn": "9780134685991"
                                                    }
                                                """))
                                .andExpect(status().is4xxClientError());
        }
}
