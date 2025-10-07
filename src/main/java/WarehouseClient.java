import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
// Import the new generated classes
import io.grpc.examples.dwh.DataWarehouseServiceGrpc;
import io.grpc.examples.dwh.Product;
import io.grpc.examples.dwh.TransferReply;
import io.grpc.examples.dwh.WarehouseRecord;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WarehouseClient {
    private final ManagedChannel channel;
    // Use the new service stub
    private final DataWarehouseServiceGrpc.DataWarehouseServiceBlockingStub blockingStub;

    public WarehouseClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        // Create a stub for the new service
        blockingStub = DataWarehouseServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void transferWarehouse() {
        System.out.println("Will try to transfer a warehouse record ...");

        Product product1 = Product.newBuilder().setProductID("P1001").setProductName("Laptop").setQuantity(50).build();
        Product product2 = Product.newBuilder().setProductID("P1002").setProductName("Mouse").setQuantity(200).build();

        // Build the new WarehouseRecord request object
        WarehouseRecord request = WarehouseRecord.newBuilder()
                .setWarehouseID("WH-VIENNA-01")
                .setWarehouseName("Vienna Central Hub")
                .setWarehouseAddress("Musterstrasse 1")
                .setWarehouseCity("Vienna")
                .setWarehouseCountry("Austria")
                .setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()))
                .addProducts(product1)
                .addProducts(product2)
                .build();

        try {
            // Call the new RPC method
            TransferReply response = blockingStub.transferWarehouseRecord(request);
            System.out.println("Server response: " + response.getMessage());
            System.out.println("Success: " + response.getSuccess());
        } catch (StatusRuntimeException e) {
            System.err.println("RPC failed: " + e.getStatus());
        }
    }

    public static void main(String[] args) throws Exception {
        WarehouseClient client = new WarehouseClient("localhost", 50051);
        try {
            client.transferWarehouse();
        } finally {
            client.shutdown();
        }
    }
}