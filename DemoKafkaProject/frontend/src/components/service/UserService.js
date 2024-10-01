import axios from "axios";

export default class UserService {
  static BASE_URL = "http://localhost:8765";

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
}
