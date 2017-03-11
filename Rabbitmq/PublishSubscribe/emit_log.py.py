#!/usr/bin/env python
import pika
from Base import config

host = config.host
port = config.port
connection = pika.BlockingConnection(pika.ConnectionParameters(host=host, port=port))
channel = connection.channel()

channel.exchange_declare(exchange='logs', type='fanout')


def publish(_channel, message):
    _channel.basic_publish(exchange='logs', routing_key='', body=message)
    print(" [x] Sent %r" % message)

print("========= Start publish message ===========")
while True:
    content = str(input("Please input message content:\n"))
    if len(content) > 0:
        publish(channel, content)
    else:
        break

connection.close()
