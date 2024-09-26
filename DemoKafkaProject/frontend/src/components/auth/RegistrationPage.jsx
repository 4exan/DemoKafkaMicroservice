import { useState } from "react";
import { useNavigate } from "react-router";
import AuthService from "../service/AuthService";

export default function RegistrationPage() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    firstname: "",
    lastname: "",
    email: "",
    phone: "",
    role: "USER",
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
    console.log(formData);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const token = localStorage.getItem("token");
      await AuthService.registration(formData, token);
      setFormData({
        username: "",
        password: "",
        firstname: "",
        lastname: "",
        email: "",
        phone: "",
        role: "USER",
      });
      alert("User registered successfully!");
      navigate("/user/profile");
    } catch (error) {
      console.log("Registration error: ", error);
      alert("Registration failed!");
    }
  };

  return (
    <div>
      <form className="form p-4 text-center">
        <h1 className="font-semibold font-montserrat text-xl">Registration</h1>
        <div className="p-2">
          <label>Username:</label>
          <input
            type="text"
            className="border-b-2 rounded-sm mx-2 text-center"
          ></input>
        </div>
        <div className="p-2">
          <label>Password:</label>
          <input
            type="password"
            className="border-b-2 rounded-sm mx-2 text-center"
          ></input>
        </div>
        <div className="p-2">
          <label>Firstname:</label>
          <input
            type="text"
            className="border-b-2 rounded-sm mx-2 text-center"
          ></input>
        </div>
        <div className="p-2">
          <label>Lastname:</label>
          <input
            type="text"
            className="border-b-2 rounded-sm mx-2 text-center"
          ></input>
        </div>
        <div className="p-2">
          <label>Email:</label>
          <input
            type="text"
            className="border-b-2 rounded-sm mx-2 text-center"
          ></input>
        </div>
        <div className="p-2">
          <label>Phone:</label>
          <input
            type="text"
            className="border-b-2 rounded-sm mx-2 text-center"
          ></input>
        </div>
        <input type="submit" value="Register" className="cursor-pointer mx-2" />
      </form>
    </div>
  );
}
