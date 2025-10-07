import grpc
from datetime import datetime

# Import the generated classes from your proto file
import hello_pb2
import hello_pb2_grpc

def run():
    # Connect to the server running on localhost port 50051
    with grpc.insecure_channel('localhost:50051') as channel:

        # Create a client stub for the DataWarehouseService
        stub = hello_pb2_grpc.DataWarehouseServiceStub(channel)

        print("--- Will try to transfer a warehouse record ---")

        # Create sample Product messages
        product1 = hello_pb2.Product(
            productID="PY-P-001",
            productName="Python Book",
            quantity=15
        )
        product2 = hello_pb2.Product(
            productID="PY-P-002",
            productName="GRPC Mug",
            quantity=150
        )

        # Create the main WarehouseRecord request message
        warehouse_request = hello_pb2.WarehouseRecord(
            warehouseID="WH-VIENNA-02",
            warehouseName="Vienna Python Hub",
            warehouseAddress="Donauinsel 1",
            warehouseCity="Vienna",
            warehouseCountry="Austria",
            timestamp=datetime.now().strftime("%Y-%m-%d %H:%M:%S.%f")[:-3],
            products=[product1, product2] # Add the products to the list
        )

        # Make the RPC call to the TransferWarehouseRecord method
        response = stub.TransferWarehouseRecord(warehouse_request)

        # Print the server's response
        print(f"Server response: {response.message}")
        print(f"Success: {response.success}")


if __name__ == '__main__':
    run()