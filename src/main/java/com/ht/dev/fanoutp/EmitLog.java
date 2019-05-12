package com.ht.dev.fanoutp;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLog {

  private static final String EXCHANGE_NAME = "logs";

  public static void main(String[] argv) throws Exception
  {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
      try (Connection connection = factory.newConnection()) {
          Channel channel = connection.createChannel();

         //definir un exchange de type FANOUT
          channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

          //recuperer le message sur la ligne de cmd
          String message = getMessage(argv);

          //pas de definition de routing key
          channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
          System.out.println(" [x] Envoi de : '" + message + "'");

          channel.close();
      }
  }

  private static String getMessage(String[] strings)
  {
    if (strings.length < 1)
    	    return "info: Bonjour le monde!";
    return joinStrings(strings, " ");
  }

  private static String joinStrings(String[] strings, String delimiter)
  {
    int length = strings.length;
    if (length == 0) return "";
    StringBuilder words = new StringBuilder(strings[0]);
    for (int i = 1; i < length; i++) {
        words.append(delimiter).append(strings[i]);
    }
    return words.toString();
  }
}

