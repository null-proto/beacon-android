# Packet Format

## Version `v1.0`
### Structure

|            Header             | Payload  |
|:-----------------------------:|:--------:|
| 24 Bytes + Variable(metadata) | Variable |

### Header
The Header is designed to be fixed length (16 Bytes) with 7 segments which includes auth and integrity checks

| version | flags  |  type  | payload_id | payload_length | metadata_length | metadata_type  | reserved | checksum |  Metadata  |
|:-------:|:------:|:------:|:----------:|:--------------:|:---------------:|:--------------:|:--------:|:--------:|:----------:|
| 1 Byte  | 1 Byte | 1 Byte |  2 Bytes   |    4 Bytes     |     2 Bytes     |     1 Byte     | 8 Bytes  | 4 Bytes  |    var     |

#### Version
A version byte defines protocol version starts with `1` refers to `v1`.There is only one version currently available.

#### Flags
Flags 1 Byte or 8 bit or 8 flags.

```asm
; Please ignore 0b at starting (because it is used in many languages to mention binary value so i'm using it)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
0b0000_000x ; bit 1 - integrity check enabled
0b0000_00x0 ; bit 2 - payload order enabled ( the real use of payload_id segmant )
0b0000_0x00 ; bit 3 - payload request - if integrity check failed payload request will send (with same payload_id, payload_length, cheksum)
0bx000_0000 ; bit 8 - if 1 = Request, 0 = Response
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
Payload id is used to determined order of payloads. It is important for reconstructing a large file transferred in several payload. It can reconstruct `65,536` payloads that is enough for large files/data.

#### Payload Length
Payload length is 3 bytes fixed length. It can carry `16 Mega Bytes`.
> ##### why 3 bytes
> Well, 4 bytes can hold up to `4 GB` which is humongous for network operations.`2 Bytes (16KB)` is too short to transfer files.So,`3 Bytes (16MB)` seems to more efficient with chunking and it can transfer `65536 chunks * 16MB = 1.048 Terra Byte or 1048 GB` size of data.

#### Authentication Bytes
Authentication Bytes are fixed 32bit or 4 byte value uniquely distributed/generated. **It is blank at this time**

#### Checksum
Checksum is also 32bt or 4byte fixed length value.There is a plan to use CRC32,or FNV-1a(32bit),or xxHash32

### Payload
Payload is added up with header, It doesn't contains any magic/identification segments inside.
