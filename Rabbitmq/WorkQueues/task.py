#!/usr/bin/env python
import pika
from Base import config

host = config.host
port = config.port
connection = pika.BlockingConnection(pika.ConnectionParameters(host=host, port=port))
channel = connection.channel()

channel.queue_declare(queue='task_queue', durable=True)


def publish(_channel, message):
    _channel.basic_publish(exchange='', routing_key='task_queue', body=message, properties=pika.BasicProperties(
                            # make message persistent
                            delivery_mode=2,
                            ))
    print(" [x] Sent %r" % message)

print("========= Start publish message ===========")
while True:
    content = str(input("Please input message content:\n"))
    if len(content) > 0:
        publish(channel, content)
    else:
        break

connection.close()
