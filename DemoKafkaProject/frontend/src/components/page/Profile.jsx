import { useState } from "react";
import { useEffect } from "react";
import AuthService from "../service/AuthService";
import PostService from "../service/PostService";
import Post from "../common/Post";
import PostModal from "../common/PostModal";

export default function Profile() {
  const [profile, setProfile] = useState({});
  const [posts, setPosts] = useState([]);
  const [isOpen, setIsOpen] = useState(false);
  const [doFetchPost, setDoFetchPost] = useState(false);
  const [filteredPosts, setFilteredPosts] = useState([]);

  useEffect(() => {
    fetchUserProfile();
    fetchUserPosts();
  }, []);

  function openModal() {
    setIsOpen(true);
    console.log("Open modal");
  }

  function closeModal() {
    setIsOpen(false);
    console.log("Close modal");
  }

  const fetchUserProfile = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await AuthService.getMyProfile(token);
      console.log(response); //REMOVE
      setProfile(response);
    } catch (error) {
      throw error;
    }
  };

  const fetchUserPosts = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await PostService.getUserPosts(token);
      console.log(response.postList); //REMOVE
      setPosts(response.postList.sort((a, b) => b.id - a.id));
      setFilteredPosts(posts);
      console.log(`Filtered posts: ${filteredPosts}`);
    } catch (error) {
      throw error;
    }
  };

  const handleRemovePost = async (id) => {
    try {
      const confirmDelete = window.confirm(
        "Are you sure you want to delete this user?"
      );
      const token = localStorage.getItem("token");
      if (confirmDelete) {
        console.log(id);
        await PostService.deletePosts(token, id);
      }
      fetchUserPosts();
    } catch (error) {
      throw error;
    }
  };

  return (
    <div className="px-10 py-5" id="profile">
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
        <div className="my-4 text-center">
          <button
            className="text-3xl font-semibold transition-all hover:bg-black hover:text-white border-2 border-black px-3 rounded-full"
            onClick={() => openModal()}
          >
            +
          </button>
        </div>
        <div className="table-container">
          <table className="">
            <thead className=""></thead>
            <tbody>
              {posts.map((post) => (
                <tr className="">
                  <td className="break-normal">
                    <Post post={post} key={post.id} />
                  </td>
                  <td className="px-4">
                    <button
                      onClick={() => handleRemovePost(post.id)}
                      className=""
                    >
                      Remove
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
      <div hidden={!isOpen}>
        <PostModal
          isOpen={isOpen}
          setIsOpen={setIsOpen}
          fetchUserPosts={fetchUserPosts}
          setDoFetchPost={setDoFetchPost}
        />
      </div>
    </div>
  );
}
