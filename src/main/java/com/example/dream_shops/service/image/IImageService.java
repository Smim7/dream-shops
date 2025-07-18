package com.example.dream_shops.service.image;

import com.example.dream_shops.dto.ImageDto;
import com.example.dream_shops.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImage(Long id);
    List<ImageDto> saveImages( List<MultipartFile> files,Long productId);
    void updateImage(MultipartFile file,Long imageId);

}
