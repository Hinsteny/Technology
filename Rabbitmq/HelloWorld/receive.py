#!/usr/bin/env python
import pika
from Base import config

host = config.host
port = config.port
connection = pika.BlockingConnection(pika.ConnectionParameters(host=host, port=port))
channel = connection.channel()

channel.queue_declare(queue='hello')


def callback(ch, method, properties, body):
    print(" [x] Received %r, %r, %r, %r" % (ch, method, properties, body))

channel.basic_consume(callback, queue='hello', no_ack=True)

print(' [*] Waiting for messages. To exit press CTRL+C')
channel.start_consuming()