#!/usr/bin/env python
import pika
from Base import config

host = config.host
port = config.port
connection = pika.BlockingConnection(pika.ConnectionParameters(host=host, port=port))
channel = connection.channel()

channel.exchange_declare(exchange='direct_logs', type='direct')


def publish(_channel, routing, message):
    _channel.basic_publish(exchange='direct_logs', routing_key=routing, body=message)
    print(" [x] Sent %r" % message)

print("========= Start publish message ===========")

log_level = ["trace", "debug", "info", "error"]
while True:
    content = str(input("Please input message content:\n"))
    if len(content) > 0:
        result = config.dispose(content, log_level)
        if len(result) > 0:
            publish(channel, result[0], result[1])
    else:
        break

connection.close()
