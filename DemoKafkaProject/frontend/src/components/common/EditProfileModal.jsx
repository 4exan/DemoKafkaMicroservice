import { useState } from "react";
import UserService from "../service/UserService";

export default function EditProfileModal({ isOpen, setIsOpen, profile }) {
  const [profileData, setProfileData] = useState({
    firstName: profile.firstName,
    lastName: profile.lastName,
    email: profile.email,
    phone: profile.phone,
  });
  const [profileImage, setProfileImage] = useState(null);

  const handleImageChange = (e) => {
    setProfileImage(e.target.files[0]);
    console.log(profileImage);
  };

  const handleChangeInfo = (e) => {
    const { name, value } = e.target;
    setProfileData({ ...profileData, [name]: value });
    console.log(profile);
  };

  const handleSubmitImage = async (e) => {
    e.preventDefault();
    if (profileImage) {
      const formData = new FormData();
      formData.append("image", profileImage);
      const token = localStorage.getItem("token");
      const response = UserService.uploadImage(formData, token);
      console.log(response);
    }
  };

  const handleSubmitInfo = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem("token");
      const response = await UserService.editUserProfile(profileData, token);
      alert("User updated successfully!");
    } catch (e) {
      throw e;
    }
  };

  return (
    <div className={isOpen ? "" : "hidden"}>
      <div className="modal-bg"></div>
      <div className="modal-edit">
        <h1>Edit profile</h1>
        <button
          className="fonst-semibold transition-all hover:bg-red-500 hover:text-white hover:border-transparent"
          onClick={() => setIsOpen()}
        >
          X
        </button>
      </div>
      <div className="absolute left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2 border-2 border-black rounded-lg p-2">
        <div>
          <h1 className="font-semibold text-xl">Edit profile picture:</h1>
          <form onSubmit={handleSubmitImage} className="p-2">
            <div>
              <input
                type="file"
                accept="image/"
                id="file"
                name="file"
                onChange={(e) => handleImageChange(e)}
              />
            </div>
            <button className="m-2 px-2 border-2 border-black rounded-lg transition-all hover:bg-black hover:text-white hover:border-transparent">
              Upload
            </button>
          </form>
        </div>
        <div className="border-t-2 border-gray-400"></div>
        <div className="p-2">
          <h1 className="font-semibold text-xl">Edit profile info:</h1>
          <form onSubmit={handleSubmitInfo}>
            <div className="p-2">
              <label>Firstname:</label>
              <input
                type="text"
                className="ml-2 text-center px-2 border-2 border-gray-200 rounded-lg transition-all hover:border-black hover:text-black hover:bg-white"
                name="firstName"
                value={profileData.firstName}
                onChange={handleChangeInfo}
              />
            </div>
            <div className="p-2">
              <label>Lastname:</label>
              <input
                type="text"
                className="ml-2 text-center px-2 border-2 border-gray-200 rounded-lg transition-all hover:border-black hover:text-black hover:bg-white"
                name="lastName"
                value={profileData.lastName}
                onChange={handleChangeInfo}
              />
            </div>
            <div className="p-2">
              <label>Email:</label>
              <input
                type="text"
                className="ml-2 text-center px-2 border-2 border-gray-200 rounded-lg transition-all hover:border-black hover:text-black hover:bg-white"
                name="email"
                value={profileData.email}
                onChange={handleChangeInfo}
              />
            </div>
            <div className="p-2">
              <label>Phone:</label>
              <input
                type="text"
                className="ml-2 text-center px-2 border-2 border-gray-200 rounded-lg transition-all hover:border-black hover:text-black hover:bg-white"
                name="phone"
                value={profileData.phone}
                onChange={handleChangeInfo}
              />
            </div>
            <input
              type="submit"
              className="px-2 border-2 border-black rounded-lg transition-all hover:bg-black hover:text-white hover:border-transparent"
            />
          </form>
        </div>
      </div>
    </div>
  );
}
