import { useEffect, useState } from "react";
import { useLocation } from "react-router";
import UserService from "../service/UserService";
import PostService from "../service/PostService";
import LikeService from "../service/LikeService";
import Post from "../common/Post";
import ProfileSection from "../common/Sections/ProfileSection";

export default function UserPage() {
  const { state } = useLocation();
  const [profile, setProfile] = useState({});
  const [posts, setPosts] = useState([]);
  const [fullInfo, setFullInfo] = useState([]);
  const [loading, setLoading] = useState(true);
  const [isFiltered, setIsFiltered] = useState(false);

  useEffect(() => {
    fetchData();
  }, [state.username]);

  const fetchData = async () => {
    setLoading(true);
    try {
      const [profileData, postData, likeData] = await Promise.all([
        fetchUserProfile(),
        fetchUserPosts(),
        fetchProfileLikes(),
      ]);
      setProfile(profileData);
      console.log(profileData);
      configurePosts(postData.postList, likeData.postList);
    } catch (e) {
      throw e;
    } finally {
      setLoading(false);
    }
  };

  const configurePosts = (postsR, likesR) => {
    if (likesR) {
      const extendedPosts = postsR.map((post) => {
        const likeInfo = likesR.find((like) => like.id === post.id);
        return {
          ...post,
          isLiked: likeInfo ? true : false,
        };
      });
      setFullInfo(extendedPosts.sort((a, b) => b.id - a.id));
    } else {
      const extendedPosts = postsR.map((post) => {
        return {
          ...post,
          isLiked: false,
        };
      });
      setFullInfo(extendedPosts.sort((a, b) => b.id - a.id));
    }
  };

  const fetchUserProfile = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await UserService.getUserProfile(token, state.username);
      return response;
    } catch (error) {
      throw error;
    }
  };

  const fetchUserPosts = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await PostService.getUserPosts(token, state.username);
      return response;
    } catch (error) {
      throw error;
    }
  };

  const fetchProfileLikes = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = LikeService.getUserLikes(token);
      return response;
    } catch (error) {
      throw error;
    }
  };

  const handleLikeButton = async (id) => {
    try {
      const token = localStorage.getItem("token");
      const response = LikeService.likePost(token, id);
      console.log(token);
      console.log(`Liked post with id: ${id}`);
    } catch (error) {
      throw error;
    }
  };

  const handleDislikeButton = async (id) => {
    try {
      const token = localStorage.getItem("token");
      const response = LikeService.dislikePost(token, id);
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
          <ProfileSection
            token={localStorage.getItem("token")}
            profile={profile}
            isOwner={false}
          />
          {/* CENTRAL PANEL - POSTS*/}
          <div className="w-2/5 flex items-start justify-center border-x-2 border-gray-300 shadow-black shadow-2xl bg-gray-300">
            <div>
              <div className="my-4 m-auto h-1/2 border-b border-x py-2 rounded-lg border-gray-400 shadow-lg bg-white">
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
                            likePost={handleLikeButton}
                            dislikePost={handleDislikeButton}
                            user={profile.username}
                            postOwner={false}
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
