import { useEffect, useState } from "react";
import { useLocation } from "react-router";
import UserService from "../service/UserService";
import PostService from "../service/PostService";
import Post from "../common/Post";

export default function UserPage() {
  const { state } = useLocation();
  const [profile, setProfile] = useState({});
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    fetchUserProfile();
    fetchUserPosts();
  }, [state.username]);

  const fetchUserProfile = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await UserService.getUserProfile(token, state.username);
      setProfile(response);
    } catch (error) {
      throw error;
    }
  };

  const fetchUserPosts = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await PostService.getUserPosts(token, state.username);
      setPosts(response.postList.sort((a, b) => b.id - a.id));
    } catch (error) {
      throw error;
    }
  };

  return (
    <div className="px-10 py-5" id="profile">
      {/* <button className="absolute text-red-600 border-2 border-black rounded-lg px-2 transition-all hover:bg-red-600 hover:text-white hover:border-transparent">
        Back
      </button> */}
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
      <div>
        <h1 className="text-center font-bold text-2xl my-4">Posts!</h1>
        <div className="table-container">
          <table className="">
            <thead className=""></thead>
            <tbody>
              {posts.map((post) => (
                <tr className="">
                  <td className="break-normal">
                    <Post post={post} key={post.id} />
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
