import "./App.css";
import React from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { useAuth } from "./components/context/AuthContext";
import Navbar from "./components/common/Navbar";
import LoginPage from "./components/auth/LoginPage";
import RegistrationPage from "./components/auth/RegistrationPage";
import HomePage from "./components/page/HomePage";
import Profile from "./components/page/Profile";
import Test from "./components/page/test";
import UserPage from "./components/page/UserPage";
import LikedPosts from "./components/page/LikedPosts";
import FeedPage from "./components/page/FeedPage";
import ErrorPage from "./components/page/ErrorPage";

export default function App() {
  const { isAuthenticated, isAdmin } = useAuth();

  return (
    <BrowserRouter>
      <div className="App">
        <Navbar />
        <div className="content">
          <Routes>
            <Route exact path="/" element={<HomePage />} />
            <Route exact path="/login" element={<LoginPage />} />
            <Route path="/registration" element={<RegistrationPage />} />
            {isAuthenticated && (
              <>
                {/* Routes for authenticated users*/}
                <Route path="/user/profile" element={<Profile />} />
                <Route path="/user/search/:username" element={<UserPage />} />
                <Route path="/test/test" element={<Test />} />
                <Route path="/post/liked" element={<LikedPosts />} />
                <Route path="/post/feed" element={<FeedPage />} />
              </>
            )}
            {/* Check if user is authenticated and admin before rendering admin-only routes */}
            {isAdmin && <>{/* Routes for ADMIN roles*/}</>}
            <Route path="*" element={<ErrorPage />} />
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  );
}
