package com.pocketchat.conversation_group.controller;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.models.enums.conversation_group.ConversationGroupType;
import com.pocketchat.services.conversation_group.ConversationGroupService;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Reference: https://www.baeldung.com/spring-boot-testing
// https://mkyong.com/spring-boot/spring-boot-junit-5-mockito/
// https://spring.io/guides/gs/testing-web/
// Lessons learned: You do not use @WebMvcTest as it will only @Autowire or @Mock related @Service @Repository beans.
// You have to use the below 2 annotations to test the Controller layer.
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class ConversationGroupControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ConversationGroupService conversationGroupService;

    @Test
    @DisplayName("Conversation Group Controller Test")
    @Disabled
    public void testConversationGroupController() throws Exception {
        System.out.println("Conversation Group Controller Test Successful.");
        String conversationGroupId = UUID.randomUUID().toString();
        ConversationGroup entity = generateConversationGroupObject();
        String url = "/conversationGroup/" + conversationGroupId;

        Mockito.when(conversationGroupService.getSingleConversation(eq(conversationGroupId))).thenReturn(entity);

        MvcResult mvcResult = mockMvc.perform(get(url)).andDo(print()).andExpect(status().isNotFound()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();

        System.out.println("CONTENT: " + content);
    }

    private ConversationGroup generateConversationGroupObject() {
        List<String> memberIds = generateRandomObjectIds(10);

        return ConversationGroup.builder()
                .conversationGroupType(ConversationGroupType.Group)
                .name(UUID.randomUUID().toString())
                .memberIds(memberIds)
                .adminMemberIds(Collections.singletonList(memberIds.get(0)))
                .createdDate(new DateTime())
                .description(UUID.randomUUID().toString())
                .creatorUserId(memberIds.get(0))
                .notificationExpireDate(new DateTime())
                .block(false)
                .build();
    }

    private List<String> generateRandomObjectIds(Integer numberOfObjectIds) {
        List<String> objectIds = new ArrayList<>();
        for (int i = 0; i < numberOfObjectIds; i++) {
            objectIds.add(ObjectId.get().toHexString());
        }

        return objectIds;
    }
}
