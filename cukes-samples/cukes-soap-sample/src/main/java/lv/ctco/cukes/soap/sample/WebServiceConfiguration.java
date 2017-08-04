package lv.ctco.cukes.soap.sample;

import lv.ctco.cukes.soap.sample.dto.CalculatorRequest;
import lv.ctco.cukes.soap.sample.dto.CalculatorResponse;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.XsdSchemaCollection;
import org.springframework.xml.xsd.commons.CommonsXsdSchemaCollection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class WebServiceConfiguration {
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    @Bean(name = "calculator")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchemaCollection schemaCollection) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("CalculatorPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://github.com/ctco/cukes/cukes-soap-sample");
        wsdl11Definition.setSchemaCollection(schemaCollection);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchemaCollection schemaCollection() throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(CalculatorRequest.class, CalculatorResponse.class);
        List<ByteArrayOutputStream> outs = new ArrayList<>();
        context.generateSchema(new SchemaOutputResolver() {
            @Override
            public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                outs.add(out);
                StreamResult result = new StreamResult(out);
                result.setSystemId(suggestedFileName);
                return result;
            }
        });
        Resource[] resources = outs.stream().
            map(ByteArrayOutputStream::toByteArray).
            map(InMemoryResource::new).
            collect(Collectors.toList()).
            toArray(new Resource[]{});
        return new CommonsXsdSchemaCollection(resources);
    }

    private class InMemoryResource extends AbstractResource {
        private byte[] content;

        public InMemoryResource(byte[] content) {
            this.content = content;
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(content);
        }
    }
}
