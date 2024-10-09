package us.kusakabe.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import us.kusakabe.entity.ProfilePicture;
import us.kusakabe.repository.ProfilePictureRepository;
import us.kusakabe.utils.ImageUtils;

import java.awt.*;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
@AllArgsConstructor
public class ImageService {

    private final ProfilePictureRepository ppRepository;
    private final JwtService jwtService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

    public String uploadImage(MultipartFile imageFile, String header) throws IOException {
        String username = jwtService.extractUsername(header.substring(7));
        if(ppRepository.findByUsername(username).isPresent()) {
            ProfilePicture pp = ppRepository.findByUsername(username).get();
            pp.setType(imageFile.getContentType());
            pp.setData(ImageUtils.compressImage(imageFile.getBytes()));
            ppRepository.save(pp);
        }else {
            var imageToSave = ProfilePicture.builder()
                    .username(username)
                    .type(imageFile.getContentType())
                    .data(ImageUtils.compressImage(imageFile.getBytes()))
                    .build();
            ppRepository.save(imageToSave);
        }
        return "File uploaded successfully!";
    }

    public byte[] downloadImage(String username){
        Optional<ProfilePicture> dbImage = ppRepository.findByUsername(username);
        if(dbImage.isEmpty()) {
            LOGGER.warn("Image not found");
        }
        return dbImage.map(image -> {
            try{
                return ImageUtils.decompressImage(image.getData());
            }catch (DataFormatException | IOException exception){
                throw new RuntimeException(exception);
            }
        }).orElse(null);
    }

}
