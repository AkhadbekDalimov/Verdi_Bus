package uz.asbt.digid.liveness.utils;

import sun.misc.BASE64Decoder;
import uz.asbt.digid.common.exception.ParseImageException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ImageUtils {

  public static BufferedImage fromBase64ToImage(String base64File) {
    BufferedImage image;
    String imageData;
    try {
      final String[] arr = base64File.split(",");
      if (arr.length == 2) {
        imageData = arr[1];
      } else {
        imageData = arr[0];
      }
      final byte[] imageByte;
      final BASE64Decoder decoder = new BASE64Decoder();
      imageByte = decoder.decodeBuffer(imageData);
      final ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
      image = ImageIO.read(bis);
      bis.close();
    } catch (final IOException ex) {
      throw new ParseImageException();
    }
    return image;
  }

  public static byte[] fromBase64ToByteArray(String base64Image) {
    final byte[] imageInByte;
    try {
      BufferedImage originalImage = ImageUtils.fromBase64ToImage(base64Image);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(originalImage,"jpg", baos);
      baos.flush();
      imageInByte = baos.toByteArray();
      baos.close();
    } catch (IOException ex) {
      throw new ParseImageException();
    }
    return imageInByte;
  }

  public static void writeImageIntoFile(final byte[] data, String file) {
    try {
      ByteArrayInputStream bis = new ByteArrayInputStream(data);
      BufferedImage bImage2 = ImageIO.read(bis);
      ImageIO.write(bImage2, "jpg", new File(file) );
    } catch (Exception ex) {
      throw new ParseImageException();
    }
  }

  private static byte[] convertStream(final Charset encoding, ByteArrayOutputStream raw) throws IOException {
    ByteArrayInputStream original = new ByteArrayInputStream(raw.toByteArray());
    InputStreamReader contentReader = new InputStreamReader(original, encoding);

    int readCount;
    char[] buffer = new char[4096];
    try (ByteArrayOutputStream converted = new ByteArrayOutputStream()) {
      try (Writer writer = new OutputStreamWriter(converted, StandardCharsets.UTF_8)) {
        while ((readCount = contentReader.read(buffer, 0, buffer.length)) != -1) {
          writer.write(buffer, 0, readCount);
        }
      }
      return converted.toByteArray();
    }
  }

}
