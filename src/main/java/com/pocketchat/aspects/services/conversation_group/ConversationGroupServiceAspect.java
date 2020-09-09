package com.pocketchat.aspects.services.conversation_group;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.user.User;
import com.pocketchat.models.controllers.request.conversation_group.CreateConversationGroupRequest;
import com.pocketchat.services.user.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Aspect
@Configuration
public class ConversationGroupServiceAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;

    @Autowired
    public ConversationGroupServiceAspect(UserService userService) {
        this.userService = userService;
    }

    @Around("execution(* com.pocketchat.services.conversation_group.ConversationGroupService.addConversation(..))")
    public Object aroundAddConversationGroup(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("aroundAddConversationGroup()");
        long startTime = System.currentTimeMillis();
        String errorMessage = "";
        User currentUser = userService.getOwnUser();

        List<Object> args = Arrays.asList(pjp.getArgs());

        CreateConversationGroupRequest createConversationGroupRequest = (CreateConversationGroupRequest) args.get(0);

        logger.info("createConversationGroupRequest: {}", createConversationGroupRequest);

        args.forEach(o -> logger.info("o: {}", o));

        // TODO: Get value of the request objects.

        Object result = pjp.proceed();
        // TODO: Get the value of the response objects.
        // TODO: Send welcome message with RabbitMQ
        // TODO: Create UnreadMessage object for the ConversationGroup
        // TODO: Create Multimedia object for the ConversationGroup object's Profile Photo.

        ConversationGroup conversationGroup = (ConversationGroup) result;

        logger.info("conversationGroup: {}", conversationGroup);

        return result;
    }
}
