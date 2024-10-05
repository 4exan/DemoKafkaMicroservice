import { useState } from "react";
import { useEffect } from "react";
import AuthService from "../service/AuthService";
import PostService from "../service/PostService";
import Post from "../common/Post";
import PostModal from "../common/PostModal";
import ProfileSection from "../common/ProfileSection";

export default function Profile() {
  const [profile, setProfile] = useState({});
  const [isOpen, setIsOpen] = useState(false);
  const [doFetchPost, setDoFetchPost] = useState(false);
  const [loading, setLoading] = useState(true);
  const [isFiltered, setIsFiltered] = useState(true);
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
      console.log(profileData);
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
    setFullInfo(extendedPosts.sort((a, b) => b.id - a.id));
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

  const filterPost = () => {
    if (isFiltered) {
      const filteredPosts = fullInfo.sort((a, b) => a.id - b.id);
      setFullInfo(filteredPosts);
      setIsFiltered(!isFiltered);
    } else {
      const filteredPosts = fullInfo.sort((a, b) => b.id - a.id);
      setFullInfo(filteredPosts);
      setIsFiltered(!isFiltered);
    }
  };

  if (!loading) {
    return (
      <>
        <div className="flex h-screen">
          {/* LEFT PANEL - PROFILE*/}
          <ProfileSection profile={profile} />
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
              <PostModal setIsOpen={setIsOpen} />
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
