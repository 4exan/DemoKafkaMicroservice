import axios from "axios";

export default class PostService {
  static BASE_URL = "http://localhost:8765";

  static async getProfilePosts(token) {
    try {
      const response = await axios.get(`${this.BASE_URL}/post/get`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      return response.data;
    } catch (err) {
      throw err;
    }
  }

  static async getUserPosts(token, username) {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/post/user/${username}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      return response.data;
    } catch (err) {
      throw err;
    }
  }

  static async getUserLikes(token) {
    try {
      const response = await axios.get(`${this.BASE_URL}/post/like/get`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      return response.data;
    } catch (error) {
      throw error;
    }
  }

  static async createNewPosts(token, formData) {
    try {
      const response = await axios.post(
        `${this.BASE_URL}/post-create/new`,
        formData,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      return response.data;
    } catch (err) {
      throw err;
    }
  }

  static async deletePosts(token, id) {
    try {
      const response = await axios.delete(`${this.BASE_URL}/post/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
    } catch (err) {
      throw err;
    }
  }
}
