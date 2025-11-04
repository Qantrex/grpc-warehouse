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
  repeated Product products = 8;
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

Transfer Methods
``` java
try {
    TransferReply response = blockingStub.transferWarehouseRecord(request);
    System.out.println("Server response: " + response.getMessage());
    System.out.println("Success: " + response.getSuccess());
} catch (StatusRuntimeException e) {
    System.err.println("RPC failed: " + e.getStatus());
}
```
``` python
response = stub.TransferWarehouseRecord(warehouse_request)
```
---
# Fragen
What is gRPC and why does it work accross languages and platforms?
A Remote Procedure Call (RPC) from google. Using HTTP/2 and Protocol Buffers to translate between languages.
https://grpc.io/docs/what-is-grpc/introduction
Describe the RPC life cycle starting with the RPC client?
The gRPC client calls a method on its local stub, which serializes the request using Protocol Buffers and sends it over an HTTP/2 connection.
https://www.baeldung.com/grpc-introduction
Describe the workflow of Protocol Buffers?
The Protocol Buffer workflow involves defining the data structure in a .proto file. Later that file can be used for new code creation usign protoc.
https://grpc.io/docs/what-is-grpc/introduction
What are the benefits of using protocol buffers?
You can get smaller message sizes and faster performance compared to text-based formats like JSON or XML.
https://www.baeldung.com/grpc-introduction
When is the use of protocol not recommended?
The use of Protocol Buffers is generally not recommended when human readability is very important
https://www.baeldung.com/grpc-introduction
List 3 different data types that can be used with protocol buffers?
string, int32, and bool
https://grpc.io/docs/what-is-grpc/introduction
