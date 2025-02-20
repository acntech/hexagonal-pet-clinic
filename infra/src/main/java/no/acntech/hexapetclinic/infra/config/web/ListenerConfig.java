package no.acntech.hexapetclinic.infra.config.web;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import java.time.Duration;
import java.time.Instant;
import no.acntech.hexapetclinic.infra.config.BaseInfraConfig;
import org.slf4j.MDC;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for registering a servlet request listener.
 *
 * This class extends {@link BaseInfraConfig} and provides a Spring Bean for registering
 * a {@link ServletRequestListener} to track the lifecycle of HTTP servlet requests.
 *
 * The registered listener intercepts request initialization and destruction events and:
 * - Records the request and response timestamps in servlet request attributes.
 * - Logs detailed trace information about the request lifecycle, such as initialization time,
 *   destruction time, and total duration.
 * - Cleans up logging context information upon request destruction.
 */
@Configuration
public class ListenerConfig extends BaseInfraConfig {

  @Bean
  public ServletListenerRegistrationBean<ServletRequestListener> servletRequestListener() {
    ServletListenerRegistrationBean<ServletRequestListener> listenerRegBean = new ServletListenerRegistrationBean<>();
    listenerRegBean.setListener(createServletRequestListener());
    return listenerRegBean;
  }

  private ServletRequestListener createServletRequestListener() {
    return new ServletRequestListener() {

      @Override
      public void requestInitialized(ServletRequestEvent event) {
        recordRequestInitialization(event.getServletRequest());
      }

      @Override
      public void requestDestroyed(ServletRequestEvent event) {
        recordRequestDestruction(event.getServletRequest());
        clear();
      }
    };
  }

  private void recordRequestInitialization(ServletRequest request) {
    request.setAttribute(ServletAttributeNames.REQUEST_TIMESTAMP, Instant.now());
    if (log.isTraceEnabled()) {
      log.trace("ServletRequest initialized [{}] for path [{}]", request.getRemoteAddr(), request.getServletContext().getContextPath());
    }
  }

  private void recordRequestDestruction(ServletRequest request) {
    Instant now = Instant.now();
    request.setAttribute(ServletAttributeNames.RESPONSE_TIMESTAMP, now);
    if (log.isTraceEnabled()) {
      Instant reqTimestamp = (Instant) request.getAttribute(ServletAttributeNames.REQUEST_TIMESTAMP);
      log.trace("ServletRequest destroyed [{}], took [{}] ms", request.getRemoteAddr(), Duration.between(reqTimestamp, now).toMillis());
    }
  }

  private void clear() {
    MDC.clear();
  }
}
