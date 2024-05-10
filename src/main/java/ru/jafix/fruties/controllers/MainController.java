package ru.jafix.fruties.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.jafix.fruties.entities.dto.Request;
import ru.jafix.fruties.entities.dto.Response;
import ru.jafix.fruties.repositories.CategoryRepository;
import ru.jafix.fruties.repositories.IngredientRepository;
import ru.jafix.fruties.services.FileService;

import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class MainController {

    @Autowired
    protected FileService fileService;
    @Autowired
    protected CategoryRepository categoryRepository;
    @Autowired
    protected IngredientRepository ingredientRepository;

    protected final String url = "http://127.0.0.1:7860/sdapi/v1/txt2img";

    @GetMapping("/")
    public ResponseEntity<?> get() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generateImage(@RequestBody Request prompt) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Response> responseEntity = restTemplate.postForEntity(url, prompt, Response.class); //запрос к нейронке
        Response response = responseEntity.getBody();

        UUID uuid = UUID.randomUUID();

        fileService.decodeAndSaveImage(response.getImages()[0], String.format("%s.png", uuid));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/categories")
    public ResponseEntity<?> categories() {
        System.out.println("categories");
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @GetMapping("/ingredients/{id}")
    public ResponseEntity<?> ingredients(@PathVariable("id") UUID id) {
        System.out.println("ingredients");
        return ResponseEntity.ok(ingredientRepository.findByCategory_Id(id));
    }
}