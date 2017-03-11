#!/usr/bin/env python
import pika
from Base import config

host = config.host
port = config.port
connection = pika.BlockingConnection(pika.ConnectionParameters(host=host, port=port))
# now we connected to the MQ
channel = connection.channel()
# Just create a queue to send message next
channel.queue_declare(queue='hello')
# In RabbitMQ a message can never be sent directly to the queue, it always needs to go through an exchange.
channel.basic_publish(exchange='',
                      routing_key='hello',
                      body='Hello Hinsteny!')

print(" [x] Sent 'Hello Hisoka!'")

connection.close()