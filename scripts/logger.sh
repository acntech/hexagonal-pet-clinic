#!/bin/bash

# Colors for log levels
RESET='\033[0m' # No Color
CYAN='\033[0;36m'
GREEN='\033[0;32m'
ORANGE='\033[38;5;214m' # Orange-like color using extended color codes
RED='\033[0;31m'
PURPLE='\033[0;35m'

START_TIME=$(date +%s)

# Log levels
TRACE=0
DEBUG=1
INFO=2
WARN=3
ERROR=4

# Default log level: INFO (2)
LOG_LEVEL=${LOG_LEVEL:-$DEBUG}

# Internal function to handle logging based on level
_log() {
    local level=$1
    local color=$2
    local prefix=$3
    local message=$4

    if [ "$LOG_LEVEL" -le "$level" ]; then
        echo -e "${color}$(date '+%Y-%m-%d %H:%M:%S') [${prefix}] $message${RESET}"
  fi
}

# Log functions for each level
log_trace() {
    _log $TRACE "$PURPLE" "TRACE" "$1"
}

log_debug() {
    _log $DEBUG "$CYAN" "DEBUG" "$1"
}

log_info() {
    _log $INFO "$GREEN" "INFO " "$1"
}

log_warn() {
    _log $WARN "$ORANGE" "WARN " "$1"
}

log_error() {
    _log $ERROR "$RED" "ERROR" "$1"
}

# Function to print the elapsed time since the script started
print_elapsed_time() {
    local end_time=$(date +%s)
    local elapsed_time=$((end_time - START_TIME))
    log_info "Elapsed time: $elapsed_time seconds"
}

# Handle --log-level argument
parse_log_level() {
    for arg in "$@"; do
        case $arg in
            --log-level)
                shift
                case "$1" in
                    TRACE) LOG_LEVEL=$TRACE ;;
                    DEBUG) LOG_LEVEL=$DEBUG ;;
                    INFO) LOG_LEVEL=$INFO ;;
                    WARN) LOG_LEVEL=$WARN ;;
                    ERROR) LOG_LEVEL=$ERROR ;;
                    *)
                        echo -e "${RED}[ERROR] Invalid log level: $1. Use TRACE, DEBUG, INFO, WARN, or ERROR.${RESET}"
                        exit 1
                        ;;
        esac
                ;;
    esac
  done

    export LOG_LEVEL
}

filter_logger_args() {
    local filtered_args=()
    local skip_next=false

    for arg in "$@"; do
        if $skip_next; then
            skip_next=false
            continue
    fi
        if [ "$arg" = "--log-level" ]; then
            skip_next=true
            continue
    fi
        filtered_args+=("$arg")
  done

    # Update the caller's positional arguments
    echo "${filtered_args[@]}"
}

# If logger.sh is sourced, allow scripts to pass their arguments for log-level parsing
if [ "${BASH_SOURCE[0]}" != "$0" ]; then
    parse_log_level "$@"
fi

# Test the logger when run standalone
if [ "${BASH_SOURCE[0]}" = "$0" ]; then
    parse_log_level "$@"
    echo "Testing logger with LOG_LEVEL=$LOG_LEVEL"
    log_trace "This is a TRACE message."
    log_debug "This is a DEBUG message."
    log_info "This is an INFO message."
    log_warn "This is a WARN message."
    log_error "This is an ERROR message."
fi
