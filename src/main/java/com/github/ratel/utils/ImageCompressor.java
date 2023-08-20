package com.github.ratel.utils;

import lombok.experimental.UtilityClass;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

@UtilityClass
public class ImageCompressor {

    private static final float QUALITY = 0.5f;

    public MockMultipartFile compressImage(MultipartFile file) throws IOException {
        RenderedImage image = ImageIO.read(file.getInputStream());
        ImageWriter imageWriter = ImageIO.getImageWritersByFormatName(getExtension(file.getContentType())).next();
        ImageWriteParam imgWriteParam = imageWriter.getDefaultWriteParam();
        imgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        imgWriteParam.setCompressionQuality(QUALITY);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageOutputStream output = new MemoryCacheImageOutputStream(byteArrayOutputStream);
        imageWriter.setOutput(output);
        IIOImage outputImage = new IIOImage(image, null, null);
        imageWriter.write(null, outputImage, imgWriteParam);
        imageWriter.dispose();
        return new MockMultipartFile(file.getName(), file.getOriginalFilename(), file.getContentType(), byteArrayOutputStream.toByteArray());
    }

    public static String getExtension(String contentType) {
        String[] split = Objects.requireNonNull(contentType).split("/");
        return split[1];
    }

    public static String getTypeData(String contentType) {
        String[] split = Objects.requireNonNull(contentType).split("/");
        return split[0];
    }

}
