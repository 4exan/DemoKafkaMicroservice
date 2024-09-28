import { useState } from "react";
import { useEffect } from "react";
import AuthService from "../service/AuthService";

export default function Profile() {
  const [profile, setProfile] = useState({});

  useEffect(() => {
    fetchUserProfile();
  }, []);

  const fetchUserProfile = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await AuthService.getMyProfile(token);
      console.log(response);
      setProfile(response);
    } catch (error) {
      throw error;
    }
  };

  return (
    <div className="px-10 py-5">
      <h1 className="font-semibold text-2xl text-center font-montserrat">
        Profile page!
      </h1>
      <div>
        <p className="font-semibold text-xl font-montserrat">
          Username:{" "}
          <span className="font-normal text-xl mx-2 font-montserrat">
            {profile.username}
          </span>
        </p>
        <p className="font-semibold text-xl font-montserrat">
          Full name:
          <span className="font-normal text-xl mx-2 font-montserrat">
            {profile.firstName} {profile.lastName}
          </span>
        </p>
        <p className="font-semibold text-xl font-montserrat">
          Email:
          <span className="font-normal text-xl mx-2 font-montserrat">
            {profile.email}
          </span>
        </p>
        <p className="font-semibold text-xl font-montserrat">
          Phone:
          <span className="font-normal text-xl mx-2 font-montserrat">
            {profile.phone}
          </span>
        </p>
      </div>
    </div>
  );
}
