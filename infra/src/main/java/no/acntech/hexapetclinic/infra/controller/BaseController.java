package no.acntech.hexapetclinic.infra.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BaseController class serves as a base class for other controllers in the application.
 * <p>
 * This class provides a logging utility by initializing a protected logger instance which can be used by the subclasses for logging
 * purposes.
 */
public class BaseController {

  protected Logger log = LoggerFactory.getLogger(this.getClass());

}
