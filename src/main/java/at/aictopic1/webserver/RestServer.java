package at.aictopic1.webserver;

import at.aictopic1.sentimentanalysis.preprocessor.PropertiesReader;
import at.aictopic1.webserver.servlets.IndexServlet;
import at.aictopic1.webserver.servlets.ResourceFilter;
import at.aictopic1.webserver.servlets.ResourceServlet;
import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.SimpleInstanceManager;
import org.eclipse.jetty.annotations.ServletContainerInitializersStarter;
import org.eclipse.jetty.apache.jsp.JettyJasperInitializer;
import org.eclipse.jetty.plus.annotation.ContainerInitializer;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.servlet.DispatcherType;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * 
 */
public class RestServer {
    // Resource path pointing to where the WEBROOT is
    private static final String WEBROOT_INDEX = "/webroot/";

    public static void main(String[] args) throws Exception
    {
        PropertiesReader _propertiesReader = PropertiesReader.getInstance();
        int port = 9998;
        try {
            port = Integer.parseInt(_propertiesReader.get("server.port"));
        }catch(Exception e){
            //ignore
        }
        RestServer main = new RestServer(port);
        main.start();
        main.waitForInterrupt();
    }

    private int port;
    private Server server;
    private URI serverURI;

    public RestServer(int port)
    {
        this.port = port;
    }

    public URI getServerURI()
    {
        return serverURI;
    }

    public void start() throws Exception
    {
        server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.addConnector(connector);

        URL indexUri = this.getClass().getResource(WEBROOT_INDEX);
        if (indexUri == null)
        {
            throw new FileNotFoundException("Unable to find resource " + WEBROOT_INDEX);
        }

        // Points to wherever /webroot/ (the resource) is
        URI baseUri = indexUri.toURI();

        // Establish Scratch directory for the servlet context (used by JSP compilation)
        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        File scratchDir = new File(tempDir.toString(),"embedded-jetty-jsp");

        if (!scratchDir.exists())
        {
            if (!scratchDir.mkdirs())
            {
                throw new IOException("Unable to create scratch directory: " + scratchDir);
            }
        }

        // Set JSP to use Standard JavaC always
        System.setProperty("org.apache.jasper.compiler.disablejsr199","false");


        // Setup the basic application "context" for this application at "/"
        // This is also known as the handler tree (in jetty speak)
        WebAppContext context = new WebAppContext();
        context.setContextPath("/");
        context.setAttribute("javax.servlet.context.tempdir", scratchDir);
        context.setResourceBase(baseUri.toASCIIString());
        context.setAttribute(InstanceManager.class.getName(), new SimpleInstanceManager());

        //Ensure the jsp engine is initialized correctly
        JettyJasperInitializer sci = new JettyJasperInitializer();
        ServletContainerInitializersStarter sciStarter = new ServletContainerInitializersStarter(context);
        ContainerInitializer initializer = new ContainerInitializer(sci, null);
        List<ContainerInitializer> initializers = new ArrayList<ContainerInitializer>();
        initializers.add(initializer);

        context.setAttribute("org.eclipse.jetty.containerInitializers", initializers);
        context.addBean(sciStarter, true);

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setResourceBase(baseUri.toASCIIString());

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { context, resource_handler });
        server.setHandler(handlers);

        ServletHolder jerseyServlet = new ServletHolder(new ServletContainer());
        jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "at.aictopic1.webserver.rest");
        context.addServlet(jerseyServlet, "/api/*");

        // Start Server
        server.start();

        // Establish the Server URI
        String scheme = "http";
        for (ConnectionFactory connectFactory : connector.getConnectionFactories())
        {
            if (connectFactory.getProtocol().equals("SSL-http"))
            {
                scheme = "https";
            }
        }
        String host = connector.getHost();
        if (host == null)
        {
            host = "localhost";
        }
        int port = connector.getLocalPort();
        serverURI = new URI(String.format("%s://%s:%d/",scheme,host,port));
    }

    public void stop() throws Exception
    {
        server.stop();
    }

    /**
     * Cause server to keep running until it receives a Interrupt.
     * <p>
     * Interrupt Signal, or SIGINT (Unix Signal), is typically seen as a result of a kill -TERM {pid} or Ctrl+C
     */
    public void waitForInterrupt() throws InterruptedException
    {
        server.join();
    }
}
