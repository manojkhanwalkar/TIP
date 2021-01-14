# **TIP Framework**

The code provides a design pattern for secure storage, use and presentation of sensitive data elements. Examples of sensitive information include PII elements like Name, Address , SSN , DOB, Email etc. as well as PCI information like Credit card numbers. 
The design pattern is independent of the nature of the data and assumes some backing service provider will be able to validate it. 

**Actors**
The three actors in this design pattern are the 
1. Wallet 
All user interactions require use of the wallet. The interactions are explained in detail below. 
2. Tokenized Identity Provider (TIP)
The entity responsible for verifying the user supplied information and acting as a responsible steward of that information. Once verified and encoded, the TIP does not have the ability to read or use the information in any way.
The TIP is also a party trusted by service providers that rely on the accuracy of the data to provide the requested services. 
3. Service Provider  
The sample service represents any service provider that needs sensitive data from the user and is looking for assurance that this data has been verified by a trusted third provider - the TIP.

**Interactions**

1. Submission of data for verification 

    a. The wallet generates a public/private key pair and securely stores the users private key. 
    
    b. The users public key is submitted along with the information to be verified to the TIP. 
    
    c. The TIP validates the submitted information via a few backing service providers and then securely stores the verified information on behalf of the user. 
        
        1. It creates an AES key and encrypts the information using that key.
        
        2. It encrypts the AES key using the user supplied public key. 
        
        3. It creates a unique token and passes that token back to the user.
        
        4. The token , encrypted key and encrypted user data is stored by the TIP. 
        
        5. Before encrypting the verified information, the TIP also signs the information using it's private key. 
        
    d.  The wallet stores the token returned by TIP.    

2. Retrieval of the verified data 

    a. The wallet submits the token generated in step 1 and sends it to TIP.
    
    b. The TIP looks up the token and then sends back the encrypted key and the encrypted data to the wallet. 
    
    c. The wallet uses the private key to decrypt the key and then the key to decrypt the data. 
 
3. Presentation of data to service provider 

    a. The wallet supplies the required data elements along with the TIP signature to the service provider. 
    
    b. The service provider uses the TIP's public key to validate the signature and assure itself that the user data is genuine as certified by the TIP. 
    
    c. The service provider then provides the service requested by the user.

**Cryptography details**

    1. The wallet generates an Elliptical curve public/private key pair on the NIST standard curve prime256v1. 
    
    2. The TIP uses the same curve for it's digital signature. (ECDSA)
    
    3. The data is encrypted by a symmetric session key (AES) 
    
    4. The data is stored in a customized Merkle tree format, deriving its inspiration from how transactions are stored in a Blockchain. 

**Exclusions**
The pattern does not cater to the following areas, which would need to be provided by alternate mechanisms.
1. Mutual authentication between the Wallet and the services.
2. Storage of keys in the wallet as well as the verified elements on the server side.  
3. Actual verification of the elements , which will be done by backing services
4. Does not use certificates for validating public keys
5. Use of a Nonce will provide additional security against replay attacks
6. For an additional layer of protection of data in transit (on top of TLS 1.2) look at the P2PE design pattern https://github.com/manojkhanwalkar/P2PE/blob/master/README.md

**Additional Use Case**
The filer service showcases another use case for the design pattern. The client submits a file and receives back a token. The token can then be used to retrieve the file. The file is stored securely o the server as only te client with access to the provate key can decrypt the contents of the file. 