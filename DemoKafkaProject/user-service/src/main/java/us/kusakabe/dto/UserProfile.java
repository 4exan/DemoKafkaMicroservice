package us.kusakabe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import us.kusakabe.entity.ProfilePicture;

@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfile {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private byte[] profilePictureBytes;
    private boolean isFollowed;

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public byte[] getProfilePictureBytes() {
        return profilePictureBytes;
    }

    public boolean isFollowed() {
        return isFollowed;
    }

    private UserProfile(UserProfileBuilder builder){
        this.username = builder.username;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.phone = builder.phone;
        this.profilePictureBytes = builder.profilePictureBytes;
        this.isFollowed = builder.isFollowed;
    }

    public static class UserProfileBuilder{
        private String username;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private byte[] profilePictureBytes;
        private boolean isFollowed;

        public UserProfileBuilder(String username, String firstName, String lastName, String email, String phone) {
            this.username = username;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.phone = phone;
        }

        public UserProfileBuilder profilePictureBytes(byte[] profilePictureBytes) {
            this.profilePictureBytes = profilePictureBytes;
            return this;
        }

        public UserProfileBuilder isFollowed(boolean isFollowed) {
            this.isFollowed = isFollowed;
            return this;
        }

        public UserProfile build() {
            return new UserProfile(this);
        }
    }

}

