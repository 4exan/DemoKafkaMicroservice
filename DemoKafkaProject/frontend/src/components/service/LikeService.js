import axios from "axios";

export default class LikeService {
  static BASE_URL = "http://localhost:8765";

  static async likePost(token, postId) {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/like-create/create/${postId}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        },
      );
      return response.data;
    } catch (err) {
      throw err;
    }
  }

  static async dislikePost(token, postId) {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/like/remove/${postId}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        },
      );
      return response.data;
    } catch (err) {
      throw err;
    }
  }

  static async getUserLikes(token) {
    try {
      const response = await axios.get(`${this.BASE_URL}/like/get`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      return response.data;
    } catch (error) {
      throw error;
    }
  }
}
