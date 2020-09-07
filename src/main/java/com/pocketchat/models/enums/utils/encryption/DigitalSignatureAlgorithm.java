package com.pocketchat.models.enums.utils.encryption;

public enum DigitalSignatureAlgorithm {
    NoneWithRSA, // Means default RSA
    SHA1WithRSA,
    SHA256WithRSA,
    SHA1WithDSA,
    MD5WithRSA,
    SHA1WithRSAWithBC, // With BouncyCastle
}
