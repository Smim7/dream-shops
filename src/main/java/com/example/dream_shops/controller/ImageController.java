package com.example.dream_shops.controller;

import com.example.dream_shops.dto.ImageDto;
import com.example.dream_shops.exception.ResourceNotFoundExceotion;
import com.example.dream_shops.model.Image;
import com.example.dream_shops.response.ApiResponse;
import com.example.dream_shops.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/images")
@RequiredArgsConstructor
public class ImageController {
    private final IImageService  imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImage(@RequestParam List <MultipartFile>file,
                                                @RequestParam Long productId){
        try{
        List<ImageDto> imageDtos=imageService.saveImages(file,productId);
        return ResponseEntity.ok(new ApiResponse("Upload success!",imageDtos));}
        catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("upload fail",e.getMessage()));
        }
    }



        @GetMapping("/image/download/{imageId}")
        public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
            Image image = imageService.getImageById(imageId);
            ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
            return  ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +image.getFileName() + "\"")
                    .body(resource);
    }


@PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> uploadImage(@PathVariable Long imageId,
                                                   @RequestBody MultipartFile file){
        try {
            Image image = imageService.getImageById(imageId);
            if(image !=null){
                imageService.updateImage(file, imageId);
                return ResponseEntity.ok(new ApiResponse("Upload success!",null));
            }
        } catch (Exception e) {
           return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("upload fail",INTERNAL_SERVER_ERROR));
    }


    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {
        try {
            Image image = imageService.getImageById(imageId);
            if(image != null) {
                imageService.deleteImage( imageId);
                return ResponseEntity.ok(new ApiResponse("Delete success!", null));
            }
        } catch (ResourceNotFoundExceotion e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete failed!", INTERNAL_SERVER_ERROR));
    }

}
