package com.pocketchat.services.conversation_group.controller;

import com.pocketchat.controllers.conversation_group.ConversationGroupController;
import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.models.enums.conversation_group.ConversationGroupType;
import com.pocketchat.services.conversation_group.ConversationGroupService;
import com.pocketchat.services.unread_message.UnreadMessageService;
import com.pocketchat.utils.pagination.PaginationUtil;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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

// https://www.infoworld.com/article/3537563/junit-5-tutorial-part-1-unit-testing-with-junit-5-mockito-and-hamcrest.html
@ExtendWith(SpringExtension.class)
@WebMvcTest(
        value = ConversationGroupController.class,
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        value = ConversationGroupController.class
                )
        }
)
@AutoConfigureMockMvc(addFilters = false)
class ConversationGroupControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ConversationGroupService conversationGroupService;

    @MockBean
    UnreadMessageService unreadMessageService;

    @MockBean
    PaginationUtil paginationUtil;

    @Test
    @DisplayName("Test Get Conversation Group API")
    @WithMockUser
        // @Disabled
    void testConversationGroupController() throws Exception {
        String conversationGroupId = UUID.randomUUID().toString();
        ConversationGroup entity = generateConversationGroupObject();
        String url = "/conversationGroup/" + conversationGroupId;

        MvcResult mvcResult = mockMvc.perform(get(url)).andDo(print()).andExpect(status().isOk())
                .andReturn();

        Mockito.verify(conversationGroupService).getSingleConversation(eq(conversationGroupId));

        String content = mvcResult.getResponse().getContentAsString(); // TODO: Unable to get correct response from the API call
        // Need to try RESTTemplate type of tests

        System.out.println("CONTENT: " + content);
    }

    private ConversationGroup generateConversationGroupObject() {
        List<String> memberIds = generateRandomObjectIds(10);

        return ConversationGroup.builder()
                .conversationGroupType(ConversationGroupType.Group)
                .name(UUID.randomUUID().toString())
                .memberIds(memberIds)
                .adminMemberIds(Collections.singletonList(memberIds.get(0)))
                .description(UUID.randomUUID().toString())
                .creatorUserId(memberIds.get(0))
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
