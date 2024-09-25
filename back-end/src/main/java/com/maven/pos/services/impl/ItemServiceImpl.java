package com.maven.pos.services.impl;

import com.maven.pos.entities.Item;
import com.maven.pos.repositories.ItemRepository;
import com.maven.pos.services.IItemService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class ItemServiceImpl implements IItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Value("${image.upload.dir}")
    private String imageUploadDir;

    @Override
    public Item addItem(Item item, MultipartFile file) throws IOException {

        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String filePath = Paths.get(imageUploadDir, uniqueFileName).toString();

        // Save the file to the specified directory
        Files.createDirectories(Paths.get(imageUploadDir)); // Ensure the directory exists
        file.transferTo(new File(filePath));

        item.setImageName(uniqueFileName);
        item.setDate(LocalDate.now());
        item.setTime(LocalTime.now());

        return itemRepository.save(item);
    }

    @Override
    public List<Item> getAllItems() {

        List<Item> items=new ArrayList<>();

        itemRepository.findAll().forEach(item -> {
             String imageName=item.getImageName();
             String path_dir = imageUploadDir + File.separator + imageName;
             try (FileInputStream imageStream = new FileInputStream(path_dir)) {
                 byte[] imageBytes = IOUtils.toByteArray(imageStream);
                 String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
                 item.setImageBase64(imageBase64);
             } catch (IOException e) {
                 e.printStackTrace();
             }

             items.add(item);
         });

        return items;

    }

    @Override
    public Item getItemById(Item item) {
        return itemRepository.findById(item.getItemId()).get();
    }


    @Override
    public Item deleteItem(Long itemId) {
        Optional<Item> itemOptional = itemRepository.findById(itemId);

        if (itemOptional.isPresent()) {
            Item item = itemOptional.get();
            itemRepository.deleteById(itemId);
            return item;
        } else {
            throw new RuntimeException("Item not found with id: " + itemId);
        }
    }


    @Override
    public Item updateItem(Item item,MultipartFile file) throws IOException {
        Item it=itemRepository.findById(item.getItemId()).get();

        if (!file.isEmpty()){
            deleteImage(it.getImageName());
        }
        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String filePath = Paths.get(imageUploadDir, uniqueFileName).toString();

        // Save the file to the specified directory
        Files.createDirectories(Paths.get(imageUploadDir)); // Ensure the directory exists
        file.transferTo(new File(filePath));

        it.setItemName(item.getItemName());
        it.setItemPrice(item.getItemPrice());
        it.setImageName(uniqueFileName);

        return itemRepository.save(it);
    }

    public void deleteImage(String imageName){
        Path imagePath = Paths.get(imageUploadDir).resolve(imageName);
        File file = imagePath.toFile();

        file.delete();
    }
}
