import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
// Import the new generated classes
import io.grpc.examples.dwh.DataWarehouseServiceGrpc;
import io.grpc.examples.dwh.TransferReply;
import io.grpc.examples.dwh.WarehouseRecord;

import java.io.IOException;

public class WarehouseServer {

    private Server server;

    private void start() throws IOException {
        int port = 50051;
        server = ServerBuilder.forPort(port)
                // Use the new service implementation
                .addService(new DataWarehouseServiceImpl())
                .build()
                .start();
        System.out.println("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            WarehouseServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final WarehouseServer server = new WarehouseServer();
        server.start();
        server.blockUntilShutdown();
    }

    // Implement the new DataWarehouseService
    static class DataWarehouseServiceImpl extends DataWarehouseServiceGrpc.DataWarehouseServiceImplBase {
        @Override
        public void transferWarehouseRecord(WarehouseRecord req, StreamObserver<TransferReply> responseObserver) {
            System.out.println("Received Warehouse Record:");
            System.out.println(req.toString());

            TransferReply reply = TransferReply.newBuilder()
                    .setSuccess(true)
                    .setMessage("Warehouse record for ID " + req.getWarehouseID() + " received successfully.")
                    .build();

            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
}