import axios from "axios";

export default class UserService {
  static BASE_URL = "http://localhost:8765";

  static async getMyProfile(token) {
    try {
      const response = await axios.get(`${this.BASE_URL}/user/profile`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      console.log(response.data);
      return response.data;
    } catch (err) {
      throw err;
    }
  }

  static async getUserProfile(token, username) {
    try {
      const response = await axios.get(`${this.BASE_URL}/user/${username}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      console.log(response.data);
      return response.data;
    } catch (err) {
      throw err;
    }
  }

  static async uploadImage(formData, token) {
    try {
      const response = await axios.post(
        `${this.BASE_URL}/user/image`,
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "multipart/form-data",
          },
        }
      );
      return response.data;
    } catch (error) {
      throw error;
    }
  }

  static async downloadImage(username, token) {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/user/image/${username}`,
        { headers: { Authorization: `Bearer ${token}` }, responseType: "blob" }
      );
      return response;
    } catch (error) {
      throw error;
    }
  }

  static async followUser(token, username) {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/user/follow/${username}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      return response;
    } catch (error) {
      throw error;
    }
  }

  static async unfollowUser(token, username) {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/user/unfollow/${username}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      return response;
    } catch (error) {
      throw error;
    }
  }

  static async isUserFollowed(token, username) {
    try {
      const response = await axios.get(
        `${UserService.BASE_URL}/user/followed/${username}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      return response;
    } catch (error) {
      throw error;
    }
  }
}
/*
FOLLOWER - FOLLOWED
4exan -> Svoboda
4exan -> Aboba
4exan -> Impostor

Svoboda -> 4exan
Impostor -> 4exan



*/
