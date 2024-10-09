import { useState } from "react";
import UserService from "../service/UserService";

export default function EditProfileModel() {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    phone: "",
  });
  const [profileImage, setProfileImage] = useState(null);

  const handleImageChange = (e) => {
    setProfileImage(e.target.files[0]);
    console.log(profileImage);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (profileImage) {
      const formData = new FormData();
      formData.append("image", profileImage);
      const token = localStorage.getItem("token");
      const response = UserService.uploadImage(formData, token);
      console.log(response);
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
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
  );
}
