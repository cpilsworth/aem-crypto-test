package com.github.cpilsworth.cryptosupport;

import com.adobe.granite.crypto.CryptoException;
import com.adobe.granite.crypto.CryptoSupport;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(
        service = Servlet.class,
        property = {
                "sling.servlet.paths=/bin/validatecrypto",
                "sling.servlet.methods=get",
        }
)
public class DecryptServlet extends SlingSafeMethodsServlet {

    @Reference
    private CryptoSupport cryptoSupport;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        final String protectedValue = request.getParameter("protected");
        response.setHeader("content-type", "text/plain");
        try {
            response.getWriter().write(this.cryptoSupport.unprotect(protectedValue));
        } catch (CryptoException e) {
            response.sendError(500, e.getMessage());
        }
    }
}