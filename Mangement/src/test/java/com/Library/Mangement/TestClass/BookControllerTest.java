package com.Library.Mangement.TestClass;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.Library.Mangement.Entity.Book;
import com.Library.Mangement.Exception.BookNotFoundException;
import com.Library.Mangement.Service.AiService;
import com.Library.Mangement.Service.BookService;

import reactor.core.publisher.Mono;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;  // Mocking book service

    @MockBean
    private AiService aiService;  // Mocking AI service

    private Book sampleBook;

    @BeforeEach
    void setUp() {
        sampleBook = new Book();
        sampleBook.setId(1L);
        sampleBook.setTitle("Spring Boot Testing");
        sampleBook.setAuthor("John Doe");
        sampleBook.setDescription("est Description");
        sampleBook.setPublicationYear(2022);
    }

    @Test
    void testCreateBook_Success() throws Exception {
        when(bookService.createBook(any(Book.class))).thenReturn(sampleBook);

        String bookJson = """
            {
                "title": "Spring Boot Testing",
                "author": "John Doe",
                "isbn": "9823871",
                "publicationYear": 2022,
                "description": "Test Description"
            }
        """;

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Spring Boot Testing"));
    }

    @Test
    void testCreateBook_InvalidData() throws Exception {
        String invalidBookJson = """
            {
                "title": "",
                "author": "John Doe",
                "isbn": "9823871",
                "publicationYear": 2022,
                "description": "Test Description"
            }
        """;

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidBookJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Title can't be blank"));
    }

    @Test
    void testGetAllBooks_Success() throws Exception {
        when(bookService.getAllBooks()).thenReturn(Arrays.asList(sampleBook));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Spring Boot Testing"));
    }

    @Test
    void testGetBookById_Success() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.of(sampleBook));

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Spring Boot Testing"));
    }

    @Test
    void testGetBookById_NotFound() throws Exception {
    	 assertThatThrownBy(() ->  mockMvc.perform(get("/books/99"))).isInstanceOf(Exception.class);

    }


    @Test
    void testDeleteBook_Success() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.of(sampleBook));

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testSearchBooks_Success() throws Exception {
        when(bookService.searchBooks("Spring Boot Testing", null))
                .thenReturn(Arrays.asList(sampleBook));

        mockMvc.perform(get("/books/search?title=Spring Boot Testing"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Spring Boot Testing"));
    }

    @Test
    void testSearchBooks_NotFound() throws Exception {
       assertThatThrownBy(() ->  mockMvc.perform(get("/books/search?title=Unknown"))).isInstanceOf(Exception.class);
       
      
    }

    @Test
    void testGetAIInsights_Success() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.of(sampleBook));
        when(aiService.generateAIInsight(any())).thenReturn(Mono.just("AI Generated Insights"));

        mockMvc.perform(get("/books/1/ai-insights"))
                .andExpect(status().isOk())
                .andExpect(content().string("AI Generated Insights"));
    }

    @Test
    void testGetAIInsights_NotFound() throws Exception {
        when(bookService.getBookById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/books/99/ai-insights"))
                .andExpect(status().isNotFound());
    }
}
