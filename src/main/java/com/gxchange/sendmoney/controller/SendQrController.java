package com.gxchange.sendmoney.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
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
        BitMatrix bitMatrix = qrCodeWriter.encode(payload, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        String base64Qr = Base64.getEncoder().encodeToString(pngOutputStream.toByteArray());

        model.addAttribute("qrImage", base64Qr);
        model.addAttribute("phoneNumber", phoneNumber);
        model.addAttribute("amount", amount);

        return "send-qr"; // JSP file name
    }
}
