package com.pocketchat.server.configurations.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.ObjectUtils;

public class WebsocketChannelInterceptor implements ChannelInterceptor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        logger.info("WebSocket Authentication");
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if(!ObjectUtils.isEmpty(accessor.getCommand())) {
            logger.info("accessor.getCommand().getMessageType().name(): {}",  accessor.getCommand().getMessageType());
            logger.info("accessor.getHost(): {}", accessor.getHost());
            logger.info("accessor.getReceipt(): {}", accessor.getReceipt());
            logger.info("accessor.getMessage(): {}", accessor.getMessage());
            logger.info("accessor.getDestination(): {}", accessor.getDestination());
        } else {
            logger.info("No Websocket Accessor Command");
        }

        switch (accessor.getCommand()) {
            case CONNECT:
                logger.info("StompCommand.CONNECT");
                // Authentication user =
                break;
            default:
                break;
        }

        return message;
    }
}
