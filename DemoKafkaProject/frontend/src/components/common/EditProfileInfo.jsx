import { useState } from "react";
import UserService from "../service/UserService.js";

export default function EditProfileInfo({ profile }) {
  const [formData, setFormData] = useState({
    firstName: profile.firstName,
    lastName: profile.lastName,
    email: profile.email,
    phone: profile.phone,
  });

  const handleChangeInfo = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
    console.log(profile);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem("token");
      const response = await UserService.editUserProfile(formData, token);
      alert("User updated successfully!");
    } catch (e) {
      throw e;
    }
  };

  return (
    <div className="p-2">
      <form onSubmit={handleSubmit}>
        <div className="p-2">
          <label>Firstname:</label>
          <input
            type="text"
            className="ml-2 text-center px-2 border-2 border-gray-200 rounded-lg transition-all hover:border-black hover:text-black hover:bg-white"
            name="firstName"
            value={formData.firstName}
            onChange={handleChangeInfo}
          />
        </div>
        <div className="p-2">
          <label>Lastname:</label>
          <input
            type="text"
            className="ml-2 text-center px-2 border-2 border-gray-200 rounded-lg transition-all hover:border-black hover:text-black hover:bg-white"
            name="lastName"
            value={formData.lastName}
            onChange={handleChangeInfo}
          />
        </div>
        <div className="p-2">
          <label>Email:</label>
          <input
            type="text"
            className="ml-2 text-center px-2 border-2 border-gray-200 rounded-lg transition-all hover:border-black hover:text-black hover:bg-white"
            name="email"
            value={formData.email}
            onChange={handleChangeInfo}
          />
        </div>
        <div className="p-2">
          <label>Phone:</label>
          <input
            type="text"
            className="ml-2 text-center px-2 border-2 border-gray-200 rounded-lg transition-all hover:border-black hover:text-black hover:bg-white"
            name="phone"
            value={formData.phone}
            onChange={handleChangeInfo}
          />
        </div>
        <input
          type="submit"
          className="px-2 border-2 border-black rounded-lg transition-all hover:bg-black hover:text-white hover:border-transparent"
        />
      </form>
    </div>
  );
}
