import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import AuthService from "../service/AuthService";
import { useAuth } from "../context/AuthContext";

export default function Navbar() {
  const { isAuthenticated, isAdmin, logout } = useAuth();
  const [search, setSearch] = useState("");
  const navigate = useNavigate();
  const [dropdownVisible, setDropdownVisible] = useState(false);

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

  const toggleDropdown = () => {
    setDropdownVisible(() => !dropdownVisible);
  };

  return (
    <nav className="navbar tred border-b-2 border-gray-400 py-2">
      <ul>
        {
          <li className="inline font-montserrat text-xl transition-all hover:border-y-2 hover:border-black hover:rounded-lg px-4">
            <Link to="/">Home</Link>
          </li>
        }
        {!isAuthenticated && (
          <li className="inline font-montserrat text-xl transition-all hover:border-y-2 hover:border-black hover:rounded-lg px-4">
            <Link to="/login">Login</Link>
          </li>
        )}
        {!isAuthenticated && (
          <li className="inline font-montserrat text-xl transition-all hover:border-y-2 hover:border-black hover:rounded-lg px-4">
            <Link to="/registration">Registration</Link>
          </li>
        )}
        {isAuthenticated && (
          <li className="inline font-montserrat text-xl transition-all hover:border-y-2 hover:border-black hover:rounded-lg px-4">
            <Link to="/user/profile">My Profile</Link>
          </li>
        )}
        {isAuthenticated && (
          <li className="dropdown inline font-montserrat text-xl transition-all hover:border-y-2 hover:border-black hover:rounded-lg px-4">
            <button onClick={toggleDropdown}>Posts</button>
            <div className="dropdown-container ">
              <Link
                className="dropdown-item transition-all hover:border-y-2 hover:border-black hover:rounded-lg"
                to={"/post/feed"}
              >
                Feed
              </Link>
              <Link
                className="dropdown-item transition-all hover:border-y-2 hover:border-black hover:rounded-lg"
                to={"/post/liked"}
              >
                Liked
              </Link>
            </div>
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
          <li className="inline font-montserrat text-xl transition-all hover:border-y-2 hover:border-black hover:rounded-lg px-4">
            <Link to="/test/test">Users</Link>
          </li>
        )}
        {isAuthenticated && (
          <li className="inline font-montserrat text-xl px-4">|</li>
        )}
        {isAuthenticated && (
          <li className="inline font-montserrat text-xl">
            <input
              type="text"
              className=" text-center px-2 border-2 border-gray-200 rounded-lg transition-all hover:border-black hover:text-black hover:bg-white"
              value={search}
              onChange={(e) => setSearch(e.target.value)}
            />
            <Link
              className="inline font-montserrat text-xl transition-all hover:border-y-2 hover:border-black hover:rounded-lg px-4"
              to={`/user/search/${search}`}
              state={{ username: `${search}` }}
            >
              Search
            </Link>
          </li>
        )}
        {isAuthenticated && (
          <li className="inline absolute right-0 font-montserrat text-xl transition-all hover:border-y-2 hover:border-black hover:rounded-lg px-4">
            <Link to="/" onClick={handleLogout}>
              Logout
            </Link>
          </li>
        )}
      </ul>
    </nav>
  );
}
