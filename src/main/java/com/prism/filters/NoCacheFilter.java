package com.prism.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Filtro HTTP que desactiva el caché del navegador en todas las respuestas.
 *
 * Sin este filtro, el navegador almacena las respuestas de la API y muestra
 * datos desactualizados aunque el servidor ya los haya modificado.
 *
 * @Component hace que Spring lo detecte y aplique automáticamente
 * a cada petición HTTP, sin necesidad de registrarlo manualmente.
 */
@Component
public class NoCacheFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Prohíbe almacenar la respuesta en cualquier caché
        httpResponse.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Compatibilidad con clientes HTTP/1.0
        httpResponse.setHeader("Pragma", "no-cache");
        // Indica que la respuesta ya expiró (fecha en el pasado)
        httpResponse.setHeader("Expires", "0");

        // Continúa con el siguiente filtro o con el servlet destino
        chain.doFilter(request, response);
    }
}
