package no.acntech.hexapetclinic.infra.controller.advice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import no.acntech.hexapetclinic.infra.config.web.ServletAttributeNames;
import no.acntech.hexapetclinic.infra.controller.ResponseDto;
import no.acntech.hexapetclinic.utils.time.TimeFormatUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Interceptor for ResponseDTO objects. Adds metadata to the response.
 */
@ControllerAdvice
@Slf4j
public class ResponseDtoBodyAdvice implements ResponseBodyAdvice<Object> {

  private static final String PARAM_HTTP_STATUS = "http_status";
  private static final String PARAM_PROCESSING_TIME = "processing_time";

  @Override
  public boolean supports(@NonNull MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  /**
   * Intercepts the body of the response before writing and adds metadata if the body is an instance of ResponseDTO.
   *
   * @param body                  the body of the response
   * @param returnType            the type of the return value
   * @param selectedContentType   the content type selected
   * @param selectedConverterType the converter type selected
   * @param request               the current request
   * @param response              the current response
   * @return the processed body, potentially with added metadata
   */
  @Override
  public Object beforeBodyWrite(Object body,
      @NonNull MethodParameter returnType,
      @NonNull MediaType selectedContentType,
      @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
      @NonNull ServerHttpRequest request,
      @NonNull ServerHttpResponse response) {

    if (body instanceof ResponseDto<?> responseDTO) {
      insertMetaData(request, response, responseDTO);
    }

    return body;
  }

  private void insertMetaData(ServerHttpRequest request, ServerHttpResponse response, ResponseDto<?> responseDto) {
    log.trace("insertMetaData: request = {}, response = {}, responseDto = {}", request, response, responseDto);

    Map<String, Object> meta = responseDto.getMeta();
    ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) (request instanceof ServletServerHttpRequest ? request : null);
    ServletServerHttpResponse servletResponse =
        (ServletServerHttpResponse) (response instanceof ServletServerHttpResponse ? response : null);
    if (servletRequest != null && servletResponse != null) {
      insertMetaData(servletRequest.getServletRequest(), servletResponse.getServletResponse(), meta);
    }
  }

  private void insertMetaData(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
    insertProcessingTime(request, map);
    insertHttpStatus(response, map);
  }

  private void insertHttpStatus(HttpServletResponse response, Map<String, Object> map) {
    map.put(PARAM_HTTP_STATUS, HttpStatus.valueOf(response.getStatus()).name());
  }

  private void insertProcessingTime(HttpServletRequest request, Map<String, Object> map) {
    Instant requestTs = (Instant) request.getAttribute(ServletAttributeNames.REQUEST_TIMESTAMP);
    if (requestTs != null) {
      long durationMillis = Instant.now().toEpochMilli() - requestTs.toEpochMilli();
      Duration duration = Duration.of(durationMillis, ChronoUnit.MILLIS);
      map.put(PARAM_PROCESSING_TIME, TimeFormatUtils.formatDurationAsSecondsAndMillis(duration));
    }
  }
}
