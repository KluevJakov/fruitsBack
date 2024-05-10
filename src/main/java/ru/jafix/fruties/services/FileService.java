package ru.jafix.fruties.services;

import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

@Service
public class FileService {

    protected final String tempStoragePath = "C:\\Users\\Aorus\\Downloads\\fruties\\fruties\\storage\\";

    public void decodeAndSaveImage(String base64ImageData, String outputPath) {
        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64ImageData);

            try (OutputStream outputStream = new FileOutputStream(tempStoragePath+ outputPath)) {
                outputStream.write(imageBytes);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при декодировании и сохранении изображения: ");
        }
    }
}
