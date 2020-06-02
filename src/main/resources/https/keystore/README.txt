This is part of creating HTTPS connection.
Tutorial link: https://www.thomasvitale.com/https-spring-boot-ssl-certificate/

.crt file is actually given by an authority like LetsEncrypt and COMMODO EasySSL**

In halfway, when you need to have myCertificate.crt file(for testing), use the following resource link & command:
https://stackoverflow.com/questions/52980370/how-to-convert-p12-to-crt-file
openssl pkcs12 -in keystore.p12 -clcerts -nokeys -out myCertificate.crt

Put your .jks/.p12 format keystore file here.
