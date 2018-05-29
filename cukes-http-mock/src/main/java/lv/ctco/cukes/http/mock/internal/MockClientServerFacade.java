package lv.ctco.cukes.http.mock.internal;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lv.ctco.cukes.core.CukesRuntimeException;
import lv.ctco.cukes.core.internal.context.GlobalWorldFacade;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Singleton
public class MockClientServerFacade {
    private final Map<String, MockServerClient> services;
    private final Map<String, ClientAndServer> servers;

    private final GlobalWorldFacade worldFacade;

    @Inject
    public MockClientServerFacade(GlobalWorldFacade worldFacade) {
        this.worldFacade = worldFacade;

        services = new ConcurrentHashMap<>();
        servers = new ConcurrentHashMap<>();

        String servicesProperty = worldFacade.get("http.mock.services")
            .or(
                () -> {
                    throw new CukesRuntimeException("No mocks defined in cukes.properties file. please add cukes.http.mock.services value");
                }
            );
        for (String serviceName : servicesProperty.split(",")) {
            MockServerClient client = new MockServerClient(
                worldFacade.get("http.mock.services." + serviceName + ".host", "localhost"),
                Integer.parseInt(worldFacade.get("http.mock.services." + serviceName + ".port")
                    .or(
                        () -> {
                            throw new CukesRuntimeException("No port provided for mock service " + serviceName + ". Please provide property cukes.http.mock.services." + serviceName + ".port");
                        })
                )
            );
            services.put(serviceName, client);
        }

    }

    public MockServerClient getMockServerClient(String serviceName) {
        return services.computeIfAbsent(serviceName, key -> {
            String availableMockServices = services.keySet().stream().collect(Collectors.joining(", "));
            throw new CukesRuntimeException("Unable to find http mock service by name:" + key + ". " +
                "Available mock services are: {" + availableMockServices + "}");
        });
    }

    public void startAllServers() {
        services.keySet().forEach(serviceName -> {
            int port = Integer.parseInt(worldFacade.get("http.mock.services." + serviceName + ".port")
                .or(
                    () -> {
                        throw new CukesRuntimeException("No port provided for mock service " + serviceName + ". Please provide property cukes.http.mock.services." + serviceName + ".port");
                    }));
            ClientAndServer server = ClientAndServer.startClientAndServer(port);
            servers.put(serviceName, server);
        });
    }

    public void stopAllServers() {
        servers.values().forEach(server -> server.stop(true));
        servers.clear();
    }

    public void resetAllServers() {
//        servers.reset();
    }
}
