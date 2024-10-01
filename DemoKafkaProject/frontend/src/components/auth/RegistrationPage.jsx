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
      <form className="form p-4">
        <h1 className="font-semibold text-center font-montserrat text-xl">
          Registration
        </h1>
        <div className="px-2">
          <label className="text-left">Username:</label>
          <input
            type="text"
            className="relative -right-4 border-2 rounded-lg m-2 text-center border-gray-300 transition-all hover:border-black"
          ></input>
        </div>
        <div className="px-2">
          <label>Password:</label>
          <input
            type="password"
            className="relative -right-6 border-2 rounded-lg m-2 text-center border-gray-300 transition-all hover:border-black"
          ></input>
        </div>
        <div className="px-2">
          <label>Firstname:</label>
          <input
            type="text"
            className="relative -right-5 border-2 rounded-lg m-2 text-center border-gray-300 transition-all hover:border-black"
          ></input>
        </div>
        <div className="px-2">
          <label>Lastname:</label>
          <input
            type="text"
            className="relative -right-6 border-2 rounded-lg m-2 text-center border-gray-300 transition-all hover:border-black"
          ></input>
        </div>
        <div className="px-2">
          <label>Email:</label>
          <input
            type="text"
            className="relative -right-14 border-2 rounded-lg m-2 text-center border-gray-300 transition-all hover:border-black"
          ></input>
        </div>
        <div className="px-2">
          <label>Phone:</label>
          <input
            type="text"
            className="relative -right-12 border-2 rounded-lg m-2 text-left border-gray-300 transition-all hover:border-black"
          ></input>
        </div>
        <div className="text-center">
          <input
            type="submit"
            value="Register"
            className="cursor-pointer px-2 border-2 border-black rounded-lg transition-all hover:bg-black hover:text-white hover:border-transparent"
          />
        </div>
      </form>
    </div>
  );
}
