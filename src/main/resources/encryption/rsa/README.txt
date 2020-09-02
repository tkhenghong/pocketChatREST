Resource links:

A demo project of spring-boot, which involves rsa javascript encryption and java decryption.
https://github.com/yuanshenjian/rsa-java-js

AES vs RSA - Which is stronger given two scenarios?
https://crypto.stackexchange.com/questions/47991/aes-vs-rsa-which-is-stronger-given-two-scenarios

Symmetric vs. Asymmetric Encryption
https://www.ssl2buy.com/wiki/symmetric-vs-asymmetric-encryption-what-are-differences#:~:text=Difference%20Between%20Symmetric%20and%20Asymmetric,and%20decrypt%20messages%20when%20communicating.

NOTE: TEMPORARY DO NOT USE THIS METHOD TO GENERATE RSA KEY PAIRS, because the key are not readable by Java in Windows

TODO: May need to encrypt/decrypt texts with RSA encryption by researching following link:
https://stackoverflow.com/questions/32161720/breaking-down-rsa-ecb-oaepwithsha-256andmgf1padding

(Deprecated due to the keys are not readable by Java in Windows)Encrypt with OpenSSL, Decrypt with Java, Using OpenSSL RSA Public Private Keys (Main)
https://www.javacodegeeks.com/2020/04/encrypt-with-openssl-decrypt-with-java-using-openssl-rsa-public-private-keys.html
Problem: It has Base64 decode error when the same code is run in Windows, MacOS doesn't have such problem.

Why use genpkey instead of genrsa?
https://serverfault.com/questions/590140/openssl-genrsa-vs-genpkey

(Current On Going)Researching on generating RSA key pair using latest "openssl genpkey" command:
https://blog.sleeplessbeastie.eu/2017/12/28/how-to-generate-private-key/

// Encrypt and decrypt solution of password from frontend to backend using RSA 4096 with OpenSSL command line tool.

1. Generate Private key with pkcs8 encoding:
# openssl genpkey -out private_key_rsa_4096_pkcs8-generated.pem -algorithm RSA -pkeyopt rsa_keygen_bits:4096

echo "keypassword" | openssl genpkey -algorithm RSA -out example.org.key -pkeyopt rsa_keygen_bits:4096 -aes256 -pass stdin

openssl rsa -in example.org.key -out example.org.enc.key -aes256 -passout pass:keypassword

It will generate a file with .pem file format.

When you view the file, using 'cat private_key_rsa_4096_pkcs8-generated.pem', it will show something like the following:
-----BEGIN PRIVATE KEY-----
MIIJRAIBADANBgkqhkiG9w0BAQEFAASCCS4wggkqAgEAAoICAQDW/FrImfXVKn2w
KxqPUXNNWaTIf1u9G5b0pemhgfdRGewf2YYI7TfbJAQcTt0mujqIqfQ9jrNDqZp0
djDTlVn56K8jJGK7A3+wKPfCDNLmfYfUlkdM4P8uZEo2/21kdd+50hvjNfyMT2Cw
.....
-----END PRIVATE KEY-----

Private key is ONLY STORED IN THE SERVER.

2. Generate Public key from the Private key:
openssl rsa -pubout -outform pem -in private_key_rsa_4096_pkcs8-generated.pem -out public_key_rsa_4096_pkcs8-exported.pem

It will also generate a file with .pem file format.

When you view the file, using 'cat public_key_rsa_4096_pkcs8-exported.pem', it will show something like the following:
-----BEGIN PUBLIC KEY-----
MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEA1vxayJn11Sp9sCsaj1Fz
TVmkyH9bvRuW9KXpoYH3URnsH9mGCO032yQEHE7dJro6iKn0PY6zQ6madHYw05VZ
+eivIyRiuwN/sCj3wgzS5n2H1JZHTOD/LmRKNv9tZHXfudIb4zX8jE9gsFrQJLuD
......
-----END PUBLIC KEY-----

Share the public key to the frontend clients(Android, iOS, Web)


