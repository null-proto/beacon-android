# Packet Format ` v1.0 `

### Structure

|     Header     |  Metadata  | Payload  |
|:--------------:|:----------:|:--------:|
|    22 Bytes    |  Variable  | Variable |

### Header
The Header is designed to be fixed length (22 Bytes) with 10 segments which includes integrity checks

> ***Deprecated***
>   `metadataType` , `payloadID` will removed in updates ; might be formated again

|       | version | flags  |  type  | payload_id | payload_length | metadata_length | metadata_type | reserved | checksum | Metadata |
|:-----:|:-------:|:------:|:------:|:----------:|:--------------:|:---------------:|:-------------:|:--------:|:--------:|:--------:|
| size  | 1 Byte  | 1 Byte | 1 Byte |  2 Bytes   |    2 Bytes     |     2 Bytes     |    1 Byte     | 8 Bytes  | 4 Bytes  | variable |
|       |         |        |        |            |                |                 |               |          |          |          |
| start |    0    |   1    |   2    |     3      |       5        |        7        |       9       |    10    |    18    |    -     |
| ends  |    0    |   1    |   2    |     4      |       6        |        8        |       9       |    17    |    21    |    -     |

#### Version
A version byte defines protocol version starts with `1` refers to `v1`.There is only one version currently available.

#### Flags
Flag 1 Byte or 8 bit.

```asm
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
0b0000_000x ; bit 8 - integrity check enabled
0b0000_00x0 ; bit 7 - payload order enabled ( the real use of payload_id segmant )
0b0000_0x00 ; bit 6 - payload request - if integrity check failed payload request will send (with same payload_id, payload_length, cheksum)
0b0xxx_x000 ; bit 2-5 - future use
0bx000_0000 ; bit 1 - if 1 = Request, 0 = Response
```

#### Type
The type is 1 byte value which defines its purpose.

| Hex  | Name   | Purpose                                   |
|:----:|:-------|:------------------------------------------|
| 0x00 | Echo   | Ping to check if the device is responsive |
| 0x01 | Data   | Active data sync                          |
| 0x02 | Sync   | Passive data sync, usually state updates  |
| 0x03 | Stream | Continuous data stream                    |
| 0x04 | File   | File transfer                             |

#### Payload ID
***Deprecated***
Payload id is used to determined order of payloads. It is important for reconstructing a large file transferred in several payload. It can reconstruct `65,536` payloads that is enough for large files/data.

#### Payload Length
Payload length is 3 bytes fixed length. It can carry `16 Mega Bytes`.

#### metadata_length
Determines overall length of metadata, 1 = Byte or 8-bit

#### metadata_type
***Deprecated****
Type of the metadata, with is used to parse metadata into objects

| code | type      |
|:----:|:----------|
|  0   | Undefined |
|  1   | Json      |

#### Reserved
These are reserved for future use.

#### Checksum
Checksum is also 32bt or 4byte fixed length value.There is a plan to use CRC32,or FNV-1a(32bit),or xxHash32 . Checksum verifies before metadata decode.

### Metadata
Metadata is used for send special messages like auth-code,session-code,encoding,packet-format,...etc.
```kotlin
// METADATA FORMAT
[ 
//  size, key..size    size, value..size
    3 , 1 , 2 , 3 ,     4 , 1 , 2 , 3 , 4 ,
]
```

### Payload
Just raw ByteArray nothing special.