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

  const handleLikeButton = async (id) => {
    try {
      const token = localStorage.getItem("token");
      const response = PostService.likePost(token, id);
      console.log(token);
      console.log(`Liked post with id: ${id}`);
    } catch (error) {
      throw error;
    }
  };

  const handleDislikeButton = async (id) => {
    try {
      const token = localStorage.getItem("token");
      const response = PostService.dislikePost(token, id);
      console.log(token);
      console.log(`Disiked post with id: ${id}`);
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
      <>
        <div className="flex h-screen">
          {/* LEFT PANEL - PROFILE*/}
          <div className="w-1/3 flex flex-col items-start justify-start  p-4">
            <img
              src="https://via.placeholder.com/150"
              className="w-full h-auto max-w-xs mb-4 rounded-full"
              alt="sample"
            />
            <div className="border-b-2 border-gray-400 w-1/2">
              <p className="font-semibold text-3xl py-2">
                {profile.firstName} {profile.lastName}
              </p>
            </div>
            <div className="">
              <p className="font-semibold text-xl font-montserrat pt-2">
                Username:{" "}
                <span className="font-normal text-xl mx-2 font-montserrat">
                  {profile.username}
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
          {/* CENTRAL PANEL - POSTS*/}
          <div className="w-2/5 flex items-start justify-center border-x-2 border-gray-300 shadow-black shadow-2xl bg-gray-300">
            <div>
              <div className="my-4 m-auto h-1/2 border-b border-x py-2 rounded-lg border-gray-400 shadow-lg bg-white">
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
              {/* table-container */}
              <div className="flex-grow overflow-y-scroll max-h-[calc(100vh-200px)] max-w-[calc(100vh-350px)] table-scroll">
                <table className="">
                  <thead>
                    <th>~ Top ~</th>
                  </thead>
                  <tbody>
                    {fullInfo.map((post, key) => (
                      <tr className="" key={key}>
                        <td className="break-normal py-2">
                          <Post
                            post={post}
                            key={post.id}
                            removePost={handleRemovePost}
                            likePost={handleLikeButton}
                            dislikePost={handleDislikeButton}
                            user={profile.username}
                            postOwner={true}
                          />
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
          {/* RIGHT PANEL - OTHER*/}
          <div className="w-1/3 flex items-center justify-center">
            <div hidden={!isOpen}>
              <PostModal
                isOpen={isOpen}
                setIsOpen={setIsOpen}
                fetchUserPosts={fetchUserPosts}
                setDoFetchPost={setDoFetchPost}
              />
            </div>
          </div>
        </div>
        {/* ----------------- */}
      </>
    );
  } else {
    return (
      <div className="loader absolute left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2"></div>
    );
  }
}
