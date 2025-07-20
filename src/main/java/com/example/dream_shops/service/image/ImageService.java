package com.example.dream_shops.service.image;

import com.example.dream_shops.dto.ImageDto;
import com.example.dream_shops.exception.ResourceNotFoundExceotion;
import com.example.dream_shops.model.Image;
import com.example.dream_shops.model.Product;
import com.example.dream_shops.repository.ImageRepository;
import com.example.dream_shops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

  private final ImageRepository imageRepository;
  private final IProductService productService;


    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundExceotion("No image found with id: "+id));
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,()->{
            throw new ResourceNotFoundExceotion("No image found with id: "+id);
        });

    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);

        List<ImageDto> savedImageDto=new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                {
                    Image image = new Image();
                    image.setFileName(file.getOriginalFilename());
                    image.setFileType(file.getContentType());
                    image.setImage(new SerialBlob((file.getBytes())));
                    image.setProduct(product);

                   String buildDownloadUrl="/api/v1/omages/image/download/";
                   String downloadUrL=buildDownloadUrl+image.getId();
                   image.setDownloadUrl(downloadUrL);
                   Image savedImage=imageRepository.save(image);

                   savedImage.setDownloadUrl(buildDownloadUrl+savedImage.getId());
                   imageRepository.save(savedImage);

                   ImageDto imageDto=new ImageDto();
                   imageDto.setImageId(savedImage.getId());
                   imageDto.setImageName(savedImage.getFileName());
                   imageDto.setDownLoadUrl(savedImage.getDownloadUrl());
                    savedImageDto.add(imageDto);
                }
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }

        }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image=getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob((file.getBytes())));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
