import { useState } from "react";
import { useNavigate } from "react-router";
import AuthService from "../service/AuthService";
import { useAuth } from "../context/AuthContext";

export default function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();
  const { login } = useAuth();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const userData = await AuthService.login(username, password);
      if (userData.token) {
        localStorage.setItem("token", userData.token);
        localStorage.setItem("role", userData.role);
        login();
        navigate("/user/profile");
      }
    } catch (error) {
      setError(error);
      setTimeout(() => {
        setError("");
      }, 5000);
    }
  };

  return (
    <div>
      <form className="form p-4 text-center" onSubmit={handleSubmit}>
        <h2 className="text-xl font-semibold text-center py-4">Login</h2>
        <div className="p-2">
          <label className="">Login:</label>
          <br />
          <input
            type="text"
            className="border-2 rounded-lg m-2 text-center border-gray-300 transition-all hover:border-black"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          ></input>
        </div>
        <div className="p-2">
          <label>Password:</label>
          <br />
          <input
            type="password"
            className="border-2 rounded-lg m-2 text-center border-gray-300 transition-all hover:border-black"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          ></input>
        </div>
        <input
          type="submit"
          value="Login"
          className="cursor-pointer px-2 border-2 border-black rounded-lg transition-all hover:bg-black hover:text-white hover:border-transparent"
        ></input>
      </form>
    </div>
  );
}
