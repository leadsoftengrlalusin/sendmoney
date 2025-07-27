<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Send Money QR</title>
</head>
<body style="background-color: #f2f2f2; text-align: center;">
    <h2>Send ₱${amount} to ${phoneNumber}</h2>
    <img src="data:image/png;base64,${qrImage}" alt="QR Code"/>
    <p>Scan this QR code to initiate a transfer.</p>
</body>
</html>
