#!/usr/bin/env python
import pika
import sys
from Base import config

host = config.host
port = config.port
connection = pika.BlockingConnection(pika.ConnectionParameters(host=host, port=port))
channel = connection.channel()

channel.exchange_declare(exchange='topic_logs', type='topic')


def publish(_channel, routing_key, message):
    print(" [x] Sent %r %r" % (routing_key, content))
    _channel.basic_publish(exchange='topic_logs', routing_key=routing_key, body=message)

while True:
    content = str(input("Please input message content:\n"))
    if len(content) > 0:
        index = content.find("#")
        if index > 0:
            publish(channel, content[0:index], content[index+1:len(content)])
    else:
        break

connection.close()
