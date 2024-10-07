package us.kusakabe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import us.kusakabe.entity.ProfilePicture;

@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter
public class UserProfile {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private byte[] profilePictureBytes;
    private boolean isFollowed;
}
