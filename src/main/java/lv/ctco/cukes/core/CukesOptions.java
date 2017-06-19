package lv.ctco.cukes.core;

/**
 * List of variables and options used in Cukes.
 */
public interface CukesOptions {

    String PROPERTIES_PREFIX = "cukes.";
    String HEADER_PREFIX = "header.";
    String DELIMITER = ",";

    String RESOURCES_ROOT = "resources_root";
    String BASE_URI = "base_uri";
    String PLUGINS = "plugins";
    String PROXY = "proxy";
    String AUTH_TYPE = "auth_type";
    String USERNAME = "username";
    String PASSWORD = "password";

    String ASSERTS_STATUS_CODE_DISPLAY_BODY = "asserts.status_code.display_body";
    String ASSERTS_STATUS_CODE_MAX_SIZE = "asserts.status_code.max_size";

    String LOGGING_LOGGER_NAME = "logging.http.logger.name";
    String LOGGING_REQUEST_INCLUDES = "logging.http.requests.include";
    String LOGGING_RESPONSE_INCLUDES = "logging.http.responses.include";

    String URL_ENCODING_ENABLED = "url_encoding_enabled";
    String RELAXED_HTTPS = "relaxed_https";

    String GZIP_SUPPORT = "gzip_support";
    String REQUEST_BODY_TEMPLATES_ENABLED = "request_body_templates_enabled";

    String CONTEXT_INFLATING_ENABLED = "context_inflating_enabled";
    String ASSERTIONS_DISABLED = "assertions_disabled";
    String LOADRUNNER_FILTER_BLOCKS_REQUESTS = "loadrunner_filter_blocks_requests";
}
