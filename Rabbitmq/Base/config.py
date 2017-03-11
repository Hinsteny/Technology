#!/usr/bin/env python

# Some config info
host = "127.0.0.1"
port = 5672
socket_timeout = 60000


def dispose(content, log_level):
    result = []
    if len(content) > 0:
        index = content.find(' ')
        if index > 0:
            level = content[0:index]
            message = content[index+1: len(content)]
            if log_level.index(level) > -1:
                result.extend([level, message])

    return result



