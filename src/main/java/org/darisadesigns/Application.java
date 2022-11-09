package org.darisadesigns;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class Application extends AbstractHandler
{
    private static String loadIndex(boolean debug) {
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Application.class.getResourceAsStream("/mainTemplate.html")))) {
//            final StringBuilder page = new StringBuilder();
//            String line;
//
//            while ((line = reader.readLine()) != null) {
//                page.append(line);
//                page.append("\n");
//            }
//
//            return Formatter.formatPage(page.toString(), Formatter.getBasicEntry(getRandomEntry(debug)));
//        } catch (final Exception exception) {
//            return getStackTrace(exception);
//        }
        return "ZOT";
    }

    private static String getStackTrace(final Throwable throwable) {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter, true);
        throwable.printStackTrace(printWriter);

        return stringWriter.getBuffer().toString();
    }

    private static int getPort() {
        String port = System.getenv().get("PORT");
        return port == null ? 9797 : Integer.parseInt(System.getenv().get("PORT"));
    }

    private void handleHttpRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println(loadIndex(request.getPathInfo().endsWith("debug")));
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        String pathInfo = request.getPathInfo();
        if (pathInfo.toLowerCase().endsWith(".jpg")) {
            // TODO: Rewrite this
            // default back to main page on missing file. No clues.
            //if (!MediaRequest.handleImageRequest(pathInfo, response)) {
                handleHttpRequest(request, response);
            //}
        } else if (pathInfo.toLowerCase().endsWith(".css")) {
            // TODO: Rewrite this
            // MediaRequest.cssRequest(pathInfo, response);
        } else {
            handleHttpRequest(request, response);
        }
    }

    public static void main(String[] args) throws Exception
    {
        Server server = new Server(getPort());
        server.setHandler(new Application());
        server.start();
        server.join();
    }
}
