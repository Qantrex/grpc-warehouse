# Middleware Engineering - Bauer 4BHIT

Start HelloWorldServer (Java)  
`gradle clean build`  
`gradle runServer`

Start HelloWorldClient (Java)  
`gradle runClient`

Make virtual Environment
`python -m venv .`

Add grpcio packages  
`pip3 install grpcio grpcio-tools`  

Compile .proto file  
`python3 -m grpc_tools.protoc -I src/main/proto  
  --python_out=src/main/resources  
  --grpc_python_out=src/main/resources  
  src/main/proto/hello.proto`  

Start HelloWorldClient (Python)  
`python3 src/main/resources/helloWorldClient.py`  

## Code Snippets
Create the Service in the proto file
``` java
// Service definition.
service DataWarehouseService {
  rpc TransferWarehouseRecord (WarehouseRecord) returns (TransferReply) {}
}
```
Define the Warehouse data in the proto file
``` java
// Warehouse data.
message WarehouseRecord {
  string warehouseID = 1;
  string warehouseName = 2;
  string warehouseAddress = 3;
  string warehousePostalCode = 4;
  string warehouseCity = 5;
  string warehouseCountry = 6;
  string timestamp = 7;
  repeated Product products = 8; // 'repeated' creates a list of Products
}
```
Establishes an unencrypted channel to the server
``` python
with grpc.insecure_channel('localhost:50051') as channel
```
Imports the gRPC library for networking and datetime
``` python
import grpc from datetime import datetime	
import hello_pb2 import hello_pb2_grpc
```
