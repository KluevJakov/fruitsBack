package ru.jafix.fruties.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ru.jafix.fruties.entities.Bouquet;
import ru.jafix.fruties.entities.Image;
import ru.jafix.fruties.entities.Ingredient;
import ru.jafix.fruties.entities.Order;
import ru.jafix.fruties.entities.dto.Request;
import ru.jafix.fruties.entities.dto.Response;
import ru.jafix.fruties.repositories.*;
import ru.jafix.fruties.services.FileService;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
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
    @Autowired
    protected BouquetRepository bouquetRepository;
    @Autowired
    protected OrderRepository orderRepository;
    @Autowired
    protected ImageRepository imageRepository;

    protected final String url = "http://127.0.0.1:7860/sdapi/v1/txt2img";
    protected final String tempStoragePath = "/home/loo9y/IdeaProjects/fruitsBack/storage/";

    @GetMapping("/")
    public ResponseEntity<?> get() {
        return ResponseEntity.ok().build();
    }


    @PostMapping(value = "/generate", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> generateImage(@RequestBody List<Ingredient> ingredients) {
        RestTemplate restTemplate = new RestTemplate();

        System.out.println(ingredients);

        StringBuilder sb = new StringBuilder();
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getCategory().getId().equals(UUID.fromString("a55225ed-4c65-46cb-b9d7-79a5d89235ce"))) {
                sb.append("[bouquet_with_sweets] ");
            }
            if (ingredient.getCategory().getId().equals(UUID.fromString("0c94587e-8654-4583-acdd-c5b51ae96f56"))) {
                sb.append("[bouquet_with_fruit] ");
            }
            if (ingredient.getCategory().getId().equals(UUID.fromString("ae0abfe9-c7d5-4959-9240-40b3d912b76d")) ||
                    ingredient.getCategory().getId().equals(UUID.fromString("55ac2221-ac1c-4d62-a47d-44f412a6364c"))) {
                sb.append("[bouquet_nuts_and_dried_fruits] ");
            }
            if (ingredient.getCategory().getId().equals(UUID.fromString("e88b0a37-f583-40a0-8efe-7d1422b96ec5"))) {
                sb.append("[bouquet_with_berries] ");
            }
        }

        for (Ingredient ingredient : ingredients) {
            sb.append(ingredient.getTranslate() + " and ");
        }

        Request request = new Request();
        request.setPrompt(sb.toString());
        request.setSteps(1); //TODO: УБРАТЬ ЭТУ СТРОКУ НА ПРОДЕ

        ResponseEntity<Response> responseEntity = restTemplate.postForEntity(url, request, Response.class); //запрос к нейронке
        Response response = responseEntity.getBody();

        byte[] imageBytes = Base64.getDecoder().decode(response.getImages()[0]);
        UUID uuid = UUID.randomUUID();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("filename", String.format("%s.png", uuid));
        //fileService.decodeAndSaveImage(response.getImages()[0], String.format("%s.png", uuid));
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @PostMapping(value = "/uploadImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            Image image = new Image();
            image.setData(file.getBytes());
            image.setMimeType(file.getContentType());
            UUID savedUUID = UUID.randomUUID();
            image.setUuid(savedUUID);

            fileService.saveImage(file.getBytes(), savedUUID+".png");
            imageRepository.save(image);

            return ResponseEntity.ok(image.getUuid());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving image");
        }
    }

    @PostMapping(value = "/order")
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        for (Bouquet bouquet : order.getBouquets()) {
            bouquet.setUuid(UUID.randomUUID());
            bouquetRepository.save(bouquet);
        }
        order.setId(UUID.randomUUID());
        orderRepository.save(order);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/order/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable UUID id) {
        orderRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/order/{id}")
    public ResponseEntity<?> approveOrder(@PathVariable UUID id) {
        Optional<Order> order = orderRepository.findById(id);

        if (order.isPresent()) {
            order.get().setApproved(true);
            orderRepository.save(order.get());
        }
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

    @GetMapping("/orders")
    public ResponseEntity<?> orders() {
        System.out.println("orders");
        return ResponseEntity.ok(orderRepository.findAll().stream()
                        .peek(e -> {
                            e.getBouquets().forEach(o -> o.setImg("http://localhost:8080/images/" + o.getImageUuid() + ".png"));
                        })
                .toList());
    }

    private final Path imageDirectory = Paths.get(tempStoragePath);

    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        Path imagePath = imageDirectory.resolve(filename);
        try {
            Resource resource = new UrlResource(imagePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG) // Change MediaType according to your image type
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}