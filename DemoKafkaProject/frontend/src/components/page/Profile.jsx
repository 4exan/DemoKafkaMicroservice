import { useState } from "react";
import { useEffect } from "react";
import AuthService from "../service/AuthService";
import PostService from "../service/PostService";
import Post from "../common/Post";
import PostModal from "../common/PostModal";

export default function Profile() {
  const [profile, setProfile] = useState({});
  const [isOpen, setIsOpen] = useState(false);
  const [doFetchPost, setDoFetchPost] = useState(false);
  const [filteredPosts, setFilteredPosts] = useState([]);
  const [loading, setLoading] = useState(true);

  const [isFiltered, setIsFiltered] = useState(false);

  const [fullInfo, setFullInfo] = useState([]);

  // USE EFFECT HERE
  useEffect(() => {
    fetchData();
  }, []);

  function openModal() {
    setIsOpen(true);
  }

  function closeModal() {
    setIsOpen(false);
  }

  //Fetch all data in one method
  const fetchData = async () => {
    setLoading(true);
    try {
      const [profileData, postData, likeData] = await Promise.all([
        fetchUserProfile(),
        fetchUserPosts(),
        fetchProfileLikes(),
      ]);
      setProfile(profileData);
      configurePosts(postData.postList, likeData.postList);
    } catch (error) {
      throw error;
    } finally {
      setLoading(false);
    }
  };

  const configurePosts = (postsR, likesR) => {
    const extendedPosts = postsR.map((post) => {
      const likeInfo = likesR.find((like) => like.id === post.id);
      return {
        ...post,
        isLiked: likeInfo ? true : false,
      };
    });
    setFullInfo(extendedPosts);
  };

  const fetchUserProfile = () => {
    try {
      const token = localStorage.getItem("token");
      const response = AuthService.getMyProfile(token);
      return response;
    } catch (error) {
      throw error;
    }
  };

  const fetchUserPosts = () => {
    try {
      const token = localStorage.getItem("token");
      const response = PostService.getProfilePosts(token);
      return response;
    } catch (error) {
      throw error;
    }
  };

  const fetchProfileLikes = () => {
    try {
      const token = localStorage.getItem("token");
      const response = PostService.getUserLikes(token);
      return response;
    } catch (error) {
      throw error;
    }
  };

  const handleRemovePost = async (id) => {
    try {
      const confirmDelete = window.confirm(
        "Are you sure you want to delete this post?"
      );
      const token = localStorage.getItem("token");
      if (confirmDelete) {
        await PostService.deletePosts(token, id);
      }
      fetchUserPosts();
    } catch (error) {
      throw error;
    }
  };

  function filterPost() {
    if (isFiltered) {
      const filteredPosts = fullInfo.sort((a, b) => a.id - b.id);
      setFullInfo(filteredPosts);
      setIsFiltered(!isFiltered);
    } else {
      const filteredPosts = fullInfo.sort((a, b) => b.id - a.id);
      setFullInfo(filteredPosts);
      setIsFiltered(!isFiltered);
    }
  }

  if (!loading) {
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
          <div className="my-4 w-1/2 m-auto h-1/2 border-b-2 border-x-2 py-2 rounded-lg border-gray-400">
            <button
              className="mx-2 text-3xl font-semibold transition-all hover:bg-black hover:text-white border-2 border-black px-3 rounded-full"
              onClick={() => openModal()}
            >
              +
            </button>
            <button
              className="mx-2 text-m font-semibold transition-all hover:bg-black hover:text-white border-2 border-black px-3 rounded-full align-text-bottom"
              onClick={filterPost}
            >
              Filter
            </button>
          </div>
          <div className="table-container">
            <table className="">
              <thead className=""></thead>
              <tbody>
                {fullInfo.map((post) => (
                  <tr className="">
                    <td className="break-normal py-2">
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
  } else {
    return (
      <div className="loader absolute left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2"></div>
    );
  }
}
