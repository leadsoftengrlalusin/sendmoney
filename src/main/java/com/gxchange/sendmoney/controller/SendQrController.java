package com.gxchange.sendmoney.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

@Controller
public class SendQrController {

    @GetMapping("/send-qr")
    public String generateQrPage(@RequestParam String phoneNumber,
                                 @RequestParam int amount,
                                 Model model) throws WriterException, IOException {

        String payload = String.format("{\"phoneNumber\":\"%s\", \"amount\":%d}", phoneNumber, amount);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(payload, BarcodeFormat.QR_CODE, 300, 300);

        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage qrImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                qrImage.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
            }
        }

        BufferedImage logo = ImageIO.read(new File("src/main/resources/static/logo.png"));

        int overlaySize = width / 5;
        int overlayX = (width - overlaySize) / 2;
        int overlayY = (height - overlaySize) / 2;

        Graphics2D g = qrImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(logo.getScaledInstance(overlaySize, overlaySize, Image.SCALE_SMOOTH),
                overlayX, overlayY, null);
        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "PNG", baos);
        String base64Qr = Base64.getEncoder().encodeToString(baos.toByteArray());

        model.addAttribute("qrImage", base64Qr);
        model.addAttribute("phoneNumber", phoneNumber);
        model.addAttribute("amount", amount);

        return "send-qr";
    }
}
