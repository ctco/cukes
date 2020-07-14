package lv.ctco.cukes.docgen;

import java.util.Comparator;
import java.util.Map;

public enum CukesComponent {

    core("General", "lv.ctco.cukes.core", "cukes-core"),
    http("HTTP", "lv.ctco.cukes.http.api", "cukes-http"),
    graphQL("GraphQL", "lv.ctco.cukes.graphql", "cukes-graphql"),
    ldap("LDAP", "lv.ctco.cukes.ldap", "cukes-ldap"),
    rabbitMQ("RabbitMQ", "lv.ctco.cukes.rabbitmq", "cukes-rabbitmq"),
    rest("REST", "lv.ctco.cukes.rest", "cukes-rest"),
    httpMock("HTTP Mock", "lv.ctco.cukes.http.mock", "cukes-http-mock");

    public static final Comparator<CukesComponent> comparator = Comparator.comparing(CukesComponent::ordinal);
    public static final Comparator<Map.Entry<CukesComponent, ?>> mapKeyComparator = (o1, o2) -> comparator.compare(o1.getKey(), o2.getKey());

    private final String name;
    private final String basePackage;
    private final String moduleName;

    CukesComponent(String name, String basePackage, String moduleName) {
        this.name = name;
        this.basePackage = basePackage;
        this.moduleName = moduleName;
    }

    public static CukesComponent findByClassName(String className) {
        for (CukesComponent component : values()) {
            if (className.startsWith(component.basePackage)) {
                return component;
            }
        }
        throw new IllegalArgumentException("Unexpected class name with steps - " + className);
    }

    public String getName() {
        return name;
    }

    public String getModuleName() {
        return moduleName;
    }
}
