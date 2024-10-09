import axios from "axios";

export default class FollowService {
  static BASE_URL = "http://localhost:8765";

  static async followUser(token, username) {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/follow-create/${username}`,
        { headers: { Authorization: `Bearer ${token}` } },
      );
      return response;
    } catch (error) {
      throw error;
    }
  }

  static async unfollowUser(token, username) {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/follow/remove/${username}`,
        { headers: { Authorization: `Bearer ${token}` } },
      );
      return response;
    } catch (error) {
      throw error;
    }
  }

  static async isUserFollowed(token, username) {
    try {
      const response = await axios.get(
        `${UserService.BASE_URL}/follow/followed/${username}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        },
      );
      return response;
    } catch (error) {
      throw error;
    }
  }

  static async getFollowedPosts(token) {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/follow/get-followed-posts`,
        {
          headers: { Authorization: `Bearer ${token}` },
        },
      );
      return response;
    } catch (error) {
      throw error;
    }
  }
}
