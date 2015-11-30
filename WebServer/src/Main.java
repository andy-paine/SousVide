import Endpoints.API_Cycle;
import com.andy.IO.IDataStore;
import com.andy.IO.MongoDB;
import com.andy.IO.PostgresDB;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.*;

import java.io.IOException;
import java.net.URI;

/**
 * Created by andy on 28/10/15.
 */
public class Main {

    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer(IDataStore dataStore) {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.andy package
        //final ResourceConfig rc = new ResourceConfig().packages("com.andy");
        ResourceConfig rc = new ResourceConfig();
        //TODO Register all endpoints
        rc.register(new API_Cycle(dataStore));


        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final IDataStore dataStore = new MongoDB();

        final HttpServer server = startServer(dataStore);
        System.out.println(String.format("Web server running at "
                + "%s\nAPI is available at /api", BASE_URI));
        System.in.read();
        server.shutdownNow();
        server.stop();
    }

}
