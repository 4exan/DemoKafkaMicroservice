import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import AuthService from "../service/AuthService";
import { useAuth } from "../context/AuthContext";

export default function Navbar() {
  const { isAuthenticated, isAdmin, logout } = useAuth();
  const [search, setSearch] = useState("");
  const navigate = useNavigate();

  const handleLogout = () => {
    const confirmLogout = window.confirm("Are you sure you want to logout?");
    if (confirmLogout) {
      logout();
    }
  };

  const handleSearch = () => {
    e.preventDefault();

    if (search != "") {
      navigate(`/user/${search}`);
    }
  };

  return (
    <nav className="navbar tred border-b-2 py-2">
      <ul>
        {
          <li className="inline font-montserrat text-xl transition-all hover:bg-black hover:text-white p-2">
            <Link to="/">Home</Link>
          </li>
        }
        {!isAuthenticated && (
          <li className="inline font-montserrat text-xl transition-all hover:bg-black hover:text-white p-2">
            <Link to="/login">Login</Link>
          </li>
        )}
        {!isAuthenticated && (
          <li className="inline font-montserrat text-xl transition-all hover:bg-black hover:text-white p-2">
            <Link to="/registration">Registration</Link>
          </li>
        )}
        {isAuthenticated && (
          <li className="inline font-montserrat text-xl transition-all hover:bg-black hover:text-white p-2">
            <Link to="/user/profile">Profile</Link>
          </li>
        )}
        {isAuthenticated && (
          <li className="inline font-montserrat text-xl transition-all hover:bg-black hover:text-white p-2">
            <Link to="/user/dashboard">Posts</Link>
          </li>
        )}
        {/* {isAdmin && (
          <li className="inline mx-2 font-montserrat text-xl">
            <Link to="/admin/user-management">User Management</Link>
          </li>
        )}
        {isAdmin && (
          <li className="inline mx-2 font-montserrat text-xl">
            <Link to="/admin/package">Package info</Link>
          </li>
        )} */}
        {isAuthenticated && (
          <li className="inline font-montserrat text-xl transition-all hover:bg-black hover:text-white p-2">
            <Link to="/user/search">Users</Link>
          </li>
        )}
        {isAuthenticated && (
          <li className="inline absolute top-0 right-0 font-montserrat text-xl transition-all hover:bg-black hover:text-white p-2">
            <Link to="/" onClick={handleLogout}>
              Logout
            </Link>
          </li>
        )}
      </ul>
    </nav>
  );
}
