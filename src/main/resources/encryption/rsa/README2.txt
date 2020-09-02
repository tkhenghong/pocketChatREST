https://stackoverflow.com/questions/11410770/load-rsa-public-key-from-file

How to generate RSA Key paid using OpenSSL:

In Windows, use Git Bash CLI.
In MacOS, use zsh terminal with openssl installed.

A = RSA Private key with 2048 rsa_keygen_bits
B = RSA Private key with 2048 rsa_keygen_bits with 2048 PKCS#8 format with DER file format.
C = RSA Public key with DER file format

Note: This method of generate RSA key pair is old, https://serverfault.com/questions/590140/openssl-genrsa-vs-genpkey

Step 1: Generate A.
openssl genrsa -out private_key.pem

Step 2: Convert A to B.
openssl pkcs8 -topk8 -inform PEM -outform DER -in private_key.pem -out private_key.der -nocrypt

Step 3: Use B to generate C
openssl rsa -in private_key.pem -pubout -outform DER -out public_key.der
