package com.Library.Mangement.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.Library.Mangement.Entity.Book;

import reactor.core.publisher.Mono;





@Service
public class AiService {

	@Value("${OpenAi.API.Url}")
	private String OpenAiUrl;
	   
	@Value("${OpenAi.API.Key}")
	private String OpenAiKey;
	
	public Mono<String> generateAIInsight(Optional<Book> book) {
	
		Book actualBook = book.get();
	    String bookContent = "Title: " + actualBook.getTitle() + 
	                         ", Author: " + actualBook.getAuthor() + 
	                         ", Description: " + actualBook.getDescription();
		
		Map<String, Object> requestBody = Map.of(
                "model", "gpt-4o-mini",
                "messages", List.of(
                        Map.of("role", "user", "content", bookContent)
                )
        );
	
		
		return WebClient.builder()
		        .baseUrl(OpenAiUrl)
		        .defaultHeader("Authorization", "Bearer " + OpenAiKey)
		        .defaultHeader("Content-Type", "application/json")
		        .build()
		        .post()
		        .uri("/completions")  
		        .contentType(MediaType.APPLICATION_JSON)
		        .bodyValue(requestBody)
		        .retrieve()
		        .bodyToMono(String.class) 
		        .onErrorResume(e -> Mono.just("Error: " + e.getMessage()));
	
	
	}
}
