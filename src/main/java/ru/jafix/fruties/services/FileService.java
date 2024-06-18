package ru.jafix.fruties.services;

import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.UUID;

import static ru.jafix.fruties.configuration.Constants.tempStoragePath;

@Service
public class FileService {

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

    public void saveImage(byte[] imageBytes, String outputPath) {
        try {
            try (OutputStream outputStream = new FileOutputStream(tempStoragePath+ outputPath)) {
                outputStream.write(imageBytes);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при декодировании и сохранении изображения: ");
        }
    }
}
