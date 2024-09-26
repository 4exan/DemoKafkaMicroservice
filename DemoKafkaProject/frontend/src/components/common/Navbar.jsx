import React, { useState } from "react";
import { Link } from "react-router-dom";
import AuthService from "../service/AuthService";
import { useAuth } from "../context/AuthContext";

export default function Navbar() {
  const { isAuthenticated, isAdmin, logout } = useAuth();

  const handleLogout = () => {
    const confirmLogout = window.confirm("Are you sure you want to logout?");
    if (confirmLogout) {
      logout();
    }
  };

  return (
    <nav className="navbar tred border-b-2 py-2">
      <ul>
        {!isAuthenticated && (
          <li className="inline mx-2 font-montserrat text-xl">
            <Link to="/">Home</Link>
          </li>
        )}
        {!isAuthenticated && (
          <li className="inline mx-2 font-montserrat text-xl">
            <Link to="/login">Login</Link>
          </li>
        )}
        {!isAuthenticated && (
          <li className="inline mx-2 font-montserrat text-xl">
            <Link to="/registration">Registration</Link>
          </li>
        )}
        {isAuthenticated && (
          <li className="inline mx-2 font-montserrat text-xl">
            <Link to="/user/profile">Profile</Link>
          </li>
        )}
        {isAuthenticated && (
          <li className="inline mx-2 font-montserrat text-xl">
            <Link to="/user/dashboard">Dashboard</Link>
          </li>
        )}
        {isAdmin && (
          <li className="inline mx-2 font-montserrat text-xl">
            <Link to="/admin/user-management">User Management</Link>
          </li>
        )}
        {isAdmin && (
          <li className="inline mx-2 font-montserrat text-xl">
            <Link to="/admin/package">Package info</Link>
          </li>
        )}
        {isAuthenticated && (
          <li className="inline mx-2 font-montserrat text-xl">
            <Link to="/" onClick={handleLogout}>
              Logout
            </Link>
          </li>
        )}
      </ul>
    </nav>
  );
}
