#!/usr/bin/env python
import pika
import sys
from Base import config


host = config.host
port = config.port
connection = pika.BlockingConnection(pika.ConnectionParameters(host=host, port=port))
channel = connection.channel()

channel.exchange_declare(exchange='direct_logs', type='direct')

result = channel.queue_declare(exclusive=True)
queue_name = result.method.queue

log_level = ["trace", "debug", "info", "error"]
severities = sys.argv[1:]
if not severities:
    sys.stderr.write("Usage: %s %r\n" % (sys.argv[0], log_level))
    sys.exit(1)

for severity in severities:
    channel.queue_bind(exchange='direct_logs',
                       queue=queue_name,
                       routing_key=severity)

print(' [*] Waiting for logs. To exit press CTRL+C')


def callback(ch, method, properties, body):
    print(" [x] Received %r, %r, %r, %r" % (ch, method.routing_key, properties, body))


channel.basic_consume(callback, queue=queue_name, no_ack=True)

channel.start_consuming()
