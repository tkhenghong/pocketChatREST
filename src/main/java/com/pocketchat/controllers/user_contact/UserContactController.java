package com.pocketchat.controllers.user_contact;

import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.models.controllers.request.user_contact.GetUserOwnUserContactsRequest;
import com.pocketchat.models.controllers.request.user_contact.UpdateUserContactRequest;
import com.pocketchat.models.controllers.response.multimedia.MultimediaResponse;
import com.pocketchat.models.controllers.response.user_contact.UserContactResponse;
import com.pocketchat.services.user_contact.UserContactService;
import com.pocketchat.utils.pagination.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/userContact")
public class UserContactController {

    private final UserContactService userContactService;

    private final PaginationUtil paginationUtil;

    @Autowired
    public UserContactController(UserContactService userContactService, PaginationUtil paginationUtil) {
        this.userContactService = userContactService;
        this.paginationUtil = paginationUtil;
    }

    @PutMapping("")
    public UserContactResponse editOwnUserContact(@Valid @RequestBody UpdateUserContactRequest userContact) {
        return userContactService.userContactResponseMapper(userContactService.editOwnUserContact(userContact));
    }

    @PutMapping("/profilePhoto")
    public MultimediaResponse uploadOwnUserContactProfilePhoto(@RequestParam("file") MultipartFile multipartFile) {
        return userContactService.uploadOwnUserContactProfilePhoto(multipartFile);
    }

    /**
     * This is used for getting own profile photo without UserContact's ID.
     * Typically used to load profile pictures of the group members of the conversation.
     *
     * @param httpServletRequest: HttpServletRequest auto instantiated by Spring Boot Web.
     * @return Resource object as Server Response.
     */
    // https://bezkoder.com/spring-boot-upload-multiple-files
    // https://dzone.com/articles/java-springboot-rest-api-to-uploaddownload-file-on
    @GetMapping("/profilePhoto")
    public ResponseEntity<Resource> getOwnUserContactProfilePhoto(HttpServletRequest httpServletRequest) {
        File file;
        Resource resource;
        try {
            file = userContactService.getOwnUserContactProfilePhoto();
            resource = new UrlResource(file.toURI());
        } catch (FileNotFoundException | MalformedURLException exception) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(determineFileContentType(httpServletRequest, file)))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }

    /**
     * This is used for getting anyone's profile photo with UserContact's ID.
     * Typically used to load profile pictures of the group members of the conversation.
     *
     * @param userContactId:      ID of the UserContact object.
     * @param httpServletRequest: HttpServletRequest auto instantiated by Spring Boot Web.
     * @return Resource object as Server Response.
     */
    @GetMapping("{userContactId}/profilePhoto")
    public ResponseEntity<Resource> getUserContactProfilePhoto(@PathVariable String userContactId, HttpServletRequest httpServletRequest) {
        File file;
        Resource resource;
        try {
            file = userContactService.getUserContactProfilePhoto(userContactId);
            resource = new UrlResource(file.toURI());
        } catch (FileNotFoundException | MalformedURLException exception) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(determineFileContentType(httpServletRequest, file)))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }

    @DeleteMapping("/profilePhoto")
    public void deleteOwnUserContactProfilePhoto() {
        userContactService.deleteOwnUserContactProfilePhoto();
    }

    @GetMapping("/{userContactId}")
    public UserContactResponse getUserContact(@PathVariable String userContactId) {
        return userContactService.userContactResponseMapper(userContactService.getUserContact(userContactId));
    }

    @GetMapping("/mobileNo/{mobileNo}")
    public UserContactResponse getUserContactByMobileNo(@PathVariable String mobileNo) {
        return userContactService.userContactResponseMapper(userContactService.getUserContactByMobileNo(mobileNo));
    }

    @GetMapping("/conversationGroup/{conversationGroupId}")
    public List<UserContactResponse> getUserContactsByConversationGroup(@PathVariable String conversationGroupId) {
        return userContactService.getUserContactsByConversationGroup(conversationGroupId)
                .stream().map(userContactService::userContactResponseMapper).collect(Collectors.toList());
    }

    @GetMapping("")
    public UserContactResponse getOwnUserContact() {
        return userContactService.userContactResponseMapper(userContactService.getOwnUserContact());
    }

    /**
     * Get user contacts with pagination.
     *
     * @param getUserOwnUserContactsRequest: GetUserOwnUserContactsRequest object, which contains search term, which is get either name or mobile number of the user contact.
     * @return Page of UserContact object.
     */
    @GetMapping("/user")
    public Page<UserContactResponse> getUserContactsOfAUser(@Valid GetUserOwnUserContactsRequest getUserOwnUserContactsRequest) {
        Pageable pageable = paginationUtil.convertPageableImplToPageable(getUserOwnUserContactsRequest.getPageable());
        Page<UserContact> userContacts = userContactService.getUserContactsOfAUser(getUserOwnUserContactsRequest.getSearchTerm(), pageable);
        return userContactService.userContactPageResponseMapper(userContacts);
    }

    /**
     * Determine the content type based on the File object.
     *
     * @return content type in String. For example: application/octet-stream
     */
    // https://dzone.com/articles/java-springboot-rest-api-to-uploaddownload-file-on
    String determineFileContentType(HttpServletRequest httpServletRequest, File file) {
        String contentType = httpServletRequest.getServletContext().getMimeType(file.getAbsolutePath());

        if (StringUtils.isEmpty(contentType)) {
            contentType = "application/octet-stream";
        }
        return contentType;
    }
}
